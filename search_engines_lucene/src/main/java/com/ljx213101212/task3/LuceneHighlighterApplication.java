package com.ljx213101212.task3;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.TokenSources;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LuceneHighlighterApplication {

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

            // Create Lucene Query object
            QueryParser parser = new QueryParser("content", analyzer);
            Query query = parser.parse("Holmes, Elizabeth, dystopian");

            // Perform search
            TopDocs results = searcher.search(query, 3);
            ScoreDoc[] hits = results.scoreDocs;

            // 1. Create Lucene Highlighter object
            SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<b>", "</b>");
            QueryScorer scorer = new QueryScorer(query);
            Highlighter highlighter = new Highlighter(formatter, scorer);
            highlighter.setTextFragmenter(new SimpleFragmenter(50)); // Fragment size

            // Display the top 3 results with highlights
            System.out.println("Highlighted snippets:");
            for (ScoreDoc hit : hits) {
                Document doc = searcher.doc(hit.doc);
                String text = doc.get("content");
                if (text != null) {
                    // Generate a snippet containing the best 3 fragments
                    String[] fragments = highlighter.getBestFragments(
                            TokenSources.getAnyTokenStream(indexReader, hit.doc, "content", analyzer),
                            text,
                            3);
                    System.out.println("Title: " + doc.get("title"));
                    System.out.println("Author: " + doc.get("author"));
                    System.out.println("Snippets:");
                    for (String fragment : fragments) {
                        System.out.println(fragment);
                    }
                    System.out.println();
                }
            }

            indexReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
