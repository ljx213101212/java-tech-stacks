package com.ljx213101212.messaging_in_java.task3;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class VirtualTopicSubscriber {

    private static final String VIRTUAL_TOPIC_NAME = "Consumer.SUBSCRIBER.VirtualTopic.TEST";

    public static void main(String[] args) throws JMSException {

        // Parameter for different subscribers
        String subscriberName = args.length > 0 ? args[0] : "Subscriber1";

        // Get broker URL from environment variable or use default (localhost)
        String brokerURL = System.getenv("BROKER_URL");
        if (brokerURL == null || brokerURL.isEmpty()) {
            brokerURL = "tcp://localhost:61616";
        }

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(VIRTUAL_TOPIC_NAME);

        MessageConsumer consumer = session.createConsumer(destination);

        System.out.println(subscriberName + " is now listening to " + VIRTUAL_TOPIC_NAME);

        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                try {
                    if (message instanceof TextMessage) {
                        String text = ((TextMessage) message).getText();
                        System.out.println(subscriberName + " received: " + text);
                    }
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        // Keep the application running
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                consumer.close();
                session.close();
                connection.close();
                System.out.println(subscriberName + " has shut down.");
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }));
    }
}

