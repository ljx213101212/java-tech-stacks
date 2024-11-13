### pre-requisite

##### Run MySQL in Docker

````commandline
cd mysql
docker compose up -d

(optional)
docker ps
docker exec -it local-mysql mysql -u root -p
Enter the root password (root) when prompted.
````

##### Download flyway cli (WSL Ubuntu)
````commandline
https://documentation.red-gate.com/fd/command-line-184127404.html

wget -qO- https://download.red-gate.com/maven/release/com/redgate/flyway/flyway-commandline/10.21.0/flyway-commandline-10.21.0-linux-x64.tar.gz | tar -xvz && sudo ln -s `pwd`/flyway-10.21.0/flyway /usr/local/bin 
````

#### Download liquibase cli
```commandline
https://docs.liquibase.com/start/install/liquibase-linux-debian-ubuntu.html
```

#### Download mysql-connector-java
```commandline
don't forget to gitignore it
```


### Flyway

#### Quickstart

##### Migrate Through flyway command
````commandline
cd to flyway.conf file
flyway migrate 
````

##### Migrate Through Spring Boot Run
```commandline
run the FlywayApplication
```


### Liquibase

#### Quick Start

##### Migrate through liquibase command
```commandline
liquibase --url=jdbc:mysql://localhost:6603/db_migration_demo --username=user --password=password --changeLogFile=src/main/resources/db/changelog/db.changelog-master.yaml --driver=com.mysql.cj.jdbc.Driver --classpath=/mnt/c/Work/elearning/java-tech-stacks/database_migration_approaches_and_tools/mysql-connector-java-8.0.29.jar --log-level=info update
```

##### Rollback through liquibase command
```commandline
liquibase --url=jdbc:mysql://localhost:6603/db_migration_demo \
          --username=user --password=password \
          --changeLogFile=src/main/resources/db/changelog/db.changelog-master.yaml \
          --log-level=info \
          --driver=com.mysql.cj.jdbc.Driver \
          --classpath=/mnt/c/Work/elearning/java-tech-stacks/database_migration_approaches_and_tools/mysql-connector-java-8.0.29.jar:/mnt/c/Work/elearning/java-tech-stacks/database_migration_approaches_and_tools/liquibase/src/main/resources \
          rollbackCount 1
          
liquibase rollbackCount 1
```

```commandline
liquibase tag V2
liquibase rollback V2s
```

##### Check state

```properties
liquibase status
```

### Trade-off

```commandline
Flyway:

- Mature and stable for production use.
- Migration history in flyway_schema_history.
- No built-in, flexible rollback, but rollback must be handled manually via custom migrations.
- Simpler and easier to use for straightforward projects or when precise rollbacks are not needed.
- Primarily focused on SQL migrations and is mostly used with SQL-only changelogs.

Liquibase:

- Newer but with more frequent updates.
- Migration history in DATABASECHANGELOG.
- Highly flexible rollback mechanism (with rollbackCount, rollbackToDate, rollbackToTag, or custom rollback logic).
- Supports multiple changelog formats (SQL, XML, YAML, JSON), which allows you to choose based on your needs.
- More complex than Flyway but offers greater flexibility and control, especially for applications that require granular database versioning and rollbacks.
```


