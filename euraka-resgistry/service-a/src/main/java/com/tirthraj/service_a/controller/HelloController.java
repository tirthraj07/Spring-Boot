package com.tirthraj.service_a.controller;

import com.tirthraj.service_a.dto.ApiResponseDto;
import com.tirthraj.service_a.feign.ServiceBClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/hello")
public class HelloController {

    @Autowired
    private ServiceBClient serviceBClient;

    @GetMapping("/world")
    public ResponseEntity<ApiResponseDto> helloWorld(){
        ApiResponseDto response = new ApiResponseDto();
        // Call service B and get the response
        ApiResponseDto responseFromB = serviceBClient.helloWorld().getBody();
        if(!responseFromB.isSuccess()){
            response.setSuccess(false);
            response.setMessage(List.of("Failed to get message from B"));
        } else {
            response.setSuccess(true);
            response.setMessage(new ArrayList<>());
            response.getMessage().addAll(responseFromB.getMessage());
            response.getMessage().add("Hello from Service A");
        }

        return ResponseEntity.ok(response);
    }
}
