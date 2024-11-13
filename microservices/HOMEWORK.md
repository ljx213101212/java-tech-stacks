# Micro-services Module Practical Task

## Necessary Tools

Java Development Kit 17+

Gradle 8.0+

Git 2.24+

Docker

Kubernetes

##Task

(20 points)

##### 1. Create services: "micro-sender", "micro-recipient", "micro-collector"
##### 2. Create "docker-compose.yml", with the following configuration:
- "micro-sender" should be in "sender" network
- "micro-recipient" should be in "recipient" network
- "rabbitmq" (you can find it into the docker-com.ljx213101212.flyway.repository). This service should be in "sender" and "recipient" networks
- "micro-collector" should be in "recipient"
- Ports configuration
##### 3. The "micro-sender" must be configured with the following requirements:
- REST endpoint (POST "/notification") receives the following JSON request:
  {
  "user": "Name LastName",
  "message": "String"
  }
- POST "/notification" call should add the "message" value from JSON to queue into "rabbitmq" service
- POST "/notification" call should be logged by any Logger
##### 4. The "micro-recipient" must be configured with the following requirements:
- Must receive messages from "rabbitmq" service and collect it to List<String> (you can use in-memory DB instead) every (N) second(-s) (use Spring Scheduler for it)
- REST endpoint (GET "/message") returns List<String> (or records from in-memory DB) and cleans it
- Scheduler should be logged by any Logger
- GET "/message" call should be logged by any Logger
##### 5. The "micro-collector" must be configured with the following requirements:
- Must call GET "/message" from "micro-recipient" and log it every (M > N) second(-s). (use Spring Scheduler and ForeignClient)
- Scheduler should be logged by any Logger

(40 points)

##### 6. Update the "docker-compose.yml" with:
- "prometheus". (u)se the "prom/prometheus" docker image)
- "grafana" (use the "grafana/grafana" docker image)
##### 7. Update the micro-sender, micro-recipient, micro-collector to collect all metrics on "/actuator/prometheus"
##### 8. Add additional metrics (you can randomize it) to one of your services
##### 9. Configure a "grafana" service to connect and visualize data from "prometheus"
##### 10. Open a "grafana" UI and check results (make screenshot)

(20 points)

##### 11. Migrate all services (with "grafana" and "prometheus") to Kubernetes
##### 12. Configure a "deployment.yaml" for your services. Test it
##### 13. Add a "postgres" service (use "postgres:latest" docker image)
##### 14. Update the "micro-collector" to save data to "postgres"
##### 15. Add a "micro-visualizer" service, with the following configuration:
    - REST endpoint (GET "/saved-messages") returns data from "postgres" DB
##### 16. Re-deploy a new version of "micro-collector" with one of the following deployment strategies (make screenshot):
    - Canary deployments
    - A/B testing
    - Blue-green deployments
##### 17. Configure the "prometheus" and the "micro-visualizer" to read metrics from the "micro-visualizer"

(20 points)

##### 18. Update the "micro-recipient" and the "micro-collector" to read only one message per call. Change Scheduler's time to (5*N) for the "micro-collector"
##### 19. Re-deploy the "micro-recipient" and the "micro-collector" with one of the following deployment strategies (make screenshot):
    - Canary deployments
    - A/B testing
    - Blue-green deployments
##### 20. Add a few replication for the "micro-collector" service
##### 21. Configure the "prometheus" service to collect "micro-collector" metrics (for each replication) into the single task

Create Pull Request with the solution's source code and screenshots

## References

[Patterns](https://microservices.io/)

[Prometheus](https://prometheus.io/docs/introduction/overview/)

[Grafana](https://grafana.com/tutorials/)

[Deployment Strategies](https://www.infoworld.com/article/3565750/4-deployment-strategies-for-resilient-microservices.html)
