### Flyway

#### Quickstart

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

##### Run flyway command

````commandline
cd to flyway.conf file

flyway migrate 
````



