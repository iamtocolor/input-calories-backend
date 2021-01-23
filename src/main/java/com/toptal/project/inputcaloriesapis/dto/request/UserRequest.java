package com.toptal.project.inputcaloriesapis.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @JsonProperty(value="userId")
    private String userId;

    @JsonProperty(value="email")
    private String email;

    @JsonProperty(value="password")
    private String passWord;

    @JsonProperty(value="dailyLimit")
    private Integer dailyLimit;
}
