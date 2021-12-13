package com.learning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ConsulServer2Application {

    public static void main(String[] args) {
        SpringApplication.run(ConsulServer2Application.class, args);
    }

}
