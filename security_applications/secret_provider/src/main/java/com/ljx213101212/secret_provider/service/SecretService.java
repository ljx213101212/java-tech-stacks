package com.ljx213101212.secret_provider.service;

import com.ljx213101212.secret_provider.model.Secret;
import com.ljx213101212.secret_provider.repository.SecretRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Optional;
import java.util.UUID;

@Service
public class SecretService {

    @Autowired
    private SecretRepository secretRepository;

    public String createSecretLink(Secret secret, Optional<String> secretPath) {
        String token = UUID.randomUUID().toString();

        secret.setToken(token);
        secretRepository.save(secret);

        String effectiveSecretPath = secretPath.orElse("/secret/");

        // Build the full URL
        String link = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(effectiveSecretPath)
                .path(token)
                .toUriString();

        return link;
    }

    public Secret deleteSecret(String token) {
        Optional<Secret> secretOpt = secretRepository.findByToken(token);
        if (secretOpt.isPresent()) {
            Secret secret = secretOpt.get();
            // Delete the secret after viewing
            secretRepository.delete(secret);
            return secret;
        }
        return null;
    }
}
