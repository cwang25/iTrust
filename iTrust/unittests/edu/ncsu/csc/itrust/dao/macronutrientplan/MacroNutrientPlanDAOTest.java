package edu.ncsu.csc.itrust.dao.macronutrientplan;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.DBBuilder;
import edu.ncsu.csc.itrust.beans.MacroNutrientPlanBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.MacroNutrientPlanDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class MacroNutrientPlanDAOTest {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private EvilDAOFactory evil;
	private MacroNutrientPlanDAO evil2;
	private MacroNutrientPlanDAO mDAO;
	private MacroNutrientPlanBean b1;
	private MacroNutrientPlanBean b2;
	private static final long PATIENT_MID = 500L;
	private static final long PATIENT_MID_2 = 503L;
	@Before
	public void setUp() throws Exception {
		evil = new EvilDAOFactory(0);
		evil2 = evil.getMacroNutrientPlanDAO();
		mDAO = factory.getMacroNutrientPlanDAO();
		DBBuilder tables = new DBBuilder();
		tables.dropTables();
		tables.createTables();
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		b1 = new MacroNutrientPlanBean(PATIENT_MID, 50, 40, 60);
		b2 = new MacroNutrientPlanBean(PATIENT_MID_2, 20, 100, 30);
	}

	@Test
	public void testInsertMacroNutrientPlan() throws DBException {
		long r1 = mDAO.insertMacroNutrientPlan(b1);
		long r2 = mDAO.insertMacroNutrientPlan(b2);
		assertTrue(r2 > r1);
	}

	@Test
	public void testGetMacroNutrientPlanByOwnerID() throws DBException {
		long r1 = mDAO.insertMacroNutrientPlan(b1);
		long r2 = mDAO.insertMacroNutrientPlan(b2);
		List<MacroNutrientPlanBean> tb = mDAO.getMacroNutrientPlanByOwnerID(PATIENT_MID);
		List<MacroNutrientPlanBean> tb2 = mDAO.getMacroNutrientPlanByOwnerID(PATIENT_MID_2);
		assertTrue(tb.get(0).getRowID() == r1);
		assertTrue(tb2.get(0).getRowID() == r2);
	}

	@Test
	public void testGetMacroNutrientPlanByRowID() throws DBException {
		long r1 = mDAO.insertMacroNutrientPlan(b1);
		MacroNutrientPlanBean tb = mDAO.getMacroNutrientPlanByRowID(r1);
		assertTrue(tb.getRowID() == r1);
	}

	@Test
	public void testUpdateFoodDiary() throws DBException {
		long r1 = mDAO.insertMacroNutrientPlan(b1);
		double proteitnComp = b1.getProtein();
		MacroNutrientPlanBean tb = mDAO.getMacroNutrientPlanByRowID(r1);
		tb.setProtein(100);
		mDAO.updateMacroNutrientPlan(tb);
		MacroNutrientPlanBean tb2 = mDAO.getMacroNutrientPlanByRowID(r1);
		assertTrue(tb2.getProtein() != proteitnComp);
		assertTrue(tb2.getProtein() == 100);
	}

	@Test
	public void testRemoveMacroNutrientPlan() throws DBException {
		long r1 = mDAO.insertMacroNutrientPlan(b1);
		MacroNutrientPlanBean bRemove = mDAO.getMacroNutrientPlanByRowID(r1);
		mDAO.removeMacroNutrientPlan(bRemove);
		List<MacroNutrientPlanBean> list = mDAO.getMacroNutrientPlanByOwnerID(b1.getOwnerID());
		assertTrue(list.size() == 0);
	}

}
