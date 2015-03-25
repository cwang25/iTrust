/**
 * 
 */
package edu.ncsu.csc.itrust.dao.fooddiary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.beans.SuggestionBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.SuggestionDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

/**
 * Class that tests SuggestionDAO functionality
 */
public class SuggestionDAOTest extends TestCase{
	//Factory needed for getting the SuggestionDAO object
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	
	//The DAO that will be used for testing
	private SuggestionDAO suggestionDAO = factory.getSuggestionDAO();
	
	/**
	 * Setup method for creating test data before each test
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.uc69();
	}
	
	/**
	 * Tests getting suggestions for a given date
	 * @throws DBException In case something goes wrong in the database
	 * @throws ParseException SimpleDateFormat throws this if it can't parse the date string
	 */
	@Test
	public void testGetSuggestionsByDate() throws DBException, ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date date = sdf.parse("04/13/2014");
		long patientID = 500;
		
		//Get the suggestions for this day
		List<SuggestionBean> suggestions = suggestionDAO.getSuggestionsByDate(new java.sql.Date(date.getTime()), patientID);
		assertEquals(2, suggestions.size());
		assertEquals("First test suggestion", suggestions.get(0).getSuggestion());
		assertEquals("Second test suggestion", suggestions.get(1).getSuggestion());
		
		//Get and test suggestions for a different day
		date = sdf.parse("05/21/2013");
		suggestions = suggestionDAO.getSuggestionsByDate(new java.sql.Date(date.getTime()), patientID);
		assertEquals(2, suggestions.size());
		assertEquals("Third test suggestion", suggestions.get(0).getSuggestion());
		assertEquals("Fourth test suggestion", suggestions.get(1).getSuggestion());
	}
	
	/**
	 * Test adding a new suggestion to the database
	 * @throws ParseException SimpleDateFormat throws this if it can't parse the date string
	 * @throws DBException if something goes wrong in the database
	 */
	@Test
	public void testInsertSuggestion() throws ParseException, DBException{
		//Make an empty suggestion bean
		SuggestionBean newSuggestion = new SuggestionBean();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy"); 
		Date date = sdf.parse("04/13/2014");
		
		//Populate its fields
		newSuggestion.setDate(date);
		newSuggestion.setHcpID(9900000012L);
		newSuggestion.setPatientID(500L);
		newSuggestion.setSuggestion("Fifth test suggestion");
		newSuggestion.setIsNew("true");
		
		//Add it to the database
		suggestionDAO.insertSuggestion(newSuggestion);
		
		//Test if it was successfully added
		List<SuggestionBean> suggestions = suggestionDAO.getSuggestionsByDate(new java.sql.Date(date.getTime()), 500L);
		assertEquals(3, suggestions.size());
		assertEquals("First test suggestion", suggestions.get(0).getSuggestion());
		assertEquals("Second test suggestion", suggestions.get(1).getSuggestion());
		assertEquals("Fifth test suggestion", suggestions.get(2).getSuggestion());
	}
	
	/**
	 * Test editing a suggestion in the database
	 * @throws ParseException SimpleDateFormat throws this if it can't parse the date string
	 * @throws DBException if something goes wrong in the database
	 */
	@Test
	public void testEditSuggestion() throws ParseException, DBException{
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy"); 
		Date date = sdf.parse("04/13/2014");
		//Get the suggestion that we plan on editing
		SuggestionBean suggestionToEdit = suggestionDAO.getSuggestionsByDate(new java.sql.Date(date.getTime()), 500L).get(0);
		//Edit one of its fields
		suggestionToEdit.setSuggestion("Changed suggestion string");
		//Edit it in the database
		suggestionDAO.editSuggestion(suggestionToEdit);
		
		//Make sure the suggestion was edited
		SuggestionBean editedSuggestion = suggestionDAO.getSuggestionsByDate(new java.sql.Date(date.getTime()), 500L).get(0);
		assertEquals("Changed suggestion string", editedSuggestion.getSuggestion());
		assertEquals(500L, editedSuggestion.getPatientID());
		assertEquals(9900000012L, editedSuggestion.getHcpID());
		assertEquals("true", editedSuggestion.getIsNew());
	}
}
