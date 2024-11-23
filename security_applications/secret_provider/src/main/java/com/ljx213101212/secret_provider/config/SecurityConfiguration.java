package com.ljx213101212.secret_provider.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        System.out.println("Custom Security Filter Chain is being applied.");
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF protection
                .headers(header -> header.frameOptions(option -> option.disable())) // Disable X-Frame-Options
                .authorizeHttpRequests(requests -> requests
                        .anyRequest().permitAll()// Allow all requests without authentication
                );
//                .formLogin(login -> login.disable())
//                .httpBasic(basic -> basic.disable());
        return http.build();
    }
}
