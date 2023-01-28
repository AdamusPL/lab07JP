package model;

public class Blank extends Artifact {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Blank(String mapSector, String sector, int excavationTime) {
		super(mapSector, sector, excavationTime);
	}

	@Override
	Category getCategory() {
		return Category.EMPTY;
	}

}
