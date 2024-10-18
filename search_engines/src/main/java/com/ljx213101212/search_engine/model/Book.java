package com.ljx213101212.search_engine.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.CompletionField;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.core.suggest.Completion;

import java.util.List;
import java.util.Map;

@Document(indexName = "books")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    @CompletionField(maxInputLength = 100)
    @Builder.Default
    private Completion suggest = new Completion(List.of("sample book"));
}