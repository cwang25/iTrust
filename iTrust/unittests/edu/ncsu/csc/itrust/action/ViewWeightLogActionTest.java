package edu.ncsu.csc.itrust.action;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.DBBuilder;
import edu.ncsu.csc.itrust.beans.WeightLogBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class ViewWeightLogActionTest extends TestCase {

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private ViewWeightLogAction action;
	
	@Before
	public void setUp() throws Exception {		
		DBBuilder dbb = new DBBuilder();
		dbb.dropTables();
		dbb.createTables();
		
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.uc69();
	}
	
	@Test
	public void testViewWeightLogsPatient() throws ITrustException {
		action = new ViewWeightLogAction(factory, 500);
		List<WeightLogBean> list = action.getWeightLogListByMID(500);
		assertEquals(5, list.size());
	}
	
	@Test
	public void testViewWeightLogsHCP() throws ITrustException {
		action = new ViewWeightLogAction(factory, 9900000012L);
		List<WeightLogBean> list = action.getWeightLogListByMID(500);
		assertEquals(5, list.size());
	}
}
