package org.example;

import com.soap.ws.client.generated.*;

import javax.jms.JMSException;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws JMSException {
        System.out.println("---Welcome on Let's Biking app!---");
        Bike bike = new Bike();
        IBikeService bikeService = bike.getBasicHttpBindingIBikeService();
        Itinary itinary = bikeService.getItinerary("Campus St Christophe, 10 Av. de l'Entreprise, 95800 Cergy", "18 All. de Chantaco, 95800 Courdimanche");
        //TODO (OU PAS DU COUP) MOURAD EFFACE PAS LES COMMENTAIRES COMME CA ON PEUT TESTER AVEC DES ADRESSES DIFFERENTES QUI ONT POSé PROBLEME
        //Itinary itinary = bikeService.getItinerary("Dieweg 69, 1180 Uccle, Belgique","Rue Geleytsbeek 2, 1180 Uccle, Belgique");
        //Itinary itinary = bikeService.getItinerary("87 Rue Greuze, 69100 Villeurbanne","21 Rue Flachet, 69100 Villeurbanne");
        if(itinary == null){
            System.out.println("UNE ERREUR EST SURVENUE");
            return;
        }
        System.out.println(itinary.getFeatures().getValue().getFeatureItinary().get(0).getProperties().getValue().getSegments().getValue().getSegment().get(0).getSteps().getValue().getStep().get(0).getInstruction().getValue());

        printItinary(itinary);
    }

    public static void printItinary(Itinary itinary){
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
