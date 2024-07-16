package com.spring.practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PracticeApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext a = SpringApplication.run(PracticeApplication.class, args);
		System.out.println(a.getEnvironment().getActiveProfiles()[0]);
	}

}
