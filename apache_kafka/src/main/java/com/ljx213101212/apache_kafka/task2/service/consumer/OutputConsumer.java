package com.ljx213101212.apache_kafka.task2.service.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ljx213101212.apache_kafka.task2.config.Constants.OUTPUT_GROUP_ID;
import static com.ljx213101212.apache_kafka.task2.config.Constants.OUTPUT_TOPIC;


@Service
public class OutputConsumer {

    Logger log = LoggerFactory.getLogger(OutputConsumer.class);
    @KafkaListener(topics = OUTPUT_TOPIC, groupId = OUTPUT_GROUP_ID)
    @Transactional("kafkaTransactionManager")
    public void consumeOutput(ConsumerRecord<String, Double> record) {
        String key = record.key();
        Double value = record.value();
        System.out.println("Vehicle id: " + key + ", latest total distance: " + value);
        log.info("[OUTPUT] - Consumer - Vehicle id: " + key + ", latest total distance: " + value);
    }
}