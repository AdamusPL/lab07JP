package model;

public class Junk extends Artifact {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Junk(String sectorMap, String sector, int excavationTime) {
		super(sectorMap,sector, excavationTime);
	}

	@Override
	Category getCategory() {
		return Category.OTHER;
	}

}
