package com.toptal.project.inputcaloriesapis.dto.response;

import com.toptal.project.inputcaloriesapis.dto.FoodDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private UUID userId;

    private String email;

    private Integer dailyLimit;

}
