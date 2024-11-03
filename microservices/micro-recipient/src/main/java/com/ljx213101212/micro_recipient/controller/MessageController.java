package com.ljx213101212.micro_recipient.controller;

import com.ljx213101212.micro_recipient.service.MessageStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
    private final MessageStorageService messageStorageService;

    public MessageController(MessageStorageService messageStorageService) {
        this.messageStorageService = messageStorageService;
    }

    @GetMapping("/message")
    public List<String> getMessages() {
        List<String> messages = messageStorageService.getMessages();
        logger.info("Returning and clearing messages: {}", messages);
        return messages;
    }
}
