package com.tirthraj.configuration_class.animals;

import com.tirthraj.configuration_class.Animal;

public class Dog implements Animal {
    @Override
    public String makeSound(){
        return "Wooof Woof!";
    }
}
