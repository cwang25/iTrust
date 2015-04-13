package edu.ncsu.csc.itrust.action;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.beans.MacroNutrientProfileBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class EditMacroNutrientProfileActionTest {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private AddMacroNutrientPlanAction add_plan_action;
	private ViewMacroNutrientPlanAction view_plan_action;
	private ViewMacroNutrientProfileAction profile_view_action;
	private EditMacroNutrientProfileAction profile_edit_action;

	@Before
	public void setUp() throws Exception {
		gen.clearAllTables();
		gen.uc72();
		add_plan_action = new AddMacroNutrientPlanAction(factory, "9900000012");
		view_plan_action = new ViewMacroNutrientPlanAction(factory, "9900000012");
		profile_view_action = new ViewMacroNutrientProfileAction(factory, "9900000012");
		profile_edit_action =  new EditMacroNutrientProfileAction(factory, "9900000012");
	}

	@Test
	public void testEditMacroNutrientProfile() throws DBException {
		Long planID = view_plan_action.getMacroNutrientPlanListByOwnerID(500L).get(0).getRowID();
		MacroNutrientProfileBean b = profile_view_action.getMacroNutrientProfileListByPlanID(planID).get(0);
		assertTrue(b.getAge() == 20);
		int oAge = b.getAge();
		double oHeight = b.getHeight();
		b.setAge(50);
		b.setHeight(200);
		profile_edit_action.editMacroNutrientProfile(b);
		MacroNutrientProfileBean b2 = profile_view_action.getMacroNutrientProfileListByPlanID(planID).get(0);
		assertFalse(oAge == b2.getAge());
		assertFalse(oHeight == b2.getHeight());
	
	}

	@Test
	public void testEditMacroNutrientProfileByStr() throws DBException, FormValidationException {
		Long planID = view_plan_action.getMacroNutrientPlanListByOwnerID(500L).get(0).getRowID();
		MacroNutrientProfileBean b = profile_view_action.getMacroNutrientProfileListByPlanID(planID).get(0);
		assertTrue(b.getAge() == 20);
		int oAge = b.getAge();
		double oHeight = b.getHeight();
		System.out.println(planID.toString());
		profile_edit_action.editMacroNutrientProfileByStr(b.getRowID(), "male", "50", "80", "200", "lose_weight","very_active", planID.toString());
		MacroNutrientProfileBean b2 = profile_view_action.getMacroNutrientProfileListByPlanID(planID).get(0);
		assertFalse(oAge == b2.getAge());
		assertFalse(oHeight == b2.getHeight());
	}

	@Test
	public void testDeleteMacroNutrientProfile() throws DBException, FormValidationException {
		Long planID = view_plan_action.getMacroNutrientPlanListByOwnerID(500L).get(0).getRowID();
		MacroNutrientProfileBean b = profile_view_action.getMacroNutrientProfileListByPlanID(planID).get(0);
		assertTrue(b.getAge() == 20);
		profile_edit_action.deleteMacroNutrientProfile(b);
		assertTrue(profile_view_action.getMacroNutrientProfileListByPlanID(planID).size() == 0);
		
	}

}
