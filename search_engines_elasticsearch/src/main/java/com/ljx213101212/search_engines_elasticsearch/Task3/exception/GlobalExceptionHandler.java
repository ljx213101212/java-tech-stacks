package com.ljx213101212.search_engines_elasticsearch.Task3.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.io.FileNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle generic exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex, WebRequest request) {
        // Log the exception details (you can use a logger instead of printing to console)
        System.err.println("Exception: " + ex.getMessage());

        // Return a generic error response
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred: " + ex.getMessage());
    }

    // Handle specific exceptions (e.g., FileNotFoundException)
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<String> handleFileNotFoundException(FileNotFoundException ex, WebRequest request) {
        // Log the exception details
        System.err.println("FileNotFoundException: " + ex.getMessage());

        // Return a 404 error response
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("File not found: " + ex.getMessage());
    }

    // Add more exception handlers as needed
}
