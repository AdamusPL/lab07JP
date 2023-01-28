package Apps.World;

import model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class WorldMap extends JFrame implements ActionListener {
    public void initialize(){
        artifacts=new ArrayList<>();
    }

    ArrayList<Artifact> artifacts;
    JPanel simPanel;
    Map map;
    private JPanel contentPane;
    JTextField x;
    JTextField y;
    JTextField artifact;
    JButton accept;
    public WorldMap() throws IOException {
        map = new Map(".");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 850, 800);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null); //zeby mozna bylo dowolnie ustawiac miejsca panelow w ramce

        simPanel = new JPanel();
        simPanel.setBounds(5, 5, 1280, 720);
        simPanel.setLayout(null);

        //tu dodaj rzeczy do okienka
        map.makeMap();

        for (ArrayList<Field> rowArray : map.fieldLabelsArray) {
            for (Field f : rowArray) {
                JLabel label = f.label;
                label.setBounds(f.x, f.y, 10, 10);
                simPanel.add(label);
            }
        }

        JLabel labelX = new JLabel("Map sector");
        labelX.setBounds(740,280,80,20);

        JLabel labelY = new JLabel("Inside sector");
        labelY.setBounds(745,330,80,20);

        JLabel labelA = new JLabel("Artifact");
        labelA.setBounds(750,380,60,20);

        x = new JTextField();
        x.setBounds(750,300,60,20);

        y = new JTextField();
        y.setBounds(750,350,60,20);

        artifact = new JTextField();
        artifact.setBounds(750,400,60,20);

        accept = new JButton("OK");
        accept.setBounds(750,450,60,20);
        accept.addActionListener(this);

        simPanel.add(labelX);
        simPanel.add(labelY);
        simPanel.add(labelA);
        simPanel.add(x);
        simPanel.add(y);
        simPanel.add(artifact);
        simPanel.add(accept);

        contentPane.add(simPanel, BorderLayout.CENTER);
        setContentPane(contentPane);
        setLocationRelativeTo(null); //ustawienie ramki na środek ekranu
        setAlwaysOnTop(true);
        setTitle("Mapa świata");
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==accept) {
            
            String a = y.getText();
            String b = x.getText();
            Field put = null;
            
            String aCat = artifact.getText();

            for (ArrayList<Field> rowArray: map.fieldLabelsArray) { //znalezienie pola które ma taki sektor
                for (Field f: rowArray) {
                    if(f.fieldSector.equals(a) && f.mapSector.equals(b)){
                        put=f;
                    }
                }
            }

            assert put != null;
            if (!put.isOccupied) { //jeśli pole nie jest zajęte
                if (aCat.equals("T")) {
                    Random random = new Random();
                    int rand = random.nextInt(3); //randomowa kategoria skarbu
                    Treasure treasure;
                    if(rand==0){
                        treasure = new Treasure(put.mapSector, put.fieldSector, 30, Category.GOLD); //tutaj mogą być różne rodzaje
                    }
                    else if(rand==1){
                        treasure = new Treasure(put.mapSector, put.fieldSector, 30, Category.SILVER); //tutaj mogą być różne rodzaje
                    }
                    else{
                        treasure = new Treasure(put.mapSector, put.fieldSector, 30, Category.BRONZE); //tutaj mogą być różne rodzaje
                    }

                    artifacts.add(treasure);
                    put.set(aCat); //ustaw na zajęte
                    System.out.println("Artefakt polozony na:"+put.fieldSector+
                            ", sektor na mapie: "+put.mapSector);
                }

                else if (aCat.equals("J")) {
                    Junk junk = new Junk(put.mapSector, put.fieldSector, 20);
                    artifacts.add(junk);
                    put.set(aCat); //ustaw na zajęte
                    System.out.println("Artefakt polozony na:"+put.fieldSector+
                            ", sektor na mapie: "+put.mapSector);
                }

                else if (aCat.equals("B")) {
                    Blank blank = new Blank(put.mapSector, put.fieldSector, 10);
                    artifacts.add(blank);
                    put.set(aCat); //ustaw na zajęte
                    System.out.println("Artefakt polozony na:"+put.fieldSector+
                            ", sektor na mapie: "+put.mapSector);
                }

                else System.out.println("I can't recognize that artifact");

            }
            else System.out.println("Tutaj juz lezy jakis artefakt mocy!");
        }

    }
}
