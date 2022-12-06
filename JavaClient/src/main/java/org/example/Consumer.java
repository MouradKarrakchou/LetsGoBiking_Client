package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.example.Map.UserGUI;

import javax.jms.*;

public class Consumer implements MessageListener {
    Connection connection;
    ConnectionFactory factory;
    Destination destination;
    Session session;
    MessageConsumer messageConsumer;
    UserGUI userGUI;

    public Consumer(UserGUI userGui) throws JMSException {
        factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        connection = factory.createConnection();
        this.userGUI=userGui;
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

    }


    public void setupConsumer(String username) throws JMSException {
        destination = session.createQueue(username);
        startConsumer();
        messageConsumer.setMessageListener(this);
    }


    public void startConsumer() throws JMSException {
        connection.start();
        messageConsumer = session.createConsumer(destination);
    }

    /**
     * Where there is a activemqResponse displays it on the interface
     * @param message
     */
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
