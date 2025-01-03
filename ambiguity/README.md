### Ambiguity Problem

Consider this example

```java
@Component
public class Dog implements Animal {
    @Override
    public String makeSound(){
        return "Wooof Woof!";
    }
}

@Component
public class Cat implements Animal {
    @Override
    public String makeSound(){
        return "Meoow Meoww!!!";
    }
}
```

If you have **two beans** (Dog and Cat) of the **same type** (Animal) defined with `@Component`, Spring will encounter **ambiguity** during dependency injection when trying to inject an Animal into another component or class.

You'll face the following error:

```
***************************
APPLICATION FAILED TO START
***************************

Description:

Field animal in com.tirthraj.ambiguity.MyController required a single bean, but 2 were found:
        - cat: defined in file [ambiguity\target\classes\com\tirthraj\ambiguity\animals\Cat.class]
        - dog: defined in file [ambiguity\target\classes\com\tirthraj\ambiguity\animals\Dog.class]

This may be due to missing parameter name information

Action:

Consider marking one of the beans as @Primary, updating the consumer to accept multiple beans, or using @Qualifier to identify the bean that should be consumed
```

Scenarios where there are multiple beans of the same type commonly arise in real-world Spring applications, especially when implementing features that involve polymorphism - When an application has multiple implementations of an interface, you might have two or more beans of the same type.

Example

```java
public interface PaymentProcessor {
    void processPayment();
}

@Component
public class CreditCardProcessor implements PaymentProcessor {
    @Override
    public void processPayment() {
        System.out.println("Processing credit card payment.");
    }
}

@Component
public class PayPalProcessor implements PaymentProcessor {
    @Override
    public void processPayment() {
        System.out.println("Processing PayPal payment.");
    }
}
```

OR In unit tests, you might register a mock or stub bean of the same type as the actual bean for testing purposes.

```java
@Component
public class EmailService {
    public void sendEmail(String message) {
        // Actual email sending logic
    }
}

@Component
public class MockEmailService extends EmailService {
    @Override
    public void sendEmail(String message) {
        System.out.println("Mock email sent: " + message);
    }
}
```

### How to resolve the Ambiguity

#### 1. Use the `@Qualifier` to Specify the Bean

You can **explicitly** tell Spring **which bean to inject** using the `@Qualifier` annotation.

Example:

```java
@Component
public class Dog implements Animal {
    @Override
    public String makeSound(){
        return "Wooof Woof!";
    }
}

@Component
public class Cat implements Animal {
    @Override
    public String makeSound(){
        return "Meoow Meoww!!!";
    }
}

@RestController
public class MyController {

    @Autowired
    @Qualifier("dog") // Specify the bean name
    private Animal animal;

    @GetMapping("/")
    public String home() {
        return animal.makeSound(); // Output: Wooof Woof!
    }
}
```

#### 2. Use the `@Primary` Annotation to define a default bean

You can mark one of the beans as the default using the @Primary annotation.

```java
@Component
@Primary
public class Dog implements Animal {
    @Override
    public String makeSound() {
        return "Wooof Woof!";
    }
}

@Component
public class Cat implements Animal {
    @Override
    public String makeSound() {
        return "Meoow Meoww!!!";
    }
}
```

When Animal is autowired, Spring injects the Dog bean by default because it is marked as @Primary.

#### 3. Use a Custom `@Qualifier` for Clarity

You can define custom qualifiers to make the code more readable and maintainable

```java
@Component("dogAnimal")
public class Dog implements Animal {
    @Override
    public String makeSound() {
        return "Wooof Woof!";
    }
}

@Component("catAnimal")
public class Cat implements Animal {
    @Override
    public String makeSound() {
        return "Meoow Meoww!!!";
    }
}

@RestController
public class MyController {

    @Autowired
    @Qualifier("catAnimal")
    private Animal animal;

    @GetMapping("/")
    public String home() {
        return animal.makeSound(); // Output: Meoow Meoww!!!
    }
}
```

#### 4. Inject all beans of type `Animal`

If you need all implementations of Animal, you can inject them as a list or a map.

Example - As a list

```java
@RestController
public class MyController {

    @Autowired
    private List<Animal> animals;

    @GetMapping("/")
    public String home() {
        return animals.stream()
                      .map(Animal::makeSound)
                      .collect(Collectors.joining(", "));
        // Output: Wooof Woof!, Meoow Meoww!!!
    }
}
```

Example - As a map

```java

@RestController
public class MyController {

    @Autowired
    private Map<String, Animal> animals;

    @GetMapping("/")
    public String home() {
        return animals.toString();
        // Output: {dog=Dog@1a2b3c, cat=Cat@4d5e6f}
    }
}
```


---

Example Code for all

```java
public interface Animal {
    public String makeSound();
}

@Component
@Primary
public class Dog implements Animal {
    @Override
    public String makeSound(){
        return "Woof Woof!";
    }
}

@Component
public class Cat implements Animal {
    @Override
    public String makeSound() {
        return "Meoow Meow!!";
    }
}

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
        return dogAnimal.makeSound();   // Wooof Woof!
    }

    // --------- x --------- x --------- x --------- x --------- x --------- x ---------

    @Autowired
    @Qualifier("cat")       // Indicates Spring to inject a Cat object in the variable
    private Animal catAnimal;

    @GetMapping("/cat")
    public String cat(){
        return catAnimal.makeSound();   // Meow Meow!
    }

    // --------- x --------- x --------- x --------- x --------- x --------- x ---------

}


```


