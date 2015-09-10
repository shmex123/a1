package cs414.a1.stprice;

public class Qualification {
	
	public final String description;
	
	public Qualification(String description) {
		this.description = description;
	}
	
	/* -------------------------------------------------------------------------
	 * Object Overrides
	 * -------------------------------------------------------------------------
	 */
	// Qualifications are considered equal if their descriptions are equal
	@Override
	public boolean equals(Object o) {
		if(o instanceof Qualification) {
			return this.description.equals(((Qualification)o).description);
		}
		return false;
	}
	// <description>
	@Override
	public String toString() {
		return description;
	}
}
