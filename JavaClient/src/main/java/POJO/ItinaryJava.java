package POJO;

import com.soap.ws.client.generated.FeatureItinary;
import com.soap.ws.client.generated.Itinary;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ItinaryJava implements Serializable {
    public ArrayList<Feature> features;
    public boolean onFoot;

    public String getInstructionAsString(){
        StringBuilder instructions = new StringBuilder();
        for (Feature f : features) {
            ArrayList<POJO.Segment> segments = f.properties.segments;
            for (Segment d : segments) {
                ArrayList<POJO.Step> steps = d.steps;
                for (Step s : steps) {
                    instructions.append(s.instruction).append("\n");
                }
            }
        }
        return instructions.toString();
    }

    @Override
    public String toString() {
        return "Itinéraire:\n"+ getInstructionAsString();
    }
}


