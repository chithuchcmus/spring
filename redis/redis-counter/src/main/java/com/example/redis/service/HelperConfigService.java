package com.example.redis.service;


import org.springframework.stereotype.Component;

@Component
public class HelperConfigService {
    private  final String redisServerAddress = "127.0.0.1:6379";

    private  final long valueStart = 0L;
    private final Integer CountInDB = 1;

    public  String getRedisServerAddress() {
        return redisServerAddress;
    }


    public  long getStartCountValue() {
        return valueStart;
    }

    public Integer getCountIdInDB() {
        return CountInDB;
    }
}
