package edu.ncsu.csc.itrust.action;

import java.sql.Date;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.DBBuilder;
import edu.ncsu.csc.itrust.beans.FoodDiaryLabelBean;
import edu.ncsu.csc.itrust.beans.FoodDiaryLabelSetBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class SetFoodDiaryLabelActionTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private SetFoodDiaryLabelAction action;
	private AddFoodDiaryLabelAction labelAct;
	
	@Before
	public void setUp() throws Exception {
		action = new SetFoodDiaryLabelAction(factory, 730);
		labelAct = new AddFoodDiaryLabelAction(factory, 730);
		DBBuilder dbb = new DBBuilder();
		dbb.dropTables();
		dbb.createTables();
		
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.uc73();
	}
	
	@Test
	public void testSetFoodDiaryLabel() throws DBException, FormValidationException {
		long referenceRowID = labelAct.addFoodDiaryLabel(new FoodDiaryLabelBean(730, "Test"));
		FoodDiaryLabelSetBean b = new FoodDiaryLabelSetBean(730, Date.valueOf("2012-09-02"), "Test", referenceRowID);
		long ret = action.setFoodDiaryLabel(b);
		assertEquals(3, ret);
	}
}
