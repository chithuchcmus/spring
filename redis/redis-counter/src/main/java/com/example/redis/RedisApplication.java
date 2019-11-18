package com.example.redis;

import com.example.redis.entity.Counter;
import com.example.redis.entity.CounterRedis;
import com.example.redis.repository.CounterRedisRepository;
import com.example.redis.repository.CounterRepository;
import com.example.redis.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicLong;

@SpringBootApplication
@EnableScheduling
@EnableRedisRepositories
public class RedisApplication {

	@Autowired
	private CounterRepository counterRepository;

	@Autowired
	private CounterRedisRepository counterRedisRepository;

	@Bean
	public LettuceConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory();
	}

	@Bean
	public RedisTemplate<?, ?> redisTemplate() {
		RedisTemplate<byte[], byte[]> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory());
		return template;
	}

	@PostConstruct
	@Transactional
	public void createCounterInDbAndRedisPreStartApplication()
	{
		Counter counterInDb = new Counter();
		counterInDb.setNumberCount(ConfigService.getStartCountValue());
		counterRepository.save(counterInDb);

		CounterRedis counterRedis =  new CounterRedis();
		counterRedis.setId(ConfigService.getCounter());
		counterRedis.setCount(ConfigService.getStartCountValue());
		counterRedisRepository.save(counterRedis);

	}
	public static void main(String[] args) {
		SpringApplication.run(RedisApplication.class, args);
	}

}
