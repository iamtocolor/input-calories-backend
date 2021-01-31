package com.toptal.project.inputcaloriesapis.controller;

import com.toptal.project.inputcaloriesapis.dto.FoodDto;
import com.toptal.project.inputcaloriesapis.dto.request.LoginRequest;
import com.toptal.project.inputcaloriesapis.dto.request.RbacRole;
import com.toptal.project.inputcaloriesapis.dto.request.SearchRequest;
import com.toptal.project.inputcaloriesapis.dto.request.UserRequest;
import com.toptal.project.inputcaloriesapis.dto.response.LoginResponse;
import com.toptal.project.inputcaloriesapis.dto.response.PagedResponse;
import com.toptal.project.inputcaloriesapis.dto.response.UserResponse;
import com.toptal.project.inputcaloriesapis.entity.FoodEntity;
import com.toptal.project.inputcaloriesapis.entity.UserEntity;
import com.toptal.project.inputcaloriesapis.exception.InputCalorieException;
import com.toptal.project.inputcaloriesapis.service.NutrionixService;
import com.toptal.project.inputcaloriesapis.service.RbacService;
import com.toptal.project.inputcaloriesapis.service.UserService;
import com.toptal.project.inputcaloriesapis.util.JwtTokenUtil;
import com.toptal.project.inputcaloriesapis.util.RbacUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/v1/user")
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RbacService rbacService;


    @Autowired
    private RbacUtils rbacUtils;

    @GetMapping("/getname")
    public String getName(@RequestHeader("Authorization") String token) {
        return "Satish Chandra Gupta" + token;
    }

    @PostMapping("")
    public UserResponse createUser(@RequestHeader("Authorization") String token, @RequestBody UserRequest request) throws InputCalorieException {
        rbacUtils.validateCrudOnUser(token);
        return userService.createUser(request);
    }

    @GetMapping("")
    private PagedResponse<UserResponse> getAllUsers(
            @RequestHeader("Authorization") String token,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ) throws InputCalorieException {
        rbacUtils.validateCrudOnUser(token);
        return userService.getAllUsers(page, size);
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@RequestHeader("Authorization") String token, @PathVariable("id") String userId) throws InputCalorieException {
        rbacUtils.validateCrudOnUser(token);
        return userService.getUserById(userId);
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(@RequestHeader("Authorization") String token, @PathVariable("id") String userId, @RequestBody UserRequest userRequest) throws InputCalorieException {
        rbacUtils.validateCrudOnUser(token);
        return userService.updateUser(userId, userRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@RequestHeader("Authorization") String token, @PathVariable("id") String userId) throws InputCalorieException {
        rbacUtils.validateCrudOnUser(token);
        userService.deleteUser(userId);
        return new ResponseEntity<String>("Successfully Deleted", HttpStatus.ACCEPTED);
    }


    // STARTING OF FOOD
    @PostMapping("/{id}/food")
    public FoodDto addFoodForUser(@RequestHeader("Authorization") String token, @PathVariable("id") String userId, @RequestBody FoodDto foodDto) throws InputCalorieException {
        rbacUtils.validateCrudOnUserRecord(token, userId);
        return userService.addFoodForUser(userId, foodDto);
    }

    // Support for Paging & Filter
    @GetMapping("/{id}/food")
    public PagedResponse<FoodDto> getAllFoodForUser(@RequestHeader("Authorization") String token,
                                                    @PathVariable("id") String userId,
                                                    @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                    @RequestParam(value = "size", defaultValue = "10") Integer size) throws InputCalorieException {
        rbacUtils.validateCrudOnUserRecord(token, userId);
        return userService.getAllFoodForUser(userId, page, size);
    }

    @PutMapping("/{id}/food/{foodId}")
    public FoodDto updateFoodForUser(@RequestHeader("Authorization") String token, @PathVariable("id") String userId, @PathVariable("foodId") String foodId, @RequestBody FoodDto foodDto) throws InputCalorieException {
        rbacUtils.validateCrudOnUserRecord(token, userId);
        return userService.updateFoodForUser(userId, foodId, foodDto);
    }

    @DeleteMapping("{id}/food/{foodId}")
    public ResponseEntity<String> deleteFoodForUser(@RequestHeader("Authorization") String token, @PathVariable("id") String userId, @PathVariable("foodId") String foodId) throws InputCalorieException {
        rbacUtils.validateCrudOnUserRecord(token, userId);
        userService.deleteFoodForUser(userId, foodId);
        return new ResponseEntity<String>(String.format("Successfully Deleted for Food Id: %s", foodId), HttpStatus.ACCEPTED);
    }


    // Beast Search API
    @PostMapping("/search")
    public PagedResponse<FoodDto> search(@RequestHeader("Authorization") String token,
                                         @RequestBody SearchRequest searchRequest,
                                   @RequestParam(value = "page", defaultValue = "0") Integer page,
                                   @RequestParam(value = "size", defaultValue = "10") Integer size) throws InputCalorieException {
        rbacUtils.validateForAdminRole(token);
        return userService.searchUserDate(searchRequest, page, size);
    }


    // LOGIN API
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) throws InputCalorieException {
        return rbacService.login(loginRequest);
    }

    @PostMapping("/admin")
    public UserResponse createAdmin(@RequestHeader("x-admin-key") String adminKey, @RequestBody UserRequest userRequest) throws InputCalorieException {
        rbacUtils.validateForAdminCreation(adminKey);
        return userService.createAdminUser(userRequest);
    }


    @PutMapping("/{id}/assign-role")
    private UserResponse assignRole(@RequestHeader("Authorization") String token,
                            @PathVariable("id") String userId,
                            @RequestBody Set<RbacRole> rbacRoles) throws InputCalorieException {
        rbacUtils.validateCrudOnUser(token);
        return userService.assignRolesToUser(userId, rbacRoles);
    }
}
