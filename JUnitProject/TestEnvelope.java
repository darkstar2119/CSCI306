package test;

import junit.framework.Assert;
import org.junit.*;

import source.Envelope;

public class TestEnvelope {

	Envelope testE = new Envelope(5,3,2,1,6);
	
	@Test
	public void testTotalCents() {
		int expected = 606;
		int actual = testE.getTotalCents();
		Assert.assertEquals(expected,actual);
	}
	
	Envelope testE2 = new Envelope(7,1,5,11,9);
	
	@Test
	public void testTotalCents2() {
		int expected = 839;
		int actual = testE2.getTotalCents();
		Assert.assertEquals(expected, actual);
	}
	
	Envelope testE3 = new Envelope(6,2,4,7,1);
	
	@Test
	public void testTotalCentrs3() {
		int expected = 726;
		int actual = testE3.getTotalCents();
		Assert.assertEquals(expected, actual);
	}
}
