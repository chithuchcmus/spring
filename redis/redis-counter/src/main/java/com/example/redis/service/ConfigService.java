package com.example.redis.service;


public class ConfigService {
    private static final Long valueStart = 0L;
    private static final Long COUNTER_CORRECT = 1l;


    public static Long getStartCountValue() {
        return valueStart;
    }

    public static Long getCounter() {
        return COUNTER_CORRECT;
    }
}
