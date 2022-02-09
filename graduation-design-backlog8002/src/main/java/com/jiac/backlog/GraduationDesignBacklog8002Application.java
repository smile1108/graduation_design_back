package com.jiac.backlog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EntityScan("com.jiac.common.entity")
public class GraduationDesignBacklog8002Application {

    public static void main(String[] args) {
        SpringApplication.run(GraduationDesignBacklog8002Application.class, args);
    }

}
