package com.toptal.project.inputcaloriesapis.service;

import com.toptal.project.inputcaloriesapis.dao.UserRepo;
import com.toptal.project.inputcaloriesapis.dto.FoodDto;
import com.toptal.project.inputcaloriesapis.dto.request.UserRequest;
import com.toptal.project.inputcaloriesapis.dto.response.UserResponse;
import com.toptal.project.inputcaloriesapis.entity.FoodEntity;
import com.toptal.project.inputcaloriesapis.entity.UserEntity;
import com.toptal.project.inputcaloriesapis.transformer.FoodTransformer;
import com.toptal.project.inputcaloriesapis.transformer.UserTransformer;
import com.toptal.project.inputcaloriesapis.util.UserFoodUtils;
import com.toptal.project.inputcaloriesapis.validator.FoodValidator;
import com.toptal.project.inputcaloriesapis.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserTransformer userTransformer;

    @Autowired
    private FoodTransformer foodTransformer;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private FoodValidator foodValidator;

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

    public FoodDto addFoodForUser(String userId, FoodDto foodDto) {
        UserEntity userEntity = userValidator.getValidatedUserById(userId);

        if (userEntity.getFoodEntities() == null) userEntity.setFoodEntities(new ArrayList<>());

        FoodEntity toSave = foodTransformer.transformDtoToEntity(foodDto);

        userEntity.getFoodEntities().add(toSave);

        boolean withinLimit = UserFoodUtils.withinLimit(userEntity, foodDto.getDate());

        for (FoodEntity foodEntity : userEntity.getFoodEntities()) {
            if (foodEntity.getDate().equals(foodDto.getDate())) {
                foodEntity.setWithinLimit(withinLimit);

            }
        }

        UserEntity updatedUserData = userRepo.save(userEntity);
        return foodTransformer.transformEntityToDto(toSave);
    }

    public List<FoodDto> getAllFoodForUser(String userId) {
        UserEntity userEntity = userValidator.getValidatedUserById(userId);
        return foodTransformer.transformEntityToDto(userEntity.getFoodEntities());
    }

    public FoodDto updateFoodForUser(String userId, String foodId, FoodDto foodDto) {
        UserEntity userEntity = userValidator.getValidatedUserById(userId);

        FoodEntity fetchedFood = foodValidator.getValidatedFoodIdForUser(userEntity, foodId);

        fetchedFood.setCalories(foodDto.getCalories());
        fetchedFood.setDate(foodDto.getDate());
        fetchedFood.setTime(foodDto.getTime());
        fetchedFood.setFood(foodDto.getFood());

        boolean withinLimit = UserFoodUtils.withinLimit(userEntity, foodDto.getDate());

        for (FoodEntity foodEntity : userEntity.getFoodEntities()) {
            if (foodEntity.getDate().equals(foodDto.getDate())) {
                foodEntity.setWithinLimit(withinLimit);
            }
        }

        UserEntity updatedUserData = userRepo.save(userEntity);
        return foodTransformer.transformEntityToDto(fetchedFood);
    }

    public void deleteFoodForUser(String userId, String foodId) {
        UserEntity validatedUserById = userValidator.getValidatedUserById(userId);
        FoodEntity fetchedFood = foodValidator.getValidatedFoodIdForUser(validatedUserById, foodId);

        String itsDate = fetchedFood.getDate();

        validatedUserById.getFoodEntities().remove(fetchedFood);

        boolean withinLimit = UserFoodUtils.withinLimit(validatedUserById, itsDate);

        for (FoodEntity foodEntity : validatedUserById.getFoodEntities()) {
            if (foodEntity.getDate().equals(itsDate)) {
                foodEntity.setWithinLimit(withinLimit);
            }
        }

        userRepo.save(validatedUserById);
    }
}
