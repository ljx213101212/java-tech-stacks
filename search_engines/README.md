### Elastic Search

- run Elastic Search as local container (no UI)
 - docker run -d --name es762 -p 9200:9200 -e "discovery.type=single-node" elasticsearch:7.6.2
- run Elastic Search as local cluster (Elastic Search + Kibana(UI))
 - https://www.elastic.co/guide/en/elasticsearch/reference/current/getting-started.html

- Source 
  - https://github.com/DwngLee/AnGi_BE/blob/7627736de9d546fcabaa4443ccde2b5a39316c07/src/main/java/com/personal/project/angi/service/impl/RestaurantServiceImpl.java#L220
  - https://github.com/andreluiz1987/search-store/blob/main/src/main/java/com/company/searchstore/core/SearchCoreService.java#L50

- Snippet (common queries backup)
```
GET /books/_search?pretty
{
  "query": {
    "match": {
      "title": "Harry Potter"
    }
  },
  "aggs": {
    "agg_title": {
      "terms": {
        "field": "title.keyword"
      }
    },
    "agg_author": {
      "terms": {
        "field": "authors.keyword"
      }
    },
     "agg_language": {
      "adjacency_matrix": {
        "filters": {
          "english_titles": {
            "term": {"language.keyword": "en"}
          },
          "harry_potter": {
            "match": {"title": "Harry Potter"}
          },
          "jk_rowling": {
            "term": {"authors.keyword": "J.K. Rowling"}
          }
        }
      }
    }
  }
}
```

````commandline
POST /books_suggest/_search
{
  "suggest": {
    "book-suggest": {
      "prefix": "J",
      "completion": {         
          "field": "suggest",
          "skip_duplicates": true
      }
    }
  }
}
````

### Solr (OutDated)

#### Quick Start

- Install Solr using Docker: https://hub.docker.com/_/solr
- Run solr as a container: docker run -d -p 8983:8983 --name solr-container solr
- Creating Solr core:  (winpty <- if you are using Git Bash) docker exec -it solr-container solr create_core -c bibdata
  - curl 'http://localhost:8983/solr/bibdata/select?q=*'
- Adding documents to Solr:
  - download sample data
    - curl -OL https://raw.githubusercontent.com/hectorcorrea/solr-for-newbies/main/books.json
  - import into docker
    - docker cp books.json solr-container:/opt/solr-9.1.0/books.json (please check your solr version in container and swap solr-9.1.0)
    - winpty docker exec -it solr-container post -c bibdata books.json
  - check the result
    - curl 'http://localhost:8983/solr/bibdata/select?q=*'

#### CRUD data
- wildcard-fetch (by default 10 rows)
  - https://solr.apache.org/guide/solr/latest/configuration-guide/requesthandlers-searchcomponents.html#defaults
    ```
    http://localhost:8983/solr/bibdata/select?q=*
    ```
  
- selecting-what-fields-to-fetch
    ```
    http://localhost:8983/solr/bibdata/select?q=*&fl=id,title_txt_en
    ```
- Filtering the documents to fetch
    ```
    curl 'http://localhost:8983/solr/bibdata/select?q=title_txt_en:teachers'
    curl 'http://localhost:8983/solr/bibdata/select?q=title_txt_en:teachers+author_txt_en:Alice'
    curl 'http://localhost:8983/solr/bibdata/select?fl=id,title_txt_en&q=title_txt_en:"art+history"'
    curl 'http://localhost:8983/solr/bibdata/select?fl=id,title_txt_en&q=title_txt_en:"art+history"~3' (few words apart)
    curl -s 'http://localhost:8983/solr/bibdata/select?debug=all&q=title_txt_en:"art+history"' | grep parsedquery
    ```
- Getting facets
  ```
   curl 'http://localhost:8983/solr/bibdata/select?q=title_txt_en:education&facet=on&facet.field=subjects_ss'
  ```

- Updating documents
```
  curl 'http://localhost:8983/solr/bibdata/select?q=id:00007345'
  curl -X POST --data '[{"id":"00007345","title_txt_en":"the new title"}]' 'http://localhost:8983/solr/bibdata/update?commit=true' (overwrite)
  curl 'http://localhost:8983/solr/bibdata/select?q=id:00007345'  (check again)
```

- Deleting documents

```
  curl 'http://localhost:8983/solr/bibdata/select?q=id:00008056'
  $ curl 'http://localhost:8983/solr/bibdata/update?commit=true' --data '<delete><query>id:00008056</query></delete>'
```

- Deleting the entire core
```
  (winpty) docker exec -it solr-container solr delete -c bibdata
```

#### Fields

```
$ curl localhost:8983/solr/bibdata/schema/fieldtypes
$ curl localhost:8983/solr/bibdata/schema/fields
$ curl localhost:8983/solr/bibdata/schema/fields/title_txt_en
$ curl localhost:8983/solr/bibdata/schema/dynamicfields
$ curl localhost:8983/solr/bibdata/schema/copyfields
```

#### Analysis Screen
> https://solr.apache.org/guide/solr/9_0/indexing-guide/analysis-screen.html
```
http://localhost:8983/solr/#/bibdata/analysis

```

#### Synonyms

```
http://localhost:8983/solr/#/bibdata/files?file=synonyms.txt

(compare)
$ curl 'http://localhost:8983/solr/bibdata/select?fl=id,title_txt_en&q=title_txt_en:"twentieth+century"'
$ curl 'http://localhost:8983/solr/bibdata/select?fl=id,title_txt_en&q=title_txt_en:"20th+century"'

(adding synonyms)
docker cp solr-container:/var/solr/data/bibdata/conf/synonyms.txt .
echo "20th,twentieth" >> synonyms.txt
docker cp synonyms.txt solr-container:/var/solr/data/bibdata/conf/

(reload core)
http://localhost:8983/solr/admin/cores?action=RELOAD&core=bibdata

(check the momemnt of truth)
curl 'http://localhost:8983/solr/bibdata/select?fl=id,title_txt_en&q=title_txt_en:"twentieth+century"'
```

#### Core-specific Configuration

```
http://localhost:8983/solr/#/bibdata/files?file=solrconfig.xml
```





### Reference
https://github.com/hectorcorrea/solr-for-newbies/blob/main/tutorial.md
https://github.com/spring-attic/spring-data-solr?tab=readme-ov-file
https://www.baeldung.com/spring-data-elasticsearch-tutorial
https://www.elastic.co/guide/en/elasticsearch/reference/current/getting-started.html

