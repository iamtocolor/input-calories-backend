package com.toptal.project.inputcaloriesapis.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class RestService {

    private final RestTemplate restTemplate;
    private ObjectMapper objectMapper;
    public RestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = new ObjectMapper();
    }


    public ResponseEntity<String> createPost(String url, Map<String, Object> body, Map<String, String> customerHeader) {

        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        Set<String> customHeaderKey = customerHeader.keySet();
        for (String ckey : customHeaderKey) {
            headers.set(ckey, customerHeader.get(ckey));
        }
        // create a map for post parameters
//        Map<String, Object> map = new HashMap<>();
//        map.put("qu", 1);
//        map.put("title", "Introduction to Spring Boot");
//        map.put("body", "Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications.");

        // build the request
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        // send POST request
        return this.restTemplate.postForEntity(url, entity, String.class);
    }
}
