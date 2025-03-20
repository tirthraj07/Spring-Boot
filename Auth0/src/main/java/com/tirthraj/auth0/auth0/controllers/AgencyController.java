package com.tirthraj.auth0.auth0.controllers;

import com.tirthraj.auth0.auth0.dto.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/role/agency")
public class AgencyController {
    @GetMapping
    public ResponseEntity<APIResponse> getAgencyInfo(){
        return ResponseEntity.status(HttpStatus.OK).body(new APIResponse(true, "Agency Controller Accessed Successfully"));
    }

}
