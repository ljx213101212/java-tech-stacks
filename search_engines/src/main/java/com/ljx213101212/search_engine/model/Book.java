package com.ljx213101212.search_engine.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Document(indexName = "books")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {
    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Nested)
    private List<String> authors;

    @Field(type = FieldType.Text)
    private String content;

    @Field(type = FieldType.Text)
    private String language;
}