package com.didong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = {MultipartAutoConfiguration.class})
@EnableEurekaClient
@EnableFeignClients
public class DidongVideoHttpApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DidongVideoHttpApplication.class, args);
    }

}
