package com.ljx213101212.kafka_stream.task3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Task3Application {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Task3Application.class);
        app.setWebApplicationType(WebApplicationType.NONE); // Set application type to non-web
        app.run(args);
    }
}
