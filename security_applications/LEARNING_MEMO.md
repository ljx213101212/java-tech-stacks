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
try it on https://jwt.io/
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

### Quick Command to kill dangling Java program (sometimes spring mvc doesn't stop)
```commandline
for /f "tokens=2" %i in ('tasklist ^| findstr java.exe') do taskkill /PID %i /F
```
#### No they are actually intellij's plugins... not spring mvc
```commandline
C:\Users\Jixiang_Li>jps -l
26272 c:\Users\Jixiang_Li\.vscode\extensions\sonarsource.sonarlint-vscode-4.12.0-win32-x64\server\sonarlint-ls.jar
33664 org.jetbrains.jps.cmdline.Launcher
5748 jdk.jcmd/sun.tools.jps.Jps
32392 c:\Users\Jixiang_Li\.vscode\extensions\redhat.java-1.36.0-win32-x64\server\plugins\org.eclipse.equinox.launcher_1.6.900.v20240613-2009.jar
32696 c:\Users\Jixiang_Li\.vscode\extensions\sonarsource.sonarlint-vscode-4.12.0-win32-x64\server\sonarlint-ls.jar
32776 com.ljx213101212.secret_provider.SecretProviderApplication
2620
```
### Add logback.xml in resources folder to display specific java file to locate the line number

```commandline
<configuration>
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} [%file:%line] - %msg%n</pattern>
        </encoder>
    </appender>
    <root level="info">
        <appender-ref ref="CONSOLE" />
    </root>
</configuration>
```




## Reference

demo source
https://www.codejava.net/frameworks/spring-boot/spring-security-limit-login-attempts-example
https://github.com/Baeldung/spring-security-registration/blob/master/src/main/java/com/baeldung/security/LoginAttemptService.java

spring source
https://github.com/spring-projects/spring-security/blob/main/web/src/main/java/org/springframework/security/web/authentication/AuthenticationFilter.java

scenario
https://onetimesecret.com/

