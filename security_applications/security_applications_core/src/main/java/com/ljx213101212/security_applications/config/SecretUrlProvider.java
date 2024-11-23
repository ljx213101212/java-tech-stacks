package com.ljx213101212.security_applications.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecretUrlProvider {

    @Value("${secret.provider.url}")
    private String secretBaseUrl;

    public String getCreateSecretUrl() {
        return secretBaseUrl + "/secret/new";
    }

    public String getViewSecretUrl() {
        return secretBaseUrl + "/secret";
    }
}
