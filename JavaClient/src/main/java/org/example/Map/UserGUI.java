package org.example.Map;

import com.soap.ws.client.generated.FeatureItinary;
import com.soap.ws.client.generated.Itinary;
import com.soap.ws.client.generated.Segment;
import com.soap.ws.client.generated.Step;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserGUI extends JFrame {
    private JPanel myPanel;
    private JTextField startPos;
    private JTextField finishPos;
    private JButton allInOnceButton;
    private JButton updatableButton;
    private JPanel ScrollBarPanel;
    private final JTextArea textArea1;

    public UserGUI(String title, Sample2 sample2){
        super(title);
        //Livraison Par Le, 20 Rue de l'Amitié, Bd Président John Fitzgerald Kennedy, 25000 Besançon
        // 91-93 Bd Léon Blum, 25000 Besançon
        this.startPos.setText("Livraison Par Le, 20 Rue de l'Amitié, Bd Président John Fitzgerald Kennedy, 25000 Besançon");
        this.finishPos.setText("91-93 Bd Léon Blum, 25000 Besançon");


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(this.myPanel);
        this.pack();
        textArea1=new JTextArea(28, 40);
        textArea1.setText("");
        textArea1.setLineWrap(true);
        textArea1.setWrapStyleWord(true);

        JScrollPane scrollPane=new JScrollPane(textArea1,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        ScrollBarPanel.add(scrollPane);



        allInOnceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Itinary itinary = sample2.generateMap(startPos.getText(),finishPos.getText());
                printAllStep(itinary);
            }
        });
    }
    private void printAllStep(Itinary itinary){
        for(FeatureItinary feature : itinary.getFeatures().getValue().getFeatureItinary()){
            Boolean first=true;
            for(Segment segment :feature.getProperties().getValue().getSegments().getValue().getSegment()) {
                for (Step step: segment.getSteps().getValue().getStep()){

                    this.textArea1.append(step.getInstruction().getValue()+"("+step.getDistance()+"m/"+step.getDuration()+"s) \n");
                }
            }
        }
    }
    public JPanel getMyPanel() {
        return myPanel;
    }

}
