package com.jiac.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableDiscoveryClient
@EntityScan("com.jiac.common.entity")
@EnableFeignClients
@EnableAsync
public class GraduationDesignUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraduationDesignUserApplication.class, args);
    }

}
