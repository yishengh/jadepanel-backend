package com.jade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class JadeRegistryApplication {

    public static void main(String[] args) {
        SpringApplication.run(JadeRegistryApplication.class, args);
    }

}
