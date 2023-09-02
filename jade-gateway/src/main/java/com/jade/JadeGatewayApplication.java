package com.jade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class JadeGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(JadeGatewayApplication.class, args);
    }

}
