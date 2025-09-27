package com.tirthraj.service_a.feign;

import com.tirthraj.service_a.dto.ApiResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "SERVICE-B")
public interface ServiceBClient {
    @GetMapping("/api/v1/hello/world")
    ResponseEntity<ApiResponseDto> helloWorld();
}
