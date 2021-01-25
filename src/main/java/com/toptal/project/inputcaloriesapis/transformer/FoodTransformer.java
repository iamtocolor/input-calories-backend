package com.toptal.project.inputcaloriesapis.transformer;

import com.toptal.project.inputcaloriesapis.dto.FoodDto;
import com.toptal.project.inputcaloriesapis.entity.FoodEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FoodTransformer {

    public FoodEntity transformDtoToEntity(FoodDto foodDto) {
        return FoodEntity.builder()
                .foodId(UUID.randomUUID())
                .date(foodDto.getDate())
                .time(foodDto.getTime())
                .food(foodDto.getFood())
                .calories(foodDto.getCalories())
                .withinLimit(foodDto.getWithinLimit())
                .build();
    }

    public FoodDto transformEntityToDto(FoodEntity foodEntity) {
        return FoodDto.builder()
                .foodId(foodEntity.getFoodId().toString())
                .date(foodEntity.getDate())
                .time(foodEntity.getTime())
                .food(foodEntity.getFood())
                .calories(foodEntity.getCalories())
                .withinLimit(foodEntity.getWithinLimit())
                .build();
    }

    public List<FoodDto> transformEntityToDto(List<FoodEntity> foodEntityList) {
        List<FoodDto> foodDtos = new ArrayList<>();
        for (FoodEntity foodEntity : foodEntityList) {
            foodDtos.add(transformEntityToDto(foodEntity));
        }
        return foodDtos;
    }
}
