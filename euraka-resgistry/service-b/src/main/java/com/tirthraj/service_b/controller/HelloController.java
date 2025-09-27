package com.tirthraj.service_b.controller;

import com.tirthraj.service_b.dto.ApiResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hello")
public class HelloController {

    @GetMapping("/world")
    public ResponseEntity<ApiResponseDto> helloWorld() {
        return ResponseEntity.ok(new ApiResponseDto(true, List.of("Hello From Service B")));
    }

}
