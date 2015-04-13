/**
 * 
 */
package edu.ncsu.csc.itrust.action;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.beans.MacroNutrientPlanBean;
import edu.ncsu.csc.itrust.beans.MacroNutrientPlanBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

/**
 * @author Chi-Han
 *
 */
public class EditMacroNutrientPlanActionTest {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private AddMacroNutrientPlanAction patientAddAction;
	private ViewMacroNutrientPlanAction patientViewAction;
	private EditMacroNutrientPlanAction patientEditAction;
	private EditMacroNutrientPlanAction NutritionistEditAction;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		patientAddAction = new AddMacroNutrientPlanAction(factory, "500");
		patientViewAction = new ViewMacroNutrientPlanAction(factory, "500");
		patientEditAction = new EditMacroNutrientPlanAction(factory, "500");
		NutritionistEditAction = new EditMacroNutrientPlanAction(factory, "9900000012");
	}

	/**
	 * Test method for {@link edu.ncsu.csc.itrust.action.EditMacroNutrientPlanAction#editMacroNutrientPlan(edu.ncsu.csc.itrust.beans.MacroNutrientPlanBean)}.
	 * @throws FormValidationException 
	 * @throws DBException 
	 */
	@Test
	public void testEditMacroNutrientPlan() throws FormValidationException, DBException {
		patientAddAction.addMacroNutrientPlanByStr("500", "40", "40", "40", "1200");
		List <MacroNutrientPlanBean> list = patientViewAction.getMacroNutrientPlanListByOwnerID(701);
		MacroNutrientPlanBean b = list.get(0);
		double carbs = b.getCarbs();
		long rowID = b.getRowID();
		//changed the value
		b.setCarbs(carbs+100);
		patientEditAction.editMacroNutrientPlan(b);
		MacroNutrientPlanBean compare = patientViewAction.getMacroNutrientPlanByRowID(rowID);
		assertFalse(carbs == compare.getCarbs());
	}

	/**
	 * Test method for {@link edu.ncsu.csc.itrust.action.EditMacroNutrientPlanAction#editMacroNutrientPlanByStrForNutritionist(long, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws DBException 
	 * @throws FormValidationException 
	 */
	@Test
	public void testEditMacroNutrientPlanByStrForNutritionist() throws DBException, FormValidationException {
		patientAddAction.addMacroNutrientPlanByStr("500", "40", "40", "40", "1500");
		List <MacroNutrientPlanBean> list = patientViewAction.getMacroNutrientPlanListByOwnerID(701);
		MacroNutrientPlanBean b = list.get(0);
		double carbs = b.getCarbs();
		long rowID = b.getRowID();
		//changed the value
		b.setCarbs(carbs+100);
		NutritionistEditAction.editMacroNutrientPlan(b);
		MacroNutrientPlanBean compare = patientViewAction.getMacroNutrientPlanByRowID(rowID);
		assertFalse(carbs == compare.getCarbs());
		b.setOwnerID(100);
	}

	/**
	 * Test method for {@link edu.ncsu.csc.itrust.action.EditMacroNutrientPlanAction#editMacroNutrientPlanByStr(long, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws DBException 
	 * @throws FormValidationException 
	 */
	@Test
	public void testEditMacroNutrientPlanByStr() throws DBException, FormValidationException {
		patientAddAction.addMacroNutrientPlanByStr("500", "40", "40", "40", "5000");
		List <MacroNutrientPlanBean> list = patientViewAction.getMacroNutrientPlanListByOwnerID(701);
		MacroNutrientPlanBean b = list.get(0);
		long rowID = b.getRowID();
		//changed the value
		patientEditAction.editMacroNutrientPlan(b);
		patientViewAction.getMacroNutrientPlanByRowID(rowID);
		try{
			int newRow = patientEditAction.editMacroNutrientPlanByStr(rowID, "500", "50", "50", "50", "1200");
			assertTrue(newRow > 0);
		}catch(FormValidationException e){
			fail("Edit macronutrient method failed. Expected no exception.");
		}
		try{
			patientEditAction.editMacroNutrientPlanByStr(rowID, "abx", "50", "50", "50", "1200");
			fail("Exception should be thrown due to invalid input.");
		}catch(FormValidationException e){
			assertTrue(e.getMessage().contains(
					"Need valid value"));		
		}
		try{
			patientEditAction.editMacroNutrientPlanByStr(rowID, "50", "asbd", "50", "50","1200");
			fail("Exception should be thrown due to invalid input.");
		}catch(FormValidationException e){
			assertTrue(e.getMessage().contains(
					"Need valid value"));		
		}
		try{
			patientEditAction.editMacroNutrientPlanByStr(rowID, "50", "50", "adb", "50","1200");
			fail("Exception should be thrown due to invalid input.");
		}catch(FormValidationException e){
			assertTrue(e.getMessage().contains(
					"Need valid value"));		
		}
		try{
			patientEditAction.editMacroNutrientPlanByStr(rowID, "50", "50", "50", "abd","1200");
			fail("Exception should be thrown due to invalid input.");
		}catch(FormValidationException e){
			assertTrue(e.getMessage().contains(
					"Need valid value"));		
		}
		try{
			patientEditAction.editMacroNutrientPlanByStr(rowID, "50", "50", "50", "50","fda");
			fail("Exception should be thrown due to invalid input.");
		}catch(FormValidationException e){
			assertTrue(e.getMessage().contains(
					"Need valid value"));		
		}
	}

	/**
	 * Test method for {@link edu.ncsu.csc.itrust.action.EditMacroNutrientPlanAction#deleteMacroNutrientPlan(edu.ncsu.csc.itrust.beans.MacroNutrientPlanBean)}.
	 * @throws DBException 
	 */
	@Test
	public void testDeleteMacroNutrientPlan() throws DBException {
		List <MacroNutrientPlanBean> list = patientViewAction.getMacroNutrientPlanListByOwnerID(701);
		int originalLength = list.size();
		try {
			patientAddAction.addMacroNutrientPlanByStr("500", "53", "34", "67","1200");
		} catch (FormValidationException e) {
			// TODO Auto-generated catch block
			fail("DB failure, expected no problem.");
		}
		assertTrue(originalLength + 1 == patientViewAction.getMacroNutrientPlanListByOwnerID(701).size());
		list = patientViewAction.getMacroNutrientPlanListByOwnerID(701);
		patientEditAction.deleteMacroNutrientPlan(list.get(0));
		assertTrue(originalLength == patientViewAction.getMacroNutrientPlanListByOwnerID(701).size());

	}

}
