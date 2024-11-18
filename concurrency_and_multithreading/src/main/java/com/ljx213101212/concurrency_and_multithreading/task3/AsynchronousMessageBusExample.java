package com.ljx213101212.concurrency_and_multithreading.task3;

import com.ljx213101212.concurrency_and_multithreading.task3.runnable.Producer;
import com.ljx213101212.concurrency_and_multithreading.task3.runnable.Consumer;
import com.ljx213101212.concurrency_and_multithreading.task3.service.MessageBus;

public class AsynchronousMessageBusExample {

    public static void main(String[] args) {
        MessageBus messageBus = new MessageBus();

        // Create and start multiple producers
        for (int i = 1; i <= 1000; i++) {
            Producer producer = new Producer(messageBus, "Producer-" + i);
            new Thread(producer).start();
        }

        // Create and start multiple consumers
        for (int i = 1; i <= 3; i++) {
            Consumer consumer = new Consumer(messageBus, "Topic-" + i, "Consumer-" + i);
            new Thread(consumer).start();
        }
    }
}
