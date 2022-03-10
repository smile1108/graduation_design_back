package com.jiac.comment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EntityScan("com.jiac.common.entity")
public class GraduationDesignComment8004Application {

    public static void main(String[] args) {
        SpringApplication.run(GraduationDesignComment8004Application.class, args);
    }

}
