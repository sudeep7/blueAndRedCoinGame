package com.genesys.connect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.genesys.connect.*")
public class GenesysConnect5Application {

	public static void main(String[] args) {
		SpringApplication.run(GenesysConnect5Application.class, args);
		System.out.println("Server Up and Running"); 
	}

}
