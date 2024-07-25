package Apps.World;

import model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class WorldMap extends JFrame implements ActionListener {
    private ArrayList<Artifact> artifacts;
    private JPanel simPanel;
    private Map map;
    private JPanel contentPane;
    private JTextField x;
    private JTextField y;
    private JTextField artifact;
    private JButton accept;

    public WorldMap(Map map, ArrayList<Artifact> artifacts) {
        this.artifacts = artifacts;
        this.map = map;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 850, 800);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);

        simPanel = new JPanel();
        simPanel.setBounds(5, 5, 1280, 720);
        simPanel.setLayout(null);

        map.makeMap();

        for (ArrayList<Field> rowArray : map.getFieldLabelsArray()) {
            for (Field f : rowArray) {
                JLabel label = f.getLabel();
                label.setBounds(f.getX(), f.getY(), 10, 10);
                simPanel.add(label);
            }
        }

        JLabel labelX = new JLabel("Map sector");
        labelX.setBounds(740, 280, 80, 20);

        JLabel labelY = new JLabel("Inside sector");
        labelY.setBounds(745, 330, 80, 20);

        JLabel labelA = new JLabel("Artifact");
        labelA.setBounds(750, 380, 60, 20);

        x = new JTextField();
        x.setBounds(750, 300, 60, 20);

        y = new JTextField();
        y.setBounds(750, 350, 60, 20);

        artifact = new JTextField();
        artifact.setBounds(750, 400, 60, 20);

        accept = new JButton("OK");
        accept.setBounds(750, 450, 60, 20);
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
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        setTitle("World's Map");
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == accept) {

            String a = y.getText();
            String b = x.getText();
            Field put = null;

            String aCat = artifact.getText();

            for (ArrayList<Field> rowArray : map.getFieldLabelsArray()) { //find field which is in specific sector
                for (Field f : rowArray) {
                    if (f.getFieldSector().equals(a) && f.getMapSector().equals(b)) {
                        put = f;
                    }
                }
            }

            assert put != null;
            if (!put.isOccupied()) { //if field is free
                if (aCat.equals("T")) {
                    Random random = new Random();
                    int rand = random.nextInt(3); //generate random category of treasure
                    Treasure treasure;
                    if (rand == 0) {
                        treasure = new Treasure(put.getMapSector(), put.getFieldSector(), 30, Category.GOLD);
                    } else if (rand == 1) {
                        treasure = new Treasure(put.getMapSector(), put.getFieldSector(), 30, Category.SILVER);
                    } else {
                        treasure = new Treasure(put.getMapSector(), put.getFieldSector(), 30, Category.BRONZE);
                    }

                    artifacts.add(treasure);
                    put.set(aCat); //set field as busy
                    System.out.println("Artifact set on field: " + put.getFieldSector() +
                            ", map sector: " + put.getMapSector());
                } else if (aCat.equals("J")) {
                    Junk junk = new Junk(put.getMapSector(), put.getFieldSector(), 20);
                    artifacts.add(junk);
                    put.set(aCat); //set field as busy
                    System.out.println("Artifact set on field: " + put.getFieldSector() +
                            ", map sector: " + put.getMapSector());
                } else if (aCat.equals("B")) {
                    Blank blank = new Blank(put.getMapSector(), put.getFieldSector(), 10);
                    artifacts.add(blank);
                    put.set(aCat); //set field as busy
                    System.out.println("Artifact set on field: :" + put.getFieldSector() +
                            ", map sector: " + put.getMapSector());
                } else System.out.println("I can't recognize that artifact");

            } else System.out.println("Here already lays powerful artifact!");
        }

    }
}
