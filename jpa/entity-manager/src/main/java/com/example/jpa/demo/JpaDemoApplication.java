package com.example.jpa.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

@SpringBootApplication
public class JpaDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaDemoApplication.class, args);

	}

}
