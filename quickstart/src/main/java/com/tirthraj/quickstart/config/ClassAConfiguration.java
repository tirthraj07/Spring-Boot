package com.tirthraj.quickstart.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tirthraj.quickstart.services.interface_X;
import com.tirthraj.quickstart.services.interface_Y;
import com.tirthraj.quickstart.services.interface_Z;
import com.tirthraj.quickstart.services.interface_A;

import com.tirthraj.quickstart.services.implementation.*;

// configuration class is used to supply bean metadata to an IoC container:

@Configuration
public class ClassAConfiguration {

    @Bean
    public interface_X getInterfaceX(){
        // Before Replacement
        // return new ClassAlpha();

        // After Replacement
         return new ClassOmega();
    }

    @Bean
    public interface_Y getInterfaceY(){
        return new ClassBeta();
    }

    @Bean
    public interface_Z getInterfaceZ(){
        return new ClassGamma();
    }

    @Bean
    public interface_A getInterfaceA(interface_X inter_X, interface_Y inter_Y, interface_Z inter_Z){
        return new ClassA(inter_X, inter_Y, inter_Z);
    }

}
