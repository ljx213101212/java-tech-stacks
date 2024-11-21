### Authentication vs Authorization

#### Authentication
- who are you?
  - Example: When a user logs in by entering a username and password, they are proving their identity

#### Authorization
- what can you do?
  - Example: After logging in, the system decides whether the user has the right to view, edit, or delete certain data.


### Spring Security

- In-memory
- JDBC
- Form-based
- LDAP
- Active Directory
- OAuth 2 
  - Authorization Server 
    - the core to handle the access management
  - Resource Server
    - the target server
  - Resource owner(user himself,  that's me)
  - Grant type
    - how the application is asking for access
      - Authorization Code
      - Resource Owner Password
      - Client Credential
      - Device Grant Type
  - Scope
    - what access the application is requiring
  - Token
    - such as JWT
- WebFlux

#### UserDetailsService
It provides flexibility by allowing you to retrieve users from a database, an LDAP server, or any other data source by implementing custom logic.

### Google OAuth 2 Playground
https://developers.google.com/oauthplayground/

### JWT
- 3 sections
    1. header
       - algorithm been used 
    2. payload
    3. Signature


### Why salted password?
1. Prevent rainbow table attacks
2. Identical password hashes


### Access Control
- Insecure references
- Regulating access privileges
- Cookies and sessions
- Deny lists and geo-filters
- Single sign-on services (Google: Drive, Docs, Gmail)


### Sample RBAC Database



### @Controller vs @RestController
@Controller for handling user-facing pages and views.
@RestController for providing RESTful endpoints that serve data for frontend frameworks like React, Angular, or mobile applications.

### No need to learn spring-boot-starter-thymeleaf
nowadays project are frontend and backend separate.

### Practicing troubleshooting skill through library source is important!
[HOW_TO_DEBUG_DEPENDENCY](../monitoring_and_troubleshooting/LEARNING_MEMO.md)


## Reference

demo source
https://www.codejava.net/frameworks/spring-boot/spring-security-limit-login-attempts-example
https://github.com/Baeldung/spring-security-registration/blob/master/src/main/java/com/baeldung/security/LoginAttemptService.java

spring source
https://github.com/spring-projects/spring-security/blob/main/web/src/main/java/org/springframework/security/web/authentication/AuthenticationFilter.java

