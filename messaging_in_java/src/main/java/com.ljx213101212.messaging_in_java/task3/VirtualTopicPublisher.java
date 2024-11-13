package com.ljx213101212.messaging_in_java.task3;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class VirtualTopicPublisher {

    private static final String VIRTUAL_TOPIC_NAME = "VirtualTopic.TEST";

    public static void main(String[] args) throws JMSException, InterruptedException {
        // Get broker URL from environment variable or use default (localhost)
        String brokerURL = System.getenv("BROKER_URL");
        if (brokerURL == null || brokerURL.isEmpty()) {
            brokerURL = "tcp://localhost:61616";
        }

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createTopic(VIRTUAL_TOPIC_NAME);

        MessageProducer producer = session.createProducer(destination);


        Long messageId = 0L;
        while (true) {
            TextMessage message = session.createTextMessage("Message " + messageId++);
            producer.send(message);
            System.out.println("Sent: " + message.getText());
            Thread.sleep(10000); // Send a message every 10 seconds
        }
    }
}
