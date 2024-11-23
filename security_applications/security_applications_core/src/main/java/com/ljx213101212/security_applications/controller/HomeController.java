package com.ljx213101212.security_applications.controller;

import org.springframework.security.core.Authentication;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

@Controller
public class HomeController {

    @GetMapping("/")
    public void handleRootRequest(Authentication authentication, HttpServletResponse response) throws Exception {
        if (authentication != null && authentication.isAuthenticated()) {
            Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

            if (roles.contains("VIEW_ADMIN")) {
                response.sendRedirect("/admin");
            } else if (roles.contains("VIEW_INFO")) {
                response.sendRedirect("/info");
            } else {
                response.sendRedirect("/default"); // Default redirection if needed
            }
        } else {
            response.sendRedirect("/login"); // Redirect to login if not authenticated
        }
    }
}
