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

