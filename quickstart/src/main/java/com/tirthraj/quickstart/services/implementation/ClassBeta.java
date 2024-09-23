package com.tirthraj.quickstart.services.implementation;

import com.tirthraj.quickstart.services.interface_Y;
import org.springframework.stereotype.Component;

@Component
public class ClassBeta implements interface_Y {
    @Override
    public void print() {
        System.out.println("This is class Beta From Y");
    }
}
