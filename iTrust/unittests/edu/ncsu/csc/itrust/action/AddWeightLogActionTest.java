package edu.ncsu.csc.itrust.action;

import java.sql.Date;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.DBBuilder;
import edu.ncsu.csc.itrust.beans.WeightLogBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class AddWeightLogActionTest extends TestCase{

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private AddWeightLogAction action;
	
	@Before
	public void setUp() throws Exception {
		action = new AddWeightLogAction(factory, 500);
		
		DBBuilder dbb = new DBBuilder();
		dbb.dropTables();
		dbb.createTables();
		
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.uc69();
	}
	
	@Test
	public void testAddWeightLog() throws DBException, FormValidationException {
		WeightLogBean bean = new WeightLogBean(500, Date.valueOf("2015-04-14"), 1, 1, 1, 1, 1, 1, 1, 1);
		long ret = action.addWeightLog(bean);
		assertEquals(6, ret);
	}
	
	@Test
	public void testAddWeightLogFailure() throws DBException {
		try {
			WeightLogBean bean = new WeightLogBean(500, Date.valueOf("2015-04-14"), -1, -1, -1, -1, -1, -1, -1, -1);
			action.addWeightLog(bean);
			fail();
		} catch (FormValidationException e) { }
	}
	
}
