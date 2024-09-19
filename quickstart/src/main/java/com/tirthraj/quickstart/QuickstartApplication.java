package com.tirthraj.quickstart;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*

Application Runner is an interface used to execute the code after the Spring Boot application started.
While CommandLineRunner serves well for basic scenarios, ApplicationRunner takes the concept to the next level.
Instead of a simple array of strings, ApplicationRunner accepts ApplicationArguments, providing a more sophisticated approach
to handling command-line arguments.

To run:
mvn clean package
java -jar .\target\quickstart-0.0.1-SNAPSHOT.jar --config=dev --debug arg1 arg2

Output:
Hello World from Application Runner
Non-Option Args: [arg1, arg2]
Config option is present!
Config values: [dev]
Option Names: [debug, config]
*/

@SpringBootApplication
public class QuickstartApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(QuickstartApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception{
		System.out.println("Hello World from Application Runner");

		System.out.println("Non-Option Args: " + args.getNonOptionArgs());

		// Check if a specific option is present
		if (args.containsOption("config")) {
			System.out.println("Config option is present!");
			// Get values of the 'config' option
			System.out.println("Config values: " + args.getOptionValues("config"));
		}

		// Print all option names
		System.out.println("Option Names: " + args.getOptionNames());
	}

}
