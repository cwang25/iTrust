package edu.ncsu.csc.itrust.dao.fooddiary;

import java.sql.Date;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.DBBuilder;
import edu.ncsu.csc.itrust.beans.FoodDiaryLabelBean;
import edu.ncsu.csc.itrust.beans.FoodDiaryLabelSetBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.FoodDiaryLabelDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class FoodDiaryLabelDAOTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private FoodDiaryLabelDAO foodDAO = factory.getFoodDiaryLabelDAO();
	private FoodDiaryLabelDAO evilDAO;
	private EvilDAOFactory evilFactory;
	
	@Before
	public void setUp() throws Exception {
		evilFactory = new EvilDAOFactory(0);
		evilDAO = evilFactory.getFoodDiaryLabelDAO();
		
		DBBuilder dbb = new DBBuilder();
		dbb.dropTables();
		dbb.createTables();
		
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.uc73();
	}
	
	@Test
	public void testInsertFoodDiaryLabel() throws DBException {
		FoodDiaryLabelBean bean = new FoodDiaryLabelBean(730, "test", "#FFFFFF");
		long result = foodDAO.insertFoodDiaryLabel(bean);
		FoodDiaryLabelBean b2 = foodDAO.getFoodDiaryLabelByRowID(result);
		assertEquals("#FFFFFF",b2.getColorCode() );
		assertTrue(result>0);
	}
	
	@Test
	public void testInsertFoodDiaryLabelFailure() {
		FoodDiaryLabelBean bean = new FoodDiaryLabelBean(730, "test");
		try {
			evilDAO.insertFoodDiaryLabel(bean);
			fail(); //evilDAO should throw a DBException and therefore not reach this point
		} catch (DBException e) { }
	}
	
	@Test
	public void testSetFoodDiaryLabel() throws DBException {
		FoodDiaryLabelSetBean bean = new FoodDiaryLabelSetBean(730, Date.valueOf("2012-09-30"), "Atkins", 3L);
		long result = foodDAO.setFoodDiaryLabel(bean);
		assertEquals(2, result);
	}
	
	@Test
	public void testSetFoodDiaryLabelFailure() {
		FoodDiaryLabelSetBean bean = new FoodDiaryLabelSetBean(730, Date.valueOf("2012-09-30"), "Atkins", 3L);
		try {
			evilDAO.setFoodDiaryLabel(bean);
			fail();
		} catch (DBException e) { }
		FoodDiaryLabelSetBean bean2 = new FoodDiaryLabelSetBean(730, Date.valueOf("2012-09-30"), "Atkins", 0L);
		try {
			foodDAO.setFoodDiaryLabel(bean2);
			fail();
		} catch (DBException e) { }
	}
	
	@Test
	public void testGetAllFoodDiaryLabels() throws DBException {
		List<FoodDiaryLabelBean> list = foodDAO.getAllFoodDiaryLabels(730);
		assertEquals(4, list.size());
	}
	
	@Test
	public void testGetAllFoodDiaryLabelsFailure() {
		try {
			evilDAO.getAllFoodDiaryLabels(730);
			fail();
		} catch (DBException e) { }
	}
	
	@Test
	public void testGetSetFoodDiaryLabel() throws DBException {
		FoodDiaryLabelSetBean label = foodDAO.getSetFoodDiaryLabel(730, Date.valueOf("2012-09-30"));
		assertEquals("Southbeach", label.getLabel());
	}
	
	@Test
	public void testGetSetFoodDiaryLabelFailure() {
		try {
			evilDAO.getSetFoodDiaryLabel(730, Date.valueOf("2012-09-30"));
			fail();
		} catch (DBException e) { }
	}
	
	@Test
	public void testRemoveFoodDiaryLabel() throws DBException {
		FoodDiaryLabelSetBean label = foodDAO.getSetFoodDiaryLabel(730, Date.valueOf("2012-09-30"));
		FoodDiaryLabelSetBean deletedLabel = foodDAO.removeFoodDiaryLabel(label);
		assertEquals(label.getRowid(), deletedLabel.getRowid());
	}
	
	@Test
	public void testRemoveFoodDiaryLabelFailure() throws DBException {
		FoodDiaryLabelSetBean label = foodDAO.getSetFoodDiaryLabel(730, Date.valueOf("2012-09-30"));
		try {
			evilDAO.removeFoodDiaryLabel(label);		
		} catch(DBException e) { }
	}
}
