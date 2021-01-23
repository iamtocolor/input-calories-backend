package com.toptal.project.inputcaloriesapis.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InputCalorieException.class)
    public ResponseEntity<List<APIError>> handleInputCalorieException(InputCalorieException ex) {

        List<APIError> apiErrors = new ArrayList<>();

        String parameters = ex.getParams() != null
                ?
                ex.getParams().stream().map(it -> it.toString()).collect(Collectors.joining(","))
                :
                "";
        ex.getErrorMessages().stream().forEach(new Consumer<ICErrorMessage>() {
            @Override
            public void accept(ICErrorMessage e) {
                apiErrors.add(new APIError(e.getCode(), e.getMessage() + ", parameters : " + parameters));
            }
        });

        return new ResponseEntity<>(apiErrors, ex.getStatus());
    }
}
