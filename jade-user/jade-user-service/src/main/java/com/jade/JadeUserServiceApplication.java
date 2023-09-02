package com.jade;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
//@EnableConfigurationProperties(JwtProperties.class)
@MapperScan("com.jade.user.mapper")
public class JadeUserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(JadeUserServiceApplication.class,args);
    }
}
