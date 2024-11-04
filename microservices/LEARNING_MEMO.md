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

### Prometheus
a monitoring system that collects metrics data, stores it, and can trigger alerts based on defined rules.

