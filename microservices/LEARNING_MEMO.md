### RabbitMQ
messaging and streaming broker

- Acknowledgment Mechanisms in RabbitMQ

```commandline
Automatic Acknowledgment (autoAck = true): 
    The message is automatically acknowledged as soon as it is delivered to the consumer, regardless of whether the consumer has successfully processed the message or not. This can result in data loss if the consumer crashes before completing processing.
Manual Acknowledgment (autoAck = false): 
    The consumer explicitly sends an acknowledgment (ack) to RabbitMQ after it has successfully processed the message. 
    Only after this acknowledgment will RabbitMQ delete the message from the queue. 
    If the consumer fails before sending the acknowledgment, RabbitMQ will requeue the message and redeliver it to another available consumer (or the same consumer, if it becomes available again).
```

- Proper Usage of Ports
```commandline
- Port 5672: This port is used for AMQP connections. 
It’s meant for applications that want to connect to RabbitMQ as a message broker, such as using libraries like Spring AMQP or RabbitMQ client.
- Port 15672: This port is used for the RabbitMQ HTTP Management API. 
Use this port for HTTP requests such as monitoring or managing queues, access the UI dashboard.

```

### Grafana
a visualization tool that displays data from various sources in customizable dashboards.
- https://grafana.com/docs/grafana-cloud/visualizations/dashboards/use-dashboards/
- 
### Prometheus
a monitoring system that collects metrics data, stores it, and can trigger alerts based on defined rules.
##### Reference
- https://prometheus.io/docs/prometheus/latest/querying/examples/

### k8s
a container orchestration system for automating software deployment, scaling and management.

- kubectl
  - command-line interface (CLI) tool used to interact with Kubernetes clusters.
  - https://kubernetes.io/docs/tasks/tools/install-kubectl-windows/
- minikube
  - minikube is a tool that enables you to run a single-node Kubernetes cluster locally on your machine.
  - https://minikube.sigs.k8s.io/docs/start/?arch=%2Fwindows%2Fx86-64%2Fstable%2F.exe+download
  - https://kubernetes.io/docs/tutorials/hello-minikube/
  - https://minikube.sigs.k8s.io/docs/start/?arch=%2Fwindows%2Fx86-64%2Fstable%2F.exe+download
  - Better to have a dashboard to support your understanding about k8s env
    - https://minikube.sigs.k8s.io/docs/handbook/dashboard/

- 3 ways of accessing the pods
- Method 1: use kubectl port-foward

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

- Method 2: use kubectl proxy
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

- Method 3: use kubectl exec -it $POD_NAME -- bash


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

