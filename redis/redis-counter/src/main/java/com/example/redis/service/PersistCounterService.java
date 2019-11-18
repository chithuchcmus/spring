package com.example.redis.service;

import com.example.redis.entity.Counter;
import com.example.redis.entity.CounterRedis;
import com.example.redis.repository.CounterRedisRepository;
import com.example.redis.repository.CounterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersistCounterService {

    @Autowired
    CounterRepository counterRepository;

    @Autowired
    CounterRedisRepository counterRedisRepository;

    @Scheduled(fixedDelay = 1000)
    @Transactional
    public void startLoopPersistCounter() {
//        Counter counterInDb = counterRepository.findCounterById(ConfigService.getCounter());
//        CounterRedis counterInRedis = counterRedisRepository.findById(ConfigService.getCounter()).get();
//        counterInDb.setNumberCount(counterInRedis.getCount().get());
//        counterRepository.save(counterInDb);
    }
}
