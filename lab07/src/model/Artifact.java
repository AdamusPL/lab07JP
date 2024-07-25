package model;

import java.io.Serializable;

public abstract class Artifact implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final int excavationTime; // milliseconds
    private String mapSector;
    private String fieldSector;
    private boolean isDiscovered;

    public Artifact(String mapSector, String fieldSector, int excavationTime) {
        this.mapSector = mapSector;
        this.fieldSector = fieldSector;
        this.excavationTime = excavationTime;
        this.isDiscovered = false;
    }

    public int getExcavationTime() {
        return excavationTime;
    }

    public String getMapSector() {
        return mapSector;
    }

    public String getFieldSector() {
        return fieldSector;
    }

    public abstract Category getCategory();
}
