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
Traditionally, we would have to do inside Class A and change the dependency.

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

	public static void main(String[] args) {
		SpringApplication.run(QuickstartApplication.class, args);
	}

	@Override
	public void run(String ...args){
		interface_A inter_A = new ClassA();
		inter_A.print();
	}


}
