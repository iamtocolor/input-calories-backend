package com.toptal.project.inputcaloriesapis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodDto {

    private String foodId;

    @JsonProperty(value = "date", required = true)
    private String date;

    @JsonProperty(value = "time", required = true)
    private String time;

    @JsonProperty(value = "food", required = true)
    private String food;

    @JsonProperty("calories")
    private Integer calories;

    @JsonProperty("withinLimit")
    private Boolean withinLimit;
}

