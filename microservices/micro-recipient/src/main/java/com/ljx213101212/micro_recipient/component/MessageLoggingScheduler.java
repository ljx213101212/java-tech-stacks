package com.ljx213101212.micro_recipient.component;

import com.ljx213101212.micro_recipient.service.MessageStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageLoggingScheduler {

    private static final Logger logger = LoggerFactory.getLogger(MessageLoggingScheduler.class);
    private final MessageStorageService messageStorageService;
    private final long interval;

    public MessageLoggingScheduler(MessageStorageService messageStorageService,  @Value("${recipient.scheduler.interval}") long interval) {
        this.messageStorageService = messageStorageService;
        this.interval = interval;
    }

    @Scheduled(fixedRateString = "${recipient.scheduler.interval}")
    public void logMessages() {
        List<String> messages = messageStorageService.getMessages();
        if (!messages.isEmpty()) {
            logger.info("Logging messages from storage: {}", messages);
        } else {
            logger.info("No messages to log at this time.");
        }
    }
}
