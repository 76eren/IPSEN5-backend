package com.cgi.ipsen5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class Ipsen5BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(Ipsen5BackendApplication.class, args);
	}

}
