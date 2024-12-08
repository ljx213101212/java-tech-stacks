# Kotlin_http4k

## Prerequisite
Start Local Mysql and create database
````commandline
cd mysql
docker compose up -d
````
```sql
CREATE DATABASE IF NOT EXISTS kotlin_demo;
```
for simplicity, we use root:root to access local mysql

## Test

```commandline
./gradlew test
```

## Run

```commandline
./gradlew run
```

## Build
```commandline
./gradlew build
```

## Package
```
./gradlew distZip
```




