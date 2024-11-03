package com.ljx213101212.micro_recipient.component;

import com.ljx213101212.micro_recipient.service.MessageStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitMessageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMessageConsumer.class);
    private final MessageStorageService messageStorageService;
    private final String queueName;

    public RabbitMessageConsumer(MessageStorageService messageStorageService, @Value("${rabbitmq.queue.name}") String queueName) {
        this.messageStorageService = messageStorageService;
        this.queueName = queueName;
    }

    // Uses SpEL (Spring Expression Language) to dynamically reference the queue name.
    @RabbitListener(queues = "#{'${rabbitmq.queue.name}'}")
    public void receiveMessage(String message) {
        logger.info("Received message from RabbitMQ: {}", message);
        messageStorageService.addMessage(message);
    }

}
