package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.example.Map.UserGUI;

import javax.jms.*;

public class Consumer implements MessageListener {
    int TIMEOUT = 1;
    Connection connection;
    ConnectionFactory factory;
    Destination destination;
    String DESTINATION_NAME = "test";
    Session session;
    MessageConsumer messageConsumer;
    UserGUI userGUI;

    public Consumer(UserGUI userGui) throws JMSException {
        factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        connection = factory.createConnection();
        this.userGUI=userGui;
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        destination = session.createQueue(DESTINATION_NAME);
        startConsumer();
        messageConsumer.setMessageListener(this);
    }

    public void startConsumer() throws JMSException {
        connection.start();
        messageConsumer = session.createConsumer(destination);
    }

    @Override
    public void onMessage(Message message) {
        String json="";
        try {
            json = ((TextMessage) message).getText();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        try {
            ActiveMqResponse activeMqResponse= new ObjectMapper().readValue(json, ActiveMqResponse.class);
            userGUI.updateItinary(activeMqResponse);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
