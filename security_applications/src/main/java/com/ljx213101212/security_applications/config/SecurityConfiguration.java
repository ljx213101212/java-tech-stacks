package com.ljx213101212.security_applications.config;

import com.ljx213101212.security_applications.security.CustomLoginFailureHandler;
import com.ljx213101212.security_applications.security.CustomLoginSuccessHandler;
import com.ljx213101212.security_applications.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.context.request.RequestContextListener;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration{

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private CustomLoginFailureHandler loginFailureHandler;

    @Autowired
    private CustomLoginSuccessHandler loginSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authz) ->
                        authz.requestMatchers("/css/**", "/js/**", "/images/**", "/about", "/login", "/logout", "/error").permitAll()
                                .requestMatchers("/info").hasAuthority("VIEW_INFO")
                                .requestMatchers("/admin", "/blocked-list").hasAuthority("VIEW_ADMIN")
                                .anyRequest().authenticated())  // Require authentication for all requests
                .formLogin(form -> form
                        .loginPage("/login") // Specify custom login page
                        .failureHandler(loginFailureHandler)
                        .successHandler(loginSuccessHandler)
                        .permitAll()
                );
//  Logout handler is added by default, no need for custom logout handler here
//                .logout(logout -> {
//                    logout.addLogoutHandler((request, response, authentication) -> {
//                        System.out.println(request.getRequestURI() + response.toString());
//
//                    });
//                })

        // If you want to allow logout via a GET request (e.g., http://localhost:8080/logout), CSRF protection must be disabled.
        // Otherwise, the CsrfFilter will block the logout operation, and /logout will be treated as a resource request instead of a state-changing operation, causing a redirect to /error.
        // .csrf(csrf -> csrf.disable());
        return http.build();
    }

//    @Bean
//    public BruteForceProtectionFilter bruteForceProtectionFilter() {
//        return new BruteForceProtectionFilter();
//    }

    //Use salt and hashing to store user passwords.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //authenticate users by loading user data from a data source and verifying the password using the specified
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    //By defining this bean, you make the AuthenticationManager available to other beans or components in the application.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    @ConditionalOnMissingBean(RequestContextListener.class)
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }
}
