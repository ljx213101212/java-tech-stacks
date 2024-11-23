package com.ljx213101212.security_applications.controller;

import com.ljx213101212.security_applications.config.SecretUrlProvider;
import com.ljx213101212.security_applications.security.CustomUserDetails;
import com.ljx213101212.security_applications.service.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequestMapping("/secret")
public class SecretController {

    private static final Logger logger = LoggerFactory.getLogger(SecretController.class);

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    SecretUrlProvider secretUrlProvider;


    @GetMapping("/new")
    public String showSecretForm(Model model) {
        return "secret_form";
    }

    @PostMapping("/new")
    public String submitSecret(@RequestParam("secret") String secret, HttpServletRequest request, Model model) {

        String createSecretUrl = secretUrlProvider.getCreateSecretUrl();

        try {
            // Create headers specifying that the request body is plain text
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create the request entity with the headers and body
            HttpEntity<String> requestEntity = new HttpEntity<>(secret, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(createSecretUrl, requestEntity, String.class);
            String link = response.getBody();

            // Extract the token from the link
            String token = link.substring(link.lastIndexOf("/") + 1);
            // Add the token to the model
            model.addAttribute("token", token);

            // Get the current endpoint (base URL)
            String endpoint = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            model.addAttribute("endpoint", endpoint);
            return "secret_link";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to create secret: " + e.getMessage());
            logger.error("error: " + "Failed to create secret: " + e.getMessage());
            return "error";
        }

    }


    @GetMapping("/{token}")
    public String viewSecret(@PathVariable String token, Model model) {

        //Check if current login user is authorized to see the secret
        CustomUserDetails userDetails =  customUserDetailsService.getCurrentLoginUser();
        //if not have StandardAuthority, direct to not authorized page.
        if (userDetails == null || !customUserDetailsService.hasStandardAuthority(userDetails)) {
            model.addAttribute("username", userDetails.getUsername());
            return "secret_not_authorized";
        }
        String viewSecretUrl = UriComponentsBuilder.fromHttpUrl(secretUrlProvider.getViewSecretUrl())
                                            .pathSegment(token)
                                            .toUriString();
        try {
            //please append token to viewSecretUrl
            ResponseEntity<String> response = restTemplate.getForEntity(viewSecretUrl, String.class);

            String secret = response.getBody();
            HttpStatusCode statusCode = response.getStatusCode();
            model.addAttribute("content", secret);
            return "secret_view";
        } catch (Exception ex) {
            logger.error("error: " + "secret not found -> " + ex.getMessage());
            return "secret_not_found";
        }
    }
}
