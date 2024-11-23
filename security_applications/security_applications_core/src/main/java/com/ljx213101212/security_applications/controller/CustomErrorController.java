package com.ljx213101212.security_applications.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController implements ErrorController {

    private static final Logger logger = LoggerFactory.getLogger(CustomErrorController.class);

    @GetMapping("/error")
    public String handleError(HttpServletRequest request) {
        // Log information about the request
        logger.error("Error occurred while processing request for URI: " + request.getRequestURI());

        // Get the HTTP status code
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        String errorMessage = (String) request.getAttribute("javax.servlet.error.message");
        logger.error("HTTP Status Code: " + statusCode);
        logger.error("Error Message: " + errorMessage);

        // Log current authentication details
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            logger.error("Authenticated user: " + authentication.getName());
            logger.error("Authorities: " + authentication.getAuthorities());
        } else {
            logger.error("No authenticated user found");
        }
        return "error";  // Return an error page (ensure an error.html template exists)
    }

    @GetMapping( "/custom-logout" )
    public void foo( final HttpServletRequest request ) throws ServletException
    {
        System.out.println( SecurityContextHolder.getContext().getAuthentication() );

        request.logout();

        System.out.println( SecurityContextHolder.getContext().getAuthentication() );
    }
}