package org.example;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Consumer {
    int TIMEOUT = 1;
    Connection connection;
    ConnectionFactory factory;
    Destination destination;
    String DESTINATION_NAME = "test";
    Session session;
    MessageConsumer messageConsumer;

    public Consumer() throws JMSException {
        factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        connection = factory.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        destination = session.createQueue(DESTINATION_NAME);
        startConsumer();
    }

    public void startConsumer() throws JMSException {
        connection.start();
        messageConsumer = session.createConsumer(destination);
    }

    public String receiveMessage() throws JMSException {
        Message message = messageConsumer.receive(TIMEOUT);
        String text = "";
        if (message != null)
            text = ((TextMessage) message).getText();
        return text;
    }
}
