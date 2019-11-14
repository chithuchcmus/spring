package com.example.redis.controller;

import com.example.redis.service.CounterCorrectService;
import com.example.redis.service.CounterCorrectServiceImpl;
import com.example.redis.service.CounterInCorrectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CounterController {

    @Autowired
    private CounterCorrectService counterCorrectService;


    @Autowired
    private CounterInCorrectService counterInCorrectService;

    @GetMapping(path = "/ping")
    @Transactional
    public String pingApi()
    {
        System.out.println(counterCorrectService.count());
        return "ping";
    }

    @GetMapping(path = "/wrong-ping")
    @Transactional
    public String inCorrectPingApi()
    {
        System.out.println(counterInCorrectService.count());
        return "ping";
    }
}
