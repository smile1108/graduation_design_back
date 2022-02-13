package com.jiac.article;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EntityScan("com.jiac.common.entity")
@EnableFeignClients
public class GraduationDesignArticle8003Application {

    public static void main(String[] args) {
        SpringApplication.run(GraduationDesignArticle8003Application.class, args);
    }

}
