package com.ljx213101212.micro_collector.service;


import com.ljx213101212.micro_collector.client.RecipientClient;
import com.ljx213101212.micro_collector.model.Message;
import com.ljx213101212.micro_collector.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageCollectorService {

    private static final Logger logger = LoggerFactory.getLogger(MessageCollectorService.class);
    private final RecipientClient recipientClient;

    private final MessageRepository messageRepository;

    public MessageCollectorService(RecipientClient recipientClient, MessageRepository messageRepository) {
        this.recipientClient = recipientClient;
        this.messageRepository = messageRepository;
    }

    @Scheduled(fixedRateString = "${collector.scheduler.interval}")
    public void collectMessages() {
        try {
            String message = recipientClient.getMessage();
            if (message != null) {
                logger.info("Collected message from recipient: {}", message);
                saveMessage(message);
            } else {
                logger.info("No messages to collect at this time.");
            }
        } catch (Exception e) {
            logger.error("Failed to collect messages from recipient: {}", e.getMessage());
        }
    }

    private void saveMessage(String messageContent) {
        Message messageEntity = new Message();
        messageEntity.setMessageContent(messageContent);
        messageRepository.save(messageEntity);
    }
}