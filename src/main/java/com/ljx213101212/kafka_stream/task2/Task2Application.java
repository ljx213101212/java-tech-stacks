package com.ljx213101212.kafka_stream.task2;

import com.ljx213101212.kafka_stream.task2.config.KafkaStreamsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Task2Application {


    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Task2Application.class);
        app.setWebApplicationType(WebApplicationType.NONE); // Set application type to non-web
        app.run(args);
    }
}
