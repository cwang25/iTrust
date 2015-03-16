package edu.ncsu.csc.itrust.action;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.beans.FoodDiaryBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class EditFoodDiaryActionTest {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private AddFoodDiaryAction emilyAddAction;
	private ViewFoodDiaryAction emilyViewAction;
	private EditFoodDiaryAction emilyEditAction;
	@Before
	public void setUp() throws Exception {
		gen.clearAllTables();
		gen.uc70();
		emilyAddAction = new AddFoodDiaryAction(factory, "701");
		emilyViewAction = new ViewFoodDiaryAction(factory, "701");
		emilyEditAction = new EditFoodDiaryAction(factory, "701");
	}

	@Test
	public void testEditFoodDiary() throws DBException {
		List <FoodDiaryBean> list = emilyViewAction.getFoodDiaryListByOwnerID(701);
		FoodDiaryBean b = list.get(0);
		double gramCarbs = b.getGramsOfCarbs();
		long rowID = b.getRowID();
		//changed the value
		b.setGramsOfCarbs(gramCarbs+100);
		emilyEditAction.editFoodDiary(b);
		FoodDiaryBean compare = emilyViewAction.getFoodDiaryByID(rowID);
		assertFalse(gramCarbs == compare.getGramsOfCarbs());
	}

	@Test
	public void testEditStrFoodDiary() throws DBException {
		// All correct input.
		FoodDiaryBean b = emilyViewAction.getFoodDiaryListByOwnerID(701).get(0);
		long beanRowID = b.getRowID();
		try {
			int newRow = emilyEditAction.editStrFoodDiary(beanRowID, "02/02/02015", "BREAKFAST",
					"GOODFOOD", "20", "5", "5", "5", "5", "5", "5", "5");
			assertTrue(newRow > 0);
		} catch (FormValidationException e) {
			// TODO Auto-generated catch block
			fail("Add food diary method failed. Expected no exception.");
		}
		// Empty name.
		try {
			emilyEditAction.editStrFoodDiary(beanRowID, "02/02/02015", "BREAKFAST", "", "20",
					"5", "5", "5", "5", "5", "5", "5");
			fail("Exception should be thrown due to empty name.");
		} catch (FormValidationException e) {
			// TODO Auto-generated catch block
			assertTrue(e.getMessage().contains(
					"The name of food cannot be empty"));
		}
		// null name.
		try {
			emilyEditAction.editStrFoodDiary(beanRowID, "02/02/02015", "BREAKFAST", null,
					"20", "5", "5", "5", "5", "5", "5", "5");
			fail("Exception should be thrown due to null name.");
		} catch (FormValidationException e) {
			// TODO Auto-generated catch block
			assertTrue(e.getMessage().contains(
					"The name of food cannot be empty"));
		}
		// Wrong date format
		try {
			emilyEditAction.editStrFoodDiary(beanRowID, "0225/02/15", "BREAKFAST", "GOODFOOD",
					"20", "5", "5", "5", "5", "5", "5", "5");
			fail("Exception should be thrwn due to wrong date format.");
		} catch (FormValidationException e) {
			// TODO Auto-generated catch block
			assertTrue(e.getMessage().contains(
					"Accepted date format is mm/dd/yyyy"));
		}
		// Wrong serving number.
		try {
			emilyEditAction.editStrFoodDiary(beanRowID, "05/02/2015", "BREAKFAST", "GOODFOOD",
					"0", "5", "5", "5", "5", "5", "5", "5");
			fail("Exception should be thrwn due to non positive serving number.");
		} catch (FormValidationException e) {
			// TODO Auto-generated catch block
			assertTrue(e.getMessage().contains(
					"Number of servings has to be a positive number"));
		}
		// Wrong calories per serving number.
		try {
			emilyEditAction.editStrFoodDiary(beanRowID, "05/02/2015", "BREAKFAST", "GOODFOOD",
					"5", "-1", "5", "5", "5", "5", "5", "5");
			fail("Exception should be thrwn due to non positive serving number.");
		} catch (FormValidationException e) {
			// TODO Auto-generated catch block
			assertTrue(e.getMessage().contains(
					"Calories per serving has to be 0 or greater"));
		}
		// Wrong grams of fat.
		try {
			emilyEditAction.editStrFoodDiary(beanRowID, "05/02/2015", "BREAKFAST", "GOODFOOD",
					"5", "5", "-1", "5", "5", "5", "5", "5");
			fail("Exception should be thrwn due to non positive grams of fat.");
		} catch (FormValidationException e) {
			// TODO Auto-generated catch block
			assertTrue(e.getMessage().contains(
					"Grams of fat has to be 0 or greater"));
		}
		// Wrong milligrams of sodium.
		try {
			emilyEditAction.editStrFoodDiary(beanRowID, "05/02/2015", "BREAKFAST", "GOODFOOD",
					"5", "5", "5", "-1", "5", "5", "5", "5");
			fail("Exception should be thrwn due to non positive milligrams of sodium.");
		} catch (FormValidationException e) {
			// TODO Auto-generated catch block
			assertTrue(e.getMessage().contains(
					"Milligrams of sodium has to be 0 or greater"));
		}
		// Wrong grams of carbs.
		try {
			emilyEditAction.editStrFoodDiary(beanRowID, "05/02/2015", "BREAKFAST", "GOODFOOD",
					"5", "5", "5", "5", "-1", "5", "5", "5");
			fail("Exception should be thrwn due to non positive grams of carbs.");
		} catch (FormValidationException e) {
			// TODO Auto-generated catch block
			assertTrue(e.getMessage().contains(
					"Grams of carbs has to be 0 or greater"));
		}
		// Wrong grams sugar
		try {
			emilyEditAction.editStrFoodDiary(beanRowID, "05/02/2015", "BREAKFAST", "GOODFOOD",
					"5", "5", "5", "5", "5", "-1", "5", "5");
			fail("Exception should be thrwn due to non positive grams of sugar.");
		} catch (FormValidationException e) {
			// TODO Auto-generated catch block
			assertTrue(e.getMessage().contains(
					"Grams of sugar has to be 0 or greater"));
		}
		// Wrong grams fiber
		try {
			emilyEditAction.editStrFoodDiary(beanRowID, "05/02/2015", "BREAKFAST", "GOODFOOD",
					"5", "5", "5", "5", "5", "5", "-1", "5");
			fail("Exception should be thrwn due to non positive grams of fiber.");
		} catch (FormValidationException e) {
			// TODO Auto-generated catch block
			assertTrue(e.getMessage().contains(
					"Grams of fiber has to be 0 or greater"));
		}
		// Wrong grams protein
		try {
			emilyEditAction.editStrFoodDiary(beanRowID, "05/02/2015", "BREAKFAST", "GOODFOOD",
					"5", "5", "5", "5", "5", "5", "5", "-1");
			fail("Exception should be thrwn due to non positive grams of protein.");
		} catch (FormValidationException e) {
			// TODO Auto-generated catch block
			assertTrue(e.getMessage().contains(
					"Grams of protein has to be 0 or greater"));
		}
		// Wrong grams protein
		try {
			emilyEditAction.editStrFoodDiary(beanRowID, "05/02/2015", "RUTHCHRIS", "GOODFOOD",
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
			emilyEditAction.editStrFoodDiary(beanRowID, sdf.format(c.getTime()), "BREAKFAST", "GOODFOOD",
					"5", "5", "5", "5", "5", "5", "5", "5");
			fail("Exception should be thrwn due to future date input.");
		} catch (FormValidationException e) {
			// TODO Auto-generated catch block
			assertTrue(e.getMessage().contains(
					"Date has to be either past or current date. (No future date.)"));
		}
	}

	@Test
	public void testDeleteFoodDiary() throws DBException {
		List <FoodDiaryBean> list = emilyViewAction.getFoodDiaryListByOwnerID(701);
		int originalLength = list.size();
		try {
			emilyAddAction.addStrFoodDiary("02/02/02015", "BREAKFAST",
					"GOODFOOD", "20", "5", "5", "5", "5", "5", "5", "5");
		} catch (FormValidationException e) {
			// TODO Auto-generated catch block
			fail("DB failure, expected no problem.");
		}
		assertTrue(originalLength + 1 == emilyViewAction.getFoodDiaryListByOwnerID(701).size());
		emilyEditAction.deleteFoodDiary(list.get(0));
		assertTrue(originalLength == emilyViewAction.getFoodDiaryListByOwnerID(701).size());
	}

}
