package com.didong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableDiscoveryClient
@SpringBootApplication
@EnableEurekaClient
@EnableScheduling
public class DidongVideoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DidongVideoServiceApplication.class, args);
    }

}
