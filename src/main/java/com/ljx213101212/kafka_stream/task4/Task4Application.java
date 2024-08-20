package com.ljx213101212.kafka_stream.task4;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class Task4Application implements ApplicationRunner {

    @Autowired
    EmployeeSerializer serializer;

    @Autowired
    EmployeeDeserializer deSerializer;

    @Autowired
    KafkaStreamConfig config;

    @Value("${task4.kafka.stream.source-topic}")
    String inputTopic;

    @Value("${task4.kafka.stream.output-topic}")
    String outputTopic;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Task4Application.class);
        app.setWebApplicationType(WebApplicationType.NONE); // Set application type to non-web
        Properties props = new Properties();
        props.put("spring.kafka.producer.value-serializer", "com.ljx213101212.kafka_stream.task4.EmployeeSerializer");
        app.setDefaultProperties(props);
        app.run(args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        StreamsBuilder builder = new StreamsBuilder();
        StreamsConfig streamsConfig = new StreamsConfig(config.getKafkaStreamConfig());
        runAsync(builder).get();
        KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfig);
        streams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }

    public CompletableFuture<Void> runAsync(StreamsBuilder builder) {
        return CompletableFuture.runAsync(() -> {
            Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
            //create topic if not exist
            config.ensureTopicExists(inputTopic, 1, (short) 2);

            Employee employee = Employee.builder().name("jixiang").company("epam").position("learner").experience(12).build();

            //simulate producer
            KafkaProducer<String, Employee> producer = config.createKafkaProducer();

            try {
                producer.send(new ProducerRecord<String, Employee>(inputTopic, "1", employee)).get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Message sent " + employee);
            producer.close();


            Serde<Employee> employeeSerde = Serdes.serdeFrom(new EmployeeSerializer(), new EmployeeDeserializer());
            KStream<String, Employee> stream = builder.stream(inputTopic, Consumed.with(Serdes.String(), employeeSerde));
            stream.foreach((key, value) -> System.out.println("Message Received: " + value));
            stream.to(outputTopic, Produced.with(Serdes.String(), employeeSerde));
        });
    }
}
