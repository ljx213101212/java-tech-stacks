### About

- micro-sender: Responsible for accepting notifications and adding messages to RabbitMQ.
- micro-recipient: Will read messages from RabbitMQ and collect them in-memory.
- micro-collector: Will call the /message endpoint of micro-recipient to log and collect data.
- micro-visualizer:  "grafana" service to connect and visualize data from "prometheus"


### Quick Start (locally)
1. Build all micro-services
```commandline
cd microservices
mvn clean package -DskipTests
```
2.1 start docker (in Ubuntu WSL 2 [HOW](../HOW_TO_DOCKER.md)) 
```commandline
cd microservices
docker compose -f docker-compose.yml up --build
docker ps
make sure all 4 microservices are on
- rabbitmq
- sender
- recipient
- collector
```
2.2 or you start docker only for rabbitmq
```commandline
cd microservices
docker compose -f docker-compose-local.yml up --build
docker ps
make sure only rabbitmq is on
```

n. Access RabbitMQ Management Console:
```commandline
http://localhost:15672
guest:guest

docker exec -it 
```


