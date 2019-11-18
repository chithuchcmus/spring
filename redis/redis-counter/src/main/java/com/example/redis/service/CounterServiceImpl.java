package com.example.redis.service;

import com.example.redis.entity.CounterRedis;
import com.example.redis.repository.CounterRedisRepository;
import com.example.redis.repository.CounterRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CounterServiceImpl implements CounterService {

    @Autowired
    CounterRepository counterRepository;

    @Autowired
    CounterRedisRepository counterRedisRepository;

    public CounterServiceImpl() {

    }

    @Override
    public long count() {
        return counterRedisRepository.findById(ConfigService.getCounter()).get()
                .getCount();
    }
}
