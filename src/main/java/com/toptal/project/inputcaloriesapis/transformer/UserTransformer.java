package com.toptal.project.inputcaloriesapis.transformer;

import com.toptal.project.inputcaloriesapis.dto.FoodDto;
import com.toptal.project.inputcaloriesapis.dto.request.RbacRole;
import com.toptal.project.inputcaloriesapis.dto.request.UserRequest;
import com.toptal.project.inputcaloriesapis.dto.response.UserResponse;
import com.toptal.project.inputcaloriesapis.entity.FoodEntity;
import com.toptal.project.inputcaloriesapis.entity.RbacRoleEntity;
import com.toptal.project.inputcaloriesapis.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserTransformer {

    @Autowired
    private FoodTransformer foodTransformer;

    @Autowired
    private RbacRoleTransformer rbacRoleTransformer;


    public UserEntity transformDtoToEntity(UserRequest userRequest) {
        return UserEntity.builder()
                .userId(UUID.randomUUID())
                .email(userRequest.getEmail())
                .passWord(userRequest.getPassWord())
                .dailyLimit(userRequest.getDailyLimit())
                .foodEntities(new ArrayList<>())
                .rbacRoleEntities(rbacRoleTransformer.transformDtoToEntity(userRequest.getRbacRoleList()))
                .build();
    }

    public UserResponse transformEntityToDto(UserEntity userEntity) {
        List<FoodEntity> foodEntities = userEntity.getFoodEntities();
        List<FoodDto> foodDtoList = new ArrayList<>();

        for (FoodEntity foodEntity : foodEntities) {
            foodDtoList.add(foodTransformer.transformEntityToDto(foodEntity));
        }

        Set<RbacRoleEntity> roleEntities = userEntity.getRbacRoleEntities();
        Set<RbacRole> rbacRoles = rbacRoleTransformer.transformEntityToDto(roleEntities);

        return UserResponse.builder()
                .userId(userEntity.getUserId())
                .email(userEntity.getEmail())
                .dailyLimit(userEntity.getDailyLimit())
                .rbacRoles(rbacRoles)
                .build();
    }
}
