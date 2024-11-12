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


