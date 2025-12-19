package com.aiService.aiService.client;

import com.aiService.aiService.dto.PriorityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class MlModelClient {
    private final RestTemplate restTemplate;

    @Value("${ml.service.url}")
    private String mlServiceUrl;

    public PriorityResponse callModel(String text){
        Map<String,String> request = Map.of("text",text);

        return restTemplate.postForObject(mlServiceUrl,request,PriorityResponse.class);
    }

}
