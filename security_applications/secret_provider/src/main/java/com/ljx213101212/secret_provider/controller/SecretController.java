package com.ljx213101212.secret_provider.controller;

import com.ljx213101212.secret_provider.model.Secret;
import com.ljx213101212.secret_provider.service.SecretService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/secret-internal")
public class SecretController {

    @Autowired
    private SecretService secretService;

    @GetMapping("/new")
    public String showSecretForm(Model model) {
        model.addAttribute("secret", new Secret());
        return "secret_form";
    }

    @PostMapping("/new")
    public String submitSecret(@ModelAttribute Secret secret, HttpServletRequest request, Model model) {
        String link = secretService.createSecretLink(secret, Optional.of("/secret-internal/"));
        model.addAttribute("link", link);
        return "secret_link";
    }


    @GetMapping("/{token}")
    public String viewSecret(@PathVariable String token, Model model) {
        Secret secretOpt = secretService.deleteSecret(token);
        if (secretOpt != null) {
            model.addAttribute("content", secretOpt.getContent());
            return "secret_view";
        } else {
            return "secret_not_found";
        }
    }
}
