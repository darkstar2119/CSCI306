package test;

import junit.framework.Assert;
import org.junit.*;
import price.Envelope;

public class TestEnvelope {

	Envelope testE = new Envelope(0,0,0,0,0);
	
	@Test
	public void testTotalCents() {
		int expected = 0;
		int actual = testE.getTotalCents();
		Assert.assertEquals(expected,actual);
	}
}
