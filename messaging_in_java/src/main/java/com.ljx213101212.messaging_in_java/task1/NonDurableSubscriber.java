package com.ljx213101212.messaging_in_java.task1;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class NonDurableSubscriber {
    public static void main(String[] args) {
        // Get broker URL from environment variable or use default (localhost)
        String brokerURL = System.getenv("BROKER_URL");
        if (brokerURL == null || brokerURL.isEmpty()) {
            brokerURL = "tcp://localhost:61616";
        }
        String topicName = "example.topic";

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
        Connection connection = null;

        try {
            // Create a connection to the broker
            connection = connectionFactory.createConnection();
            connection.start();

            // Create a session (non-transacted, auto-acknowledge)
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the topic
            Topic topic = session.createTopic(topicName);

            // Create a non-durable subscriber to the topic
            MessageConsumer consumer = session.createConsumer(topic);

            // Set a message listener to handle incoming messages
            consumer.setMessageListener(message -> {
                if (message instanceof TextMessage) {
                    try {
                        TextMessage textMessage = (TextMessage) message;
                        System.out.println("Received (Non-Durable): " + textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });

            // Keep the program running to listen for messages
            System.out.println("Listening for messages on topic: " + topicName);
            // Keep the application running to listen for messages indefinitely
            synchronized (NonDurableSubscriber.class) {
                NonDurableSubscriber.class.wait();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Clean up
            try {
                if (connection != null) connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
