package model;

import javax.swing.*;

public class Field {
    private int occupiedByClubs; //pole
    private String mapSector; //which sector on map
    private String fieldSector; //which inside sector
    private int x;
    private int y;
    private String sign;
    private JLabel label;

    public int getOccupiedByClubs() {
        return occupiedByClubs;
    }

    public void setOccupiedByClubs(int occupiedByClubs) {
        this.occupiedByClubs = occupiedByClubs;
    }

    public String getMapSector() {
        return mapSector;
    }

    public String getFieldSector() {
        return fieldSector;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public JLabel getLabel() {
        return label;
    }

    public Field(int x, int y, boolean isOccupied, String sign, String sector, String mapSector) {
        this.occupiedByClubs = 0;
        this.x = x;
        this.y = y;
        this.isOccupied = isOccupied;
        this.sign = sign;
        this.label = new JLabel(sign);
        this.fieldSector = sector;
        this.mapSector = mapSector;
        this.isOccupied = false;
    }

    private boolean isOccupied; //if field is occupied by other player

    public boolean isOccupied() {
        return isOccupied;
    }

    public synchronized boolean set(String aCat) { //set on field
        if (isOccupied) return false;

        label.setText(aCat);
        isOccupied = true;
        return true;
    }

    public synchronized void unSet() { //free up place on field
        if (!isOccupied) return;

        //set label
        label.setText(".");
        isOccupied = false;
    }
}
