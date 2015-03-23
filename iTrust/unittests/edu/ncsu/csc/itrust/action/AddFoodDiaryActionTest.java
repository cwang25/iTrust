/**
 * 
 */
package edu.ncsu.csc.itrust.action;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.beans.FoodDiaryBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

/**
 * @author Chi-Han
 *
 */
public class AddFoodDiaryActionTest {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private AddFoodDiaryAction aaron_action;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		aaron_action = new AddFoodDiaryAction(factory, "500");

	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc.itrust.action.AddFoodDiaryAction#addFoodDiary(edu.ncsu.csc.itrust.beans.FoodDiaryBean)}
	 * .
	 * 
	 * @throws DBException
	 */
	@Test
	public void testAddFoodDiary() throws DBException {
		FoodDiaryBean testBean = new FoodDiaryBean(500L, new Date(),
				FoodDiaryBean.MealTypes.BREAKFAST, "Test food", 2, 2, 2,
				2, 2, 2, 2, 2);
		long newID = aaron_action.addFoodDiary(testBean);
		FoodDiaryBean returnBean = factory.getFoodDiaryDAO().getFoodDiaryByID(
				newID);
		assertTrue(testBean.getNameOfFood().equals(returnBean.getNameOfFood()));
		assertTrue(testBean.getGramsOfFat() == returnBean.getGramsOfFat());
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc.itrust.action.AddFoodDiaryAction#addStrFoodDiary(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testAddStrFoodDiary() {
		// All correct input.
		try {
			long rID = aaron_action.addStrFoodDiary("02/02/02015", "BREAKFAST",
					"GOODFOOD", "20", "5", "5", "5", "5", "5", "5", "5");
			assertTrue(rID > 0);
		} catch (FormValidationException e) {
			// TODO Auto-generated catch block
			fail("Add food diary method failed. Expected no exception.");
		}
		// Empty name.
		try {
			aaron_action.addStrFoodDiary("02/02/02015", "BREAKFAST", "", "20",
					"5", "5", "5", "5", "5", "5", "5");
			fail("Exception should be thrown due to empty name.");
		} catch (FormValidationException e) {
			// TODO Auto-generated catch block
			assertTrue(e.getMessage().contains(
					"The name of food cannot be empty"));
		}
		// null name.
		try {
			aaron_action.addStrFoodDiary("02/02/02015", "BREAKFAST", null,
					"20", "5", "5", "5", "5", "5", "5", "5");
			fail("Exception should be thrown due to null name.");
		} catch (FormValidationException e) {
			// TODO Auto-generated catch block
			assertTrue(e.getMessage().contains(
					"The name of food cannot be empty"));
		}
		// Wrong date format
		try {
			aaron_action.addStrFoodDiary("0225/02/15", "BREAKFAST", "GOODFOOD",
					"20", "5", "5", "5", "5", "5", "5", "5");
			fail("Exception should be thrwn due to wrong date format.");
		} catch (FormValidationException e) {
			// TODO Auto-generated catch block
			assertTrue(e.getMessage().contains(
					"Accepted date format is mm/dd/yyyy"));
		}
		// Wrong serving number.
		try {
			aaron_action.addStrFoodDiary("05/02/2015", "BREAKFAST", "GOODFOOD",
					"0", "5", "5", "5", "5", "5", "5", "5");
			fail("Exception should be thrwn due to non positive serving number.");
		} catch (FormValidationException e) {
			// TODO Auto-generated catch block
			assertTrue(e.getMessage().contains(
					"Number of servings has to be a positive number"));
		}
		// Wrong calories per serving number.
		try {
			aaron_action.addStrFoodDiary("05/02/2015", "BREAKFAST", "GOODFOOD",
					"5", "-1", "5", "5", "5", "5", "5", "5");
			fail("Exception should be thrwn due to non positive serving number.");
		} catch (FormValidationException e) {
			// TODO Auto-generated catch block
			assertTrue(e.getMessage().contains(
					"Calories per serving has to be 0 or greater"));
		}
		// Wrong grams of fat.
		try {
			aaron_action.addStrFoodDiary("05/02/2015", "BREAKFAST", "GOODFOOD",
					"5", "5", "-1", "5", "5", "5", "5", "5");
			fail("Exception should be thrwn due to non positive grams of fat.");
		} catch (FormValidationException e) {
			// TODO Auto-generated catch block
			assertTrue(e.getMessage().contains(
					"Grams of fat has to be 0 or greater"));
		}
		// Wrong milligrams of sodium.
		try {
			aaron_action.addStrFoodDiary("05/02/2015", "BREAKFAST", "GOODFOOD",
					"5", "5", "5", "-1", "5", "5", "5", "5");
			fail("Exception should be thrwn due to non positive milligrams of sodium.");
		} catch (FormValidationException e) {
			// TODO Auto-generated catch block
			assertTrue(e.getMessage().contains(
					"Milligrams of sodium has to be 0 or greater"));
		}
		// Wrong grams of carbs.
		try {
			aaron_action.addStrFoodDiary("05/02/2015", "BREAKFAST", "GOODFOOD",
					"5", "5", "5", "5", "-1", "5", "5", "5");
			fail("Exception should be thrwn due to non positive grams of carbs.");
		} catch (FormValidationException e) {
			// TODO Auto-generated catch block
			assertTrue(e.getMessage().contains(
					"Grams of carbs has to be 0 or greater"));
		}
		// Wrong grams sugar
		try {
			aaron_action.addStrFoodDiary("05/02/2015", "BREAKFAST", "GOODFOOD",
					"5", "5", "5", "5", "5", "-1", "5", "5");
			fail("Exception should be thrwn due to non positive grams of sugar.");
		} catch (FormValidationException e) {
			// TODO Auto-generated catch block
			assertTrue(e.getMessage().contains(
					"Grams of sugar has to be 0 or greater"));
		}
		// Wrong grams fiber
		try {
			aaron_action.addStrFoodDiary("05/02/2015", "BREAKFAST", "GOODFOOD",
					"5", "5", "5", "5", "5", "5", "-1", "5");
			fail("Exception should be thrwn due to non positive grams of fiber.");
		} catch (FormValidationException e) {
			// TODO Auto-generated catch block
			assertTrue(e.getMessage().contains(
					"Grams of fiber has to be 0 or greater"));
		}
		// Wrong grams protein
		try {
			aaron_action.addStrFoodDiary("05/02/2015", "BREAKFAST", "GOODFOOD",
					"5", "5", "5", "5", "5", "5", "5", "-1");
			fail("Exception should be thrwn due to non positive grams of protein.");
		} catch (FormValidationException e) {
			// TODO Auto-generated catch block
			assertTrue(e.getMessage().contains(
					"Grams of protein has to be 0 or greater"));
		}
		// Wrong grams protein
		try {
			aaron_action.addStrFoodDiary("05/02/2015", "RUTHCHRIS", "GOODFOOD",
					"5", "5", "5", "5", "5", "5", "5", "5");
			fail("Exception should be thrwn due to non existing Meal type.");
		} catch (FormValidationException e) {
			// TODO Auto-generated catch block
			assertTrue(e.getMessage().contains(
					"Meal type has to be either Breakfast, Lunch, Dinner or Snack"));
		}
		// Future date (will get error message.)
		try {
			Calendar c = Calendar.getInstance();
			c.set(Calendar.HOUR_OF_DAY, 0);            
			c.set(Calendar.MINUTE, 0);                 
			c.set(Calendar.SECOND, 0);                
			c.set(Calendar.MILLISECOND, 0); 
			c.setTimeInMillis(c.getTimeInMillis()+(24*60*60*1000L));
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			aaron_action.addStrFoodDiary(sdf.format(c.getTime()), "BREAKFAST", "GOODFOOD",
					"5", "5", "5", "5", "5", "5", "5", "5");
			fail("Exception should be thrwn due to future date input.");
		} catch (FormValidationException e) {
			// TODO Auto-generated catch block
			assertTrue(e.getMessage().contains(
					"Date has to be either past or current date. (No future date.)"));
		}
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc.itrust.action.base.FoodDiaryBaseAction#getOwnderID()}
	 * .
	 */
	@Test
	public void testGetOwnderID() {
		assertTrue(aaron_action.getOwnderID() == 500L);
	}

}
