package com.toptal.project.inputcaloriesapis.controller;

import com.toptal.project.inputcaloriesapis.dto.request.UserRequest;
import com.toptal.project.inputcaloriesapis.dto.response.UserResponse;
import com.toptal.project.inputcaloriesapis.exception.InputCalorieException;
import com.toptal.project.inputcaloriesapis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getname")
    public String getName() {
        return "Satish Chandra Gupta";
    }

    @PostMapping("user")
    public UserResponse createUser(@RequestBody UserRequest request) throws InputCalorieException {
        return userService.createUser(request);
    }

    @GetMapping("user/{id}")
    public UserResponse getUser(@PathVariable("id") String userId) throws InputCalorieException {
        return userService.getUserById(userId);
    }

    @PutMapping("user/{id}")
    public UserResponse updateUser(@PathVariable("id") String userId, @RequestBody UserRequest userRequest) throws InputCalorieException {
        return userService.updateUser(userId, userRequest);
    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") String userId) throws InputCalorieException {
        return new ResponseEntity<String>("Successfully Deleted", HttpStatus.ACCEPTED);
    }
}
