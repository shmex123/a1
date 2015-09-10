package cs414.a1.stprice;

import java.util.HashSet;
import java.util.Set;

public class Company {
	
	public final String name;
	public final Set<Worker> employees;
	public final Set<Project> projects; // care for coherency issues!
	public final int defaultSalary;
	
	public Company(String name) {
		this.name = name;
		this.employees = new HashSet<Worker>();
		this.projects = new HashSet<Project>();
		this.defaultSalary = 100000;
	}
	
	/* -------------------------------------------------------------------------
	 * Company Interface
	 * -------------------------------------------------------------------------
	 */
	public void hire(Worker w) {

	}
	public void fire(Worker w) {

	}
	public void start(Project p) {
		
	}
	public void finish(Project p) {
		
	}
	public Worker createWorker(String nickname, 
			Set<Qualification> qualifications) {
		Worker w = new Worker(nickname, defaultSalary);
		w.qualifications.addAll(qualifications);
		return w;
	}
	public Project createProject(String name, Set<Worker> workers, 
			Set<Qualification> qualifications, ProjectSize size) {
		Project p = new Project(name, size);
		p.qualifications.addAll(qualifications);
		return p;
	}
	
	/* -------------------------------------------------------------------------
	 * Object Overrides
	 * -------------------------------------------------------------------------
	 */
	// Companies are considered equal if their names are the same
	@Override
	public boolean equals(Object o) {
		if(o instanceof Company) {
			return this.name.equals(((Company)o).name);
		}
		return false;
	}
	// <name>:<# employees>:<# projects>
	@Override
	public String toString() {
		return name + ":" + employees.size() + ":" + projects.size();
	}
}
