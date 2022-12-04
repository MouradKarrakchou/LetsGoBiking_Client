package org.example;

import POJO.ItinaryJava;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soap.ws.client.generated.*;

import javax.jms.JMSException;
import java.util.List;

public class Client {
    Bike bike;
    IBikeService bikeService;
    Consumer consumer;
    public Client() throws JMSException {
        this.bike = new Bike();
        this.bikeService = bike.getBasicHttpBindingIBikeService();
        consumer = new Consumer();

    }

    public Itinary getItinary() throws Exception {
        Itinary itinary = bikeService.getItinerary("Livraison Par Le, 20 Rue de l'Amitié, Bd Président John Fitzgerald Kennedy, 25000 Besançon", "91-93 Bd Léon Blum, 25000 Besançon");
        if(itinary == null) throw new Exception("Nous n'avons pas pu répondre à votre demande.");
        return itinary;
    }

    public ItinaryJava getItinaryByQueue() throws Exception {
        String json = consumer.receiveMessage();
        if(json == "") throw new Exception("La queue est vide / ou ya le beug");
        ItinaryJava itinaryJava = new ObjectMapper().readValue(json, ItinaryJava.class);
        return itinaryJava;
    }

    public void printItinary(Itinary itinary){
        List<FeatureItinary> features = itinary.getFeatures().getValue().getFeatureItinary();
        for (FeatureItinary f : features) {
            List<Segment> segments = f.getProperties().getValue().getSegments().getValue().getSegment();
            for (Segment d : segments) {
                List<Step> steps = d.getSteps().getValue().getStep();
                for (Step s : steps) {
                    System.out.println(s.getInstruction().getValue());
                }
            }
        }
    }

}
