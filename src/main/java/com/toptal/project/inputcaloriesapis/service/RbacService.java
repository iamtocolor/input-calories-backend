package com.toptal.project.inputcaloriesapis.service;

import com.toptal.project.inputcaloriesapis.dto.request.LoginRequest;
import com.toptal.project.inputcaloriesapis.dto.response.LoginResponse;
import com.toptal.project.inputcaloriesapis.entity.UserEntity;
import com.toptal.project.inputcaloriesapis.exception.ICErrorMessage;
import com.toptal.project.inputcaloriesapis.exception.InputCalorieException;
import com.toptal.project.inputcaloriesapis.rbac.Permission;
import com.toptal.project.inputcaloriesapis.rbac.Roles;
import com.toptal.project.inputcaloriesapis.util.JwtTokenUtil;
import com.toptal.project.inputcaloriesapis.validator.UserValidator;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

import static com.toptal.project.inputcaloriesapis.rbac.Permission.*;

@Service
public class RbacService {

    @Getter
    private Map<Roles, List<Permission>> rolesToPermissions = new HashMap<>();

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;



    @PostConstruct
    public void init() {

        List<Permission> regularUserPermission = new ArrayList<>();
        regularUserPermission.add(CREATE_FOOD);
        regularUserPermission.add(READ_FOOD);
        regularUserPermission.add(UPDATE_FOOD);
        regularUserPermission.add(DELETE_FOOD);

        List<Permission> userManagerPermission = new ArrayList<>();
        userManagerPermission.add(CREATE_USER);
        userManagerPermission.add(READ_USER);
        userManagerPermission.add(UPDATE_USER);
        userManagerPermission.add(DELETE_USER);

        List<Permission> adminPermission = new ArrayList<>();
        adminPermission.addAll(regularUserPermission);
        adminPermission.addAll(userManagerPermission);
    }


    public LoginResponse login(LoginRequest loginRequest) {
        String emailId = loginRequest.getEmailId();
        UserEntity userEntity = userValidator.getValidatedUserByEmailId(emailId);
        String passWord = loginRequest.getPassword();
        if (userEntity.getPassWord().equals(passWord)) {
            String token = jwtTokenUtil.generateToken(userEntity);
            return LoginResponse.builder()
                    .emailId(loginRequest.getEmailId())
                    .token(token)
                    .build();
        } else {
            throw new InputCalorieException(
                    HttpStatus.UNAUTHORIZED,
                    Collections.singletonList(ICErrorMessage.USER_LOGIN_FAILED),
                    Collections.singletonList(loginRequest.getEmailId()));
        }
    }
}
