package edu.ncsu.csc.itrust.action;

import java.sql.Date;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.DBBuilder;
import edu.ncsu.csc.itrust.beans.FoodDiaryLabelBean;
import edu.ncsu.csc.itrust.beans.FoodDiaryLabelSetBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class GetFoodDiaryLabelActionTest extends TestCase{
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private GetFoodDiaryLabelAction action;
	
	@Before
	public void setUp() throws Exception {
		action = new GetFoodDiaryLabelAction(factory, 730);
		
		DBBuilder dbb = new DBBuilder();
		dbb.dropTables();
		dbb.createTables();
		
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.uc73();
	}
	
	@Test
	public void testGetSetFoodDiaryLabel() throws DBException {
		FoodDiaryLabelSetBean bean = action.getSetFoodDiaryLabel(730, Date.valueOf("2012-09-30"));
		assertEquals(1, bean.getRowid());
	}
	
	@Test
	public void testGetAllFoodDiaryLabels() throws DBException {
		List<FoodDiaryLabelBean> list = action.getAllFoodDiaryLabels(730);
		assertEquals(4, list.size());
	}
}
