package cs414.a1.stprice;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Project {

	public final String name;
	public final ProjectSize size;
	public ProjectStatus status;
	private final Map<Qualification, Set<Worker>> qualifications;
	private final Set<Worker> team; // similar data cached in Worker
	// care for coherency issues

	public Project(String name, ProjectSize size) {
		this.name = name;
		this.size = size;
		this.status = ProjectStatus.planned;
		this.qualifications = new HashMap<Qualification, Set<Worker>>();
		this.team = new HashSet<Worker>();
	}

	/* -------------------------------------------------------------------------
	 * Project Interface Methods
	 * -------------------------------------------------------------------------
	 */
	public Set<Qualification> missingQualifications() {
		Set<Qualification> missing = new HashSet<Qualification>();
		for(Qualification q : qualifications.keySet()) {
			if(qualifications.get(q).size() == 0) missing.add(q);
		}
		return missing;
	}
	public Set<Qualification> getQualifications() {
		// prevent data leak of qualifications
		return new HashSet<Qualification>(qualifications.keySet());
	}
	public void addQualification(Qualification q) {
		if(qualifications.get(q) == null) {
			qualifications.put(q, new HashSet<Worker>());
		}
	}
	public void addQualifications(Set<Qualification> qs) {
		for(Qualification q : qs) {
			addQualification(q);
		}
	}
	public void removeQualification(Qualification q) {
		qualifications.remove(q);
	}
	public boolean isHelpful(Worker worker) {
		Set<Qualification> missing = missingQualifications();
		for(Qualification q : worker.qualifications) {
			if(missing.contains(q)) return true;
		}
		return false;
	}
	public boolean isResumable() {
		return status == ProjectStatus.suspended 
				|| status == ProjectStatus.planned;
	}
	public boolean startIfPossible() {
		if(isResumable() && missingQualifications().isEmpty()) {
			status = ProjectStatus.active;
			return true;
		}
		return false;
	}
	public boolean endIfPossible() {
		if(status == ProjectStatus.active) {
			status = ProjectStatus.finished;
			for(Worker w : new HashSet<Worker>(team)) { // for concurrent modification
				removeTeamMember(w);
			}
			return true;
		}
		return false;
	}
	public void addTeamMember(Worker w) {
		if(!validateNewTeamMember(w)) return;
		if(this.team.add(w)) {
			w.projects.add(this);
			addToQualifications(w);
			updateStatusIfNeeded();
		}
	}
	public void removeTeamMember(Worker w) {
		if(team.remove(w)) {
			w.projects.remove(this);
			removeFromQualifications(w);
			updateStatusIfNeeded();
		}
	}
	public Set<Worker> getTeam() {
		return new HashSet<Worker>(team); // to prevent leak
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

	/* -------------------------------------------------------------------------
	 * Private Methods
	 * -------------------------------------------------------------------------
	 */
	private boolean validateNewTeamMember(Worker w) {
		if(w == null) return false;
		return true;
	}
	private void updateStatusIfNeeded() {
		if(status == ProjectStatus.finished) return;
		// if not active and there are no missing qualifications, make active
		if(status != ProjectStatus.active && missingQualifications().isEmpty()) {
			status = ProjectStatus.active;
		}
		// if active and there are missing qualifications, make suspended
		if(status == ProjectStatus.active && !missingQualifications().isEmpty()) {
			status = ProjectStatus.suspended;
		}
	}
	private void addToQualifications(Worker w) {
		for(Qualification q : w.qualifications) {
			if(qualifications.get(q) != null) {
				qualifications.get(q).add(w);
			}
		}
	}
	private void removeFromQualifications(Worker w) {
		for(Qualification q : w.qualifications) {
			if(qualifications.get(q) != null) {
				qualifications.get(q).remove(w);
			}
		}
	}
}
