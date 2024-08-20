package com.example.stepperbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StepperBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(StepperBackendApplication.class, args);
	}

}
