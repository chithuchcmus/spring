package com.example.redis.controller;

import com.example.redis.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CounterController {

    @Autowired
    private CounterService counterService;

    @GetMapping(path = "/ping")
    public String pingApi()
    {
        System.out.println(counterService.counter());
        return "ping";
    }
}
