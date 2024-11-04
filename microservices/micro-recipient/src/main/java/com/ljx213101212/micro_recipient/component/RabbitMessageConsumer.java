package com.ljx213101212.micro_recipient.component;

import com.ljx213101212.micro_recipient.service.MessageStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Counter;


@Component
public class RabbitMessageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMessageConsumer.class);
    private final MessageStorageService messageStorageService;
    private final String queueName;

    //custom prometheus metrics
    private final Counter messagesReceivedCounter;

    public RabbitMessageConsumer(MessageStorageService messageStorageService, @Value("${rabbitmq.queue.name}") String queueName, MeterRegistry registry) {
        this.messageStorageService = messageStorageService;
        this.queueName = queueName;
        this.messagesReceivedCounter = registry.counter("messages_received_total");
    }

    // Uses SpEL (Spring Expression Language) to dynamically reference the queue name.
    @RabbitListener(queues = "#{'${rabbitmq.queue.name}'}")
    public void receiveMessage(String message) {
        logger.info("Received message from RabbitMQ: {}", message);

        // Increment the counter each time a message is received
        messagesReceivedCounter.increment();
        messageStorageService.addMessage(message);
    }

}
