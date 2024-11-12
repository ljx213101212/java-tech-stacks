cd /mnt/c/Work/elearning/java-tech-stacks/messaging_in_java
#!/bin/bash

# Maven build and packaging
mvn dependency:copy-dependencies package -DskipTests

# Publisher deployment
kubectl delete deployment publisher-deployment --ignore-not-found
docker rmi publisher-app
docker build -t publisher-app -f docker/task1/Dockerfile-publisher .
kubectl apply -f k8s/task1/publisher-deployment.yaml

# Subscriber deployment
kubectl delete deployment subscriber-deployment --ignore-not-found
docker rmi subscriber-app
docker build -t subscriber-app -f docker/task1/Dockerfile-subscriber .
kubectl apply -f k8s/task1/subscriber-deployment.yaml

# Non-Durable Subscriber deployment
kubectl delete deployment non-durable-subscriber-deployment --ignore-not-found
docker rmi non-durable-subscriber-app
docker build -t non-durable-subscriber-app -f docker/task1/Dockerfile-nonDurableSubscriber .
kubectl apply -f k8s/task1/non-durable-subscriber-deployment.yaml
