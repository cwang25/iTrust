package edu.ncsu.csc.itrust.action;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.beans.MacroNutrientPlanBean;
import edu.ncsu.csc.itrust.beans.MacroNutrientProfileBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class AddMacroNutrientProfileActionTest {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private AddMacroNutrientPlanAction add_plan_action;
	private ViewMacroNutrientPlanAction view_plan_action;
	private ViewMacroNutrientProfileAction profile_view_action;
	private EditMacroNutrientProfileAction profile_edit_action;
	private AddMacroNutrientProfileAction profile_add_action;

	@Before
	public void setUp() throws Exception {
		gen.clearAllTables();
		gen.uc72();
		view_plan_action = new ViewMacroNutrientPlanAction(factory, "9900000012");
		profile_view_action = new ViewMacroNutrientProfileAction(factory, "9900000012");
		profile_edit_action =  new EditMacroNutrientProfileAction(factory, "9900000012");
		profile_add_action =  new AddMacroNutrientProfileAction(factory, "9900000012");

	}

	@Test
	public void testAddMacroNutrientProfile() throws DBException, FormValidationException {
		MacroNutrientPlanBean planPlanB = view_plan_action.getMacroNutrientPlanListByOwnerID(500L).get(0);
		MacroNutrientProfileBean bR = profile_view_action.getMacroNutrientProfileListByPlanID(planPlanB.getRowID()).get(0);
		profile_edit_action.deleteMacroNutrientProfile(bR);
		assertTrue(profile_view_action.getMacroNutrientProfileListByPlanID(planPlanB.getRowID()).size()== 0);
		profile_add_action.addMacroNutrientProfile(bR);
		assertTrue(profile_view_action.getMacroNutrientProfileListByPlanID(planPlanB.getRowID()).size()== 1);

	}

	@Test
	public void testAddMacroNutrientPlanByStr() throws DBException, FormValidationException {
		MacroNutrientPlanBean planPlanB = view_plan_action.getMacroNutrientPlanListByOwnerID(500L).get(0);
		MacroNutrientProfileBean bR = profile_view_action.getMacroNutrientProfileListByPlanID(planPlanB.getRowID()).get(0);
		profile_edit_action.deleteMacroNutrientProfile(bR);
		assertTrue(profile_view_action.getMacroNutrientProfileListByPlanID(planPlanB.getRowID()).size()== 0);
		profile_add_action.addMacroNutrientPlanByStr("male", "25", "80", "168", "lose_weight", "very_active", planPlanB.getRowID()+"");
		assertTrue(profile_view_action.getMacroNutrientProfileListByPlanID(planPlanB.getRowID()).size()== 1);


	}

}
