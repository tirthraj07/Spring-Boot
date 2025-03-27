package com.tirthraj.custom_filters.custom_filters.Controllers;

import com.tirthraj.custom_filters.custom_filters.DTO.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/protected")
public class ProtectedController {
    @GetMapping
    public ResponseEntity<APIResponse> getProtectedInfo(){
        return ResponseEntity.ok(new APIResponse(true, "Protected route accessed"));
    }

}
