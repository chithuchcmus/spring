package com.example.redis;

import com.example.redis.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableScheduling
public class RedisApplication {

	@Autowired
	private CounterService counterService;

	public static void main(String[] args) {
		SpringApplication.run(RedisApplication.class, args);
	}

}
