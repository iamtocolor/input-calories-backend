package com.toptal.project.inputcaloriesapis.validator;

import com.toptal.project.inputcaloriesapis.dao.UserRepo;
import com.toptal.project.inputcaloriesapis.dto.request.UserRequest;
import com.toptal.project.inputcaloriesapis.entity.FoodEntity;
import com.toptal.project.inputcaloriesapis.entity.UserEntity;
import com.toptal.project.inputcaloriesapis.exception.ICErrorMessage;
import com.toptal.project.inputcaloriesapis.exception.InputCalorieException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserValidator {

    @Autowired
    private UserRepo userRepo;

    public void validateUserCreateRequest(UserRequest userRequest) {
        if (userRepo.findByEmail(userRequest.getEmail()) != null) {
            throw new InputCalorieException(HttpStatus.CONFLICT,
                    Collections.singletonList(ICErrorMessage.USER_ALREADY_EXISTS),
                    Collections.singletonList(userRequest.getEmail()));
        }
    }

    public UserEntity getValidatedUserById(String userId) {
        UUID givenUserId = null;
        try {
            givenUserId = UUID.fromString(userId);
        } catch (IllegalArgumentException e) {
            throw new InputCalorieException(HttpStatus.BAD_REQUEST,
                    Collections.singletonList(ICErrorMessage.INVALID_USER_ID),
                    Collections.singletonList(userId));
        }

        UserEntity fetchUserData = userRepo.findByUserId(givenUserId);
        if (fetchUserData == null) {
            throw new InputCalorieException(HttpStatus.BAD_REQUEST,
                    Collections.singletonList(ICErrorMessage.USER_NOT_FOUND),
                    Collections.singletonList(userId));
        }
        return fetchUserData;
    }

    public void validateUserUpdateRequest(UserRequest userRequest) {
        // If we support update of Email
        // validateUserCreateRequest(userRequest);
    }
}
