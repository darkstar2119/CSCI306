package test;

import junit.framework.Assert;
import org.junit.*;
import price.Cashier;
import price.Envelope;


public class TestCashier {
	Cashier testC;
	Envelope testE = new Envelope(0, 0, 0, 0, 0);
	
	@Test
	public void testMakeChange() {
		Envelope expected = new Envelope(0,0,0,0,0);
		Envelope actual = new Envelope(0, 0, 0, 0, 0);
		actual = testC.makeChange(testE, 0,0);
		Assert.assertEquals(expected, actual);
	}

}
