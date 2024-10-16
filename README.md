### Pre-requisite
- Install Docker

> Use Docker Cli and Docker Compose without Docker Desktop(needs license for business use)

```commandline
1. Install Docker Engine
https://docs.docker.com/engine/install/binaries/

2. Install Docker Compose
https://docs.docker.com/compose/install/standalone/

3. Run dockerd as docker daemon service
https://stackoverflow.com/questions/63828261/run-dockerd-exe-to-start-docker-daemon
3.1. Create new config file daemon.json
{
  "group": "dd ocker-users"
}
3.2. Run dockerd with elevated permission
New-Service -Name Docker -BinaryPathName "C:\Apps\docker\dockerd.exe  --run-service --config-file C:\Apps\docker\daemon.json" -DisplayName "Docker Engine" -StartupType "Automatic"

(optional): to delete the service:
.\sc.exe STOP "Docker"
.\sc.exe DELETE "Docker"
```

run
```commandline
docker compose up

no matching manifest for windows/amd64 10.0.22631 in the manifest list entries

The error message indicates that the Docker images for Elasticsearch and Kibana don't have compatible versions for Windows on your system architecture (windows/amd64 10.0.22631).

```

#### solution

- Install WSL 2: Follow the instructions from the [Microsoft documentation](https://learn.microsoft.com/en-us/windows/wsl/install).
- Open Ubuntu WSL 2
- Update the package index and install required packages:
```commandline
sudo apt-get update
sudo apt-get install -y \
  apt-transport-https \
  ca-certificates \
  curl \
  gnupg \
  lsb-release
```
- Add Docker’s official GPG key:
```commandline
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
```
- Set up the Docker stable repository:
```commandline

echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu \
  $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

```

- Install Docker Engine:
```commandline
sudo apt-get update
sudo apt-get install -y docker-ce docker-ce-cli containerd.io

sudo systemctl enable docker
sudo systemctl start docker
```

- Bingo!:
```commandline
docker-compose -f docker-compose.yml up
```

#### advanced_multithreading

```commandline
mvn package -pl advanced_multithreading
```

#### apache_kafka

```commandline
mvn package -pl apache_kafka
```

#### kafka_stream

```commandline
mvn package -pl kafka_stream
```

#### monitoring_and_troubleshooting

```commandline
mvn package -pl monitoring_and_troubleshooting
```

#### spring_boot

```commandline
mvn package -DskipTests -pl spring_boot
java -jar spring_boot/target/spring_boot_3_2024-0.0.1-SNAPSHOT.jar
```

#### search_engines
- Setup ELK
```commandline
cd elastic-start-local
docker-compose -f docker-compose.yml up
```
```commandline
mvn package -pl search_engines
```

#### search_engine_lucene

```commandline
mvn package -pl search_engine_lucene
```

#### search_engine_elasticsearch

- Setup ELK
```commandline
cd elastic-start-local
docker-compose -f docker-compose.yml up
```

```commandline
mvn package -pl search_engines_elasticsearch
```


