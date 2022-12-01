package org.example;

import com.soap.ws.client.generated.Bike;
import com.soap.ws.client.generated.GeoLoca;
import com.soap.ws.client.generated.IBikeService;
import com.soap.ws.client.generated.Itinary;

import javax.jms.JMSException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws JMSException {
        System.out.println("---Welcome on Let's Biking app!---");
        Bike bike = new Bike();
        IBikeService bikeService= bike.getBasicHttpBindingIBikeService();
        Itinary itinary = bikeService.getItinerary("150 Rue Saint-Sever, 76100 Rouen","139 Rue du Gros Horloge, 76000 Rouen");

        Consumer consumer = new Consumer();
        System.out.println(itinary.getFeatures().getValue().getFeatureItinary().get(0).getProperties().getValue().getSegments().getValue().getSegment().get(0).getSteps().getValue().getStep().get(0).getInstruction().getValue());
        System.out.println(consumer.receiveMessage());
    }
}
