package edu.ncsu.csc.itrust.dao.macronutrientprofile;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.DBBuilder;
import edu.ncsu.csc.itrust.beans.MacroNutrientPlanBean;
import edu.ncsu.csc.itrust.beans.MacroNutrientProfileBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.MacroNutrientPlanDAO;
import edu.ncsu.csc.itrust.dao.mysql.MacroNutrientProfileDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class MacroNutrientProfileDAOTest {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private EvilDAOFactory evil;
	private MacroNutrientProfileDAO evil2;
	private MacroNutrientProfileDAO mDAO;
	private MacroNutrientPlanDAO planDAO;
	private MacroNutrientPlanBean planBean;
	long reference;
	private MacroNutrientProfileBean b1;
	private MacroNutrientProfileBean b2;
	private static final long PATIENT_MID = 500L;
	@Before
	public void setUp() throws Exception {
		evil = new EvilDAOFactory(0);
		evil2 = evil.getMacroNutrientProfileDAO();
		mDAO = factory.getMacroNutrientProfileDAO();
		planDAO = factory.getMacroNutrientPlanDAO();
		DBBuilder tables = new DBBuilder();
		tables.dropTables();
		tables.createTables();
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		planBean = new MacroNutrientPlanBean(PATIENT_MID, 50, 40, 60, 1220);
		reference = planDAO.insertMacroNutrientPlan(planBean);
		b1 = new MacroNutrientProfileBean(MacroNutrientProfileBean.Gender.MALE, 22, 80, 168, 
				MacroNutrientProfileBean.Goal.LOSE_WEIGHT, MacroNutrientProfileBean.Activity.SEDENTARY, reference);
		b2 = new MacroNutrientProfileBean(MacroNutrientProfileBean.Gender.MALE, 22, 80, 168, 
				MacroNutrientProfileBean.Goal.LOSE_WEIGHT, MacroNutrientProfileBean.Activity.SEDENTARY, 50L);

	}

	@Test
	public void testInsertMacroNutrientProfile() throws DBException {
		long newProfile = mDAO.insertMacroNutrientProfile(b1);
		assertTrue(newProfile > -1);
		try{
			mDAO.insertMacroNutrientProfile(b2);
			fail("foreign key error should be thrown.");
		}catch(DBException e){
		}
	}

	@Test
	public void testGetProfileListByMacroNutrientPlanID() throws DBException {
		long newProfile = mDAO.insertMacroNutrientProfile(b1);
		assertTrue(newProfile > -1);
		MacroNutrientProfileBean b = mDAO.getProfileListByMacroNutrientPlanID(reference).get(0);
		assertTrue(newProfile == b.getRowID());
	}

	@Test
	public void testGetMacroNutrientProfileByID() throws DBException {
		long newProfile = mDAO.insertMacroNutrientProfile(b1);
		assertTrue(newProfile > -1);
		MacroNutrientProfileBean b = mDAO.getMacroNutrientProfileByID(newProfile);
		assertTrue(newProfile == b.getRowID());
		assertTrue(b1.getAct() == b.getAct());
	}

	@Test
	public void testUpdateMacroNutrientProfile() throws DBException {
		long newProfile = mDAO.insertMacroNutrientProfile(b1);
		assertTrue(newProfile > -1);
		MacroNutrientProfileBean b = mDAO.getMacroNutrientProfileByID(newProfile);
		assertTrue(newProfile == b.getRowID());
		assertTrue(b1.getAct() == b.getAct());
		b.setAge(100);
		b.setHeight(180);
		mDAO.updateMacroNutrientProfile(b);
		MacroNutrientProfileBean b2tmp = mDAO.getMacroNutrientProfileByID(newProfile);
		assertFalse(b1.getHeight() == b2tmp.getHeight());
		assertFalse(b1.getAge() == b2tmp.getAge());

	}

	@Test
	public void testRemoveMacroNutrientProfile() throws DBException {
		long newProfile = mDAO.insertMacroNutrientProfile(b1);
		assertTrue(newProfile > -1);
		MacroNutrientProfileBean b = mDAO.getMacroNutrientProfileByID(newProfile);
		mDAO.removeMacroNutrientProfile(b);
		
		assertTrue(mDAO.getProfileListByMacroNutrientPlanID(reference).size() == 0);
	}

}
