package com.example.redis.service;

import com.example.redis.entity.Counter;
import com.example.redis.repository.CounterRepository;
import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.PostConstruct;

@Service
public class CounterCorrectServiceImpl implements CounterCorrectService{

    private RedissonClient redissonClient;
    private RAtomicLong counterInRedis;

    @Autowired
    private ConfigService configService;

    @Autowired
    private CounterRepository counterRepository;

    public CounterCorrectServiceImpl()
    {
    }

    @PostConstruct
    private void prepareConfigService()
    {
        Config config = new Config();
        config.useSingleServer()
                .setAddress(configService.getRedisServerAddress());
        redissonClient = Redisson.create(config);
        counterInRedis =  redissonClient.getAtomicLong("counter");
        counterInRedis.set(configService.getStartCountValue());

        Counter counterEntity = new Counter();
        counterEntity.setNumberCount(configService.getStartCountValue());
        counterRepository.save(counterEntity);


    }

    @Override
    @Scheduled(fixedDelay = 1000)
    @Transactional
    public void startLoopPersistCounter()
    {
        Counter counterEntity = counterRepository.findCounterById(configService.getCountIdInDB());
        counterEntity.setNumberCount(counterInRedis.get());
        counterRepository.save(counterEntity);
    }

    @Override
    public long count()
    {
        return counterInRedis.incrementAndGet();
    }
}
