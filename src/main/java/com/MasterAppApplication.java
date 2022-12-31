package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class MasterAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MasterAppApplication.class, args);
	}
	@Bean
	public BCryptPasswordEncoder getEncoder() {
		System.out.println("Password Encoder is Ready...........");
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		return bcrypt;
	}
}
