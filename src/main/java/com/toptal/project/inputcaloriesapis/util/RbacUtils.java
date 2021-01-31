package com.toptal.project.inputcaloriesapis.util;

import com.toptal.project.inputcaloriesapis.dto.request.RbacRole;
import com.toptal.project.inputcaloriesapis.entity.RbacRoleEntity;
import com.toptal.project.inputcaloriesapis.entity.UserEntity;
import com.toptal.project.inputcaloriesapis.exception.ICErrorMessage;
import com.toptal.project.inputcaloriesapis.exception.InputCalorieException;
import com.toptal.project.inputcaloriesapis.rbac.Roles;
import com.toptal.project.inputcaloriesapis.service.UserService;
import com.toptal.project.inputcaloriesapis.validator.UserValidator;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class RbacUtils {

    @Value("${admin.create.key}")
    @Getter
    private String adminCreateKey;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;


    public void validateForAdminCreation(String key) {
        if (!adminCreateKey.equals(key)) {
            throw new InputCalorieException(
                    HttpStatus.UNAUTHORIZED,
                    Collections.singletonList(ICErrorMessage.INVALID_ADMIN_CREATION_KEY),
                    Collections.singletonList(key)
            );
        }
    }

    public void validateCrudOnUser(String token) {
        List<Roles> eligibleRoles = new ArrayList<>();
        eligibleRoles.add(Roles.ADMIN);
        eligibleRoles.add(Roles.USER_MANAGER);

        boolean hasRole = validateTokenHas(token, eligibleRoles);
        if (!hasRole) {
            throw new InputCalorieException(
                    HttpStatus.UNAUTHORIZED,
                    Collections.singletonList(ICErrorMessage.UNAUTHORIZED_ACCESS)
            );
        }
    }

    public void validateCrudOnUserRecord(String token, String targetId) {
        boolean hasRole = validateTokenForScope(token, targetId);
        if (!hasRole) {
            throw new InputCalorieException(
                    HttpStatus.UNAUTHORIZED,
                    Collections.singletonList(ICErrorMessage.UNAUTHORIZED_ACCESS)
            );
        }
    }

    public void validateForAdminRole(String token) {
        List<Roles> eligibleRoles = new ArrayList<>();
        eligibleRoles.add(Roles.ADMIN);

        boolean hasRole = validateTokenHas(token, eligibleRoles);
        if (!hasRole) {
            throw new InputCalorieException(
                    HttpStatus.UNAUTHORIZED,
                    Collections.singletonList(ICErrorMessage.UNAUTHORIZED_ACCESS)
            );
        }
    }

    private boolean validateTokenForScope(String token, String targetId) {
        UserEntity userEntity = getUserForToken(token);
        boolean hasRole = false;

        for (RbacRoleEntity rbacRoleEntity : userEntity.getRbacRoleEntities()) {
            if (rbacRoleEntity.getRole().equals(Roles.ADMIN)) {
                hasRole = true;
                break;
            }
            if (rbacRoleEntity.getRole().equals(Roles.REGULAR_USER)) {
                if (targetId.equals(rbacRoleEntity.getScopeId())) {
                    hasRole = true;
                    break;
                }
            }
        }
        return hasRole;
    }

    private boolean validateTokenHas(String token, List<Roles> roles) {

        UserEntity userEntity = getUserForToken(token);
        Set<RbacRoleEntity> rbacRoleEntities = userEntity.getRbacRoleEntities();

        boolean hasRole = false;

        for (RbacRoleEntity rbacRoleEntity : rbacRoleEntities) {
            if (roles.contains(rbacRoleEntity.getRole())) {
                hasRole = true;
            }
        }
        return hasRole;
    }

    private UserEntity getUserForToken(String token) {
        token = token.split(" ")[1];
        String userId = jwtTokenUtil.getUserIdFromToken(token);

        return userValidator.getValidatedUserById(userId);
    }
}
