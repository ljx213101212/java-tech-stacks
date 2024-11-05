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

### Migrate K8S

0. Prerequisite
- docker
  - [HOW](../HOW_TO_DOCKER.md)
- kubectl + minikube
  - [HOW](../HOW_TO_K8S_ON_WSL.md)

```commandline

```

1. Publish microservice image to Docker Hub

```commandline
cd microservices
docker login

docker build -t ljx213101212/micro-sender:latest ./micro-sender/.
docker push ljx213101212/micro-sender:latest

docker build -t ljx213101212/micro-recipient:latest ./micro-recipient/.
docker push ljx213101212/micro-recipient:latest

docker build -t ljx213101212/micro-collector:latest ./micro-collector/.
docker push ljx213101212/micro-collector:latest

```
> Delete all dangling images
> docker rmi $(docker images --filter "dangling=true" -q --no-trunc)


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
```

3. explore your microservices in k8s env (locally)

- 3.1 Method 1: use kubectl port-foward

- https://kubernetes.io/docs/tasks/access-application-cluster/port-forward-access-application-cluster/
- make local port [localport]:[forwarded port] to be forwarded to the pod's port, so we can control the microservice on the pod through our host machine(localhost)
```commandline
kubectl port-forward service/rabbitmq 5672:5672
kubectl port-forward service/prometheus 9090:9090
kubectl port-forward service/grafana 3000:3000

kubectl port-forward service/micro-sender 8081:8080
kubectl port-forward service/micro-recipient 8082:8080
kubectl port-forward service/micro-collector 8083:8080

sample:
curl --location 'localhost:8081/api/notification' \
--header 'Content-Type: application/json' \
--data '{
    "user": "jixiang",
    "message": "hello world!"
}'
```

- 3.2 Method 2: use kubectl proxy
- https://kubernetes.io/docs/tutorials/kubernetes-basics/explore/explore-intro/
```commandline
kubectl proxy

sample:
curl --location 'http://localhost:8001/api/v1/namespaces/default/pods/micro-sender-75dfcfcd85-bd58t:8080/proxy/api/notification' \
--header 'Content-Type: application/json' \
--data '{
    "user": "jixiang",
    "message": "hello world!"
}'
```

- 3.3 Method 3: use kubectl exec -it $POD_NAME -- bash


```commandline
sample:
kubectl exec micro-sender-75dfcfcd85-bd58t -- env
kubectl exec -it micro-sender-75dfcfcd85-bd58t -- bash

curl --location 'localhost:8080/api/notification' \
--header 'Content-Type: application/json' \
--data '{
    "user": "jixiang",
    "message": "hello world!"
}'

Please be noted that 8080 is the port inside container as we configured in deployment.yaml (containerPort)
```



