package com.didong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@EnableEurekaClient
public class DidongVideoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DidongVideoServiceApplication.class, args);
    }

}
