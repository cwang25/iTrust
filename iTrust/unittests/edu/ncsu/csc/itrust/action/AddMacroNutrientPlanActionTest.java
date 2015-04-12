package edu.ncsu.csc.itrust.action;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.beans.MacroNutrientPlanBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class AddMacroNutrientPlanActionTest {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private AddMacroNutrientPlanAction aaron_action;
	private AddMacroNutrientPlanAction nutritionist_action;

	@Before
	public void setUp() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		aaron_action = new  AddMacroNutrientPlanAction(factory, "500");
		nutritionist_action = new  AddMacroNutrientPlanAction(factory, "9900000012");
	}

	@Test
	public void testAddMacroNutrientPlan() throws DBException {
		MacroNutrientPlanBean b1 = new MacroNutrientPlanBean(500L, 50, 50, 50, 1200);
		long newID = aaron_action.addMacroNutrientPlan(b1);
		MacroNutrientPlanBean retreiveB = factory.getMacroNutrientPlanDAO().getMacroNutrientPlanByRowID(newID);
		assertTrue(b1.getProtein() == retreiveB.getProtein());
		assertTrue(b1.getOwnerID() == retreiveB.getOwnerID());
		
	}
	
	@Test
	public void testAddMacroNutrientPlanForNutritionist() throws DBException {
		MacroNutrientPlanBean b1 = new MacroNutrientPlanBean(500L, 50, 50, 50, 1200);
		long newID = nutritionist_action.addMacroNutrientPlan(b1);
		MacroNutrientPlanBean retreiveB = factory.getMacroNutrientPlanDAO().getMacroNutrientPlanByRowID(newID);
		assertTrue(b1.getProtein() == retreiveB.getProtein());
		assertTrue(b1.getOwnerID() == retreiveB.getOwnerID());
		MacroNutrientPlanBean b2 = new MacroNutrientPlanBean(100L, 50, 50, 50, 2100);
		long failID = nutritionist_action.addMacroNutrientPlan(b2);
		System.out.println(nutritionist_action.isNutritionist());
		assertTrue(failID < 0);
	}

	@Test
	public void testAddMacroNutrientPlanByStrForNutritionist() throws DBException, NumberFormatException, FormValidationException {
		long newID = nutritionist_action.addMacroNutrientPlanByStrForNutritionist("500", "50", "40", "50", "5000");
		MacroNutrientPlanBean retreiveB = factory.getMacroNutrientPlanDAO().getMacroNutrientPlanByRowID(newID);
		assertTrue(50.0 == retreiveB.getProtein());
		assertTrue(500L == retreiveB.getOwnerID());
		long failID = nutritionist_action.addMacroNutrientPlanByStrForNutritionist("100", "50", "40", "50","1200");
		assertTrue(failID < 0);
	}

	@Test
	public void testAddMacroNutrientPlanByStr() throws DBException, FormValidationException {
		long newID = aaron_action.addMacroNutrientPlanByStr("500", "50","60", "50", "1200");
		MacroNutrientPlanBean retreiveB = factory.getMacroNutrientPlanDAO().getMacroNutrientPlanByRowID(newID);
		assertTrue(50.0 == retreiveB.getProtein());
		assertTrue(500L == retreiveB.getOwnerID());
	}

}
