package com.ljx213101212.search_engine.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class BookSearchResponse {
    private List<BookDetails> books;
    private List<FacetDetails> facets;
    private long numFound;

    @Data
    @Getter
    @Setter
    public static class BookDetails {
        private String id;
        private List<String> authors;
        private String title;
        private String language;
        private String content;
    }

    @Data
    @Getter
    @Setter
    public static class FacetDetails {
        private long valueCount;
        private String value;
        private FieldDetails field;
        private KeyDetails key;
    }

    @Data
    @Getter
    @Setter
    public static class FieldDetails {
        private String name;
    }

    @Data
    @Getter
    @Setter
    public static class KeyDetails {
        private String name;
    }
}