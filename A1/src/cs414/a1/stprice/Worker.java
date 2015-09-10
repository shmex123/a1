package cs414.a1.stprice;

import java.util.HashSet;
import java.util.Set;

public class Worker {

	public final String nickname;
	public final int salary;
	public final Set<Qualification> qualifications;
	public final Set<Project> projects; // care for coherency issues!
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
		return true;
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
}
