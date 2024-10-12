package com.ljx213101212.search_engine.Service;

import nl.siegmann.epublib.epub.EpubReader;
import nl.siegmann.epublib.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

// Other necessary imports

@Service
public class BookService {

    @Autowired
    private SolrTemplate solrTemplate;

    public void indexBooks(String directoryPath) throws IOException {
        File dir = new File(directoryPath);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".epub"));
        EpubReader epubReader = new EpubReader();

        if (files != null) {
            for (File file : files) {
                Book epubBook = epubReader.readEpub(new FileInputStream(file));
                Book book = transformEpubToBook(epubBook);

                // Save book to Solr
                solrTemplate.saveBean("books", book);
            }
            solrTemplate.commit("books");
        }
    }

    private Book transformEpubToBook(Book epubBook) {
        // Transformation logic
        System.out.println(epubBook.getTitle());
        return new Book();
    }
}