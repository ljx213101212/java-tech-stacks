package com.ljx213101212.kafka_stream.task2.config;

import lombok.Data;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class KafkaStreamsConfig {

    @Value("${task2.kafka.stream.application-id}")
    private String applicationIdConfig;
    @Value("${task2.kafka.stream.bootstrap-server}")
    private String bootstrapServerConfig;
    @Value("${task2.kafka.stream.source-topic}")
    private String sourceTopic;
    @Value("${task2.kafka.stream.output-topic}")
    private String outputTopic;

//    private String defaultKeySerdeClassConfig;
//    private String defaultValueSerdeClassConfig;

}
