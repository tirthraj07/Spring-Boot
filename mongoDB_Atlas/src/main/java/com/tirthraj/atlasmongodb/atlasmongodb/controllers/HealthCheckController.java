package com.tirthraj.atlasmongodb.atlasmongodb.controllers;

import com.tirthraj.atlasmongodb.atlasmongodb.dto.HealthCheckDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/health")
public class HealthCheckController {
    @GetMapping("/healthcheck")
    public ResponseEntity<HealthCheckDTO> healthCheck(){
        HealthCheckDTO response = new HealthCheckDTO();
        response.setStatus("success");
        response.setMessage("service is up and running");
        return ResponseEntity.ok(response);
    }

}
