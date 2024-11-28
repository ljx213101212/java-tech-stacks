## How to enable HTTP/2 by using nginx as reverse proxy

1. Generate TLS Certificate
```commandline
cd nginx
mkdir certs
cd certs
openssl req -x509 -newkey rsa:4096 -keyout privkey.pem -out fullchain.pem -days 365 -nodes
```
This command creates two files:
- privkey.pem: The private key.
- fullchain.pem: The SSL certificate.

2. Config nginx.conf
   [check](nginx.conf)

3. Run nginx as docker
```commandline
cd nginx
docker compose up -d
```
4. Run you application on host machine

```commandline
cd your-maven-springboot-project
mvn package -DskipTests
java -jar target/spring_boot-0.0.1-SNAPSHOT.jar --server.port=8080 --server.address=0.0.0.0
```


### Miscellaneous

fast commands (for debugging purpose)
```commandline
docker cp nginx.conf  nginx_reverse_proxy:/etc/nginx/nginx.conf
docker run --name nginx_reverse_proxy --network="host" -d -p 443:443 -v $(pwd)/nginx.conf:/etc/nginx/nginx.conf:ro -v $(pwd)/certs:/etc/nginx/certs:ro nginx:latest

docker container start nginx_reverse_proxy
docker container stop nginx_reverse_proxy
docker container rm nginx_reverse_proxy
```