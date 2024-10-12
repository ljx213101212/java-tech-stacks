package com.ljx213101212.search_engine.Model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

@Document(indexName = "books")
@Getter
@Setter
public class Book {
    @Id
    private String id;
    private String title;
    private List<String> authors;
    private String content;
    private String language;
}