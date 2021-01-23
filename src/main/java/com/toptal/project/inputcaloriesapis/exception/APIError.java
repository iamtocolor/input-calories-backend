package com.toptal.project.inputcaloriesapis.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class APIError {
    private Integer errorCode;
    private String errorMessage;
}
