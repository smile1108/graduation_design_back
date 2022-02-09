package com.jiac.backlog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GraduationDesignBacklog8002Application {

    public static void main(String[] args) {
        SpringApplication.run(GraduationDesignBacklog8002Application.class, args);
    }

}
