cd /mnt/c/Work/elearning/java-tech-stacks/messaging_in_java
#!/bin/bash

# Maven build and packaging
mvn dependency:copy-dependencies package -DskipTests

# Requester deployment
kubectl delete deployment requester-deployment --ignore-not-found
docker rmi requester-app
docker build -t requester-app -f docker/task2/Dockerfile-requester .
kubectl apply -f k8s/task2/requester-deployment.yaml

# Replier deployment
kubectl delete deployment replier-deployment --ignore-not-found
docker rmi replier-app
docker build -t replier-app -f docker/task2/Dockerfile-replier .
kubectl apply -f k8s/task2/replier-deployment.yaml
