package com.ljx213101212.security_applications.controller;

import com.ljx213101212.security_applications.config.SecretUrlProvider;
import com.ljx213101212.security_applications.model.User;
import com.ljx213101212.security_applications.security.CustomUserDetails;
import com.ljx213101212.security_applications.service.BruteForceLoginAttemptServices;
import com.ljx213101212.security_applications.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    BruteForceLoginAttemptServices loginAttemptService;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    SecretUrlProvider secretUrlProvider;

    @GetMapping("/admin")
    public String getAdmin(Model model) {

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
//            //check if current login user has authority "STANDARD",
//            if (customUserDetailsService.hasStandardAuthority(userDetails)) {
//                model.addAttribute("secretUrl", secretUrlProvider.getSecretUrl());
//            }
//        }
        return "admin";
    }

    @GetMapping("/blocked-list")
    public String getBlockedList(Model model) {
        List<User> blockedUsers = loginAttemptService.getAllLockedUser();

        model.addAttribute("blockedUsers", blockedUsers);
        return "blocked";
    }
}
