package edu.ncsu.csc.itrust.dao.weightlog;

import java.sql.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.DBBuilder;
import edu.ncsu.csc.itrust.beans.WeightLogBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.WeightLogDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class WeightLogDAOTest extends TestCase {

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private WeightLogDAO logDAO = factory.getWeightLogDAO();
	private WeightLogDAO evilDAO;
	private EvilDAOFactory evilFactory;
	
	@Before
	public void setUp() throws Exception {
		evilFactory = new EvilDAOFactory(0);
		evilDAO = evilFactory.getWeightLogDAO();
		
		DBBuilder dbb = new DBBuilder();
		dbb.dropTables();
		dbb.createTables();
		
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.uc69();
	}
	
	@Test
	public void testAddWeightLog() throws DBException {
		WeightLogBean bean = new WeightLogBean(500, Date.valueOf("2015-04-14"), 1, 1, 1, 1, 1, 1, 1, 1);
		long result = logDAO.insertWeightLog(bean);
		assertEquals(6, result);
	}
	
	@Test
	public void testAddWeightLogFailure() {
		WeightLogBean bean = new WeightLogBean(500, Date.valueOf("2015-04-14"), 1, 1, 1, 1, 1, 1, 1, 1);
		try {
			evilDAO.insertWeightLog(bean);
			fail(); //evilDAO should throw a DBException and therefore not reach this point
		} catch (DBException e) { }
	}
	
	@Test
	public void testGetAllWeightLogs() throws DBException {
		List<WeightLogBean> list = logDAO.getWeightLogListByMID(500);
		assertEquals(5, list.size());
	}
	
	@Test
	public void testGetAllWeightLogsFailure() {
		try {
			evilDAO.getWeightLogListByMID(500);
			fail();
		} catch (DBException e) { }
	}
	
}
