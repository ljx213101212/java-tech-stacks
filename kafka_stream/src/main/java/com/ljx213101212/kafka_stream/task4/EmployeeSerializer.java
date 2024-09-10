package com.ljx213101212.kafka_stream.task4;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EmployeeSerializer implements Serializer<Employee> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String topic, Employee employee) {
        try {
            return objectMapper.writeValueAsBytes(employee);
        } catch (Exception e) {
            throw new IllegalStateException("Error serializing value", e);
        }
    }

    @Override
    public void close() {
        Serializer.super.close();
    }
}
