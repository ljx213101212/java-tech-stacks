package com.ljx213101212.kafka_stream.task4;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {
    private String name;
    private String company;
    private String position;
    private int experience;
}
