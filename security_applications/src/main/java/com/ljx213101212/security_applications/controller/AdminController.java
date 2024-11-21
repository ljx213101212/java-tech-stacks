package com.ljx213101212.security_applications.controller;

import com.ljx213101212.security_applications.model.User;
import com.ljx213101212.security_applications.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    UserServices usrservice;

    @GetMapping("/admin")
    public String getAdmin() {
        return "admin";
    }

    @GetMapping("/blocked-list")
    public String getBlockedList(Model model) {
        List<User> blockedUsers = usrservice.getAllLockedUser();

        model.addAttribute("blockedUsers", blockedUsers);
        return "blocked";
    }
}
