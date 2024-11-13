package com.ljx213101212.messaging_in_java.task2;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Replier {
    public static void main(String[] args) {
        // Get broker URL from environment variable or use default (localhost)
        String brokerURL = System.getenv("BROKER_URL");
        if (brokerURL == null || brokerURL.isEmpty()) {
            brokerURL = "tcp://localhost:61616";
        }

        String requestQueue = "request.queue";

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
        Connection connection = null;

        try {
            // Create a connection to the broker
            connection = connectionFactory.createConnection();
            connection.start();

            // Create a session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the request queue
            Destination requestDestination = session.createQueue(requestQueue);

            // Create a consumer to receive request messages
            MessageConsumer consumer = session.createConsumer(requestDestination);

            // Set a message listener to handle incoming requests
            consumer.setMessageListener(message -> {
                if (message instanceof TextMessage) {
                    try {
                        TextMessage requestMessage = (TextMessage) message;
                        System.out.println("Received request: " + requestMessage.getText());

                        // Create the reply message
                        Destination replyDestination = requestMessage.getJMSReplyTo();
                        TextMessage replyMessage = session.createTextMessage("Here is your reply!");

                        // Create a producer to send the reply message
                        MessageProducer producer = session.createProducer(replyDestination);
                        producer.send(replyMessage);

                        System.out.println("Reply sent: " + replyMessage.getText());

                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });

            // Keep the program running to listen for incoming messages
            synchronized (Replier.class) {
                Replier.class.wait();
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
