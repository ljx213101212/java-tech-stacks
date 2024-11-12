package com.ljx213101212.messaging_in_java.task1;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Publisher {
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
            connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic(topicName);
            MessageProducer producer = session.createProducer(topic);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            while (true) {
                TextMessage message = session.createTextMessage("Hello, this is a test message!");
                producer.send(message);

                System.out.println("Message sent to topic: " + topicName);

                // Wait for 10 seconds before sending the next message
                Thread.sleep(10000);
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
