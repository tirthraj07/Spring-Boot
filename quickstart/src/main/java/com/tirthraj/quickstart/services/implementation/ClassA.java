package com.tirthraj.quickstart.services.implementation;

import com.tirthraj.quickstart.services.interface_A;
import com.tirthraj.quickstart.services.interface_X;
import com.tirthraj.quickstart.services.interface_Y;
import com.tirthraj.quickstart.services.interface_Z;
import org.springframework.stereotype.Component;

@Component
public class ClassA implements interface_A {

    private interface_X inter_X;
    private interface_Y inter_Y;
    private interface_Z inter_Z;

    public ClassA(interface_X inter_X, interface_Y inter_Y, interface_Z inter_Z){
        this.inter_X = inter_X;
        this.inter_Y = inter_Y;
        this.inter_Z = inter_Z;
    }


    @Override
    public void print() {
        System.out.println("Inside Class A");
        inter_X.print();
        inter_Y.print();
        inter_Z.print();
    }
}
