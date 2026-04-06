package com.mouraadvocacia.api.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.mouraadvocacia.api")
public class Launch {

    public static void main(String[] args) {
        SpringApplication.run(Launch.class, args);
    }
}
