package com.tirthraj.quickstart.services.implementation;

import com.tirthraj.quickstart.services.interface_X;
import org.springframework.stereotype.Component;

@Component
public class ClassOmega implements interface_X {
    @Override
    public void print() {
        System.out.println("This is class Omega From X");
    }
}
