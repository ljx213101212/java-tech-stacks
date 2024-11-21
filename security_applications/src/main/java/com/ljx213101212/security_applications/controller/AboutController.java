package com.ljx213101212.security_applications.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//Please note that the diff between RestController and Controller
@RestController
public class AboutController {
    @GetMapping("/about")
    public String getAbout() {
        return "This is About page, should be accessible by anyone";
    }
}
