package com.tirthraj.ambiguity.animals;

import com.tirthraj.ambiguity.Animal;
import org.springframework.stereotype.Component;

@Component
public class Cat implements Animal {
    @Override
    public String makeSound() {
        return "Meoow Meow!!";
    }
}
