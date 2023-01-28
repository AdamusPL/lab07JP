package model;

import java.io.Serializable;

public class Report implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String sector;
	private final String field;
	private final Artifact artifact;

	public Report(String sector, String field, Artifact artifact) {
		this.sector = sector;
		this.field = field;
		this.artifact = artifact;
	}

	public String getSector() {
		return sector;
	}

	public String getField() {
		return field;
	}

	public Artifact getArtifact() {
		return artifact;
	}

}
