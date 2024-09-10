package com.ljx213101212.kafka_stream.task4;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EmployeeDeserializer implements Deserializer<Employee> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public Employee deserialize(String topic, byte[] employeeInBytes) {
        if (employeeInBytes == null) {
            return null;
        }
        try {
            Employee employee = objectMapper.readValue(employeeInBytes, Employee.class);
            return employee;
        } catch (Exception e) {
            //throw new IllegalStateException("Error deserializing value", e);
            System.out.println("Deserializer error: " + e);
            return null;
        }
    }

    @Override
    public void close() {
        Deserializer.super.close();
    }
}
