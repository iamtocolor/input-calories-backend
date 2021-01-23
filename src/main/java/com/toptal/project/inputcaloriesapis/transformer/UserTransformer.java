package com.toptal.project.inputcaloriesapis.transformer;

import com.toptal.project.inputcaloriesapis.dto.request.UserRequest;
import com.toptal.project.inputcaloriesapis.dto.response.UserResponse;
import com.toptal.project.inputcaloriesapis.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserTransformer {

    public UserEntity transformDtoToEntity(UserRequest userRequest) {
        return UserEntity.builder()
                .userId(UUID.randomUUID())
                .email(userRequest.getEmail())
                .passWord(userRequest.getPassWord())
                .dailyLimit(userRequest.getDailyLimit())
                .build();
    }

    public UserResponse transformEntityToDto(UserEntity userEntity) {
        return UserResponse.builder()
                .userId(userEntity.getUserId())
                .email(userEntity.getEmail())
                .dailyLimit(userEntity.getDailyLimit())
                .build();
    }
}
