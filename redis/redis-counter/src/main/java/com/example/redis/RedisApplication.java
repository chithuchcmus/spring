package com.example.redis;

import com.example.redis.service.CounterCorrectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RedisApplication {

	@Autowired
	private CounterCorrectServiceImpl counterCorrectServiceImpl;

	public static void main(String[] args) {
		SpringApplication.run(RedisApplication.class, args);
	}

}
