package com.ljx213101212.kafka_stream.task1.config;


import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;

import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.util.Properties;

public class KafkaStreamsConfig {

    public String getInputTopic() {
        return inputTopic;
    }

    public String getOutputTopic() {
        return outputTopic;
    }

    private String inputTopic;

    private String outputTopic;

    private String bootstrapServer;

    public KafkaStreamsConfig() {
        Constants constants = getConstants();
        this.inputTopic = constants.getInputTopicName();
        this.outputTopic = constants.getOutputTopicName();
        this.bootstrapServer = constants.getBootstrapServer();
        this.applicationId = constants.getApplicationId();
    }

    private String applicationId;



    public Properties kStreamsConfigs() {
        Properties props = new Properties();

        props.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationId);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        return props;
    }

    public Constants getConstants() {
        Constants constants = new Constants();
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream("src/main/resources/application.properties")) {
            // Load the properties file
            prop.load(input);

            // Retrieve properties
            String inputTopic = prop.getProperty("task1.kafka.stream.input-topic");
            String outputTopic = prop.getProperty("task1.kafka.stream.output-topic");
            String applicationId = prop.getProperty("task1.kafka.stream.application-id");
            String bootstrapServers = prop.getProperty("task1.kafka.stream.bootstrap-server");

           constants.setInputTopicName(inputTopic);
           constants.setOutputTopicName(outputTopic);
           constants.setApplicationId(applicationId);
           constants.setBootstrapServer(bootstrapServers);

            // Use properties
            System.out.println("Input Topic: " + inputTopic);
            System.out.println("Output Topic: " + outputTopic);
            System.out.println("Application ID: " + applicationId);
            System.out.println("Bootstrap Servers: " + bootstrapServers);

            return constants;

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return constants;
    }
}
