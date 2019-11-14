package com.example.redis.entity;

import javax.persistence.*;

@Entity
@Table(name = "counter")
public class Counter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Long numberCount;

    public Counter()
    {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getNumberCount() {
        return numberCount;
    }

    public void setNumberCount(Long numberCount) {
        this.numberCount = numberCount;
    }
}
