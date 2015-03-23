package edu.ncsu.csc.itrust.action;

import java.sql.Date;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.DBBuilder;
import edu.ncsu.csc.itrust.beans.FoodDiaryLabelSetBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class RemoveFoodDiaryLabelActionTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private RemoveFoodDiaryLabelAction action;
	
	@Before
	public void setUp() throws Exception {
		action = new RemoveFoodDiaryLabelAction(factory, 730);
		
		DBBuilder dbb = new DBBuilder();
		dbb.dropTables();
		dbb.createTables();
		
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.uc73();
	}
	
	@Test
	public void testSetFoodDiaryLabel() throws DBException {
		FoodDiaryLabelSetBean b = new FoodDiaryLabelSetBean(1, 730, Date.valueOf("2012-09-30"), "Southbeach");
		FoodDiaryLabelSetBean ret = action.removeFoodDiaryLabel(b);
		assertEquals(b.getRowid(), ret.getRowid());
	}
}
