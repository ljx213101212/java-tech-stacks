package com.ljx213101212.task1;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;


//Add VM Options:
//
//Choose your application configuration on the left pane.
//Look for the "VM options" field.
//Enter your property: -DprojectBasePath=C:\Work\elearning\spring-boot-3-2024\search_engines_lucene
public class LuceneIndexingApplication {

    public static void main(String[] args) {

        String basePath = System.getProperty("projectBasePath");
        if (basePath == null) {
            throw new IllegalArgumentException("Project base path not set. Use -DprojectBasePath=<path>");
        }
        Path projectBasePath = Paths.get(basePath);
        Path indexPath = projectBasePath.resolve("index");
        // Directory for the index
        try (Directory indexDirectory = FSDirectory.open(indexPath)) {
            StandardAnalyzer analyzer = new StandardAnalyzer();
            IndexWriterConfig config = new IndexWriterConfig(analyzer);

            try (IndexWriter writer = new IndexWriter(indexDirectory, config)) {
                // Provide the path to your text files directory
                URL resourceUrl = LuceneIndexingApplication.class.getClassLoader().getResource("");
                File dir = new File(resourceUrl.getPath());
                for (File file : dir.listFiles()) {
                    if (file.isFile()) {
                        addDocument(writer, file);
                    }
                }
                System.out.println("Indexing completed.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Document parseFile(File file) throws IOException {
        Document doc = new Document();
        StringBuilder content = new StringBuilder();
        String title = "";
        String author = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Title:")) {
                    title = line.substring(7).trim();
                } else if (line.startsWith("Author:")) {
                    author = line.substring(8).trim();
                } else {
                    content.append(line).append("\n");
                }
            }
        }

        // Add fields to the document
        doc.add(new StringField("title", title, Field.Store.YES));
        doc.add(new StringField("author", author, Field.Store.YES));
        doc.add(new TextField("content", content.toString().trim(), Field.Store.YES));

        return doc;
    }

    private static void addDocument(IndexWriter writer, File file) throws IOException {
        Document doc = parseFile(file);
        writer.addDocument(doc);
    }
}