package com.example.demospringboottest.model;

import org.springframework.stereotype.Component;

@Component
public class Student {
    private String name;
    public Student()
    {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
