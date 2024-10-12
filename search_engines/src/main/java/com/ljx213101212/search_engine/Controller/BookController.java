package com.ljx213101212.search_engine.Controller;

import com.ljx213101212.search_engine.Service.BookService;
import com.ljx213101212.search_engine.repository.BookRepository;
import nl.siegmann.epublib.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @PostMapping("/index")
    public ResponseEntity<String> indexBooks() {
        try {
            bookService.indexBooks("/path/to/your/epub/files");
            return ResponseEntity.ok("Indexing started");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error indexing books");
        }
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<Optional<Book>> getBookById(@PathVariable String id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book != null) {
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
