package com.ljx213101212.search_engine.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.aggregations.*;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.*;
import co.elastic.clients.elasticsearch.indices.PutMappingRequest;
import com.ljx213101212.search_engine.config.Constants.Aggregations;
import com.ljx213101212.search_engine.config.Constants.Suggest;
import com.ljx213101212.search_engine.model.Book;
import com.ljx213101212.search_engine.model.dto.BookSearchRequest;
import com.ljx213101212.search_engine.model.dto.BookSearchResponse;
import com.ljx213101212.search_engine.model.dto.BookSuggestResponse;
import com.ljx213101212.search_engine.repository.BookRepository;
import nl.siegmann.epublib.domain.Metadata;
import nl.siegmann.epublib.epub.EpubReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

// Other necessary imports

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    private Book transformByEpubBook(nl.siegmann.epublib.domain.Book epubBook) {
        Book book = new Book();

//        // Assign a unique ID, you might want to use a UID generator
        book.setId(java.util.UUID.randomUUID().toString());

        // Extract metadata
        Metadata metadata = epubBook.getMetadata();

        // Set title
        if (!metadata.getTitles().isEmpty()) {
            book.setTitle(metadata.getTitles().get(0));
        } else {
            book.setTitle("Unknown Title");
        }

        // Set authors
        book.setAuthors(metadata.getAuthors().stream()
                .map(author -> author.getFirstname() + " " + author.getLastname())
                .collect(Collectors.toList()));

        // Set language
        book.setLanguage(metadata.getLanguage().isEmpty() ? "en": metadata.getLanguage());

        // Extract and set content
        StringWriter writer = new StringWriter();
        epubBook.getContents().forEach(content -> {
            try (InputStreamReader reader = new InputStreamReader(content.getInputStream(), StandardCharsets.UTF_8)) {
                char[] buffer = new char[1024];
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        book.setContent(writer.toString());

        return book;
    }

    private String readJsonFile(String fileName) {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (in == null) {
                throw new IllegalArgumentException("File not found: " + fileName);
            }
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON file", e);
        }
    }

    public void indexBooks(File file) throws IOException {
        EpubReader epubReader = new EpubReader();
        nl.siegmann.epublib.domain.Book epubBook = epubReader.readEpub(new FileInputStream(file));

        Book book = transformByEpubBook(epubBook);
        //Save book into elastic search
        bookRepository.save(book);
    }

    public Optional<Book> getBooksById(String id) {
       return bookRepository.findById(id);
    }

    //uses Elastic Search Client
    public void indexBooks2(File file) throws IOException {
        EpubReader epubReader = new EpubReader();
        nl.siegmann.epublib.domain.Book epubBook = epubReader.readEpub(new FileInputStream(file));

        Book book = transformByEpubBook(epubBook);

        // Index document in Elasticsearch
        IndexRequest<Book> request = IndexRequest.of(i -> i
                .index("books")
                .id(book.getId())
                .document(book)
        );
        elasticsearchClient.index(request);
    }

    public void indexBooks3(File file) throws IOException {
        EpubReader epubReader = new EpubReader();
        nl.siegmann.epublib.domain.Book epubBook = epubReader.readEpub(new FileInputStream(file));

        Book book = transformByEpubBook(epubBook);

        // Step 1: Load mapping from JSON file
        String mappingJson = readJsonFile("CompleteSuggesterMapping.json");

        // Step 2: Create index with mapping
        // Check if the index exists
        Boolean indexExists = elasticsearchClient.indices().exists(e -> e.index("books_suggest")).value();

        if (indexExists) {
            PutMappingRequest putMappingRequest = PutMappingRequest.of(
                    m -> m.index("books_suggest").properties("title", prop -> prop
                                    .text(t -> t)
                            )
                            .properties("authors", prop -> prop
                                    .text(t -> t)
                            )
                            .properties("suggest", prop -> prop
                                    .completion(cmp -> cmp)
                            ));
            elasticsearchClient.indices().putMapping(putMappingRequest);
        } else {
            // Create index with mapping
            elasticsearchClient.indices().create(c -> c
                    .index("books_suggest")
                    .withJson(new StringReader(mappingJson))
            );
        }

//        book.setSuggest(new Completion(List.of(book.getTitle(), String.join(", ", book.getAuthors()))));
        BulkRequest.Builder br = new BulkRequest.Builder();
        br.operations(op -> op
                .index(idx -> idx
                        .index("books_suggest")
                        .id(book.getId())
                        .document(Map.of(
                                "title", book.getTitle(),
                                "authors", String.join(", ", book.getAuthors()),
                                "suggest", Map.of(
                                        "input", List.of(book.getTitle(), String.join(", ", book.getAuthors()))
                                )
                        )))
        );
        BulkResponse bulkResponse = elasticsearchClient.bulk(br.build());
        if (bulkResponse.errors()) {
            bulkResponse.items().forEach(item -> {
                if (item.error() != null) {
                    System.out.println("Error: " + item.error().reason());
                }
            });
        } else {
            System.out.println("Document indexed successfully.");
        }
    }

    private Map<String, Aggregation> getAggregation() throws IOException {
        Map<String, Aggregation> map = new HashMap<>();
        map.put(Aggregations.FACET_TITLE_NAME, new Aggregation.Builder()
                .terms(new TermsAggregation.Builder().field(Aggregations.FACET_TITLE).size(25).build())
                .build());
        map.put(Aggregations.FACET_AUTHOR_NAME, new Aggregation.Builder()
                .terms(new TermsAggregation.Builder().field(Aggregations.FACET_AUTHOR).size(25).build())
                .build());
        map.put(Aggregations.FACET_LANGUAGE_NAME, new Aggregation.Builder()
                .terms(new TermsAggregation.Builder().field(Aggregations.FACET_LANGUAGE).size(10).build())
                .build());

        return map;
    }

    private Suggester getSuggester(String term) {

        Suggester suggester = Suggester.of(sg -> sg
                .suggesters(Suggest.SUGGEST_BOOK, new FieldSuggester.Builder().prefix(term)
                        .completion(CompletionSuggester.of(c -> c.field("suggest").skipDuplicates(true)))
                        .build()));
        return suggester;
    }

    private List<BookSearchResponse.FacetDetails> mapAggregationsToFacetDetails(Map<String, Aggregate> aggregations) {
        List<BookSearchResponse.FacetDetails> facetDetailsList = new ArrayList<>();

        aggregations.forEach((aggName, aggData) -> {

            StringTermsAggregate stringTerms = aggData.sterms();

            List<StringTermsBucket> buckets = stringTerms.buckets().array();

            for (StringTermsBucket bucket : buckets) {
                BookSearchResponse.FacetDetails facetDetails = new BookSearchResponse.FacetDetails();
                facetDetails.setValueCount(bucket.docCount());
                facetDetails.setValue(bucket.key().stringValue());

                BookSearchResponse.FieldDetails fieldDetails = new BookSearchResponse.FieldDetails();
                fieldDetails.setName(aggName);
                facetDetails.setField(fieldDetails);

                BookSearchResponse.KeyDetails keyDetails = new BookSearchResponse.KeyDetails();
                keyDetails.setName(bucket.key().stringValue());
                facetDetails.setKey(keyDetails);

                facetDetailsList.add(facetDetails);
            }

        });

        return facetDetailsList;
    }

    private BookSuggestResponse mapToBookSuggestResponse(Map<String, List<Suggestion<Book>>> suggestionMap) {
        List<Suggestion<Book>> bookSuggestList = suggestionMap.get("book_suggest");

        if (bookSuggestList == null || bookSuggestList.isEmpty()) {
            return new BookSuggestResponse();
        }

        // Iterate through all suggestions
        List<BookSuggestResponse.SuggestionResponse> suggestionResponses = bookSuggestList.stream()
                .map(suggestion -> {
                    List<BookSuggestResponse.SuggestionOption> suggestionOptions = suggestion.completion().options().stream()
                            .map(option -> {
                                Book book = option.source();

                                return new BookSuggestResponse.SuggestionOption(
                                        option.text(),
                                        option.index(),
                                        option.id(),
                                        option.score().floatValue(),
                                        new BookSuggestResponse.BookSource(
                                                book.getTitle(),
                                                String.join(", ", book.getAuthors()),
                                                new BookSuggestResponse.BookSource.Suggest(
                                                        List.of(book.getTitle(), String.join(", ", book.getAuthors()))
                                                )
                                        )
                                );
                            })
                            .collect(Collectors.toList());

                    return new BookSuggestResponse.SuggestionResponse(
                            suggestion.completion().text(),
                            suggestion.completion().offset(),
                            suggestion.completion().length(),
                            suggestionOptions
                    );
                })
                .collect(Collectors.toList());

        BookSuggestResponse response = new BookSuggestResponse();
        response.setBookSuggest(suggestionResponses);
        return response;
    }

    public BookSearchResponse searchBooks(BookSearchRequest request) throws IOException {

        String term = "snow";
        // Build the search request
        SearchRequest searchRequest = new SearchRequest.Builder()
                .index("books")
                .query(q-> q.match(t -> t.field("content").query(term)))
                .aggregations(getAggregation())
                .size(10) // You can adjust the size based on your needs
                .build();

        SearchResponse<Book> response = elasticsearchClient.search(searchRequest, Book.class);

        //#1
        List<Book> books = response.hits().hits().stream()
                .map(Hit::source)
                .toList();

        //#2
        Map<String, Aggregate> aggregations = response.aggregations();
        List<BookSearchResponse.FacetDetails> facets = mapAggregationsToFacetDetails(aggregations);

        //#3
        long numFound = response.hits().total().value();

        BookSearchResponse bookSearchResponse = new BookSearchResponse();
        bookSearchResponse.setBooks(books.stream().map(book -> {
            BookSearchResponse.BookDetails bookDetails = new BookSearchResponse.BookDetails();
            bookDetails.setId(book.getId());
            bookDetails.setTitle(book.getTitle());
            bookDetails.setLanguage(book.getLanguage());
            bookDetails.setContent(book.getContent());
            bookDetails.setAuthors(book.getAuthors());
            return bookDetails;
        }).toList());
        bookSearchResponse.setFacets(facets);
        bookSearchResponse.setNumFound(numFound);

        return bookSearchResponse;
    }

    public BookSuggestResponse suggestBooks(BookSearchRequest request) throws IOException {

        String term = "harry";
        // Build the search request
        SearchRequest searchRequest = new SearchRequest.Builder()
                .index("books_suggest")
                .suggest(getSuggester(term))
                .size(10) // You can adjust the size based on your needs
                .build();

        SearchResponse<Book> response = elasticsearchClient.search(searchRequest, Book.class);
        Map<String, List<Suggestion<Book>>> suggestion = response.suggest();

        BookSuggestResponse suggestResponse = mapToBookSuggestResponse(suggestion);
        return suggestResponse;
    }
}