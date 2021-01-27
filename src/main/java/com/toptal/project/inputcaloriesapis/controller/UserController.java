package com.toptal.project.inputcaloriesapis.controller;

import com.toptal.project.inputcaloriesapis.dto.FoodDto;
import com.toptal.project.inputcaloriesapis.dto.request.UserRequest;
import com.toptal.project.inputcaloriesapis.dto.response.PagedResponse;
import com.toptal.project.inputcaloriesapis.dto.response.UserResponse;
import com.toptal.project.inputcaloriesapis.exception.InputCalorieException;
import com.toptal.project.inputcaloriesapis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/user")
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getname")
    public String getName() {
        return "Satish Chandra Gupta";
    }

    @PostMapping("")
    public UserResponse createUser(@RequestBody UserRequest request) throws InputCalorieException {
        return userService.createUser(request);
    }

    @GetMapping("")
    private PagedResponse<UserResponse> getAllUsers(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ) throws InputCalorieException {
        return userService.getAllUsers(page, size);
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable("id") String userId) throws InputCalorieException {
        return userService.getUserById(userId);
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable("id") String userId, @RequestBody UserRequest userRequest) throws InputCalorieException {
        return userService.updateUser(userId, userRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") String userId) throws InputCalorieException {
        userService.deleteUser(userId);
        return new ResponseEntity<String>("Successfully Deleted", HttpStatus.ACCEPTED);
    }

    // STARTING OF FOOD
    @PostMapping("/{id}/food")
    public FoodDto addFoodForUser(@PathVariable("id") String userId, @RequestBody FoodDto foodDto) throws InputCalorieException {
        return userService.addFoodForUser(userId, foodDto);
    }

    // Support for Paging & Filter
    @GetMapping("/{id}/food")
    public PagedResponse<FoodDto> getAllFoodForUser(@PathVariable("id") String userId,
                                                    @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                    @RequestParam(value = "size", defaultValue = "10") Integer size) throws InputCalorieException {
        return userService.getAllFoodForUser(userId, page, size);
    }

    @PutMapping("/{id}/food/{foodId}")
    public FoodDto updateFoodForUser(@PathVariable("id") String userId, @PathVariable("foodId") String foodId, @RequestBody FoodDto foodDto) throws InputCalorieException {
        return userService.updateFoodForUser(userId, foodId, foodDto);
    }

    @DeleteMapping("{id}/food/{foodId}")
    public ResponseEntity<String> deleteFoodForUser(@PathVariable("id") String userId, @PathVariable("foodId") String foodId) throws InputCalorieException {
        userService.deleteFoodForUser(userId, foodId);
        return new ResponseEntity<String>(String.format("Successfully Deleted for Food Id: %s", foodId), HttpStatus.ACCEPTED);
    }
}
