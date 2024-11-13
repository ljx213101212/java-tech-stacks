package com.ljx213101212.messaging_in_java.task1;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class DurableSubscriber {
    public static void main(String[] args) {
        // Get broker URL from environment variable or use default (localhost)
        String brokerURL = System.getenv("BROKER_URL");
        if (brokerURL == null || brokerURL.isEmpty()) {
            brokerURL = "tcp://localhost:61616";
        }
        String topicName = "example.topic";
        String clientID = "durableSubscriberClient";

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
        Connection connection = null;

        try {
            connection = connectionFactory.createConnection();
            connection.setClientID(clientID);
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic(topicName);
            MessageConsumer consumer = session.createDurableSubscriber(topic, "subscriptionName");

            consumer.setMessageListener(message -> {
                if (message instanceof TextMessage) {
                    try {
                        TextMessage textMessage = (TextMessage) message;
                        System.out.println("Received (Durable): " + textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });

            System.out.println("Listening for messages on topic: " + topicName);

            // Keep the application running to listen for messages indefinitely
            synchronized (DurableSubscriber.class) {
                DurableSubscriber.class.wait();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
