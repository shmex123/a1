package cs414.a1.stprice;

import java.util.HashSet;
import java.util.Set;

public class Worker {

	private final int BIG_MULTIPLIER = 1;
	private final int MEDIUM_MULTIPLIER = 2;
	private final int SMALL_MULTIPLIER = 0;
	private final int MAX_OVERLOAD_VALUE = 4;

	public final String nickname;
	public final int salary;
	private final Set<Qualification> qualifications;
	private final Set<Project> projects; // care for coherency issues!
	public Company employer;

	public Worker(String nickname, int salary) {
		this.nickname = nickname;
		this.salary = salary;
		this.qualifications = new HashSet<Qualification>();
		this.projects = new HashSet<Project>();
	}

	/* -------------------------------------------------------------------------
	 * Worker Interface Methods
	 * -------------------------------------------------------------------------
	 */
	public boolean isOverloaded() {
		return calculateOverloadValue() > MAX_OVERLOAD_VALUE;
	}
	public boolean willBecomeOverloaded(Project p) {
		return isOverloaded() || 
				((calculateOverloadValue() + calculateOverloadValue(p))
				> MAX_OVERLOAD_VALUE);
	}
	public Set<Project> getProjects() {
		return new HashSet<Project>(projects);
	}
	public void addProject(Project p) {
		if(!validateProject(p)) return;
		projects.add(p);
	}
	public void addProjects(Set<Project> ps) {
		for(Project p : ps) {
			addProject(p);
		}
	}
	public void removeProject(Project p) {
		projects.remove(p);
	}
	public Set<Qualification> getQualifications() {
		return new HashSet<Qualification>(qualifications);
	}
	public void addQualification(Qualification q) {
		if(!validateQualification(q)) return;
		qualifications.add(q);
	}
	public void addQualifications(Set<Qualification> qs) {
		for(Qualification q : qs) {
			addQualification(q);
		}
	}
	public void removeQualification(Qualification q) {
		qualifications.remove(q);
	}

	/* -------------------------------------------------------------------------
	 * Object Overrides
	 * -------------------------------------------------------------------------
	 */
	// Workers are considered equal if their nicknames are equal
	@Override
	public boolean equals(Object o) {
		if(o instanceof Worker) {
			return this.nickname.equals(((Worker)o).nickname);
		}
		return false;
	}
	// <nickname>:<# projects>:<# qualifications>:<salary>
	@Override
	public String toString() {
		return nickname + ":" + projects.size() + ":" + 
				qualifications.size() + ":" + salary;
	}

	/* -------------------------------------------------------------------------
	 * Private Methods 
	 * -------------------------------------------------------------------------
	 */
	private int calculateOverloadValue() {
		int val = 0;
		for(Project p : projects) {
			val += calculateOverloadValue(p);
		}
		return val;
	}
	private int calculateOverloadValue(Project p) {
		switch(p.size) {
		case small: return SMALL_MULTIPLIER;
		case medium: return MEDIUM_MULTIPLIER;
		case big: return BIG_MULTIPLIER;
		default: return 0;
		}
	}
	private boolean validateProject(Project p) {
		if(p == null) return false;
		return !willBecomeOverloaded(p);
	}
	private boolean validateQualification(Qualification q) {
		if(q == null) return false;
		return true;
	}
}
