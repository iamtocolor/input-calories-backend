package com.toptal.project.inputcaloriesapis.dto.response;

import com.toptal.project.inputcaloriesapis.dto.FoodDto;
import com.toptal.project.inputcaloriesapis.dto.request.RbacRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private UUID userId;

    private String email;

    private Integer dailyLimit;

    private Set<RbacRole> rbacRoles;

}
