package com.toptal.project.inputcaloriesapis.service;

import com.toptal.project.inputcaloriesapis.dao.FoodRepo;
import com.toptal.project.inputcaloriesapis.dao.UserRepo;
import com.toptal.project.inputcaloriesapis.dto.FoodDto;
import com.toptal.project.inputcaloriesapis.dto.request.SearchRequest;
import com.toptal.project.inputcaloriesapis.dto.request.UserRequest;
import com.toptal.project.inputcaloriesapis.dto.response.PagedResponse;
import com.toptal.project.inputcaloriesapis.dto.response.UserResponse;
import com.toptal.project.inputcaloriesapis.entity.FoodEntity;
import com.toptal.project.inputcaloriesapis.entity.UserEntity;
import com.toptal.project.inputcaloriesapis.filter.FoodSearchSpecification;
import com.toptal.project.inputcaloriesapis.transformer.FoodTransformer;
import com.toptal.project.inputcaloriesapis.transformer.UserTransformer;
import com.toptal.project.inputcaloriesapis.util.TypeConverterRegistry;
import com.toptal.project.inputcaloriesapis.util.UserFoodUtils;
import com.toptal.project.inputcaloriesapis.validator.FoodValidator;
import com.toptal.project.inputcaloriesapis.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private FoodRepo foodRepo;

    @Autowired
    private UserTransformer userTransformer;

    @Autowired
    private FoodTransformer foodTransformer;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private FoodValidator foodValidator;

    @Autowired
    private TypeConverterRegistry typeConverterRegistry;

    public UserResponse createUser(UserRequest userRequest) {
        userValidator.validateUserCreateRequest(userRequest);

        UserEntity toCreateEntity = userTransformer.transformDtoToEntity(userRequest);

        UserEntity createdEntity = userRepo.save(toCreateEntity);

        return userTransformer.transformEntityToDto(createdEntity);
    }

    public PagedResponse<UserResponse> getAllUsers(Integer page, Integer size) {
        List<UserResponse> allUsersDto = new ArrayList<>();
        Page<UserEntity> allUsersEntity = userRepo.findAll(PageRequest.of(page, size));

        for (UserEntity userEntity : allUsersEntity) {
            allUsersDto.add(userTransformer.transformEntityToDto(userEntity));
        }
        return new PagedResponse<UserResponse>(allUsersEntity.getTotalPages(), allUsersEntity.getTotalElements(), page, size, allUsersDto);
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
        toSave.setUserId(UUID.fromString(userId));

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

    public PagedResponse<FoodDto> getAllFoodForUser(String userId, Integer page, Integer size) {
        UserEntity userEntity = userValidator.getValidatedUserById(userId);

        Page<FoodEntity> response = foodRepo.findAllByUserId(UUID.fromString(userId), PageRequest.of(page, size));
        return new PagedResponse<FoodDto>(
                response.getTotalPages(),
                response.getTotalElements(),
                page,
                size,
                foodTransformer.transformEntityToDto(response.getContent())
        );
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




    //SEARCH

    public PagedResponse<FoodDto> searchUserDate(SearchRequest searchRequest, Integer page, Integer size) {
        Page<FoodEntity> filteredFoods = foodRepo.findAll(new FoodSearchSpecification(searchRequest, typeConverterRegistry), PageRequest.of(page, size));

        List<FoodDto> filterDtoList = foodTransformer.transformEntityToDto(filteredFoods.getContent());
        return PagedResponse.<FoodDto>builder()
                .data(filterDtoList)
                .pageNo(filteredFoods.getNumber())
                .pageSize(size)
                .totalCount(filteredFoods.getTotalElements())
                .totalPages(filteredFoods.getTotalPages())
                .build();
    }
}
