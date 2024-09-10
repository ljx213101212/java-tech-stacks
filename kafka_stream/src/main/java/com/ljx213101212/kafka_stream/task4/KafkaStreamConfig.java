package com.ljx213101212.kafka_stream.task4;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Configuration
public class KafkaStreamConfig {

    @Value("${task4.kafka.stream.application-id}")
    private String applicationIdConfig;
    @Value("${task4.kafka.stream.customer-group}")
    private String customerGroup;
    @Value("${task4.kafka.stream.bootstrap-server}")
    private String bootstrapServerConfig;
    @Value("${task4.kafka.stream.client-id}")
    private String clientIdConfig;

    public KafkaProducer<String, Employee> createKafkaProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServerConfig);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, clientIdConfig);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "com.ljx213101212.kafka_stream.task4.EmployeeSerializer");

        return new KafkaProducer<>(props);
    }

    public Properties getKafkaStreamConfig() {
        Properties props = new Properties();

        props.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationIdConfig);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServerConfig);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, "com.ljx213101212.kafka_stream.task4.EmployeeDeserializer");
        return props;
    }

    public  void ensureTopicExists(String topicName, int partitions, short replicationFactor) {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServerConfig);

        try (AdminClient adminClient = AdminClient.create(props)) {
            Set<String> existingTopics = adminClient.listTopics().names().get();
            if (!existingTopics.contains(topicName)) {
                System.out.println("Topic " + topicName + " not found, creating...");
                createTopic(topicName, partitions, replicationFactor);
            } else {
                System.out.println("Topic " + topicName + " already exists.");
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Failed to connect to Kafka or failed to retrieve topics.");
            e.printStackTrace();
        }
    }


    public void createTopic(String topicName, int numPartitions, short replicationFactor) {
        Properties config = new Properties();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServerConfig); // Adjust this to your Kafka server settings

        try (AdminClient admin = AdminClient.create(config)) {
            NewTopic newTopic = new NewTopic(topicName, numPartitions, replicationFactor);
            CreateTopicsResult result = admin.createTopics(Collections.singleton(newTopic));
            result.all().get();  // Synchronously wait for the topic to be created
            System.out.println("Topic created successfully");
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Failed to create topic: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
