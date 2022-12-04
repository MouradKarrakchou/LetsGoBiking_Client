package org.example;

import POJO.ItinaryJava;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soap.ws.client.generated.*;

import javax.jms.JMSException;
import java.io.*;
import java.lang.Exception;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws Exception, Exception {
        System.out.println("---Welcome on Let's Biking app!---");

        Client client = new Client();
        client.getItinaryByQueue();
        ActiveMqResponse response = client.readQueue();
        if(response.exception == null)
            System.out.println(response);
        else
            System.out.println(response.exception);
    }


}
