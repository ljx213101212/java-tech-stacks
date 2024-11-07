### About

- micro-sender: Responsible for accepting notifications and adding messages to RabbitMQ.
- micro-recipient: Will read messages from RabbitMQ and collect them in-memory.
- micro-collector: Will call the /message endpoint of micro-recipient to log and collect data.
- micro-visualizer:  "grafana" service to connect and visualize data from "prometheus"


### Quick Start (locally) Task 1 -10
1. Build all micro-services
```commandline
cd microservices
mvn clean package -DskipTests
```
2.1 Run container through docker-compose.yml (in Ubuntu WSL 2 [HOW](../HOW_TO_DOCKER.md)) 
```commandline
cd microservices
docker compose -f docker-compose.yml up --build
```
2.2 Run container through docker directly

```commandline
docker container run -d -p 5672:5672 -p 15672:15672 rabbitmq:3-management
```

2.3 Run container through k8s
```commandline
cd rabbit-mq
kubectl apply -f deployment.yaml
kubectl port-forward service/rabbitmq 5672:5672 15672:15672
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

### Migrate K8S

0. Prerequisite
- docker
  - [HOW](../HOW_TO_DOCKER.md)
- kubectl + minikube
  - [HOW](../HOW_TO_K8S_ON_WSL.md)

```commandline
mvn clean package -DskipTests
```

1. Publish microservice image to Docker Hub

```commandline
cd microservices
docker login

docker build -t ljx213101212/micro-sender:latest ./micro-sender/.
docker push ljx213101212/micro-sender:latest

docker build -t ljx213101212/micro-recipient:latest ./micro-recipient/.
docker push ljx213101212/micro-recipient:latest

docker build -t ljx213101212/micro-collector:latest -f ./micro-collector/Dockerfile-docker ./micro-collector/.
docker push ljx213101212/micro-collector:latest

docker build -t ljx213101212/micro-collector-k8s:latest -f ./micro-collector/Dockerfile-k8s ./micro-collector/.
docker push ljx213101212/micro-collector-k8s:latest

docker build -t ljx213101212/micro-visualizer-k8s:latest -f ./micro-visualizer/Dockerfile-k8s ./micro-visualizer/.
docker push ljx213101212/micro-visualizer-k8s:latest
```
> Delete all dangling images
> docker rmi $(docker images --filter "dangling=true" -q --no-trunc)

> tailing logs
> kubectl logs -f --tail=10 micro-collector-667ddc4f55-6d7g2


2. deploy your microservices into k8s (locally)

```commandline
cd your microservice
kubectl apply -f deployment.yaml

for prometheus need to create the configmap
kubectl create configmap prometheus-config --from-file=k8s/prometheus.yml

for prometheu to update the configmap
kubectl create configmap prometheus-config --from-file=k8s/prometheus.yml --dry-run=client -o yaml | kubectl apply -f -
kubectl delete pod -l app=prometheus
(it will automatically recreate the pod)

for micro-collector (task 13 onwards)
kubectl set image deployment/micro-collector micro-collector=ljx213101212/micro-collector:latest
kubectl apply -f deployment-k8s.yaml
```

3. explore your microservices in k8s env (locally) [check](./LEARNING_MEMO.md)

```commandline
kubectl port-forward service/rabbitmq 5672:5672

kubectl proxy
http://localhost:8001/api/v1/namespaces/default/pods/micro-sender-75dfcfcd85-bd58t:8080/proxy

kubectl exec -it micro-sender-75dfcfcd85-bd58t -- bash
```



