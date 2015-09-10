package cs414.a1.stprice;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CompanyTest {
	
	private Company company;
	private Worker worker;
	private Project project;
	private Qualification q1;
	private Qualification q2;

	@Before
	public void setUp() throws Exception {
		q1 = new Qualification("Systems Architect");
		q2 = new Qualification("Database Administrator");
		company = new Company("Google");
		worker = new Worker("Shane", 10000);
		project = new Project("ToDo App", ProjectSize.small);
	}

	@After
	public void tearDown() throws Exception {
	}

	/* -------------------------------------------------------------------------
	 * Object Override Tests
	 * -------------------------------------------------------------------------
	 */
	// Ensure equals method works with equal input
	@Test
	public void testEquals() {
		Company c = new Company("Google");
		assertEquals(company, c);
	}
	// Ensure equals method works with unequal input
	@Test
	public void testNotEquals() {
		Company c = new Company("Microsoft");
		assertNotEquals(company, c);
	}
	// Ensure toString method works
	@Test
	public void testToString() {
		String correct = "Google:0:0";
		assertEquals(company.toString(), correct);
	}
	
	/* -------------------------------------------------------------------------
	 * Hire Tests
	 * -------------------------------------------------------------------------
	 */
	// Ensure unemployed worker is added to company's employees when hired 
	@Test
	public void testWorkerAddedToEmployees() {
		company.hire(worker);
		assertTrue(company.employees.contains(worker));
	}
	// Ensure employed worker is not hired
	@Test
	public void testHireAlreadyEmployed() {
		Company c = new Company("Microsoft");
		c.hire(worker);
		company.hire(worker);
		assertFalse(company.employees.contains(worker));
	}
	// Ensure hired worker is added to small project that requires his 
	// qualifications
	@Test
	public void testHireAddedToSmallProject() {
		project.addQualification(q1);
		company.projects.add(project);
		worker.qualifications.add(q1);
		company.hire(worker);
		assertTrue(project.getTeam().contains(worker));
	}
	// Ensure hired worker is not added to medium project that requires his 
    // qualifications
	@Test
    public void testHireNotAddedToMediumProject() {
		Project p = new Project("Medium Project", ProjectSize.medium);
		p.addQualification(q1);
		company.projects.add(p);
		worker.qualifications.add(q1);
		company.hire(worker);
		assertFalse(p.getTeam().contains(worker));
    }
	// Ensure hired worker is not added to big project despite
	// fulfilling needed requirements
	@Test
	public void testHireNotAddedToBigProject() {
		Project p = new Project("Big Project", ProjectSize.big);
		p.addQualification(q1);
		company.projects.add(p);
		worker.qualifications.add(q1);
		company.hire(worker);
		assertFalse(p.getTeam().contains(worker));
	}
	// Ensure hired worker is added to suspended project
	@Test
	public void testHireAddedToSuspended() {
		Project p = new Project("Suspended", ProjectSize.small);
		p.status = ProjectStatus.suspended;
		p.addQualification(q1);
		company.projects.add(p);
		worker.qualifications.add(q1);
		company.hire(worker);;
		assertTrue(p.getTeam().contains(worker));
	}
	// Ensure hired worker is not added to active projects
	@Test
	public void testHireNotAddedToActive() {
		project.status = ProjectStatus.active;
		project.addQualification(q1);
		company.projects.add(project);
		worker.qualifications.add(q1);
		company.hire(worker);
		assertFalse(project.getTeam().contains(worker));
	}
	// Ensure hired worker that does not fulfill project requirements
	// is not added to that project
	@Test
	public void testHireUnqualifiedNotAddedToProject() {
		project.addQualification(q1);
		company.projects.add(project);
		company.hire(worker);
		assertFalse(project.getTeam().contains(worker));
	}
	// Ensure hired worker that completely satisfies missing project
	// requirements causes the project to become active
	@Test
	public void testHireSatisfiedRequirementsActivate() {
		project.addQualification(q1);
		company.projects.add(project);
		worker.qualifications.add(q1);
		company.hire(worker);
		assertEquals(project.status, ProjectStatus.active);
	}
	// Ensure hired worker that has missing project requirements but 
	// does not completely fulfill them does not cause the project to
	// become active
	@Test
	public void testHireNotSatisfiedRequirementsNotActivate() {
		project.addQualification(q1);
		project.addQualification(q2);
		company.projects.add(project);
		worker.qualifications.add(q1);
		company.hire(worker);
		assertNotEquals(project.status, ProjectStatus.active);
	}
	// Ensure a fired worker can be rehired
	@Test
	public void testRehire() {
		company.hire(worker);
		company.fire(worker);
		Company c = new Company("Microsoft");
		c.hire(worker);
		assertTrue(c.employees.contains(worker));
	}
	
	
	/* -------------------------------------------------------------------------
	 * Fire Tests
	 * -------------------------------------------------------------------------
	 */
	// Ensure an employee can be fired
	@Test
	public void testFire() {
		company.hire(worker);
		company.fire(worker);
		assertFalse(company.employees.contains(worker));
	}
	// Ensure firing a non employee doesnt do anything
	@Test
	public void testFireNonEmployee() {
		company.fire(worker);
		assertFalse(company.employees.contains(worker));
	}
	// Ensure firing an employee removes their employer var
	@Test
	public void testFireRemoveEmployer() {
		company.hire(worker);
		company.fire(worker);
		assertTrue(worker.employer == null);
	}
	// Ensure firing a different company's employee
	// doesnt change the workers status
	@Test
	public void testFireOtherEmployee() {
		Company c = new Company("Microsoft");
		c.hire(worker);
		company.fire(worker);
		assertTrue(worker.employer == c); //ref should not have changed
	}
	// Ensure firing other company's employee doesnt
	// remove them from their employee list
	@Test
	public void testFireOtherEmployeeRemainsEmployed() {
		Company c = new Company("Microsoft");
		c.hire(worker);
		company.fire(worker);
		assertTrue(c.employees.contains(worker));
	}
	// Ensure fired employee gets removed from projects they were on
	@Test
	public void testFireRemoveFromProject() {
		project.addQualification(q1);
		company.projects.add(project);
		worker.qualifications.add(q1);
		company.hire(worker);
		company.fire(worker);
		assertFalse(project.getTeam().contains(worker));
	}
	// Ensure firing employee doesnt change status of other projects
	@Test
	public void testFireUnaffectedOtherProjects() {
		Project p = new Project("Test", ProjectSize.small);
		project.addQualification(q1);
		company.projects.add(project);
		worker.qualifications.add(q1);
		company.hire(worker);
		company.fire(worker);
		assertTrue(p.status == ProjectStatus.planned);
	}
	// Ensure fired employee gets removed from qualifications
	// lists
	@Test
	public void testFireRemoveFromQualifications() {
		project.addQualification(q1);
		company.projects.add(project);
		worker.qualifications.add(q1);
		company.hire(worker);
		company.fire(worker);
		assertFalse(project.missingQualifications().isEmpty());
	}
	// Ensure fired employee removed from active project causes it
	// to become inactive
	@Test
	public void testFireMakeProjectInactive() {
		project.addQualification(q1);
		company.projects.add(project);
		worker.qualifications.add(q1);
		company.hire(worker);
		company.fire(worker);
		assertTrue(project.status == ProjectStatus.suspended);
	}
	// Ensure fired employee removed from planned project
	// doesnt do anything
	@Test
	public void testFireUnaffectedPlannedProject() {
		project.addQualification(q1);
		project.addQualification(q2);
		company.projects.add(project);
		worker.qualifications.add(q1);
		company.hire(worker);
		company.fire(worker);
		assertTrue(project.status == ProjectStatus.planned);
	}
	
	/* -------------------------------------------------------------------------
	 * Start Tests
	 * -------------------------------------------------------------------------
	 */
	// Ensures a planned project with no qualifications that
	// is started turns active
	@Test
	public void testStartPlanned() {
		company.projects.add(project);
		company.start(project);
		assertTrue(project.status == ProjectStatus.active);
	}
	// Ensures a suspended project with no qualifications that
	// is started turns active
	@Test
	public void testStartSuspended() {
		project.status = ProjectStatus.suspended;
		company.projects.add(project);
		company.start(project);
		assertTrue(project.status == ProjectStatus.active);
	}
	// Ensures a finished project cannot be started
	@Test
	public void testStartFinished() {
		project.status = ProjectStatus.finished;
		company.projects.add(project);
		company.start(project);
		assertTrue(project.status == ProjectStatus.finished);
	}
	// Ensures a planned project that does not have completely
	// fulfilled requirements does not get its status changed
	@Test
	public void testStartUnfulfilledPlanned() {
		project.addQualification(q1);
		company.projects.add(project);
		company.start(project);
		assertTrue(project.status == ProjectStatus.planned);
	}
	// Ensures a suspended project that does not have completely
	// fulfilled requirements does not get its status changed
	@Test
	public void testStartUnfulfilledSuspended() {
		project.addQualification(q1);
		project.status = ProjectStatus.suspended;
		company.projects.add(project);
		company.start(project);
		assertTrue(project.status == ProjectStatus.suspended);
	}
	// Ensures a project that is not owned by a company does not
	// get affected
	@Test
	public void testStartOtherProject() {
		Company c = new Company("Microsoft");
		c.projects.add(project);
		company.start(project);
		assertTrue(project.status == ProjectStatus.planned);
	}
}
