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
2.1 all in docker (in Ubuntu WSL 2 [HOW](../HOW_TO_DOCKER.md)) 
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
2.2 start docker only for rabbitmq,prometheus, grafana
```commandline
cd microservices
docker compose -f docker-compose-local.yml up --build
docker ps
make sure only rabbitmq is on
```

3. Access RabbitMQ Management Console:
```commandline
http://localhost:15672
guest:guest

docker exec -it container-name bin/bash
```

4. Access prometheus Management Console:
```commandline
http://localhost:9090/targets
```

5. Access grafana console:

```commandline
http://localhost:3000/
```


