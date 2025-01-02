package com.tirthraj.ioc_container;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @Autowired
    Dog dog;

    @GetMapping("/")
    public String home(){
        return dog.bark();
    }
}
