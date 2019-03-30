package com.didong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class DidongVedioServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DidongVedioServiceApplication.class, args);
    }

}
