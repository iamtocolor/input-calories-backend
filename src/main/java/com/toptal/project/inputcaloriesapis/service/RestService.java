package com.toptal.project.inputcaloriesapis.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
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

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        Set<String> customHeaderKey = customerHeader.keySet();
        for (String ckey : customHeaderKey) {
            headers.set(ckey, customerHeader.get(ckey));
        }
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        return this.restTemplate.postForEntity(url, entity, String.class);
    }
}
