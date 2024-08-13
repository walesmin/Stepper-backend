package com.example.stepperbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = {"env.properties"})
public class StepperBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(StepperBackendApplication.class, args);
	}

}
