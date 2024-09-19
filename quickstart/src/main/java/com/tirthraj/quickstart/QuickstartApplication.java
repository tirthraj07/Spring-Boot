package com.tirthraj.quickstart;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuickstartApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(QuickstartApplication.class, args);
	}

	@Override
	public void run(String ...args) throws Exception{
		System.out.println("Hello World from Command Line Runner");

		for (String arg : args) {
			System.out.println(arg);
		}

		// Check if specific arguments are passed
		if (args.length > 0 && args[0].equals("--debug")) {
			System.out.println("Debug mode is ON");
		}
	}

}
