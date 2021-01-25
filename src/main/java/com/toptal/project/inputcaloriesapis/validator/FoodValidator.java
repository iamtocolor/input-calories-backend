package com.toptal.project.inputcaloriesapis.validator;

import com.toptal.project.inputcaloriesapis.entity.FoodEntity;
import com.toptal.project.inputcaloriesapis.entity.UserEntity;
import com.toptal.project.inputcaloriesapis.exception.ICErrorMessage;
import com.toptal.project.inputcaloriesapis.exception.InputCalorieException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class FoodValidator {

    @Autowired
    private UserValidator userValidator;

    public FoodEntity getValidatedFoodIdForUser(UserEntity userEntity, String foodId) {
        for (FoodEntity foodEntity : userEntity.getFoodEntities()) {
            if (foodEntity.getFoodId().toString().equals(foodId)) {
                return foodEntity;
            }
        }
        throw new InputCalorieException(HttpStatus.BAD_REQUEST,
                Collections.singletonList(ICErrorMessage.FOOD_NOT_FOUND),
                Collections.singletonList(foodId));
    }

    public FoodEntity getValidatedFoodIdForUser(String userId, String foodId) {
        return getValidatedFoodIdForUser(userValidator.getValidatedUserById(userId), foodId);
    }
}
