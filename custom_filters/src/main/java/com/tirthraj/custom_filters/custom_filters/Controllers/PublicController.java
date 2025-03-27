package com.tirthraj.custom_filters.custom_filters.Controllers;

import com.tirthraj.custom_filters.custom_filters.DTO.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {
    @GetMapping
    public ResponseEntity<APIResponse> getPublicInfo(){
        return ResponseEntity.ok(new APIResponse(true, "Public controller accessed"));
    }
}
