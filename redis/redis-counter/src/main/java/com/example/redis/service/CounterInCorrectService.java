package com.example.redis.service;

public interface CounterInCorrectService {
    void startLoopPersistCounter();
    long count();
}
