### Quick Start

#### Prerequisite
>If you don’t have Docker installed, download and install Docker Desktop for your operating system.
>If you’re using Microsoft Windows, then install Windows Subsystem for Linux (WSL).
>curl -fsSL https://elastic.co/start-local | sh


#### Task1

use org.springframework.data.elasticsearch.com.ljx213101212.flyway.repository.ElasticsearchRepository
```
POST /api/v1/book/index
Index Sample books into elastic search

GET /api/v1/book/{id}
Get Indexed sample books as document by document id
```

#### Task2
use co.elastic.clients.elasticsearch
```
POST /api/v2/books/index
Index Sample books into elastic search (use different way than task 1)

GET /api/v2/book/search
The search book api by query using the task pre-defined payload

sample payload
{
    "field": "title",
    "value": "harry",
    "facetField": "agg_author",
    "fulltext": "true",
    "q": "magic"
}
```

#### Task3
use co.elastic.clients.elasticsearch
```
POST /api/v3/books/index
Index Sample book into elastic search with completion suggester mapping

Sample curl
curl --location 'http://localhost:8080/api/v3/books/suggest?query=harry' \
--header 'Content-Type: application/json
```