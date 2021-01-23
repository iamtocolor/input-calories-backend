package com.toptal.project.inputcaloriesapis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@Controller
public class UserController {

    @GetMapping("/getname")
    public String getName() {
        return "Satish Chandra Gupta";
    }
}
