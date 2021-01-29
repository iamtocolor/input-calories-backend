package com.toptal.project.inputcaloriesapis.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleSearchRequest extends SearchRequest {
    private SearchField operand1;

    private String operand2;
}