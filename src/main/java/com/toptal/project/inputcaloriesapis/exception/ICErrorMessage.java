package com.toptal.project.inputcaloriesapis.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

public enum ICErrorMessage {

    //USER RELATED
    USER_ALREADY_EXISTS(1000, "User for given email already Exists"),
    INVALID_USER_ID(1001, "Given user Id is Invalid"),
    USER_NOT_FOUND(1002, "User Not Found"),

    //FOOD RELATED
    FOOD_NOT_FOUND(2002, "Food Not Found");


    private int code;
    private String message;

    ICErrorMessage(int code, java.lang.String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {

        return code;
    }

    public void setCode(int code) {

        this.code = code;
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {

        this.message = message;
    }

}
