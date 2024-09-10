package com.ljx213101212.kafka_stream.task4;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class Task4ApplicationTest {

    TopologyTestDriver topologyTestDriver = null;
    TestInputTopic<String, Employee> testInputTopic = null;
    TestOutputTopic<String, Employee> testOutputTopic = null;

    @Value("${task4.kafka.stream.source-topic}")
    private String sourceTopic;

    @Value("${task4.kafka.stream.output-topic}")
    private String outputTopic;

    @Autowired
    Task4Application outputTopology;

    StreamsBuilder streamsBuilder;


    @BeforeEach
    void setUp() throws ExecutionException, InterruptedException {
        streamsBuilder = new StreamsBuilder();
        //create stream config
        outputTopology.runAsync(streamsBuilder).get();
        topologyTestDriver = new TopologyTestDriver(streamsBuilder.build());
        Serde<Employee> employeeSerde = Serdes.serdeFrom(new EmployeeSerializer(), new EmployeeDeserializer());
        testInputTopic = topologyTestDriver.createInputTopic(
                sourceTopic, Serdes.String().serializer(),
                employeeSerde.serializer());
        testOutputTopic = topologyTestDriver.createOutputTopic(
                outputTopic,
                Serdes.String().deserializer(),
                employeeSerde.deserializer());

    }

    @AfterEach
    void tearDown() {
        topologyTestDriver.close();
    }

    @Test
    public void testSerde() {

        Employee employee = Employee.builder().name("jixiang").company("epam").position("learner").experience(12).build();

        testInputTopic.pipeInput("1", employee);
        var output = testOutputTopic.getQueueSize();
        assertEquals(1, output);

        Employee outputEmployee = testOutputTopic.readValue();
        assertEquals(outputEmployee.getName(), "jixiang");
        assertEquals(outputEmployee.getCompany(), "epam");
        assertEquals(outputEmployee.getPosition(), "learner");
        assertEquals(outputEmployee.getExperience(), 12);
    }
}
