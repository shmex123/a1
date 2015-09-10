package cs414.a1.stprice;

import java.util.HashSet;
import java.util.Set;

public class Project {
	
	public final String name;
	public final ProjectSize size;
	public ProjectStatus status;
	public final Set<Qualification> qualifications;
	public final Set<Worker> team; // similar data cached in Worker
								   // care for coherency issues
	
	public Project(String name, ProjectSize size) {
		this.name = name;
		this.size = size;
		this.status = ProjectStatus.planned;
		this.qualifications = new HashSet<Qualification>();
		this.team = new HashSet<Worker>();
	}
	
	/* -------------------------------------------------------------------------
	 * Project Interface Methods
	 * -------------------------------------------------------------------------
	 */
	public Set<Qualification> missingQualifications() {
		Set<Qualification> missing = new HashSet<Qualification>();
		for(Qualification q : qualifications) {
			missing.add(q);
		}
		for(Worker w : team) {
			for(Qualification q : w.qualifications) {
				missing.remove(q);
			}
		}
		return missing;
	}
	public boolean isHelpful(Worker worker) {
		Set<Qualification> missing = missingQualifications();
		for(Qualification q : worker.qualifications) {
			if(missing.contains(q)) return true;
		}
		return false;
	}
	
	
	/* -------------------------------------------------------------------------
	 * Object Overrides
	 * -------------------------------------------------------------------------
	 */
	// Projects are considered equal if their names are equal
	@Override
	public boolean equals(Object o) {
		if(o instanceof Project) {
			return this.name.equals(((Project)o).name);
		}
		return false;
	}
	// <name>:<# employees>:<status>
	@Override
	public String toString() {
		return name + ":" + team.size() + ":" + status;
	}
}
