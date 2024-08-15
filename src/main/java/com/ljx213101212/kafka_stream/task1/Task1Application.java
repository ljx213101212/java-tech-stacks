package com.ljx213101212.kafka_stream.task1;


import com.ljx213101212.kafka_stream.task1.config.KafkaStreamsConfig;
import org.apache.kafka.streams.KafkaStreams;

import static com.ljx213101212.kafka_stream.task1.service.TopicMigrator.buildKafkaStreams;

public class Task1Application {


    public static void main(String[] args) {
        final boolean doReset = args.length > 1 && args[1].equals("--reset");

        final KafkaStreams streams = buildKafkaStreams(new KafkaStreamsConfig());
        // Add shutdown hook to respond to SIGTERM and gracefully close Kafka Streams
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

        // Delete the application's local state on reset
        if (doReset) {
            streams.cleanUp();
        }
        streams.start();
        // Add shutdown hook to respond to SIGTERM and gracefully close Kafka Streams
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }

}
