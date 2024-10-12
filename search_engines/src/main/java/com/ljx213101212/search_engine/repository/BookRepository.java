package com.ljx213101212.search_engine.repository;

import nl.siegmann.epublib.domain.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, String> {
}
