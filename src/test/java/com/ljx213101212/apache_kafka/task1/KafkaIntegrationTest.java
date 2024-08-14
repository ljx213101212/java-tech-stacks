package com.ljx213101212.apache_kafka.task1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.concurrent.ExecutionException;


@Testcontainers
public class KafkaIntegrationTest {

    public static String TEST_TOPIC_NAME = "test-topic";
    public static String TEST_KEY = "test-key";
    public static String TEST_VALUE = "Hello, World!";

    @Test
    public void testAtLeastOnceAtMostOnce() throws InterruptedException, ExecutionException {

        AtLeastOnceProducer producer = new AtLeastOnceProducer(KafkaProperties.BOOTSTRAP_SERVERS);
        AtMostOnceConsumer consumer = new AtMostOnceConsumer(KafkaProperties.BOOTSTRAP_SERVERS, TEST_TOPIC_NAME);

        String consumedValue = consumer.consumeOnce();
        producer.send(TEST_TOPIC_NAME, TEST_KEY, TEST_VALUE);
        producer.close();

        Assertions.assertEquals(consumedValue, TEST_VALUE);
    }
}