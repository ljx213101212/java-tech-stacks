package com.ljx213101212.kafka_stream.task1.service;

import com.ljx213101212.kafka_stream.task1.config.KafkaStreamsConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.stereotype.Service;

@Service
public class TopicMigrator {

    public static KafkaStreams buildKafkaStreams(final KafkaStreamsConfig config) {
        // Define the processing topology
        final StreamsBuilder builder = new StreamsBuilder();
        final KStream<String, String> input = builder.stream(config.getInputTopic());
        input.to(config.getOutputTopic(), Produced.with(Serdes.String(), Serdes.String()));
        return new KafkaStreams(builder.build(), config.kStreamsConfigs());
    }

    public static void resetKafkaStreams(final KafkaStreams streams) {
        streams.cleanUp();
    }
}
