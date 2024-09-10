package com.ljx213101212.kafka_stream.task3;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class KafkaStreamService implements ApplicationRunner {

    @Value("${task3.kafka.stream.source-topic-1}")
    private String sourceTopic1;

    @Value("${task3.kafka.stream.source-topic-2}")
    private String sourceTopic2;

    @Autowired
    private KafkaStreamConfig config;

    //@Bean will be executed if its under stereotypes like @Configuration, @Component, @Service, @Repository, @Controller
    public void runKafkaLeftJoinStreams() {

        StreamsBuilder builder = new StreamsBuilder();

        KeyValueMapper<String, String, KeyValue<String, String>> keyValueMapper = (key, value) -> {

            if (value == null || value.length() == 1) {
                return new KeyValue<>("0x3f", "");
            }
            String newKey = value.split(":")[0];
            return new KeyValue<>(newKey, value.split(":")[1]);
        };

        KStream<String, String> stream1 = builder.stream(sourceTopic1, Consumed.with(Serdes.String(), Serdes.String()))
                .filter((key, value) -> value != null && value.contains(":"))
                .map(keyValueMapper);

        stream1.foreach((key, value) -> {
            System.out.println("Stream1: " + key + ":" + value);
        });

        KStream<String, String> stream2 = builder.stream(sourceTopic2, Consumed.with(Serdes.String(), Serdes.String()))
                .filter((key, value) -> value != null && value.contains(":"))
                .map(keyValueMapper);

        stream2.foreach((key, value) -> {
            System.out.println("Stream2: " + key + ":" + value);
        });

        JoinWindows joinWindows = JoinWindows.ofTimeDifferenceWithNoGrace(Duration.ofMinutes(1)).after(Duration.ofSeconds(30));

        KStream<String, String> joinedStream = stream1.join(stream2,
                (leftValue, rightValue) -> leftValue + " + " + rightValue,
                joinWindows,
                StreamJoined.with(Serdes.String(), Serdes.String(), Serdes.String()));

        joinedStream.foreach((key, value) -> System.out.println("Join Key: " + key + ", Value: " + value));

        final KafkaStreams streams = new KafkaStreams(builder.build(), config.getKafkaConfig());
        streams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        runKafkaLeftJoinStreams();
    }
}
