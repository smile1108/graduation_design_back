package com.jiac.article;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GraduationDesignArticle8003Application {

    public static void main(String[] args) {
        SpringApplication.run(GraduationDesignArticle8003Application.class, args);
    }

}
