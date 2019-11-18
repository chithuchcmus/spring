package com.example.redis.entity;

import org.hibernate.annotations.Columns;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

@RedisHash("counter_redis")
public class CounterRedis implements Serializable {

    @Id
    private Long id;

    @JsonPr
    private Long count;

    public CounterRedis() {

    }

    public CounterRedis(Long id, Long count) {
        this.id = id;
        this.count = count;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
