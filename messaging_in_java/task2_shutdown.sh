cd /mnt/c/Work/elearning/java-tech-stacks/messaging_in_java
#!/bin/bash

# teardown deployment
kubectl delete -f k8s/task2/ --ignore-not-found


# wait kubectl delete clear-out the image references
sleep 5

# remove docker image
docker rmi requester-app
docker rmi replier-app