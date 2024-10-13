package com.ljx213101212.search_engine.repository;

import com.ljx213101212.search_engine.model.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface BookRepository extends ElasticsearchRepository<Book, String> {
}