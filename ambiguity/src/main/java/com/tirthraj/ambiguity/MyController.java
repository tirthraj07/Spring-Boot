package com.tirthraj.ambiguity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class MyController {

    @Autowired
    private Animal animal;
    //  @Primary is applied to Dog, but it only affects cases where Spring must resolve ambiguity between beans of the same type for a single injection (like @Autowired on a single Animal instance).

    @GetMapping("/")
    public String home(){
        return animal.makeSound();  // Woof Woof! Since Dog is marked with @Primary Annotation
    }

    // --------- x --------- x --------- x --------- x --------- x --------- x ---------

    @Autowired
    private List<Animal> animalList;
    // When you use @Autowired on a List<Animal>, Spring automatically injects all beans of type Animal into the list, regardless of @Primary.

    @GetMapping("/animals")
    public String animals(){
        return animalList.stream().map(Animal::makeSound).collect(Collectors.joining(", "));    // Meoow Meow!!, Woof Woof!
    }

    // --------- x --------- x --------- x --------- x --------- x --------- x ---------

    @Autowired
    @Qualifier("dog")           // Indicates Spring to inject a Dog object in the variable
    private Animal dogAnimal;

    @GetMapping("/dog")
    public String dog(){
        return dogAnimal.makeSound();
    }

    // --------- x --------- x --------- x --------- x --------- x --------- x ---------

    @Autowired
    @Qualifier("cat")       // Indicates Spring to inject a Cat object in the variable
    private Animal catAnimal;

    @GetMapping("/cat")
    public String cat(){
        return catAnimal.makeSound();
    }

    // --------- x --------- x --------- x --------- x --------- x --------- x ---------

}
