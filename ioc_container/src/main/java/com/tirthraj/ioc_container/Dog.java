package com.tirthraj.ioc_container;

import org.springframework.stereotype.Component;

@Component
public class Dog {
    public String bark(){
        return "Wooof Wooof!";
    }

}
