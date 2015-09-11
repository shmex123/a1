package cs414.a1.stprice;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class WorkerTest {
	
	private Worker worker;
	
	@Before
	public void setUp() throws Exception {
		worker = new Worker("Shane", 10000);
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
		Worker w = new Worker("Shane", 10000);
		assertEquals(worker, w);
	}
	// Ensure equals method works with unequal input
	@Test
	public void testNotEquals() {
		Worker w = new Worker("Rando", 100000);
		assertNotEquals(worker, w);
	}
	// Ensure toString method works
	@Test
	public void testToString() {
		String correct = "Shane:0:0:10000";
		assertEquals(worker.toString(), correct);
	}
	
	
	/* -------------------------------------------------------------------------
	 * Worker Interface Tests 
	 * -------------------------------------------------------------------------
	 */
	// Test isOverloaded calculates correctly
	@Test
	public void testIsNotOverloaded() {
		worker.addProject(new Project("TestMedium2", ProjectSize.medium));
		assertFalse(worker.isOverloaded());
	}
	// Ensure a worker who will become overloaded
	// is not added to the project
	@Test
	public void testOverloadedWorkerNotAdded() {
		Project p2 = new Project("Medium1", ProjectSize.medium);
		Project p3 = new Project("Medium2", ProjectSize.medium);
		Project p4 = new Project("Medium3", ProjectSize.medium);
		worker.addProject(p2);
		worker.addProject(p3);
		worker.addProject(p4);
		assertFalse(worker.getProjects().contains(p4));
	}
}
