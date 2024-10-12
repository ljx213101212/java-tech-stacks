package com.ljx213101212.search_engine.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.List;

@SolrDocument(collection = "books")
public class Book {
    @Id
    private String id;
    private String title;
    private List<String> authors;
    private String content;
    private String language;

    // Getters and setters
}