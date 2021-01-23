package com.toptal.project.inputcaloriesapis.validator;

import com.toptal.project.inputcaloriesapis.dao.UserRepo;
import com.toptal.project.inputcaloriesapis.dto.request.UserRequest;
import com.toptal.project.inputcaloriesapis.exception.ICErrorMessage;
import com.toptal.project.inputcaloriesapis.exception.InputCalorieException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;

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
}
