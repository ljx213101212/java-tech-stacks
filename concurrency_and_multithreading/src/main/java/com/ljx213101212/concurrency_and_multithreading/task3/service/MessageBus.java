package com.ljx213101212.concurrency_and_multithreading.task3.service;

import com.ljx213101212.concurrency_and_multithreading.task3.model.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MessageBus {
    private final List<Message> messageQueue = new ArrayList<>();
    private final Random random = new Random();

    // Method for adding a message to the bus
    public synchronized void postMessage(Message message) {
        messageQueue.add(message);
        System.out.println("Posted message: " + message + " " + "Queue length: " + messageQueue.size());
        // Notify consumers that a new message is available
        notifyAll();
    }

    // Method for consuming messages on a specific topic
    public synchronized Message consumeMessage(String topic) {
        while (messageQueue.isEmpty() || !hasMessageForTopic(topic)) {
            try {
                wait(); // Wait until a message is available
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null; // Exit if interrupted
            }
        }

        // Find and remove the first message that matches the topic
        for (Message message : messageQueue) {
            if (message.getTopic().equals(topic)) {
                messageQueue.remove(message);

                return message;
            }
        }
        return null; // Should never reach here due to the while loop condition
    }

    public int messageBusSize() {
        return this.messageQueue.size();
    }

    private boolean hasMessageForTopic(String topic) {
        return messageQueue.stream().anyMatch(msg -> msg.getTopic().equals(topic));
    }
}
