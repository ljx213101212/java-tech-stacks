package com.ljx213101212.micro_recipient.controller;

import com.ljx213101212.micro_recipient.service.MessageStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
    private final MessageStorageService messageStorageService;

    @Value("${ab.testing.version}")
    private String version;

    public MessageController(MessageStorageService messageStorageService) {
        this.messageStorageService = messageStorageService;
    }

    @GetMapping("/message")
    public String getMessage() {
        String message = messageStorageService.getMessage();
        logger.info("[{}]: Returning and clearing messages: {}", version ,message);
        return message;
    }
}
