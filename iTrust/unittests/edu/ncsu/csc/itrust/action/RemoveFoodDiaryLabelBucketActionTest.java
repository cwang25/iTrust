package edu.ncsu.csc.itrust.action;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.beans.FoodDiaryLabelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class RemoveFoodDiaryLabelBucketActionTest {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private RemoveFoodDiaryLabelBucketAction action;
	@Before
	public void setUp() throws Exception {
		gen.clearAllTables();
		gen.uc73();
		action = new RemoveFoodDiaryLabelBucketAction(factory, 500L);
	}

	@Test
	public void testRemoveFoodDiaryLabel() throws DBException, FormValidationException {
		int originSize = factory.getFoodDiaryLabelDAO().getAllFoodDiaryLabels(730L).size();
		FoodDiaryLabelBean b = factory.getFoodDiaryLabelDAO().getAllFoodDiaryLabels(730L).get(0);
		action.removeFoodDiaryLabel(b);
		assertEquals(factory.getFoodDiaryLabelDAO().getAllFoodDiaryLabels(730L).size(), originSize-1);
	}

}
