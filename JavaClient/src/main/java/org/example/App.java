package org.example;

import POJO.ItinaryJava;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soap.ws.client.generated.*;

import javax.jms.JMSException;
import java.io.*;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("---Welcome on Let's Biking app!---");
        Client client = new Client();
        System.out.println((client.getItinaryByQueue()));
    }


}
