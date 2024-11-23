### Quick Start

1. Start Local Mysql
````commandline
cd mysql
docker compose up -d
````

2. create database

```
connect to local mysql => 127.0.0.1:6603

CREATE DATABASE IF NOT EXISTS security_applications_demo;
GRANT ALL PRIVILEGES ON security_applications_demo.* TO 'user'@'%';
FLUSH PRIVILEGES;
```

3. You are all set.

4. All users/Admin password credentials
```commandline
user@example.com
admin@example.com
user2@example.com  <- non-standard user

password is 
123456
for each user/admin
```
5. Test
   1. Test bruteforce attempts
      - use any user/admin email, type the email correctly ,but make password wrong for 3 times
      - logout and login admin@example.com to view locked user list
   2. Test secret provider
      - use admin@example.com to create secret link
      - logout and login user@example.com to view the secret link (success)
      - logout and login admin@example.com to create a new secret link
      - logout and login user2@example.com to view the secret link (fail - because this user has no standard authority)


### Run in Docker

```commandline
docker compose down --remove-orphans
docker compose up --build -d
```

1. 
```commandline
docker exec -it <mysql-container-id> mysql -uroot -p
```
2. grant permission

```commandline
GRANT ALL PRIVILEGES ON security_applications_demo.* TO 'user'@'%';
GRANT ALL PRIVILEGES ON security_applications_demo_secret_provider.* TO 'user'@'%';

SHOW GRANTS FOR 'user'@'%';
```

2. 
```commandline
CREATE DATABASE IF NOT EXISTS security_applications_demo;
CREATE DATABASE IF NOT EXISTS security_applications_demo_secret_provider;
```
### Remarks

Can use separate internal page to create secret link for debugging purpose.
http://localhost:8081/secret-internal/new

Again, Don't mixed up with Docker's port  
6603:3306  
6603: The port which is forwarded outside container to your host PC (used for connecting from my PC) 
3306: The port which is inside container (used for connecting among container's network)

Please note: DNS rules because underscores are not permitted in domain names.

