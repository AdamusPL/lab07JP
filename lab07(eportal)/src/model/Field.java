package model;

import javax.swing.*;

public class Field { //pole
    public int occupiedByClubs=0;
    public String mapSector; //który sektor na mapie
    public String fieldSector; //który sektor wewnętrzny
    public int x;
    public int y;
    public String sign;
    public JLabel label;

    public Field(int x, int y, boolean isOccupied, String sign, String sector, String mapSector){
        this.x=x;
        this.y=y;
        this.isOccupied=isOccupied;
        this.sign=sign;
        this.label=new JLabel(sign);
        this.fieldSector =sector;
        this.mapSector=mapSector;
    }

    public boolean isOccupied = false; //czy pole jest zajmowane przez jakiegoś gracza

    public synchronized boolean set(String aCat){ //ustaw statek na planszy
        if(isOccupied) return false;

        label.setText(aCat);
        isOccupied = true;
        return true;
    }

    public synchronized boolean set(){ //ustaw statek na planszy
        if(isOccupied) return false;

        isOccupied = true;
        return true;
    }

    public synchronized void unSet(){ //zwolnij miejsce na planszy
        if(!isOccupied) return;

        //ustaw labelke
        label.setText(".");
        isOccupied = false;
    }
}
