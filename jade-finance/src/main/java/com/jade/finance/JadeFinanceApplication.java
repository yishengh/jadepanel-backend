package com.jade.finance;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.jade.finance.mapper")
public class JadeFinanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(JadeFinanceApplication.class, args);
    }

}
