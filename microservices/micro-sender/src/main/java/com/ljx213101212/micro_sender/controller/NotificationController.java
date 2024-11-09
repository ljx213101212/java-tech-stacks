package com.ljx213101212.micro_sender.controller;

import com.ljx213101212.micro_sender.model.dto.NotificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class NotificationController {

    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);
    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.template.default-receive-queue}")
    private String queueName;

    public NotificationController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/notification")
    public ResponseEntity<String> sendNotification(@RequestBody NotificationRequest request) {
        String message = String.format("User: %s, Message: %s", request.getUser(), request.getMessage());
        rabbitTemplate.convertAndSend(queueName, message);

        logger.info("Message sent to RabbitMQ: {}", message);
        return ResponseEntity.ok("Notification sent!");
    }
}