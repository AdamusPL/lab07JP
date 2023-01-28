package model;

import java.io.Serializable;

public abstract class Artifact implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final int excavationTime; // milliseconds
	String mapSector;
	String fieldSector;
	boolean isDiscovered;

	public Artifact(String mapSector, String sector, int excavationTime) {
		this.mapSector=mapSector;
		this.fieldSector =sector;
		this.excavationTime = excavationTime;
		this.isDiscovered=false;
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

	abstract Category getCategory();
}
