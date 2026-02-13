package com.project.learning.stayIn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StayInApplication {

	public static void main(String[] args) {
		SpringApplication.run(StayInApplication.class, args);
	}
}
