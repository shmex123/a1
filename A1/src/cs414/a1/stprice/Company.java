package cs414.a1.stprice;

import java.util.HashSet;
import java.util.Set;

public class Company {
	
	public final String name;
	private final Set<Worker> employees;
	private final Set<Project> projects; // care for coherency issues!
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
		if(!validateWorker(w)) return;
		employees.add(w);
		w.employer = this;
		for(Project p : determineEligibleProjects(w)) {
			p.addTeamMember(w);
		}
	}
	public void fire(Worker w) {
		if(employees.remove(w)) {
			w.employer = null;
			for(Project p : projects) {
				p.removeTeamMember(w);
			}
		}
	}
	public void start(Project p) {
		if(validateProject(p)) {
			p.startIfPossible();
		}
	}
	public void finish(Project p) {
		if(validateProject(p)) {
			p.endIfPossible();
		}
	}
	public Worker createWorker(String nickname, 
			Set<Qualification> qualifications) {
		Worker w = new Worker(nickname, defaultSalary);
		w.addQualifications(qualifications);
		hire(w);
		return w;
	}
	public void addProject(Project p) {
		if(!validateNewProject(p)) return;
		projects.add(p);
	}
	public Project createProject(String name, Set<Worker> workers, 
			Set<Qualification> qualifications, ProjectSize size) {
		workers = filterEmployees(workers);
		Project p = new Project(name, size);
		p.addQualifications(qualifications);
		p.addTeamMembers(workers);
		projects.add(p);
		return p;
	}
	public Set<Worker> workersForQualification(Qualification q) {
		Set<Worker> qualified = new HashSet<Worker>();
		for(Worker w : employees) {
			if(w.getQualifications().contains(q)) {
				qualified.add(w);
			}
		}
		return qualified;
	}
	public Set<Worker> getEmployees() {
		return new HashSet<Worker>(employees);
	}
	public Set<Project> getProjects() {
		return new HashSet<Project>(projects);
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
	
	/* -------------------------------------------------------------------------
	 * Private Methods
	 * -------------------------------------------------------------------------
	 */
	private boolean validateWorker(Worker w) {
		if(w == null) return false;
		return w.employer == null;
	}
	private Set<Project> determineEligibleProjects(Worker w) {
		Set<Project> eligible = new HashSet<Project>();
		for(Project p : projects) {
			if(p.isHelpful(w) && p.isResumable()
					&& p.size == ProjectSize.small) eligible.add(p);
		}
		return eligible;
	}
	private boolean validateProject(Project p) {
		if(p == null) return false;
		return projects.contains(p);
	}
	private boolean validateNewProject(Project p) {
		if(p == null) return false;
		return true;
	}
	private Set<Worker> filterEmployees(Set<Worker> ws) {
		HashSet<Worker> emps = new HashSet<Worker>();
		for(Worker w : ws) {
			if(employees.contains(w)) emps.add(w);
		}
		return emps;
	}
}
