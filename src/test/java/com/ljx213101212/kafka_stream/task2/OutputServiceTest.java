package com.ljx213101212.kafka_stream.task2;

import com.ljx213101212.kafka_stream.task2.service.OutputService;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class OutputServiceTest {

    TopologyTestDriver topologyTestDriver = null;
    TestInputTopic<String, String> testInputTopic = null;
    TestOutputTopic<Integer, String> testOutputTopic = null;

    @Value("${task2.kafka.stream.source-topic}")
    private String sourceTopic;

    @Value("${task2.kafka.stream.output-topic}")
    private String outputTopic;

    @Autowired
    OutputService outputTopology;

    StreamsBuilder streamsBuilder;

    @BeforeEach
    void setUp() {
        streamsBuilder = new StreamsBuilder();
        //start streaming
        outputTopology.performTask(streamsBuilder);
        topologyTestDriver = new TopologyTestDriver(streamsBuilder.build());

        testInputTopic = topologyTestDriver.createInputTopic(
                sourceTopic, Serdes.String().serializer(),
                Serdes.String().serializer());
        testOutputTopic = topologyTestDriver.createOutputTopic(
                outputTopic,
                Serdes.Integer().deserializer(),
                Serdes.String().deserializer());
    }

    @AfterEach
    void tearDown() {
        topologyTestDriver.close();
    }

    @Test
    public void testShortWord() {
        testInputTopic.pipeInput("short", "today is a good day");
        var output = testOutputTopic.getQueueSize();
        assertEquals(3, output); //since only today, a, day contains 'a'

        var outputValues = testOutputTopic.readKeyValuesToList();
        List<KeyValue<Integer, String>> expectedList = List.of(KeyValue.pair(5, "today"), KeyValue.pair(1, "a"), KeyValue.pair(3, "day"));

        for (int i = 0; i < expectedList.size(); i++) {
            var pair = expectedList.get(i);
            assertEquals(pair.key, outputValues.get(i).key);
            assertEquals(pair.value, outputValues.get(i).value);
        }
    }

    @Test
    public void testLongWord() {
        testInputTopic.pipeInput("long", "asdfghj1234 abc zxcvbn1234 qwertyui890 short");
        var output = testOutputTopic.getQueueSize();
        assertEquals(2, output); //since only asdfghj1234 abc contains 'a'

        var outputValues = testOutputTopic.readKeyValuesToList();
        List<KeyValue<Integer, String>> expectedList = List.of(KeyValue.pair("asdfghj1234".length(), "asdfghj1234"), KeyValue.pair("abc".length(), "abc"));

        for (int i = 0; i < expectedList.size(); i++) {
            var pair = expectedList.get(i);
            assertEquals(pair.key, outputValues.get(i).key);
            assertEquals(pair.value, outputValues.get(i).value);
        }
    }
}