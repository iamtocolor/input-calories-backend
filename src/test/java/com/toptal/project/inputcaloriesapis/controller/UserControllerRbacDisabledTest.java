package com.toptal.project.inputcaloriesapis.controller;


import com.toptal.project.inputcaloriesapis.dto.FoodDto;
import com.toptal.project.inputcaloriesapis.dto.request.LoginRequest;
import com.toptal.project.inputcaloriesapis.dto.request.UserRequest;
import com.toptal.project.inputcaloriesapis.dto.response.LoginResponse;
import com.toptal.project.inputcaloriesapis.dto.response.UserResponse;
import com.toptal.project.inputcaloriesapis.util.RbacUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan("com.inmobi")
@EnableAutoConfiguration
@ContextConfiguration
@WebAppConfiguration
@SpringBootTest()
@Transactional
public class UserControllerRbacDisabledTest {

    @Autowired
    private UserController userController;

    @MockBean
    private RbacUtils rbacUtils;

    private String dummyToken = "";

    @Before
    public void setup() {
        Mockito.doNothing().when(rbacUtils).validateCrudOnUser(any());
        Mockito.doNothing().when(rbacUtils).validateForAdminRole(any());
        Mockito.doNothing().when(rbacUtils).validateForAdminCreation(any());
        Mockito.doNothing().when(rbacUtils).validateCrudOnUserRecord(any(), any());
    }

    @Test
    public void testUserCreate() {
        UserRequest request = getDummyUserRequest();

        UserResponse response = userController.createUser(dummyToken, request);
        Assert.assertEquals(response.getDailyLimit(), request.getDailyLimit());
        Assert.assertEquals(response.getEmail(), request.getEmail());
    }

    @Test
    public void testUserGet() {
        UserRequest request = getDummyUserRequest();

        UserResponse response = userController.createUser(dummyToken, request);
        String userId = response.getUserId().toString();

        UserResponse responseByGet = userController.getUser(dummyToken, userId);
        Assert.assertEquals(responseByGet.getUserId().toString(), userId);
    }

    @Test
    public void testAddFoodForUser() {
        UserRequest request = getDummyUserRequest();

        UserResponse response = userController.createUser(dummyToken, request);
        String userId = response.getUserId().toString();


        FoodDto requestFoodDto = getDummyFoodDto();
        FoodDto responseFoodDto = userController.addFoodForUser(dummyToken, userId, requestFoodDto);

        Assert.assertEquals(responseFoodDto.getFood(), requestFoodDto.getFood());
    }

    @Test
    public void testLogin() {
        UserRequest request = getDummyUserRequest();
        String password = request.getPassWord();

        UserResponse response = userController.createUser(dummyToken, request);
        String userId = response.getUserId().toString();

        LoginResponse loginResponse = userController.login(LoginRequest.builder()
                .emailId(response.getEmail())
                .password(password)
                .build());

        Assert.assertEquals(loginResponse.getEmailId(), request.getEmail());
        Assert.assertNotNull(loginResponse.getToken());
    }
    private UserRequest getDummyUserRequest() {
        return UserRequest.builder()
                .userId(UUID.randomUUID().toString())
                .email("test@email.com")
                .dailyLimit(100)
                .passWord("testPassword")
                .rbacRoleList(Collections.emptySet())
                .build();
    }

    private FoodDto getDummyFoodDto() {
        return FoodDto.builder()
                .foodId(UUID.randomUUID().toString())
                .calories(100)
                .food("dummyFood")
                .date("2019-22-22")
                .time("12:11AM")
                .build();
    }
}
