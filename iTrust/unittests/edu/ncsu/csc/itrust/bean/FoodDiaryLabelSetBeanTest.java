package edu.ncsu.csc.itrust.bean;

import java.sql.Date;

import org.junit.Test;

import edu.ncsu.csc.itrust.beans.FoodDiaryLabelSetBean;
import junit.framework.TestCase;

public class FoodDiaryLabelSetBeanTest extends TestCase {
	
	@Test
	public void testLabelSetBeanGetSet() {
		FoodDiaryLabelSetBean bean = new FoodDiaryLabelSetBean(12, null, "test", 2L);
		bean.setRowid(2);
		bean.setMid(730);
		bean.setDate(Date.valueOf("2012-09-12"));
		bean.setLabel("New Label");
		assertEquals(2, bean.getRowid());
		assertEquals(730, bean.getMid());
		assertEquals(Date.valueOf("2012-09-12"), bean.getDate());
		assertEquals("New Label", bean.getLabel());
	}
}
