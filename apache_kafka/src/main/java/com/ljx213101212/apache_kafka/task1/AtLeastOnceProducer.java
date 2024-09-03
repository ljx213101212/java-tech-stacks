package com.ljx213101212.apache_kafka.task1;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

public class AtLeastOnceProducer {
    private final Producer<String, String> producer;

    public AtLeastOnceProducer(String bootstrapServers) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        // Ensure at least once delivery
        props.put(ProducerConfig.ACKS_CONFIG, "all");

        this.producer = new KafkaProducer<>(props);
    }

    public void send(String topic, String key, String value) {
        producer.send(new ProducerRecord<>(topic, key, value));
        producer.flush(); // Ensure all messages are sent
    }

    public void close() {
        producer.close();
    }
}