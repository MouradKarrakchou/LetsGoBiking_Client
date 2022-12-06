package org.example.Map;

import POJO.Feature;
import POJO.ItinaryJava;
import com.soap.ws.client.generated.*;
import org.example.Client;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.CenterMapListener;
import org.jxmapviewer.input.PanKeyListener;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.*;

import javax.jms.JMSException;
import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * A simple sample application that shows
 * a OSM map of Europe containing a route with waypoints
 * @author Martin Steiger
 */
public class MapTool
{
    JXMapViewer mapViewer = new JXMapViewer();
    Client client;

    /**
     * Initialise the map template
     */
    public void initialiseMap()
    {
        MapTool mapTool =new MapTool();


        // Display the viewer in a JFrame
        JFrame frame = new JFrame("JXMapviewer2 Example 2");
        UserGUI userGUI=new UserGUI("test", mapTool);
        frame.add(userGUI.getMyPanel());
        frame.setLayout(new GridLayout());
        frame.add(mapTool.mapViewer);
        mapTool.mapViewer.setSize(800,600);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


        // Add interactions
        MouseInputListener mia = new PanMouseInputListener(mapTool.mapViewer);
        mapTool.mapViewer.addMouseListener(mia);
        mapTool.mapViewer.addMouseMotionListener(mia);

        mapTool.mapViewer.addMouseListener(new CenterMapListener(mapTool.mapViewer));

        mapTool.mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCursor(mapTool.mapViewer));

        mapTool.mapViewer.addKeyListener(new PanKeyListener(mapTool.mapViewer));

        // Add a selection painter
        SelectionAdapter sa = new SelectionAdapter(mapTool.mapViewer);
        SelectionPainter sp = new SelectionPainter(sa);
        mapTool.mapViewer.addMouseListener(sa);
        mapTool.mapViewer.addMouseMotionListener(sa);
        mapTool.mapViewer.setOverlayPainter(sp);

        // Create a TileFactoryInfo for OpenStreetMap
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapTool.mapViewer.setTileFactory(tileFactory);
    }

    /**
     * Ask the server for the itinary in the case where we are asking the server directly
     * @param departure
     * @param finish
     * @param city
     * @return
     */
    static List<Itinary> checkItinary(String departure,String finish,String city){

        System.out.println("---Welcome on Let's Biking app!---");
        Bike bike = new Bike();
        IBikeService bikeService = bike.getBasicHttpBindingIBikeService();
        List<Itinary> itinary = bikeService.getItinerary(departure,finish, city).getItinary();

        if(itinary == null){
            System.out.println("UNE ERREUR EST SURVENUE");
            return null;
        }
        return itinary;
    }

    /**
     * Load the map with the Roads and checkpoints
     * @param departure
     * @param arrival
     * @param city
     * @return
     * @throws JMSException
     */
    public List<Itinary> generateMap(String departure, String arrival, String city) throws JMSException {

        List<Itinary> itinaryList=checkItinary(departure,arrival,city);
        generateMapContent(itinaryList);
        return(itinaryList);
    }

    /**
     * Create the connection to the queue
     * @param userGui
     * @param startPosText
     * @param finishPosText
     * @param city
     * @param username
     * @throws Exception
     */
    public void setUpConnectionToQueue(UserGUI userGui, String startPosText, String finishPosText, String city, String username) throws Exception {
        client = new Client(userGui);
        this.client.consumer.setupConsumer(username);
        client.getItinaryByQueue(startPosText,finishPosText,city, username);
    }

    /**
     * Check all the position in the itinary and display them on the map
     * @param itinaryList
     */
    public void generateMapContent(List<Itinary> itinaryList){

        Set<Waypoint> waypoints=new HashSet<>();
        List<RoutePainter> routePainterBycicle = new ArrayList<>();
        List<RoutePainter> routePainterFoot = new ArrayList<>();
        List<GeoPosition> allPositions=new ArrayList<>();


        for (Itinary itinary:itinaryList){
            List<GeoPosition> track=new ArrayList<>();

            for(FeatureItinary feature : itinary.getFeatures().getValue().getFeatureItinary()){
                Boolean first=true;

                for(ArrayOfdouble doubles :feature.getGeometry().getValue().getCoordinates().getValue().getArrayOfdouble()) {
                    List<Double> doubleList = doubles.getDouble();
                    if (first) waypoints.add(new DefaultWaypoint(doubleList.get(1), doubleList.get(0)));
                    track.add(new GeoPosition(doubleList.get(1), doubleList.get(0)));
                    first = false;
                }

            }

            if (itinary.isOnFoot())
                routePainterFoot.add(new RoutePainter(track,Color.BLUE));
            else
                routePainterBycicle.add(new RoutePainter(track,Color.RED));
            allPositions.addAll(track);
        }
        updateMap( waypoints,routePainterBycicle,routePainterFoot,allPositions);
    }

    /**
     * Check all the position in the itinary and display them on the map
     * @param itinary
     */
    public void updateForQueue(ItinaryJava itinary) {
        Set<Waypoint> waypoints=new HashSet<>();
        List<RoutePainter> routePainterBycicle = new ArrayList<>();
        List<RoutePainter> routePainterFoot = new ArrayList<>();
        List<GeoPosition> allPositions=new ArrayList<>();
        List<GeoPosition> track=new ArrayList<>();

        for(Feature feature : itinary.features){

            Boolean first=true;
            for(ArrayList<Double> doubles :feature.geometry.coordinates) {

                if (first) waypoints.add(new DefaultWaypoint(doubles.get(1), doubles.get(0)));
                track.add(new GeoPosition(doubles.get(1), doubles.get(0)));
                first = false;

            }

        }

        if (itinary.onFoot)
            routePainterFoot.add(new RoutePainter(track,Color.BLUE));
        else
            routePainterBycicle.add(new RoutePainter(track,Color.RED));
        allPositions.addAll(track);
        updateMap( waypoints,routePainterBycicle,routePainterFoot,allPositions);
    }

    /**
     * Create the points and roads on the map
     * @param waypoints
     * @param routePainterBycicle
     * @param routePainterFoot
     * @param allPositions
     */
    public void updateMap( Set<Waypoint> waypoints,List<RoutePainter> routePainterBycicle,List<RoutePainter> routePainterFoot,List<GeoPosition> allPositions){

        waypoints.add(new DefaultWaypoint(allPositions.get(allPositions.size()-1).getLatitude(),allPositions.get(allPositions.size()-1).getLongitude()));

        // Set the focus
        mapViewer.zoomToBestFit(new HashSet<GeoPosition>(List.of(allPositions.get(0))), 0.7);


        // Create a waypoint painter that takes all the waypoints
        WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<Waypoint>();
        waypointPainter.setWaypoints(waypoints);

        // Create a compound painter that uses both the route-painter and the waypoint-painter
        List<Painter<JXMapViewer>> painters = new ArrayList<Painter<JXMapViewer>>();
        painters.addAll(routePainterFoot);
        painters.addAll(routePainterBycicle);
        painters.add(waypointPainter);

        CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>(painters);

        mapViewer.setOverlayPainter(painter);
    }

}
