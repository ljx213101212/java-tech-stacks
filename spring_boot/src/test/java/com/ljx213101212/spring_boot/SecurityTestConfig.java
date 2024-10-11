package com.ljx213101212.spring_boot;//package com.ljx213101212.spring_boot_3_2024;
//
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@TestConfiguration
//public class SecurityTestConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http.csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("**").permitAll()
//                        .anyRequest().authenticated()
//                ).build();
//    }
//
//}