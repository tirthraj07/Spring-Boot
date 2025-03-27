package com.tirthraj.springSecurity.spring_security.Controller;

import com.tirthraj.springSecurity.spring_security.DTO.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping
    public ResponseEntity<APIResponse> getUser(){
        return ResponseEntity.ok(new APIResponse(true, "User Controller accessed successfully"));
    }
}
