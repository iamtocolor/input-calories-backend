package com.toptal.project.inputcaloriesapis.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class NutrionixService {

    @Value("${nutritionix.url}")
    @Getter
    private String url;

    @Value("${nutritionix.app.id}")
    @Getter
    private String appId;

    @Value("${nutritionix.app.key}")
    @Getter
    private String appKey;

    @Autowired
    private RestService restService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Cache<String, Integer> cache = null;

    @PostConstruct
    public void init() {
        cache = CacheBuilder.newBuilder()
                .maximumSize(2000)
                .build(new CacheLoader<String, Integer>() {
                    @Override
                    public Integer load(String s) throws Exception {
                        return computeCaloriesFoodFood(s);
                    }
                });

    }

    public int getCaloriesForFood(String food) {
        try {
            return cache.get(food);
        } catch (ExecutionException e) {
            System.out.println("Failed to fetch calories. Setting default: 0");
            return 0;
        }
    }

    public int computeCaloriesFoodFood(String food) {
        Map<String, String> headers = new HashMap<>();
        headers.put("x-app-id", appId);
        headers.put("x-app-key", appKey);

        Map<String, Object> body = new HashMap<>();
        body.put("query", food);

        ResponseEntity<String> response = restService.createPost(url, body, headers);

        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                JsonNode root = objectMapper.readTree(response.getBody());
                return root.get("foods").get(0).get("nf_calories").asInt();
            } catch (Exception e) {
                System.out.println("Error while fetching from Nutritionix. " + e.getMessage());
                return 0;
            }
        } else {
            return 0;
        }
    }
}
