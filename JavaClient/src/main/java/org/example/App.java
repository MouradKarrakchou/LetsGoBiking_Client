package org.example;

import POJO.ItinaryJava;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soap.ws.client.generated.*;
import org.example.Map.UserGUI;

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

        /*Client client = new Client(new UserGUI());
        client.getItinaryByQueue("Livraison Par Le, 20 Rue de l'Amitié, Bd Président John Fitzgerald Kennedy, 25000 Besançon", "91-93 Bd Léon Blum, 25000 Besançon", "");
        //System.out.println(client.readQueue());
        client.update();
        System.out.println("After update");
       // System.out.println(client.readQueue());*/

    }


}
