### Lombok Library

Lombok is a **Java library** that helps developers reduce boilerplate code by automatically generating commonly used methods like **getters**, **setters**, **constructors**, **`equals()`**, **`hashCode()`**, and **`toString()`** at compile time. It uses annotations to inject this functionality into your Java classes, making the code more concise and easier to maintain.


#### Lombok Annotations
- `@Getter` / `@Setter`: Generates getter and setter methods for fields.
- `@ToString`: Creates a `toString()` method.
- `@EqualsAndHashCode`: Generates `equals()` and `hashCode()` methods.
- `@Data`: Combines `@Getter`, `@Setter`, `@ToString`, `@EqualsAndHashCode`, and `@RequiredArgsConstructor`.
- `@NoArgsConstructor`, `@AllArgsConstructor`, `@RequiredArgsConstructor`: Creates constructors with no arguments, all arguments, or required arguments respectively.
- `@Builder`: Provides a builder pattern implementation.
- `@Slf4j`: Generates a logger instance (e.g., for logging).

> Lombok leverages **annotation processing** to inject the generated methods directly into the compiled bytecode, so the developer doesn't see them in the source code.

### Example Usage

Without Lombok:

```java
public class User {
    private String name;
    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{name='" + name + "', age=" + age + '}';
    }
}
```

With Lombok:
```java
import lombok.Data;

@Data
public class User {
    private String name;
    private int age;
}
```

### How to use Lombok in project

- [Lombok - Maven Repository](https://mvnrepository.com/artifact/org.projectlombok/lombok)
