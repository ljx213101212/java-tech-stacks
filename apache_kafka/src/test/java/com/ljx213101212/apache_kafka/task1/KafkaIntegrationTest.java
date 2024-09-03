package com.ljx213101212.apache_kafka.task1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;


@Testcontainers
public class KafkaIntegrationTest {

    public static String TEST_TOPIC_NAME = "test-topic";
    public static String TEST_KEY = "test-key";
    public static String TEST_VALUE = "Hello, World!";

    @Test
    public void testAtLeastOnceAtMostOnce() throws InterruptedException, ExecutionException {

        AtLeastOnceProducer producer = new AtLeastOnceProducer(KafkaProperties.BOOTSTRAP_SERVERS);
        AtMostOnceConsumer consumer = new AtMostOnceConsumer(KafkaProperties.BOOTSTRAP_SERVERS, TEST_TOPIC_NAME);

        AtomicReference<String> consumedValue = new AtomicReference<>("");
        // Create a thread to execute consumer.consumeOnce()
        Thread consumerThread = new Thread(() -> {
            try {
                consumedValue.set(consumer.consumeOnce());
                System.out.println("Consumed Value: " + consumedValue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Start the consumer thread
        consumerThread.start();
        producer.send(TEST_TOPIC_NAME, TEST_KEY, TEST_VALUE);
        producer.close();

        // Wait for the consumer thread to complete before exiting the main thread
        try {
            consumerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Main thread interrupted while waiting for consumer thread to finish.");
        }

        Assertions.assertEquals(consumedValue.get(), TEST_VALUE);
    }
}