package com.ljx213101212.micro_visualizer.controller;

import com.ljx213101212.micro_visualizer.model.Message;
import com.ljx213101212.micro_visualizer.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/saved-messages")
    public List<String> getMessages() {
        List<String> messages = messageService.getMessages().stream().map(Message::getMessageContent).collect(Collectors.toList());
        logger.info("Fetching messages from postgres: {}", messages);
        return messages;
    }
}
