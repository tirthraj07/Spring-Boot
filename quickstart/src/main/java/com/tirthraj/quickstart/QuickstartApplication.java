package com.tirthraj.quickstart;

import com.tirthraj.quickstart.services.implementation.ClassA;
import com.tirthraj.quickstart.services.interface_A;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
In traditional programming, developers create instances of objects and manage the flow of the program

Suppose we have Class A which depends on class α, class β and class γ
and we want to change the dependency of class α with class Ω.

In Dependency injection (DI) process, objects define their dependencies
(that is, the other objects with which they work) only through
constructor arguments,
arguments to a factory method,
or properties that are set on the object instance after it is constructed or returned from a factory method.


Suppose Class A has 3 dependencies.
To achieve IoC, what we can do is define the interface of the dependencies inside the class
and let the Framework "inject" the actual objects into the classes.

We can achieve this using Spring Beans

Before Replacement:
Inside Class A
This is class Alpha From X
This is class Beta From Y
This is class Gamma From Z

After Replacement:
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
