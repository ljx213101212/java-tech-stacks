package com.ljx213101212.search_engines_elasticsearch.Task3_4.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Document(indexName = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee {

    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Date, pattern = "yyyy-MM-dd")
    private String dob;

    @Field(type = FieldType.Object)
    private Address address;

    @Field(type = FieldType.Text)
    private String email;

    @Field(type = FieldType.Keyword)
    private List<String> skills;

    @Field(type = FieldType.Integer)
    private int experience;

    @Field(type = FieldType.Double)
    private double rating;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Boolean)
    private boolean verified;

    @Field(type = FieldType.Integer)
    private int salary;

    // Define the Address class as an inner class
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Address {
        @Field(type = FieldType.Text)
        private String country;

        @Field(type = FieldType.Text)
        private String town;
    }
}