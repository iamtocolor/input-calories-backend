package com.toptal.project.inputcaloriesapis.dto.request;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum SearchField {

    DATE("date"),
    NUMBER_OF_CALORIES("calories"),
    TIME("time"),
    USER_ID("userId");

    @Getter
    private String tableKey;

    private SearchField(String tableKey) {
        this.tableKey = tableKey;
    }
}

