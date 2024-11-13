cd /mnt/c/Work/elearning/java-tech-stacks/messaging_in_java
#!/bin/bash

# teardown deployment
kubectl delete -f k8s/task1/ --ignore-not-found

# remove docker image
docker rmi publisher-app
docker rmi subscriber-app
docker rmi non-durable-subscriber-app