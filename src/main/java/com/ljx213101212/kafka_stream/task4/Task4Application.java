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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Task4Application implements ApplicationRunner {

    @Autowired
    EmployeeSerializer serializer;

    @Autowired
    EmployeeDeserializer deSerializer;

    @Autowired
    KafkaStreamConfig config;

    @Value("${task4.kafka.stream.source-topic}")
    String topic;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Task4Application.class);
        app.setWebApplicationType(WebApplicationType.NONE); // Set application type to non-web
        app.run(args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //create topic if not exist
        config.ensureTopicExists(topic, 1, (short) 3);
        Employee employee = Employee.builder().name("jixiang").company("epam").position("learner").experience(12).build();

        //simulate producer
        KafkaProducer<String, Employee> producer = config.createKafkaProducer();

        //make it synchronous (.get())
        producer.send(new ProducerRecord<String, Employee>(topic, "1", employee)).get();
        System.out.println("Message sent " + employee);
        producer.close();


        StreamsConfig streamsConfig = new StreamsConfig(config.getKafkaStreamConfig());
        Serde<Employee> employeeSerde = Serdes.serdeFrom(new EmployeeSerializer(), new EmployeeDeserializer());
        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, Employee> stream = builder.stream(topic, Consumed.with(Serdes.String(), employeeSerde));
        stream.foreach((key, value) -> System.out.println("Received: " + value));

        KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfig);
        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
