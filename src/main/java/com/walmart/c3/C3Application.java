package com.walmart.c3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@PropertySource(value = "${secrets.file}", ignoreResourceNotFound = true)
@ComponentScan(basePackages = {"com.walmart.gbs.common.microservice.filters", "com.walmart.c3"})
public class C3Application {

    public static void main(String[] args) {
        SpringApplication.run(C3Application.class, args);
    }
}
