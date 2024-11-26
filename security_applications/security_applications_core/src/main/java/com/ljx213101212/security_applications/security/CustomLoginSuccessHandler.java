package com.ljx213101212.security_applications.security;

import com.ljx213101212.security_applications.service.BruteForceLoginAttemptServices;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private BruteForceLoginAttemptServices userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        if (userDetails.getFailedAttempt() > 0) {
            userService.resetFailedAttempts(userDetails.getUsername());
        }

        // Custom redirect based on authority
        if (authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("VIEW_ADMIN"))) {
            response.sendRedirect("/admin");
        } else {
            response.sendRedirect("/info");
        }
        //super.onAuthenticationSuccess(request, response, authentication);
    }

}
