package com.tirthraj.quickstart.services.implementation;

import com.tirthraj.quickstart.services.interface_A;
import com.tirthraj.quickstart.services.interface_X;
import com.tirthraj.quickstart.services.interface_Y;
import com.tirthraj.quickstart.services.interface_Z;

public class ClassA implements interface_A {

    private interface_X inter_X;
    private interface_Y inter_Y;
    private interface_Z inter_Z;

    public ClassA(){

        // Before Replacement
        // inter_X = new ClassAlpha();

        // After Replacement
        inter_X = new ClassOmega();

        inter_Y = new ClassBeta();
        inter_Z = new ClassGamma();
    }


    @Override
    public void print() {
        System.out.println("Inside Class A");
        inter_X.print();
        inter_Y.print();
        inter_Z.print();
    }
}
