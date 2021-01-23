package com.toptal.project.inputcaloriesapis.service;

import com.toptal.project.inputcaloriesapis.dao.UserRepo;
import com.toptal.project.inputcaloriesapis.dto.request.UserRequest;
import com.toptal.project.inputcaloriesapis.dto.response.UserResponse;
import com.toptal.project.inputcaloriesapis.entity.UserEntity;
import com.toptal.project.inputcaloriesapis.transformer.UserTransformer;
import com.toptal.project.inputcaloriesapis.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserTransformer userTransformer;

    @Autowired
    private UserValidator userValidator;

    public UserResponse createUser(UserRequest userRequest) {
        userValidator.validateUserCreateRequest(userRequest);

        UserEntity toCreateEntity = userTransformer.transformDtoToEntity(userRequest);

        UserEntity createdEntity = userRepo.save(toCreateEntity);

        return userTransformer.transformEntityToDto(createdEntity);
    }

    public UserResponse getUserById(String userId) {
        return userTransformer.transformEntityToDto(userValidator.getValidatedUserById(userId));
    }

    public UserResponse updateUser(String userId, UserRequest userRequest) {
        UserEntity fetchUserData = userValidator.getValidatedUserById(userId);
        userValidator.validateUserUpdateRequest(userRequest);

        // If we support update of Email
        // fetchUserData.setEmail(userRequest.getEmail());
        fetchUserData.setDailyLimit(userRequest.getDailyLimit());
        fetchUserData.setPassWord(userRequest.getPassWord());

        UserEntity updatedUserData = userRepo.save(fetchUserData);
        return userTransformer.transformEntityToDto(updatedUserData);
    }

    public void deleteUser(String userId) {
        userRepo.delete(userValidator.getValidatedUserById(userId));
    }
}
