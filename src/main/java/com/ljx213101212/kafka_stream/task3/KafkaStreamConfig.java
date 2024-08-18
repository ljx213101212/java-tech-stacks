package com.ljx213101212.kafka_stream.task3;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import java.util.Properties;

@Configuration
public class KafkaStreamConfig {

    @Value("${task3.kafka.stream.application-id}")
    private String applicationIdConfig;
    @Value("${task3.kafka.stream.bootstrap-server}")
    private String bootstrapServerConfig;

    public Properties getKafkaConfig() {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationIdConfig);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServerConfig);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        return props;
    }
}