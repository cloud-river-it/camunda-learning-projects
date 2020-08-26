package com.company.poc.activitylog;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableProcessApplication
public class ActivityLogBpmApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActivityLogBpmApplication.class, args);
	}

}
