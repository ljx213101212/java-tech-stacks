package com.ljx213101212.task2;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.nio.file.Path;
import java.nio.file.Paths;

public class LuceneSearchingApplication {

    public static void main(String[] args) {
        try {
            // Set the index path
            String basePath = System.getProperty("projectBasePath");
            Path projectBasePath = Paths.get(basePath);
            Path indexPath = projectBasePath.resolve("index");

            // Open the index directory
            Directory indexDirectory = FSDirectory.open(indexPath);
            IndexReader indexReader = DirectoryReader.open(indexDirectory);
            IndexSearcher searcher = new IndexSearcher(indexReader);
            StandardAnalyzer analyzer = new StandardAnalyzer();

            // 1. Create Lucene Query object
            QueryParser parser = new QueryParser("content", analyzer);
            Query query = parser.parse("Holmes,Elizabeth,freedom");

            // 2. Searcher for the index
            // 3. Search for top 3 documents
            TopDocs results = searcher.search(query, 3);
            ScoreDoc[] hits = results.scoreDocs;

            // Display the top 3 results
            System.out.println("Top 3 search results:");
            for (ScoreDoc hit : hits) {
                Document doc = searcher.doc(hit.doc);
                System.out.println("Title: " + doc.get("title"));
                System.out.println("Author: " + doc.get("author"));
                System.out.println("Score: " + hit.score);
                System.out.println();
            }

            indexReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
