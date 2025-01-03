package com.tirthraj.configuration_class;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    // Using autowired
    @Autowired
    public Animal animal;


    /*

        // Using constructor based dependency injection
        private final Animal animal;

        public MyController(Animal animal){
            this.animal = animal;
        }

     */

    @GetMapping("/")
    public String home(){
        return animal.makeSound();
    }

}
