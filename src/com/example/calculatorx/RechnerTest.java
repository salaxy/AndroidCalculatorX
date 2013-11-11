/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fhb.taschenrechner;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author MacYser
 */
public class RechnerTest {

	public RechnerTest() {
	}

	@BeforeClass
	public static void setUpClass() {
	}

	@AfterClass
	public static void tearDownClass() {
	}

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	@Test
	public void testAdd() {
		System.out.println("add");
		Double val1 = -2.;
		Double val2 = 3.;
		Rechner instance = new Rechner();
		Double expResult = 1.;
		Double result = instance.add(val1, val2);
		assertEquals(expResult, result);
	}

	@Test
	public void testSub() {
		System.out.println("sub");
		Double val1 = 1.;
		Double val2 = 3.;
		Rechner instance = new Rechner();
		Double expResult = -2.;
		Double result = instance.sub(val1, val2);
		assertEquals(expResult, result);
	}

	@Test
	public void testMul() {
		System.out.println("mul");
		Double val1 = 3.;
		Double val2 = 4.;
		Rechner instance = new Rechner();
		Double expResult = 12.;
		Double result = instance.mul(val1, val2);
		assertEquals(expResult, result);
	}

	@Test
	public void testDiv() {
		System.out.println("div");
		Double val1 = 2.;
		Double val2 = 1.;
		Rechner instance = new Rechner();
		Double expResult = 2.;
		Double result = instance.div(val1, val2);
		assertEquals(expResult, result);
		System.out.println("DoubleMax: " + (long) Double.MAX_VALUE);
		System.out.println("IntMaxMul: " + (Integer.MAX_VALUE));
		System.out.println("IntMaxMul: " + (((long) Integer.MAX_VALUE * (long) Integer.MAX_VALUE)));
	}

	@Test(expected = ArithmeticException.class)
	public void testDivNull() {
		System.out.println("div");
		Double val1 = 2.;
		Double val2 = 0.;
		Rechner instance = new Rechner();
		Double expResult = null;
		Double result = instance.div(val1, val2);
		assertEquals(expResult, result);
	}
}
