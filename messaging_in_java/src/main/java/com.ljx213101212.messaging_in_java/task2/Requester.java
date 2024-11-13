package com.ljx213101212.messaging_in_java.task2;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Requester {
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

            // Create the producer to send a request message
            MessageProducer producer = session.createProducer(requestDestination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            // Create a temporary queue for the reply
            TemporaryQueue replyQueue = session.createTemporaryQueue();

            // Create and send the request message
            TextMessage requestMessage = session.createTextMessage("Hello, I need a reply!");
            requestMessage.setJMSReplyTo(replyQueue);
            producer.send(requestMessage);

            System.out.println("Request sent: " + requestMessage.getText());

            // Create a consumer for the reply queue
            MessageConsumer replyConsumer = session.createConsumer(replyQueue);

            // Wait for replies and resend requests in a loop
            while (true) {
                // Wait for the reply indefinitely
                Message replyMessage = replyConsumer.receive();
                if (replyMessage instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) replyMessage;
                    System.out.println("Received reply: " + textMessage.getText());
                }

                // Wait for 10 seconds before sending the request message again
                Thread.sleep(10000);

                // Send the request message again
                producer.send(requestMessage);
                System.out.println("Request sent again: " + requestMessage.getText());
            }

        } catch (JMSException | InterruptedException e) {
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
