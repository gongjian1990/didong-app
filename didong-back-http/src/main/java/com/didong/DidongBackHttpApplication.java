package com.didong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = {MultipartAutoConfiguration.class})
@EnableEurekaClient
@EnableFeignClients
public class DidongBackHttpApplication {

    public static void main(String[] args) {
        SpringApplication.run(DidongBackHttpApplication.class, args);
    }

}
