package edu.ncsu.csc.itrust.bean;

import org.junit.Test;

import edu.ncsu.csc.itrust.beans.FoodDiaryLabelBean;
import junit.framework.TestCase;

public class FoodDiaryLabelBeanTest extends TestCase {
	
	@Test
	public void testLabelBeanGetSet() {
		FoodDiaryLabelBean bean = new FoodDiaryLabelBean(730, "Test");
		bean.setRowid(2);
		bean.setMid(702);
		bean.setLabel("New Test");
		assertEquals(2, bean.getRowid());
		assertEquals(702, bean.getMid());
		assertEquals("New Test", bean.getLabel());
	}
}
