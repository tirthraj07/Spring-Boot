package com.tirthraj.configuration_class;

import com.tirthraj.configuration_class.animals.Cat;
import com.tirthraj.configuration_class.animals.Dog;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Animal getAnimal(){
        return new Cat();
        // return new Dog();   // Can be swapped with Dog object
    }

}
