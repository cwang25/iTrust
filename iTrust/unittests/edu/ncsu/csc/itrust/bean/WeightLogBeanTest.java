package edu.ncsu.csc.itrust.bean;

import java.sql.Date;

import org.junit.Test;

import edu.ncsu.csc.itrust.beans.WeightLogBean;
import junit.framework.TestCase;

public class WeightLogBeanTest extends TestCase {

	@Test
	public void testWeightLogBeanGetSet() {
		WeightLogBean b = new WeightLogBean(0, Date.valueOf("1993-06-28"), 0, 0, 0, 0, 0, 0, 0, 0);
		b.setCalves(1);
		b.setChest(1);
		b.setDate(Date.valueOf("2015-04-14"));
		b.setForearm(1);
		b.setMid(1);
		b.setNeck(1);
		b.setThigh(1);
		b.setUpperarm(1);
		b.setWaist(1);
		b.setWeight(1);
		
		assertTrue(b.getCalves() == 1);
		assertTrue(b.getChest() == 1);
		assertTrue(b.getDate().equals(Date.valueOf("2015-04-14")));
		assertTrue(b.getForearm() == 1);
		assertTrue(b.getMid() == 1);
		assertTrue(b.getNeck() == 1);
		assertTrue(b.getThigh() == 1);
		assertTrue(b.getUpperarm() == 1);
		assertTrue(b.getWaist() == 1);
		assertTrue(b.getWeight() == 1);
	}
	
}
