package com.ljx213101212.apache_kafka.task2.service;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.stereotype.Service;

import static com.ljx213101212.apache_kafka.task2.config.Constants.INPUT_TOPIC;
import static com.ljx213101212.apache_kafka.task2.config.Constants.OUTPUT_TOPIC;

@Service
public class TransactionService {

    @Bean
    public NewTopic transactionsTopic() {
        return TopicBuilder.name(INPUT_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic ordersTopic() {
        return TopicBuilder.name(OUTPUT_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }

//    @Bean
//    public KafkaTransactionManager<Object, Object> kafkaTransactionManager(ProducerFactory<Object, Object> producerFactory) {
//        return new KafkaTransactionManager<>(producerFactory);
//    }
}
