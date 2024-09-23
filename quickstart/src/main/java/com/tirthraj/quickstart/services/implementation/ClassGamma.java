package com.tirthraj.quickstart.services.implementation;

import com.tirthraj.quickstart.services.interface_Z;
import org.springframework.stereotype.Component;

@Component
public class ClassGamma implements interface_Z {
    @Override
    public void print() {
        System.out.println("This is class Gamma From Z");
    }
}
