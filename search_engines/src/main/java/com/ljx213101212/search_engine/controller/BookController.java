package com.ljx213101212.search_engine.controller;

//import com.ljx213101212.search_engine.Model.Book;
import com.ljx213101212.search_engine.model.Book;
import com.ljx213101212.search_engine.service.BookService;
//import com.ljx213101212.search_engine.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/index")
    public ResponseEntity<String> indexBooks() throws IOException {
        // Load the file from the resources folder
        Resource resource = new ClassPathResource("Harry_Potter_and_the_Sorcerer_s_Stone.epub");

        // Ensure the resource exists
        if (!resource.exists()) {
            throw new FileNotFoundException("File not found: " + resource.getFilename());
        }

        bookService.indexBooks(resource.getFile());
        return ResponseEntity.ok("Indexing started");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Book>> getBookById(@PathVariable String id) {
        Optional<Book> book = bookService.getBooksById(id);

        if (book != null) {
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
