package com.ljx213101212.micro_recipient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MicroRecipientApplication {
    public static void main(String[] args) {
        SpringApplication.run(MicroRecipientApplication.class, args);
    }
}
