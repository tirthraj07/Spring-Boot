package com.tirthraj.quickstart.services.implementation;

import com.tirthraj.quickstart.services.interface_X;

// @Component // Before
public class ClassAlpha implements interface_X {
    @Override
    public void print() {
        System.out.println("This is class Alpha From X");
    }
}
