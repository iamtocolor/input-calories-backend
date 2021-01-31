package com.toptal.project.inputcaloriesapis.transformer;

import com.toptal.project.inputcaloriesapis.dto.request.RbacRole;
import com.toptal.project.inputcaloriesapis.entity.RbacRoleEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RbacRoleTransformer {

    public RbacRoleEntity transformDtoToEntity(RbacRole rbacRole) {
        return RbacRoleEntity.builder()
                .role(rbacRole.getRole())
                .scopeId(rbacRole.getScopeId())
                .build();
    }

    public Set<RbacRoleEntity> transformDtoToEntity(Set<RbacRole> rbacRoles) {
        Set<RbacRoleEntity> rbacRoleEntities = new HashSet<>();

        for (RbacRole rbacRole : rbacRoles) {
            rbacRoleEntities.add(transformDtoToEntity(rbacRole));
        }
        return rbacRoleEntities;
    }

    public RbacRole transformEntityToDto(RbacRoleEntity rbacRoleEntity) {
        return RbacRole.builder()
                .role(rbacRoleEntity.getRole())
                .scopeId(rbacRoleEntity.getScopeId())
                .build();
    }

    public Set<RbacRole> transformEntityToDto(Set<RbacRoleEntity> rbacRoleEntities) {
        Set<RbacRole> rbacRoles = new HashSet<>();
        for (RbacRoleEntity rbacRoleEntity : rbacRoleEntities) {
            rbacRoles.add(transformEntityToDto(rbacRoleEntity));
        }
        return rbacRoles;
    }
}
