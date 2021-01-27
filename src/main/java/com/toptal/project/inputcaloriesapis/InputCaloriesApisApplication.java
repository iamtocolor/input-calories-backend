package com.toptal.project.inputcaloriesapis;

import com.toptal.project.inputcaloriesapis.dto.FoodDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static sun.misc.Version.println;

@SpringBootApplication
public class InputCaloriesApisApplication {

	public static void main(String[] args) {
		SpringApplication.run(InputCaloriesApisApplication.class, args);
	}
}
