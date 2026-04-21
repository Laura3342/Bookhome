package com.bookhome.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication(scanBasePackages = "com.bookhome")
public class BookhomeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookhomeApplication.class, args);
	}

}
