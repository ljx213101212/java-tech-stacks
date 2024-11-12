### Prerequisites

```properties
ActiveMQ Classic 6.1.3 
https://activemq.apache.org/components/classic/download/

for
minikube
docker
please check microservices project
```

### Quick Start (in k8s)

#### Start the ActiveMQ Broker
```commandline
cd bin
On Windows: activemq.bat start
On Unix/Linux/Mac: ./activemq start
```

#### Start Minikube
```commandline
minikube start

minikube tunnel (expose LoadBalancer or NodePort service ip outside k8s cluster)
```

#### Start Jenkins pipeline [TODO]

Jenkins should run these command below:

```commandline
#mvn clean dependency:copy-dependencies package -DskipTests
mvn dependency:copy-dependencies package -DskipTests

kubectl delete deployment publisher-deployment
docker rmi publisher-app
docker build -t publisher-app -f docker/task1/Dockerfile-publisher .
kubectl apply -f k8s/task1/publisher-deployment.yaml


kubectl delete deployment subscriber-deployment
docker rmi subscriber-app
docker build -t subscriber-app -f docker/task1/Dockerfile-subscriber .
kubectl apply -f k8s/task1/subscriber-deployment.yaml


kubectl delete deployment non-durable-subscriber-deployment
docker rmi non-durable-subscriber-app
docker build -t non-durable-subscriber-app -f docker/task1/Dockerfile-nonDurableSubscriber .
kubectl apply -f k8s/task1/non-durable-subscriber-deployment.yaml


```

### k8s Deployment

- activemq
```commandline
kubectl apply -f k8s/activemq-deployment.yaml

make sure service type is :LoadBalancer
```

- publisher

```commandline
kubectl apply -f k8s/task1/publisher-deployment.yaml

if you need to re-run the pod
kubectl delete pods -l app=publisher


kubectl apply -f k8s/task1/subscriber-deployment.yaml
```





