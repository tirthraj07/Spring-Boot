package com.tirthraj.springSecurity.spring_security.Controller;

import com.tirthraj.springSecurity.spring_security.DTO.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/protected")
public class ProtectedController {

    @GetMapping
    public ResponseEntity<APIResponse> getProtectedData(){
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse(true, "Protected Route Accessed"));
    }

}
