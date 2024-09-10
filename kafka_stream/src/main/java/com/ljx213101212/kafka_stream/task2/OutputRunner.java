package com.ljx213101212.kafka_stream.task2;

import com.ljx213101212.kafka_stream.task2.service.OutputService;
import org.apache.kafka.streams.StreamsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class OutputRunner implements ApplicationRunner {

    private final OutputService myService;

    @Autowired
    public OutputRunner(OutputService myService) {
        this.myService = myService;
    }

//    @Override
//    public void run(String... args) throws Exception {
//        // Invoke your service here
//        myService.performTask();
//    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        StreamsBuilder builder = new StreamsBuilder();
        myService.performTask(builder);
    }
}
