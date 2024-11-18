package com.ljx213101212.concurrency_and_multithreading.task3.runnable;

import com.ljx213101212.concurrency_and_multithreading.task3.model.Message;
import com.ljx213101212.concurrency_and_multithreading.task3.service.MessageBus;

import java.util.Random;

public class Producer implements Runnable {
    private final MessageBus messageBus;
    private final String producerName;
    private final Random random = new Random();

    public Producer(MessageBus messageBus, String producerName) {
        this.messageBus = messageBus;
        this.producerName = producerName;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(random.nextInt(1000)); // Random delay between messages
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

            // Create a random message
            String topic = "Topic-" + (random.nextInt(3) + 1); // Topics: Topic-1, Topic-2, Topic-3
            String payload = producerName + " - Message " + random.nextInt(100);
            Message message = new Message(topic, payload);

            // Post the message to the bus
            messageBus.postMessage(message);
        }
    }
}