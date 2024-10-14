package com.ljx213101212.search_engine.controller.v3;

//import com.ljx213101212.search_engine.Model.Book;

import com.ljx213101212.search_engine.model.dto.BookSearchRequest;
import com.ljx213101212.search_engine.model.dto.BookSearchResponse;
import com.ljx213101212.search_engine.model.dto.BookSuggestResponse;
import com.ljx213101212.search_engine.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v3/books")
public class BookControllerV3 {

    @Autowired
    private BookService bookService;

    @PostMapping("/index")
    public ResponseEntity<String> indexBooks() {
        try {
            // Load the file from the resources folder
            Resource resource =  new ClassPathResource("Harry_Potter_and_the_Sorcerer_s_Stone.epub");

            // Ensure the resource exists
            if (!resource.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("File not found");
            }
            bookService.indexBooks3(resource.getFile());
            return ResponseEntity.ok("Indexing started");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error indexing books");
        }
    }

    @PostMapping("/suggest")
    public ResponseEntity<BookSuggestResponse> suggestBooks(@RequestBody BookSearchRequest request) {
        try {

            // Use the service to process the search request
            BookSuggestResponse searchResults = bookService.suggestBooks(request);
            return ResponseEntity.ok(searchResults);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null); // Return appropriate error handling
        }
    }
}
