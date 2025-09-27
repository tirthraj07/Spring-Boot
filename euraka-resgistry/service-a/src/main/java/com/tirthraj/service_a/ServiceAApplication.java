package com.tirthraj.service_a;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
/*
in Spring Boot 3.5.x + Spring Cloud 2025.x, you don’t need @EnableEurekaClient anymore
*/
// @EnableEurekaClient
@EnableFeignClients
public class ServiceAApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceAApplication.class, args);
	}

}
