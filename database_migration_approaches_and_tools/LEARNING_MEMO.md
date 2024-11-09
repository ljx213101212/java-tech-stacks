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
