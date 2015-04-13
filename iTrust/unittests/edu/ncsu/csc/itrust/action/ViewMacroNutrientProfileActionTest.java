package edu.ncsu.csc.itrust.action;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.beans.MacroNutrientProfileBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class ViewMacroNutrientProfileActionTest {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private AddMacroNutrientPlanAction add_plan_action;
	private ViewMacroNutrientPlanAction view_plan_action;
	private ViewMacroNutrientProfileAction profile_view_action;
	@Before
	public void setUp() throws Exception {
		gen.clearAllTables();
		gen.uc72();
		add_plan_action = new AddMacroNutrientPlanAction(factory, "9900000012");
		view_plan_action = new ViewMacroNutrientPlanAction(factory, "9900000012");
		profile_view_action = new ViewMacroNutrientProfileAction(factory, "9900000012");

	}

	@Test
	public void testGetMacroNutrientProfileListByPlanID() throws DBException {
		Long planID = view_plan_action.getMacroNutrientPlanListByOwnerID(500L).get(0).getRowID();
		MacroNutrientProfileBean b = profile_view_action.getMacroNutrientProfileListByPlanID(planID).get(0);
		assertTrue(b.getAge() == 20);
	}

	@Test
	public void testGetMacroNutrientProfileByRowID() throws DBException {
		Long planID = view_plan_action.getMacroNutrientPlanListByOwnerID(500L).get(0).getRowID();
		MacroNutrientProfileBean b = profile_view_action.getMacroNutrientProfileListByPlanID(planID).get(0);
		long rowID = b.getRowID();
		assertTrue(b.getAge() == 20);
		MacroNutrientProfileBean b2 = profile_view_action.getMacroNutrientProfileListByPlanID(planID).get(0);
		assertEquals(b2.getAge(), b.getAge());
		assertEquals(b2.getAct(), b.getAct());
 		assertEquals(b2.getGender(), b.getGender());

	}

}
