package com.ljx213101212.micro_recipient.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MessageStorageService {

    private final List<String> messages = Collections.synchronizedList(new ArrayList<>());
    private final RabbitTemplate rabbitTemplate;
    private static final Logger logger = LoggerFactory.getLogger(MessageStorageService.class);

    @Value("${rabbitmq.queue.name}")
    private String queueName;

    @Value("${ab.testing.version}")
    private String version;

    public MessageStorageService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public String getMessage() {
        if (messages.isEmpty()) {
            return null; // Or throw an exception if appropriate
        }

        String message = messages.get(0);
        messages.remove(0);
        return message;
    }

    // Poll RabbitMQ for messages every N seconds (fixed rate can be configured)
    @Scheduled(fixedRateString = "${recipient.scheduler.interval}")
    public void fetchMessagesFromQueue() {
        while (true) {
            // Fetch a message from RabbitMQ queue
            String message = (String) rabbitTemplate.receiveAndConvert(queueName);

            if (message == null) {
                // No more messages to fetch, break the loop
                break;
            }

            logger.info("[{}]: Fetched message from RabbitMQ: {}", version, message);
            messages.add(message); // Add the fetched message to the in-memory list
        }
    }
}
