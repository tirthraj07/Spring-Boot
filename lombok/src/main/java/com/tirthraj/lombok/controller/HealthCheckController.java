package com.tirthraj.lombok.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/health-check")
public class HealthCheckController {

    @GetMapping
    public ResponseEntity<Map<String, String>> healthCheck(){
        Map<String, String> response = new HashMap<>(){{
            put("status","success");
            put("message","service is up and running");
        }};
        return ResponseEntity.ok(response);
    }

}
