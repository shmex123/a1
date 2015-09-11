package cs414.a1.stprice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ProjectTest {

	private Project project;
	private Qualification q1;
	private Qualification q2;

	@Before
	public void setUp() throws Exception {
		project = new Project("Test Project", ProjectSize.small);
		q1 = new Qualification("Database Administrator");
		q2 = new Qualification("Systems Architect");
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
		Project p = new Project("Test Project", ProjectSize.small);
		assertEquals(project, p);
	}
	// Ensure equals method works with unequal input
	@Test
	public void testNotEquals() {
		Project p = new Project("Other", ProjectSize.small);
		assertNotEquals(project, p);
	}
	// Ensure toString method works
	@Test
	public void testToString() {
		String correct = "Test Project:0:planned";
		assertEquals(project.toString(), correct);
	}
	
	/* -------------------------------------------------------------------------
	 * Project Interface Tests 
	 * -------------------------------------------------------------------------
	 */
	// Ensure missing qualifications return when employees do not fulfill
	@Test
	public void testMissingQualifications() {
		project.addQualification(q1);
		assertTrue(project.missingQualifications().contains(q1));
	}
	// Ensure fulfilled qualifications return empty set
	@Test
	public void testFulfilledMissingQualifications() {
		project.addQualification(q1);
		Worker w = new Worker("Shane", 1000);
		w.addQualification(q1);
		project.addTeamMember(w);
		assertFalse(project.missingQualifications().contains(q1));
	}
	// Ensure helpful worker is considered helpful
	@Test
	public void testHelpfulWorker() {
		project.addQualification(q1);
		Worker w = new Worker("shane", 1000);
		w.addQualification(q1);
		project.addTeamMember(w);
		assertTrue(project.missingQualifications().isEmpty());
	}
	// Ensure unhelpful worker is not considered helpful
	@Test
	public void testUnhelpfulWorker() {
		project.addQualification(q1);
		Worker w = new Worker("shane", 1000);
		project.addTeamMember(w);
		assertFalse(project.missingQualifications().isEmpty());
	}
}
