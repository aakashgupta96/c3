package com.walmart.c3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value="${secrets.file}",ignoreResourceNotFound = true)
public class C3Application {

    public static void main(String[] args) {
        SpringApplication.run(C3Application.class, args);
    }
}
