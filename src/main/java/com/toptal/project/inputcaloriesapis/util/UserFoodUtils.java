package com.toptal.project.inputcaloriesapis.util;

import com.toptal.project.inputcaloriesapis.entity.FoodEntity;
import com.toptal.project.inputcaloriesapis.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class UserFoodUtils {


    public static Boolean withinLimit(UserEntity userEntity, String date) {
        return leftForDate(userEntity, date) >= 0;
    }
    public static Integer leftForDate(UserEntity userEntity, String date) {
        return userEntity.getDailyLimit() - getAte(userEntity, date);
    }

    public static Integer getAte(UserEntity userEntity, String date) {
        int count = 0;
        List<FoodEntity> foodForDate = getAteFoodEntity(userEntity, date);
        for (FoodEntity foodEntity : foodForDate) {
            count += foodEntity.getCalories();
        }
        return count;
    }

    public static List<FoodEntity> getAteFoodEntity(UserEntity userEntity, String date) {
        List<FoodEntity> foodForDate = new ArrayList<>();
        for (FoodEntity foodEntity : userEntity.getFoodEntities()) {
            if (foodEntity.getDate().equals(date)) {
                foodForDate.add(foodEntity);
            }
        }
        return foodForDate;
    }

}
