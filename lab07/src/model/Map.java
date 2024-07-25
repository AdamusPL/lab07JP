package model;

import java.util.ArrayList;

public class Map {

    private char rowSector = 'J'; //sector signs
    private char columnSector = '1';
    private String column10Sector = "10";
    private String sector;

    private char rowMap = 'H'; //map sector signs
    private char columnMap = '1';
    private String mapSector;
    private String sign;


    public Map(String sign) {
        this.sign = sign;
    }

    private ArrayList<ArrayList<Field>> fieldLabelsArray; //field arraylist

    public ArrayList<ArrayList<Field>> getFieldLabelsArray() {
        return fieldLabelsArray;
    }

    private ArrayList<Field> makeRow(int x, int y) {
        ArrayList<Field> rowField = new ArrayList<>();
        for (int i = 0; i < 80; i++) {
            x += 8;
            boolean isOccupied = false;

            if ((i + 1) % 10 == 0) {
                sector = new StringBuilder().append(rowSector).append(column10Sector).toString();
            } else {
                sector = new StringBuilder().append(rowSector).append(columnSector).toString();
            }

            mapSector = new StringBuilder().append(rowMap).append(columnMap).toString();

            Field field = new Field(x, y, isOccupied, sign, sector, mapSector);
            rowField.add(field);

            columnSector++;
            if ((i + 1) % 10 == 0) {
                columnMap++;
                columnSector = '1';
                x += 10;
            }
        }
        columnMap = '1';
        return rowField;
    }

    public void makeMap() {
        fieldLabelsArray = new ArrayList<>();
        int y = 5;
        for (int i = 0; i < 80; i++) {
            int x = 5;
            ArrayList<Field> rowLabel = makeRow(x, y);
            fieldLabelsArray.add(rowLabel);
            rowSector--;
            y += 8;
            if ((i + 1) % 10 == 0) {
                rowMap--;
                rowSector = 'J';
                y += 10;
            }
        }
    }
}
