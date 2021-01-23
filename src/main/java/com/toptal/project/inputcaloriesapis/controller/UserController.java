package com.toptal.project.inputcaloriesapis.controller;

import com.toptal.project.inputcaloriesapis.dto.request.UserRequest;
import com.toptal.project.inputcaloriesapis.dto.response.UserResponse;
import com.toptal.project.inputcaloriesapis.exception.InputCalorieException;
import com.toptal.project.inputcaloriesapis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

}
