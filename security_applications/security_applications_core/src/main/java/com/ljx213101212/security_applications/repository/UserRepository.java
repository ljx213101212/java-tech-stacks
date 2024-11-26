package com.ljx213101212.security_applications.repository;

import com.ljx213101212.security_applications.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);

    @Query("UPDATE User u SET u.failedAttempt = ?1 WHERE u.email = ?2")
    @Modifying
    public void updateFailedAttempts(int failAttempts, String email);

    List<User> findByAccountNonLockedFalse();

    //Add one method to fetch user who has authority "STANDARD"
    @Query("SELECT u FROM User u JOIN u.authorities a WHERE a.authority = 'STANDARD'")
    List<User> findByAuthorityStandard();
}
