## Task 1 - 5 (20 points) 

1. [x] Create services: "micro-sender", "micro-recipient", "micro-collector"
2. [x] Create "docker-compose.yml"
3. [x] "micro-sender": REST endpoint (POST "/notification"), add message to rabbitmq , log by any logger
4. [] "micro-recipient": REST endpoint (GET "/message"), receive message to  List<String> by Spring Scheduler, log by any logger
5. [] "micro-collector": call GET "/message", log it by Spring Scheduler and ForeignClient