package com.toptal.project.inputcaloriesapis.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Data
public class InputCalorieException extends RuntimeException {

    HttpStatus status;
    List<Object> params;
    List<ICErrorMessage> errorMessages;

    public InputCalorieException(HttpStatus status) {
        this.status = status;
        this.params = new ArrayList<>();
    }

    public InputCalorieException(HttpStatus status, List<ICErrorMessage> errorMessages) {
        this.errorMessages = errorMessages;
        this.status = status;
        this.params = new ArrayList<>();
    }

    public InputCalorieException(HttpStatus status, List<ICErrorMessage> errorMessages, List<Object> params) {
        this.errorMessages = errorMessages;
        this.status = status;
        this.params = params;
    }
}
