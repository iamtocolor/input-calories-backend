package com.toptal.project.inputcaloriesapis.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplexSearchRequest extends SearchRequest {
    private SearchRequest operand1;

    private SearchRequest operand2;
}