package com.ljx213101212.micro_collector.service;


import com.ljx213101212.micro_collector.client.RecipientClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageCollectorService {

    private static final Logger logger = LoggerFactory.getLogger(MessageCollectorService.class);
    private final RecipientClient recipientClient;

    public MessageCollectorService(RecipientClient recipientClient) {
        this.recipientClient = recipientClient;
    }

    @Scheduled(fixedRateString = "${collector.scheduler.interval}")
    public void collectMessages() {
        try {
            List<String> messages = recipientClient.getMessages();
            if (messages != null && !messages.isEmpty()) {
                logger.info("Collected messages from recipient: {}", messages);
            } else {
                logger.info("No messages to collect at this time.");
            }
        } catch (Exception e) {
            logger.error("Failed to collect messages from recipient: {}", e.getMessage());
        }
    }
}