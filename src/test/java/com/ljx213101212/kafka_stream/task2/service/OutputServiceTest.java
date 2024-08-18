package com.ljx213101212.kafka_stream.task2.service;

import com.ljx213101212.kafka_stream.task2.config.KafkaStreamsConfig;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class OutputServiceTest {

    public static void main(String[] args) throws Exception {

//        OutputService outputService = new OutputService();
//        KafkaStreamsConfig kafkaStreamsConfig = new KafkaStreamsConfig();

        OutputService outputService = createInstance(OutputService.class);
        KafkaStreamsConfig kafkaStreamsConfig = createInstance(KafkaStreamsConfig.class);


        Class<OutputService> outputServiceClass = OutputService.class;
        System.out.println("first="+outputService.getConfig());
        Field fieldNamedConfig = outputServiceClass.getDeclaredField("config");
        fieldNamedConfig.setAccessible(true);

        fieldNamedConfig.set(outputService, kafkaStreamsConfig);
        System.out.println("second="+outputService.getConfig());




    }

    private static <T> T createInstance(Class<T> clazz) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
        Constructor<?> declaredConstructor = declaredConstructors[0];
        return (T) declaredConstructor.newInstance();
    }
}