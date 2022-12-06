package org.example.Map;

import POJO.ItinaryJava;
import com.soap.ws.client.generated.*;
import org.example.ActiveMqResponse;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UserGUI extends JFrame {
    private JPanel myPanel;
    private JTextField startPos;
    private JTextField finishPos;
    private JButton allInOnceButton;
    private JButton updatableButton;
    private JPanel ScrollBarPanel;
    private JButton updateButton;
    private JTextArea textAreaError;
    private JTextField closestCity;
    private final JTextArea mainTextArea;
    Sample2 sample;

    public UserGUI(String title, Sample2 sample2){
        super(title);
        this.sample=sample2;

        //Livraison Par Le, 20 Rue de l'Amitié, Bd Président John Fitzgerald Kennedy, 25000 Besançon
        // 91-93 Bd Léon Blum, 25000 Besançon
        this.startPos.setText("Livraison Par Le, 20 Rue de l'Amitié, Bd Président John Fitzgerald Kennedy, 25000 Besançon");
        this.finishPos.setText("91-93 Bd Léon Blum, 25000 Besançon");


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(this.myPanel);
        this.pack();
        mainTextArea =new JTextArea(28, 40);
        mainTextArea.setText("");
        mainTextArea.setLineWrap(true);
        mainTextArea.setWrapStyleWord(true);

        JScrollPane scrollPane=new JScrollPane(mainTextArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        ScrollBarPanel.add(scrollPane);

        updateButton.setVisible(false);

        allInOnceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateButton.setVisible(false);
                List<Itinary> itinary = sample2.generateMap(startPos.getText(),finishPos.getText());
                printAllStep(itinary);
            }
        });

        updatableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateButton.setVisible(true);
                try {
                    sample2.startMapWithQueue(UserGUI.this,startPos.getText(),finishPos.getText());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
    private void printAllStep(List<Itinary> itinaryList){
        for(Itinary itinary:itinaryList){
            if (itinary.isOnFoot())
                this.mainTextArea.append("========================================\nSTEPS WALK : \n========================================\n");
            else
                this.mainTextArea.append("========================================\nSTEPS ON BIKE : \n========================================\n");
            for(FeatureItinary feature : itinary.getFeatures().getValue().getFeatureItinary()){
                for(Segment segment :feature.getProperties().getValue().getSegments().getValue().getSegment()) {
                    for (Step step: segment.getSteps().getValue().getStep()){
                        this.mainTextArea.append(step.getInstruction().getValue()+"("+step.getDistance()+"m/"+step.getDuration()+"s) \n");
                    }
                }
            }
        }
    }
    private void printStep(ActiveMqResponse activeMqResponse ){
        this.mainTextArea.setText("");
        if (activeMqResponse.itinary!=null)
        {
        this.sample.updateForQueue(activeMqResponse.itinary);
        List<ItinaryJava> itinarys=activeMqResponse.itinary;
        for(ItinaryJava itinary:itinarys){
            if (itinary.onFoot)
                this.mainTextArea.append("========================================\nSTEPS WALK : \n========================================\n");
            else
                this.mainTextArea.append("========================================\nSTEPS ON BIKE : \n========================================\n");
            for(POJO.Feature feature : itinary.features){
                for(POJO.Segment segment :feature.properties.segments) {
                    for (POJO.Step step: segment.steps){
                        this.mainTextArea.append(step.instruction+"("+step.distance+"m/"+step.duration+"s) \n");
                    }
                }
            }
        }}
        this.textAreaError.setText(activeMqResponse.exception);

    }


    public void updateItinary(ActiveMqResponse activeMqResponse){
        printStep(activeMqResponse);
    }
    public JPanel getMyPanel() {
        return myPanel;
    }

}
