package com.ljx213101212.search_engine.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookSuggestResponse {

    private List<SuggestionResponse> bookSuggest;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SuggestionResponse {
        private String text;
        private int offset;
        private int length;
        private List<SuggestionOption> options;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SuggestionOption {
        private String text;
        private String index;
        private String id;
        private float score;
        private BookSource source;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookSource {
        private String title;
        private String authors;
        private Suggest suggest;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Suggest {
            private List<String> input;
        }
    }
}

