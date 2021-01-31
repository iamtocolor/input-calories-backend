package com.toptal.project.inputcaloriesapis.dto.request;

import com.toptal.project.inputcaloriesapis.rbac.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RbacRole {

    private Roles role;

    private String scopeId;
}
