### Flyway

- video
  - https://www.red-gate.com/hub/university/courses/flyway/getting-started-with-flyway

- Read
    - https://dev.to/aharmaz/database-migrations-flyway-for-spring-boot-projects-2coi

- Source
- https://github.com/callicoder/spring-boot-flyway-example
- https://github.com/Aharmaz/flyway-demo/blob/main/src/main/resources/application-local.yml

````commandline
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-mysql</artifactId>
</dependency>
````

#### Note

1. there is no way to turn back from high version to low version (which is good and keep the version safe)
2. if you want to start from V1, you have to clean the database
here is just a demo

check current version
````commandline
SELECT version, description
FROM flyway_schema_history
WHERE success = true
ORDER BY installed_rank DESC
LIMIT 1;

````

update flyway.conf (please never do this in production)
````commandline
flyway.cleanDisabled=false
````
or in application.properties
````commandline
spring.flyway.target=4
````

````commandline
flyway clean
 flyway migrate -target=4
````



### Liquibase


##### Run liquibase command or Simply start the spring boot

```commandline
liquibase --url=jdbc:mysql://localhost:6603/db_migration_demo \
          --username=user --password=password \
          --changeLogFile=src/main/resources/db/changelog/db.changelog-master.yaml \
          --driver=com.mysql.cj.jdbc.Driver \
          --classpath=/path/to/mysql-connector-java-8.x.x.jar \
          --log-level=info \
          [Your command]
```

````commandline
liquibase --url=jdbc:mysql://localhost:6603/db_migration_demo --username=user --password=password --changeLogFile=src/main/resources/db/changelog/db.changelog-master.yaml --driver=com.mysql.cj.jdbc.Driver --classpath=/mnt/c/Work/elearning/java-tech-stacks/database_migration_approaches_and_tools/mysql-connector-java-8.0.29.jar --log-level=info update

(don't do this in production)
liquibase --url=jdbc:mysql://localhost:6603/db_migration_demo --username=user --password=password --changeLogFile=src/main/resources/db/changelog/db.changelog-master.yaml --driver=com.mysql.cj.jdbc.Driver --classpath=/mnt/c/Work/elearning/java-tech-stacks/database_migration_approaches_and_tools/mysql-connector-java-8.0.29.jar --log-level=info drop-all
````


##### Rollback

- rollback by 1 changeset
````commandline
liquibase --url=jdbc:mysql://localhost:6603/db_migration_demo --username=user --password=password --changeLogFile=src/main/resources/db/changelog/db.changelog-master.yaml rollbackCount 1
````

- tagging and rollback to certain tag
```commandline

         
liquibase --url=jdbc:mysql://localhost:6603/db_migration_demo \
          --username=user --password=password \
          --changeLogFile=src/main/resources/db/changelog/db.changelog-master.yaml \
          --log-level=info \
          --driver=com.mysql.cj.jdbc.Driver \
          --classpath=/mnt/c/Work/elearning/java-tech-stacks/database_migration_approaches_and_tools/mysql-connector-java-8.0.29.jar:/mnt/c/Work/elearning/java-tech-stacks/database_migration_approaches_and_tools/liquibase/src/main/resources \
          tag V2
          
          
liquibase rollback V2
```

##### liquibase.properties
- add this file liquibase.properties, so you don't have to mention the param each time
```.properties
url=jdbc:mysql://localhost:6603/db_migration_demo
username=user
password=password
driver=com.mysql.cj.jdbc.Driver
classpath=/mnt/c/Work/elearning/java-tech-stacks/database_migration_approaches_and_tools/mysql-connector-java-8.0.29.jar:/mnt/c/Work/elearning/java-tech-stacks/database_migration_approaches_and_tools/liquibase/src/main/resources
changeLogFile=liquibase/src/main/resources/db/changelog/db.changelog-master.yaml
```