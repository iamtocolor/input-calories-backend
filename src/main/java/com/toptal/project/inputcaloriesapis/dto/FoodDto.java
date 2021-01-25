package com.toptal.project.inputcaloriesapis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodDto {

    private String foodId;

    @JsonProperty("date")
    private String date;

    @JsonProperty("time")
    private String time;

    @JsonProperty(value = "food", required = true)
    private String food;

    @JsonProperty("calories")
    private Integer calories;

    @JsonProperty("withinLimit")
    private Boolean withinLimit;
}

