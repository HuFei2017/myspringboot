package com.learning.exporter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class ExporterApplication {

    public static void main(String[] args) throws IOException {
//        new HTTPServer(1234);
//        new PortMonitorCollector().register();
        SpringApplication.run(ExporterApplication.class, args);
    }

}
