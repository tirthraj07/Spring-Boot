package com.tirthraj.restAPI.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@RestController
@RequestMapping("/health-check")
public class HealthCheckController {

    @GetMapping
    public ResponseEntity<Map<String, String>> healthCheck(){
        // Return {status: "OK"} with status Code 200

        Map<String, String> response = new HashMap<>(){{
            put("status", "OK");
            put("message", "Service is up and running");
        }};

        return ResponseEntity.ok(response);
    }

}
