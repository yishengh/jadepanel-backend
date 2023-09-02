package com.jade.report.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {
    @Autowired
    StringRedisTemplate redisTemplate;

    @GetMapping("reportOne")
    public String reportOne(Integer id){
        return "true";
    }
}
