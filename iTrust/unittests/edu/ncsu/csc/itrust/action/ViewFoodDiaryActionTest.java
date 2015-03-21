/**
 * 
 */
package edu.ncsu.csc.itrust.action;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.beans.FoodDiaryBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

/**
 * @author Chi-Han
 *
 */
public class ViewFoodDiaryActionTest {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private ViewFoodDiaryAction Nutritionist_action;
	private ViewFoodDiaryAction Non_nutritionist_action;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		Nutritionist_action = new ViewFoodDiaryAction(factory, "9900000012");
		Non_nutritionist_action = new ViewFoodDiaryAction(factory, "9000000000");
		
	}

	/**
	 * Test method for {@link edu.ncsu.csc.itrust.action.ViewFoodDiaryAction#getFoodDiaryListByOwnerID(long)}.
	 * @throws DBException 
	 */
	@Test
	public void testGetFoodDiaryListByOwnerID() throws DBException {
		List<FoodDiaryBean> fooddiarylist = Nutritionist_action.getFoodDiaryListByOwnerID(500L);
		assertTrue(fooddiarylist.size()== 2);
		List<FoodDiaryBean> emptylist = Nutritionist_action.getFoodDiaryListByOwnerID(503L);
		assertTrue(emptylist.size() == 0);
		List<FoodDiaryBean> fail_fooddiarylist = Non_nutritionist_action.getFoodDiaryListByOwnerID(500L);
		assertTrue(null == fail_fooddiarylist);
	}

	/**
	 * Test method for {@link edu.ncsu.csc.itrust.action.ViewFoodDiaryAction#getFoodDiaryByID(long)}.
	 * @throws DBException 
	 */
	@Test
	public void testGetFoodDiaryByID() throws DBException {
		List<FoodDiaryBean> fooddiarylist = Nutritionist_action.getFoodDiaryListByOwnerID(500L);
		assertTrue(fooddiarylist.size()== 2);
		FoodDiaryBean tmp = fooddiarylist.get(0);
		FoodDiaryBean fromByID = Nutritionist_action.getFoodDiaryByID(tmp.getRowID());
		assertTrue(tmp.getRowID()== fromByID.getRowID());
		assertTrue(tmp.getNameOfFood().equals(fromByID.getNameOfFood()));
	}

	/**
	 * Test method for {@link edu.ncsu.csc.itrust.action.ViewFoodDiaryAction#isNutritionist()}.
	 */
	@Test
	public void testIsNutritionist() {
		assertTrue(Nutritionist_action.isNutritionist());
		assertFalse(Non_nutritionist_action.isNutritionist());
	}

}
