package com.tirthraj.myFirstProject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
    @GetMapping("/")
    public String home(){
        return "My First Spring Boot Application. Make sure to visit /greetings";
    }

    @GetMapping("/greetings")
    public String greetings(){
        return "Hello World!";
    }
}
