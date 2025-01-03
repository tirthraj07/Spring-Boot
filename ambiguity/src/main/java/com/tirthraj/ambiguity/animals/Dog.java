package com.tirthraj.ambiguity.animals;

import com.tirthraj.ambiguity.Animal;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class Dog implements Animal {
    @Override
    public String makeSound(){
        return "Woof Woof!";
    }
}
