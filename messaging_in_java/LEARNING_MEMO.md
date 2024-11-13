### Key Concepts
### Messaging patterns
https://www.enterpriseintegrationpatterns.com/patterns/messaging/MessageChannel.html

#### Publish-Subscribe Channel
https://www.enterpriseintegrationpatterns.com/patterns/messaging/PublishSubscribeChannel.html
##### Non-Durable Subscription
The subscriber only receives messages when it is actively connected. Messages sent while the subscriber is offline are not received.

##### Durable Subscription
```text
The broker retains messages sent to the topic while the subscriber is offline. When the subscriber reconnects, it receives all missed messages.

Please note that Start the DurableSubscriber at least once before running the Publisher to establish the durable subscription.
```

#### Request-Reply Channel
https://www.enterpriseintegrationpatterns.com/patterns/messaging/RequestReply.html

#### Virtual Topics
https://activemq.apache.org/components/classic/documentation/virtual-destinations
```text
 Allow producers to be decoupled from consumers so that they do not even know how many consumers are interested in the messages they publish
```

#### TASK 1 vs Task 2 vs Task 3

##### Task 1
```text
Producer (Publisher): Sends messages to a topic.
Consumer (Subscriber): Consumes messages from the topic. 
- DurableSubscriber: Receives messages even if disconnected, as messages are retained by the broker.
- NonDurableSubscriber: Receives messages only when actively connected.
```

##### Task 2
```text
Producer (Requester): Sends a message to a temporary queue and waits for a reply. Once a reply is received, it repeats the previous step.
Consumer (Replier): Consumes messages from the temporary queue, sends a reply back to the temporary queue, and repeats the process.
```

##### Task 3
```text
Producer (VirtualTopicPublisher): Sends messages to a virtual topic without concern for the number of consumers.
Consumer (VirtualTopicSubscriber): Consumes messages from a queue created for the virtual topic. Each consumer works independently, allowing for easy scaling without affecting the producer or other consumers.
```