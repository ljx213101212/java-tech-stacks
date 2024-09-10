package com.ljx213101212.kafka_stream.task2.service;

import com.ljx213101212.kafka_stream.task2.config.KafkaStreamsConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

@Service
public class OutputService {

    @Autowired
    private KafkaStreamsConfig config;

    public KafkaStreamsConfig getConfig() {
        return config;
    }

    public void performTask(StreamsBuilder builder) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, config.getApplicationIdConfig());
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, config.getBootstrapServerConfig());
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());


        KStream<Integer, String> sourceStream = builder.stream(config.getSourceTopic(), Consumed.with(Serdes.String(), Serdes.String()))
                .filter((key, value) -> value != null && !value.trim().isEmpty()) //filter the data by value that the value is not null
                .flatMap((key, value) -> Arrays.stream(value.split("\\s+"))
                        .map(word -> new KeyValue<>(word.length(), word))
                        .toList());

        //print to the console every message after it all
        sourceStream.foreach((key, value) -> System.out.println("Key: " + key + ", Value: " + value));
        // Split the stream into short and long words
        Map<String, KStream<Integer, String>> branches = sourceStream.split(Named.as("words-"))
                .branch((key, value) -> key < 10 && value.contains("a"), Branched.as("short"))
                .branch((key, value) -> key >= 10 && value.contains("a"), Branched.as("long"))
                .noDefaultBranch();

        KStream<Integer, String> shortWords = branches.get("words-short");
        KStream<Integer, String> longWords = branches.get("words-long");


        shortWords.foreach((key, value) -> System.out.println("[ShortWords]: " + key + " --- " + value));
        longWords.foreach((key, value) -> System.out.println("[LongWords]: " + key + " --- " + value));

        // Merge and output the results
        KStream<Integer,String> mergedStream = shortWords.merge(longWords);
        mergedStream.foreach((key, value) -> System.out.println("[OverAll]: " + value + " with length: " + key));

        mergedStream.to(config.getOutputTopic(),  Produced.with(Serdes.Integer(), Serdes.String()));

        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();

        // Add shutdown hook to respond to SIGTERM and gracefully close Kafka Streams
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
