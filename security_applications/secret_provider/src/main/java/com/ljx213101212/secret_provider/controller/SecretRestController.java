package com.ljx213101212.secret_provider.controller;

import com.ljx213101212.secret_provider.model.Secret;
import com.ljx213101212.secret_provider.service.SecretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/secret")
public class SecretRestController {

    @Autowired
    private SecretService secretService;

    @PostMapping("/new")
    public ResponseEntity<String> submitSecret(@RequestBody String secret) {
        // Generate the secret link
        Secret secretObj = new Secret();
        secretObj.setToken(UUID.randomUUID().toString());
        secretObj.setContent(secret);
        String link = secretService.createSecretLink(secretObj, Optional.empty());
        return ResponseEntity.status(HttpStatus.CREATED).body(link); // Return the link as the response
    }

    @GetMapping("/{token}")
    public ResponseEntity<String> viewSecret(@PathVariable String token) {

        Secret secretOpt = secretService.deleteSecret(token);
        if (secretOpt != null) {
            return ResponseEntity.ok(secretOpt.getContent()); // Return the secret content
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Secret not found");
        }
    }
}
