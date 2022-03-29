package com.jiac.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EntityScan("com.jiac.common.entity")
@EnableFeignClients
public class GraduationDesignChat8005Application {

    public static void main(String[] args) {
        SpringApplication.run(GraduationDesignChat8005Application.class, args);
    }

}
