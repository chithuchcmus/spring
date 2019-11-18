package com.example.redis.repository;

import com.example.redis.entity.CounterRedis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CounterRedisRepository extends CrudRepository<CounterRedis,Long> {
}
