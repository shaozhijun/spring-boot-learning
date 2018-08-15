package com.shaozj.activemq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ActiveMqApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActiveMqApplication.class, args);
	}
}
