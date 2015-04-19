package edu.ncsu.csc.itrust.action;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.DBBuilder;
import edu.ncsu.csc.itrust.beans.FoodDiaryLabelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class AddFoodDiaryLabelActionTest extends TestCase {

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private AddFoodDiaryLabelAction action;
	
	@Before
	public void setUp() throws Exception {
		action = new AddFoodDiaryLabelAction(factory, 730);
		
		DBBuilder dbb = new DBBuilder();
		dbb.dropTables();
		dbb.createTables();
		
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.uc73();
	}
	
	@Test
	public void testAddFoodDiaryLabel() throws DBException, FormValidationException {
		FoodDiaryLabelBean b = new FoodDiaryLabelBean(730, "Test");
		long ret = action.addFoodDiaryLabel(b);
		FoodDiaryLabelBean bFromDB = factory.getFoodDiaryLabelDAO().getFoodDiaryLabelByRowID(ret);
		assertEquals(b.getMid(), bFromDB.getMid());
		assertEquals(b.getLabel(), bFromDB.getLabel());

	}
	
	@Test
	public void testAddFoodDiaryLabelFailure() throws DBException {
		FoodDiaryLabelBean b = new FoodDiaryLabelBean(730, "-23-asd");
		try {
			action.addFoodDiaryLabel(b);
			fail();
		} catch (FormValidationException e) { }
	}	
}
