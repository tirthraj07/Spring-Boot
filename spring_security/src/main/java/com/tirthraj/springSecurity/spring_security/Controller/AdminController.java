package com.tirthraj.springSecurity.spring_security.Controller;

import com.tirthraj.springSecurity.spring_security.DTO.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public ResponseEntity<APIResponse> getAdminInfo(){
        return ResponseEntity.ok(new APIResponse(true, "Admin Controller accessed successfully"));
    }

}
