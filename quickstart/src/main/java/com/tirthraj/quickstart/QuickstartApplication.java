package com.tirthraj.quickstart;

import com.tirthraj.quickstart.services.implementation.ClassA;
import com.tirthraj.quickstart.services.interface_A;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
Instead of having a Configuration Class, we can use the @Component Annotation from Spring Boot

Before we can understand the value of @Component, we first need to understand a little bit about the Spring ApplicationContext.

Spring ApplicationContext is where Spring holds instances of objects that it has identified to be managed and distributed automatically. These are called beans.

@Component is an annotation that allows Spring to detect our custom beans automatically.

In other words, without having to write any explicit code, Spring will:

Scan our application for classes annotated with @Component
Instantiate them and inject any specified dependencies into them
Inject them wherever needed

We can use @Component across the application to mark the beans as Spring’s managed components.
Spring will only pick up and register beans with @Component

@Service and @Repository are special cases of @Component. They are technically the same, but we use them for the different purposes.

We mark beans with @Service to indicate that they’re holding the business logic. Besides being used in the service layer, there isn’t any other special use for this annotation.
@Repository’s job is to catch persistence-specific exceptions and re-throw them as one of Spring’s unified unchecked exceptions.


Before:
Inside Class A
This is class Alpha From X
This is class Beta From Y
This is class Gamma From Z

After	:
Inside Class A
This is class Omega From X
This is class Beta From Y
This is class Gamma From Z

*/


@SpringBootApplication
public class QuickstartApplication implements CommandLineRunner {

	private interface_A inter_A;

	QuickstartApplication(interface_A inter_A){
		this.inter_A = inter_A;
	}

	public static void main(String[] args) {
		SpringApplication.run(QuickstartApplication.class, args);
	}

	@Override
	public void run(String ...args){
		inter_A.print();
	}


}
