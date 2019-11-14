package com.example.redis.service;

import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class CounterService {

    private RedissonClient redissonClient;
    private RAtomicLong counter;

//    public CounterService()
//    {
//
//    }

    public CounterService()
    {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("127.0.0.1:6379");
        redissonClient = Redisson.create(config);
        counter =  redissonClient.getAtomicLong("counter");
        counter.set(0L);
    }

    public void restartCounter()
    {
        redissonClient.getAtomicLong("counter").set(0l);
    }

    public long counter()
    {
        return counter.incrementAndGet();
    }
}
