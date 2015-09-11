package cs414.a1.stprice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class QualificationTest {

	private Qualification q;
	
	@Before
	public void setUp() throws Exception {
		q = new Qualification("Systems Architect");
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
		Qualification q2 = new Qualification("Systems Architect");
		assertEquals(q, q2);
	}
	// Ensure equals method works with unequal input
	@Test
	public void testNotEquals() {
		Qualification q2 = new Qualification("Architect");
		assertNotEquals(q, q2);
	}
	// Ensure toString method works
	@Test
	public void testToString() {
		String correct = "Systems Architect";
		assertEquals(q.toString(), correct);
	}
}
