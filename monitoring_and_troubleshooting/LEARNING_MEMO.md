### Debug Library source code (intellij)

- Download Source File
  - File > Project Structure (ctrl + shift + alt + s)
  - Dependencies 
    - locate the library to debug
    - edit icon
    - "Add" or "Attach" *-source.jar file

- Go to Navigate in the top menu bar
  - Select "Class"
  - Type keyword and open

- add breakpoints and start debugging!
- make sure to use **Evaluate Expression** to check all variable, expression return value under current context

#### Important Tips (DATA FLOW)

Switch on "TRACE" in applications.properties to check the data flow for more clues!

For instance if you want to understand the full flow of logout handler in spring security
```properties
logging.level.org.springframework.security=TRACE
```

Then you can trace the data flow step by step (n/14)
```java
logger.trace(LogMessage.format("Invoking %s (%d/%d)", name, this.currentPosition, this.size));

2024-11-21T12:16:50.452+08:00 DEBUG 14660 --- [nio-8080-exec-7] o.s.security.web.FilterChainProxy        : Securing POST /logout
2024-11-21T12:16:50.452+08:00 TRACE 14660 --- [nio-8080-exec-7] o.s.security.web.FilterChainProxy        : Invoking DisableEncodeUrlFilter (1/14)
2024-11-21T12:16:50.453+08:00 TRACE 14660 --- [nio-8080-exec-7] o.s.security.web.FilterChainProxy        : Invoking WebAsyncManagerIntegrationFilter (2/14)
2024-11-21T12:16:50.453+08:00 TRACE 14660 --- [nio-8080-exec-7] o.s.security.web.FilterChainProxy        : Invoking SecurityContextHolderFilter (3/14)
2024-11-21T12:16:50.453+08:00 TRACE 14660 --- [nio-8080-exec-7] o.s.security.web.FilterChainProxy        : Invoking HeaderWriterFilter (4/14)
2024-11-21T12:16:50.453+08:00 TRACE 14660 --- [nio-8080-exec-7] o.s.security.web.FilterChainProxy        : Invoking CorsFilter (5/14)
2024-11-21T12:16:50.453+08:00 TRACE 14660 --- [nio-8080-exec-7] o.s.security.web.FilterChainProxy        : Invoking CsrfFilter (6/14)

```