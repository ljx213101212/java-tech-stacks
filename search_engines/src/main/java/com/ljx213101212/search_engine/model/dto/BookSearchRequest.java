package com.ljx213101212.search_engine.model.dto;

import lombok.Data;

@Data
public class BookSearchRequest {
    private String field;       // Field for filtering
    private String value;       // Value of the field for filtering
    private String facetField;  // Field for facet
    private boolean fulltext;   // Is full text search
    private String q;           // Query for full text search
}