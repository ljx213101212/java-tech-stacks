Prerequisites

Install Solr using Docker: https://hub.docker.com/_/solr

Java version 8+
Maven
Java IDE


Task 1 - Indexing (40 Points)

Find collection of the books in English language. It is better to use epub format.
Implement indexing process of these books.
Document should have the following fields:
- Id
- Title (book title, string)
- Authors (collection of strings)
- Content
- Language
  The process of indexing could be described as following:
- Read all books from local folder
- Use epubreader library (ex. com.positiondev.epublib) for transforming from File to EbubBook object
- Then transform EpubBook to Book com.ljx213101212.flyway.model which represent Solr document
- Save Books to Solr
  Implement API endpoint to start indexing process
  Using spring data solr implement com.ljx213101212.flyway.repository to retrieving solr document by id
  Implement API endpoint


GET /api/v1/book/{id}



Task 2 - Search book by query (40 points)

Implement API endpoint with following request:


{
"field": "string", // field for filtering
"value": "string", // value of the field for filtering
"facetField": "string", // field for facet
"fulltext": true, // is full text search
"q": "string" // query for full text search
}


The response structure is:

{
"books": [
{
"id": "string",
"authors": [
"string"
],
"title": "string",
"language": "string",
"content": "string",
}
],
"facets": [
{
"valueCount": 0,
"value": "string",
"field": {
"name": "string"
},
"key": {
"name": "string"
}
}
],
"numFound": 0
}



Using *org.springframework.data.solr.core.SolrTemplate * implement search request to Solr using parameters from response


Task 3 - Implement autosuggestions (20 Point)

Implement API endpoint which is returning array of suggestions:


GET api/v1/book/suggest?query=



Create suggest field in scheme using org.springframework.data.solr.core.SolrTemplate which is filled from Book.title & Book.authors
Add required searchComponent and requestHandler for suggestions to solrconfig.xml

Using org.apache.solr.client.solrj.SolrClient execute suggest query

## Note
Spring-data-solr was archived with issues(https://github.com/spring-attic/spring-data-solr?tab=readme-ov-file), this home work will be done in elastic search
https://github.com/spring-projects/spring-data-elasticsearch