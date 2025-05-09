package com.example.Blog_Application;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Blog_Application {

	public static void main(String[] args) {
		SpringApplication.run(Blog_Application.class, args);
	}

	private static final ModelMapper modelMapper = new ModelMapper();

	@Bean
	public static ModelMapper getModelMapper() {
		return modelMapper;
	}
}
