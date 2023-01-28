package model;

public class Treasure extends Artifact {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Category category;
	public Treasure(String sectorMap, String sector, int excavationTime, Category category) {
		super(sectorMap,sector,excavationTime);
		this.category = category;
	}

	
	@Override
	Category getCategory() {
		return category;
	}

}
