package org.example;

import POJO.ItinaryJava;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soap.ws.client.generated.*;
import org.example.Map.UserGUI;

import javax.jms.JMSException;
import javax.naming.NamingException;
import java.lang.Exception;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Client {
    Bike bike;
    IBikeService bikeService;
    public Consumer consumer;


    public Client(UserGUI userGui) throws JMSException,NamingException {
        this.bike = new Bike();
        this.bikeService = bike.getBasicHttpBindingIBikeService();
        consumer = new Consumer(userGui);

    }

    /**
     *
     * @param startPosText
     * @param finishPosText
     * @param city
     * @param username
     * @throws Exception
     */
    public void getItinaryByQueue(String startPosText, String finishPosText,String city, String username) throws Exception {
        bikeService.putDataContainerInQueue(startPosText, finishPosText,city, username);
    }

    /**
     * Update the content of the queue, if the itinary need to be updated, he will be updated
     * @throws Exception
     */
    public void update() throws Exception {
        bikeService.update();
    }



}
