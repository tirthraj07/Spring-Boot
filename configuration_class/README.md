## Configuration Class and Bean Annotation

A `Configuration` Class and the `@Bean` annotation are used in Spring to **explicitly** define beans and their dependencies. This is part of manual dependency injection where you have fine-grained control over how objects are created and wired together.

A **configuration class** is a class annotated with `@Configuration`. It indicates to Spring that the class contains bean definitions. These definitions tell Spring how to instantiate, configure, and wire objects manually.

Suppose we have an interface `Animal.java` with a single method `makeSound()`  
Acts as the contract for any class (like Dog or Cat) that implements it

`Dog.java` implements `Animal.java` with function `makeSound()` that return "Wooof Woof!"  
Similarly, `Cat.java` also implements `Animal.java` with function `makeSound()` that return "Meow Meow!"

### AppConfig.java
Here we define the **Configuration Class** which tells how beans are created and managed by Spring

It indicates to Spring that the class contains bean definitions. These definitions tell Spring how to instantiate, configure, and wire objects manually.


```java
@Configuration
public class AppConfig {

    @Bean
    public Animal getAnimal() {
        
        return new Cat(); // Currently returns a Cat instance. 
        
        // return new Dog(); // Can be swapped with Dog.
    }

}
```

The `@Bean` annotation tells Spring that the method it is applied to will return a bean that should be managed by the Spring container. This gives you control over how the bean is created.

- `@Bean` is used inside a `@Configuration` class.
- Each `@Bean` method returns an object that is added to the **ApplicationContext**.
- It allows custom logic in bean creation.


### MyController.java

It is a simple REST controller that uses the Animal bean and provides an endpoint (/) to access its makeSound() method.

Uses the `@Autowired` annotation to inject the Animal bean defined in AppConfig

```java
@RestController
public class MyController {

    @Autowired
    public Animal animal;

    @GetMapping("/")
    public String home() {
        return animal.makeSound();
    }
}
```


### Constructor Based Injection

Instead of using `@Autowired` directly on fields, you can use **constructor-based** injection. This is generally the preferred approach because it ensures immutability and easier testing.

```java
@RestController
public class MyController {

    private final Animal animal;

    // Constructor-based injection
    public MyController(Animal animal) {
        this.animal = animal;
    }

    @GetMapping("/")
    public String home() {
        return animal.makeSound();
    }
}
```

- No need for `@Autowired` annotation if there is only one constructor in the class (Spring automatically injects the dependencies).

---

### Difference between `@Component` Annotation and `@Configuration` + `@Bean` Annotation

The main difference between `@Component` and `@Configuration` + `@Bean` lies in how Spring manages beans and the level of control you have over the bean creation process. Both approaches are used to define and manage Spring beans, but they serve different purposes and use cases.

**`@Component` Annotation`

It marks a class as a Spring-managed bean. 
The entire class is treated as a bean
Spring automatically detects classes annotated with `@Component` during classpath scanning if the package is included in the `@ComponentScan` path.
The class must have a no-argument constructor (or dependencies must also be Spring-managed for injection to work).


Example 

```java
public interface Animal {
    public String makeSound();
}

@Component
public class Dog implements Animal {
    @Override
    public String makeSound() {
        return "Woof Woof!";
    }
}

@RestController
public class MyController {

    @Autowired
    private Animal animal;

    @GetMapping("/")
    public String home() {
        return animal.makeSound();
    }
}

```

**`@Configuration` + `@Bean` Annotation**

It **explicitly** define Spring beans with custom logic or conditions for creation
Allows you to create beans by defining factory methods in a configuration class.

The `@Bean` annotation provides more control over the object creation process (e.g., adding parameters, initializing with specific data, or applying custom logic).

Example

```java
@Configuration
public class AppConfig {

    @Bean
    public Animal getAnimal() {
        return new Dog();
    }
}

@RestController
public class MyController {

    @Autowired
    private Animal animal;

    @GetMapping("/")
    public String home() {
        return animal.makeSound();
    }
}
```

You can **control** whether to return Dog, Cat, or even inject runtime conditions.

---

### Difference between `@Autowired` and Constructor-based Dependency Injection

The difference between @Autowired and constructor-based dependency injection lies in the **mechanism of injecting dependencies** and the best practices they follow. Both are used to achieve dependency injection in Spring, but their implementation, benefits, and use cases differ

**`@Autowired` Annotation**

@Autowired is a Spring annotation used to enable automatic dependency injection into a bean.

It can be applied to:

- Fields
- Constructor
- Setter methods

Example 

```java
@RestController
public class MyController {

    @Autowired
    private Animal animal;

    @GetMapping("/")
    public String home() {
        return animal.makeSound();
    }
}
```

Automatically injects a bean by type from the Spring container
Requires the field, constructor, or setter to be accessible (not private unless reflection is used).
Works well for simplicity but is less preferred in modern Spring applications (e.g., field injection is discouraged).

**Constructor-based Dependency Injection**

Constructor-based dependency injection explicitly injects dependencies via the constructor.

If there's only one constructor in the class, Spring automatically wires it without needing the @Autowired annotation.

Example  

```java

@RestController
public class MyController {

    private final Animal animal;

    // Constructor-based injection
    public MyController(Animal animal) {
        this.animal = animal;
    }

    @GetMapping("/")
    public String home() {
        return animal.makeSound();
    }
}

```

- Dependencies are passed explicitly to the constructor
- No need for @Autowired if the class has a single constructor.
- Follows the principle of immutability and explicit dependency declaration. 
Dependencies are declared final, making them immutable and ensuring they cannot be changed after the object is constructed.
Dependencies are clearly listed in the constructor, improving readability and maintainability
- Ensures all dependencies are provided during object creation, avoiding runtime NullPointerException


Spring's official recommendation is to prefer constructor-based injection over @Autowired field injection for mandatory dependencies. Field injection should be avoided in modern applications due to its drawbacks


