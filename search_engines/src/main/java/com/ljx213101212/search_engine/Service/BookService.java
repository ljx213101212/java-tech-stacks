package com.ljx213101212.search_engine.Service;

import com.ljx213101212.search_engine.Model.Book;
import com.ljx213101212.search_engine.repository.BookRepository;
import nl.siegmann.epublib.domain.Metadata;
import nl.siegmann.epublib.epub.EpubReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.stream.Collectors;

// Other necessary imports

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    private Book transformByEpubBook(nl.siegmann.epublib.domain.Book epubBook) {
        Book book = new Book();

        // Assign a unique ID, you might want to use a UID generator
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

}