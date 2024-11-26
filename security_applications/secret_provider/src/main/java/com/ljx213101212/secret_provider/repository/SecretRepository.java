package com.ljx213101212.secret_provider.repository;

import com.ljx213101212.secret_provider.model.Secret;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SecretRepository extends JpaRepository<Secret, Long> {
    Optional<Secret> findByToken(String token);
}
