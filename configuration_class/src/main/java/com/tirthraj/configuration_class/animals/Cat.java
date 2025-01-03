package com.tirthraj.configuration_class.animals;

import com.tirthraj.configuration_class.Animal;

public class Cat implements Animal {
    @Override
    public String makeSound(){
        return "Meoow Meoww!!";
    }
}
