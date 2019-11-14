package com.example.redis.service;

import com.example.redis.entity.Counter;
import com.example.redis.repository.CounterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicLong;


@Service
public class CounterInCorrectServiceImpl implements CounterInCorrectService {

    private AtomicLong counter;

    @PostConstruct
    public void prepareConfig()
    {
        counter  = new AtomicLong(configService.getStartCountValue());
        Counter counterEntity = new Counter();
        counterEntity.setNumberCount(counter.get());
        counterRepository.save(counterEntity);
    }

    @Autowired
    private CounterRepository counterRepository;

    @Autowired
    private ConfigService configService;

    @Override
    @Transactional
    @Scheduled(fixedDelay = 1000)
    public void startLoopPersistCounter() {
        Counter counterEntity = counterRepository.findCounterById(configService.getCountIdInDB());
        counterEntity.setNumberCount(counter.get());
        counterRepository.save(counterEntity);
    }

    @Override
    public long count() {
       return counter.incrementAndGet();
    }
}
