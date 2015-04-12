package edu.ncsu.csc.itrust.action;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.beans.MacroNutrientPlanBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class ViewMacroNutrientPlanActionTest {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private ViewMacroNutrientPlanAction Nutritionist_action;
	private AddMacroNutrientPlanAction Nutritionsit_add_action;
	private ViewMacroNutrientPlanAction Non_nutritionist_action;
	@Before
	public void setUp() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		Nutritionist_action = new ViewMacroNutrientPlanAction(factory, "9900000012");
		Non_nutritionist_action = new ViewMacroNutrientPlanAction(factory, "9000000000");
		Nutritionsit_add_action = new AddMacroNutrientPlanAction(factory, "9900000012");
	}

	@Test
	public void testGetMacroNutrientPlanListByOwnerID() throws DBException, NumberFormatException, FormValidationException {
		Nutritionsit_add_action.addMacroNutrientPlanByStrForNutritionist("500", "50", "50", "50", "1500");
		List<MacroNutrientPlanBean> MacroNutrientPlanlist = Nutritionist_action.getMacroNutrientPlanListByOwnerID(500L);
		System.out.println(MacroNutrientPlanlist.size());
		assertTrue(MacroNutrientPlanlist.size()== 1);
		List<MacroNutrientPlanBean> emptylist = Nutritionist_action.getMacroNutrientPlanListByOwnerID(503L);
		assertTrue(emptylist.size() == 0);
		List<MacroNutrientPlanBean> fail_MacroNutrientPlanlist = Non_nutritionist_action.getMacroNutrientPlanListByOwnerID(500L);
		assertTrue(null == fail_MacroNutrientPlanlist);
		//Test if the nutritionist is not the designated for this patient.
		List<MacroNutrientPlanBean> fail_not_designated_emptylist = Nutritionist_action.getMacroNutrientPlanListByOwnerID(100L);
		assertTrue(null == fail_not_designated_emptylist);
	}

	@Test
	public void testGetMacroNutrientPlanByRowID() throws DBException, NumberFormatException, FormValidationException {
		Nutritionsit_add_action.addMacroNutrientPlanByStrForNutritionist("500", "50", "50", "50","1500");
		List<MacroNutrientPlanBean> MacroNutrientPlanlist = Nutritionist_action.getMacroNutrientPlanListByOwnerID(500L);
		assertTrue(MacroNutrientPlanlist.size()== 1);
		MacroNutrientPlanBean tmp = MacroNutrientPlanlist.get(0);
		MacroNutrientPlanBean fromByID = Nutritionist_action.getMacroNutrientPlanByRowID(tmp.getRowID());
		assertTrue(tmp.getRowID()== fromByID.getRowID());

	}

}
