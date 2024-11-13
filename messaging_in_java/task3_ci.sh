cd /mnt/c/Work/elearning/java-tech-stacks/messaging_in_java
#!/bin/bash

# Maven build and packaging
mvn dependency:copy-dependencies package -DskipTests

# VirtualTopicPublisher  deployment
kubectl delete deployment virtual-topic-publisher-deployment --ignore-not-found
docker rmi virtual-topic-publisher-app
docker build -t virtual-topic-publisher-app -f docker/task3/Dockerfile-virtualTopicPublisher .
kubectl apply -f k8s/task3/virtual-topic-publisher-deployment.yaml

# VirtualTopicSubscriber  deployment
kubectl delete deployment virtual-topic-subscriber-deployment --ignore-not-found
docker rmi virtual-topic-subscriber-app
docker build -t virtual-topic-subscriber-app -f docker/task3/Dockerfile-virtualTopicSubscriber .
kubectl apply -f k8s/task3/virtual-topic-subscriber-deployment.yaml