### Component Scanning

The @SpringBootApplication includes the @ComponentScan Annotation which looks for "Components"  
These "Components" are nothing but Beans  

Beans are classes for which only one object is created. The entire lifecycle of Bean (Instantiation to Deletion) is handled by an IOC (Inversion of Control) container - Spring.


Reason for creating a Bean is that, if you have a class say `class A` which is required in multiple packages, each package would have to create a new object for instantiation of the class leading to space wastage.


So we use the concept of **Beans**

By default, Spring beans are **singletons**.
This means Spring creates only one instance of a bean and reuses it wherever it is needed (within the same ApplicationContext).

The lifecycle is fully managed by Spring.

A bean is defined using annotations like `@Component`, `@Service`, `@Repository`, or via explicit configuration in `@Configuration` classes using `@Bean`.

In this example, we'll see how to **automatically** find and inject a bean into another component.
This can be achieved by `@Autowired` annotation in Spring


Say we have a class Dog, which is required in multiple packages. We can mark it as component using the `@Component` Annotation

```java
@Component
public class Dog {
    public String bark(){
        return "Wooof Wooof!";
    }

}
```

We can use the `@Autowired` Annotation for dependency injection

Note: 
Without `@Component` (or another bean definition like `@Configuration` + `@Bean`), Spring won't know about the class, and `@Autowired` will fail.

```java
public class MyController {

    @Autowired
    Dog dog;

    @GetMapping("/")
    public String home(){
        return dog.bark();
    }
}
```

**Full Flow**
1. Spring scans the application for components.
2. It registers Dog as a bean because of the **@Component** annotation.
3. When Spring initializes MyController, it notices the **@Autowired** annotation - which tells Spring to inject that bean wherever its needed.
4. Spring injects the Dog bean into the dog field of MyController


When should one mark a class by `@Component`?
- If the class will be a dependency for other Spring-managed beans, you should mark it with @Component.
Example: A service, utility, or helper class used by controllers or other beans

- For objects that don't maintain state (stateless), @Component works well because the singleton scope is often sufficient.
Example: Utility or service classes that perform tasks like calculations or sending notifications.

- If the class is part of the application's business logic or core features, it should typically be a Spring-managed bean.

- If the class needs to use Spring's lifecycle hooks (like @PostConstruct or @PreDestroy), it must be a Spring bean. 
Example

```java
@Component
public class Dog {
    @PostConstruct
    public void init() {
        System.out.println("Dog initialized!");
    }
}
```

- For classes that need proxies (e.g., for transaction management, logging, or security), they should be Spring-managed and marked with @Component

- Classes that are POJOs (Plain Old Java Objects) with no logic (e.g., entities, DTOs, or models) should **NOT** be marked as @Component. These are typically managed by frameworks like JPA or created manually.
Example

```
public class Dog {
    private String name;
    private int age;

    // Getters and setters
}
```
