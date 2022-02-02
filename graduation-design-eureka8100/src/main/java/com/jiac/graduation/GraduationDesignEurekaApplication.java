package com.jiac.graduation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class GraduationDesignEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraduationDesignEurekaApplication.class, args);
    }

}
