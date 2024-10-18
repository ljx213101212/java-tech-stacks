### About
This project contains minimal working examples for each major topic in the Java tech stack. 
It aims to help readers/users gain a basic understanding of the fundamental usage of each topic.

### Pre-requisite
- [Install Docker](HOW_TO_DOCKER.md)

#### advanced_multithreading

```commandline
mvn package -pl advanced_multithreading
```

#### apache_kafka

```commandline
mvn package -pl apache_kafka
```

#### kafka_stream

```commandline
mvn package -pl kafka_stream
```

#### monitoring_and_troubleshooting

```commandline
mvn package -pl monitoring_and_troubleshooting
```

#### spring_boot

```commandline
mvn package -DskipTests -pl spring_boot
java -jar spring_boot/target/spring_boot_3_2024-0.0.1-SNAPSHOT.jar
```

#### search_engines
- Setup ELK
```commandline
cd elastic-start-local
docker-compose -f docker-compose.yml up
```
```commandline
mvn package -pl search_engines
```

#### search_engine_lucene

```commandline
mvn package -pl search_engine_lucene
```

#### search_engine_elasticsearch

- Setup ELK
```commandline
cd elastic-start-local
docker-compose -f docker-compose.yml up
```

```commandline
mvn package -pl search_engines_elasticsearch
```


