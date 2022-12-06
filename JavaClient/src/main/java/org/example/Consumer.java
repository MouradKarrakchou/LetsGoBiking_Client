package org.example;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

public class Consumer {
    int TIMEOUT = 1;
    Connection connection;
    ConnectionFactory factory;
    Destination destination;
    String DESTINATION_NAME = "test";
    Session session;
    MessageConsumer messageConsumer;
    InitialContext context = null;
    Queue queue;
    javax.jms.MessageConsumer qReceiver;


    public Consumer() throws JMSException, NamingException {
        Hashtable properties = new Hashtable();
        properties.put(Context.INITIAL_CONTEXT_FACTORY,"org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        properties.put(Context.PROVIDER_URL, "tcp://localhost:61616");
        context = new InitialContext(properties);

        factory =  (ConnectionFactory) context.lookup("ConnectionFactory");
        connection = factory.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //destination = session.createQueue(DESTINATION_NAME);
        queue = (Queue) context.lookup("dynamicQueues/"+DESTINATION_NAME);

        qReceiver = session.createConsumer(queue);

        startConsumer();
    }

    public void startConsumer() throws JMSException {
        connection.start();
        //messageConsumer = session.createConsumer(destination);
    }

    public String receiveMessage() throws JMSException {
        Message message = messageConsumer.receive(TIMEOUT);
        String text = "";
        if (message != null)
            text = ((TextMessage) message).getText();
        return text;
    }

    public String receiveMessage2() throws JMSException {
        Message message = qReceiver.receive(TIMEOUT);
        String text = "";
        if (message != null)
            text = ((TextMessage) message).getText();
        return text;
    }
}
