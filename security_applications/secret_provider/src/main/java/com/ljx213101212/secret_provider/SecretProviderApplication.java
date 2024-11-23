package com.ljx213101212.secret_provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication()
public class SecretProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecretProviderApplication.class, args);
    }
}
