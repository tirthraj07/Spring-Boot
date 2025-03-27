package com.tirthraj.custom_filters.custom_filters.Controllers;


import com.tirthraj.custom_filters.custom_filters.DTO.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthCheckController {

    @GetMapping("/check")
    public ResponseEntity<APIResponse> getHealth(){
        return ResponseEntity.ok(new APIResponse(true, "System is up and running"));
    }

}
