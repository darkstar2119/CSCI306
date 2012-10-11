package test;

import junit.framework.Assert;
import org.junit.*;
import source.Cashier;
import source.Envelope;
import source.NegativeBalanceException;


public class TestCashier {
	Cashier testC = new Cashier();
	Envelope testE = new Envelope(8,6,5,3,7);
	Envelope testEa = new Envelope(3,4,8,0,4);
	
	@Test
	public void testMakeChange() throws NegativeBalanceException {
		int expected = testEa.getTotalCents();
		int actual = testC.makeChange(testE, 5, 38).getTotalCents();
		Assert.assertEquals(expected, actual);
	}
	
	Cashier testC2 = new Cashier();
	Envelope testE2 = new Envelope(0,0,0,0,0);
	
	@Test (expected = NegativeBalanceException.class)
	public void testMakeChange2() throws NegativeBalanceException {
		int actual = testC2.makeChange(testE2, 5, 45).getTotalCents();
	}
		
	Cashier testC3 = new Cashier();
	Envelope testE3 = new Envelope(5,5,5,5,5);
	Envelope testE3a = new Envelope(0,0,0,0,0);
	
	@Test
	public void testMakeChange3() throws NegativeBalanceException {
		int expected = testE3a.getTotalCents();
		int actual = testC3.makeChange(testE3, 7, 5).getTotalCents();
		Assert.assertEquals(expected, actual);
	}

}
