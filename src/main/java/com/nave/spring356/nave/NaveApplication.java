package com.nave.spring356.nave;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class NaveApplication {

	public static void main(String[] args) {
		SpringApplication.run(NaveApplication.class, args);
	}

}
