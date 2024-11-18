package com.ljx213101212.concurrency_and_multithreading.task3.runnable;

import com.ljx213101212.concurrency_and_multithreading.task3.model.Message;
import com.ljx213101212.concurrency_and_multithreading.task3.service.MessageBus;

public class Consumer implements Runnable {
    private final MessageBus messageBus;
    private final String topic;
    private final String consumerName;

    public Consumer(MessageBus messageBus, String topic, String consumerName) {
        this.messageBus = messageBus;
        this.topic = topic;
        this.consumerName = consumerName;
    }

    @Override
    public void run() {
        while (true) {
            Message message = messageBus.consumeMessage(topic);
            if (message != null) {
                System.out.println(consumerName + " consumed message: " + message + " " + "Queue length:" + messageBus.messageBusSize());
            }
        }
    }
}