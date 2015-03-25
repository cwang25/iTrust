package edu.ncsu.csc.itrust.action;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.beans.SuggestionBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

/**
 * Tests the SuggestionAction class
 *
 */
public class SuggestionActionTest {
	/** Factory*/
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	
	/** SuggestionAction for testing*/
	private SuggestionAction action;
	
	/**
	 * Sets up fields needed for testing
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		//Login as Nutritionist Spencer Reid
		action = new SuggestionAction(factory, 9900000012L);
		
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
	public void testGetSuggestionsByDate() throws ParseException, DBException {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date date = sdf.parse("04/13/2014");
		long patientID = 500;
		
		//Get the suggestions for this day
		List<SuggestionBean> suggestions = action.getSuggestionsByDate(new java.sql.Date(date.getTime()), patientID);
		assertEquals(2, suggestions.size());
		
		//Get and test suggestions for a different day
		date = sdf.parse("05/21/2013");
		suggestions = action.getSuggestionsByDate(new java.sql.Date(date.getTime()), patientID);
		assertEquals(2, suggestions.size());
	}
	
	/**
	 * Test editing a suggestion in the database
	 * @throws ParseException SimpleDateFormat throws this if it can't parse the date string
	 * @throws DBException if something goes wrong in the database
	 */
	@Test
	public void testEditSuggestion() throws DBException, ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy"); 
		Date date = sdf.parse("04/13/2014");
		//Get the suggestion that we plan on editing
		SuggestionBean suggestionToEdit = action.getSuggestionsByDate(new java.sql.Date(date.getTime()), 500L).get(0);
		//Edit one of its fields
		suggestionToEdit.setSuggestion("Edited");
		//Edit it in the database
		action.editSuggestion(suggestionToEdit);
		
		//Make sure the suggestion was edited
		SuggestionBean editedSuggestion = action.getSuggestionsByDate(new java.sql.Date(date.getTime()), 500L).get(0);
		assertEquals("Edited", editedSuggestion.getSuggestion());
		assertEquals(500L, editedSuggestion.getPatientID());
		assertEquals(9900000012L, editedSuggestion.getHcpID());
		assertEquals("true", editedSuggestion.getIsNew());
	}
	
	/**
	 * Test adding a new suggestion to the database
	 * @throws ParseException SimpleDateFormat throws this if it can't parse the date string
	 * @throws DBException if something goes wrong in the database
	 */
	@Test
	public void testAddSuggestion() throws ParseException, DBException {
		//Make an empty suggestion bean
		SuggestionBean newSuggestion = new SuggestionBean();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy"); 
		Date date = sdf.parse("05/21/2013");
		
		//Populate its fields
		newSuggestion.setDate(date);
		newSuggestion.setHcpID(9900000012L);
		newSuggestion.setPatientID(500L);
		newSuggestion.setSuggestion("Fifth test suggestion");
		newSuggestion.setIsNew("true");
		
		//Add it to the database
		action.addSuggestion(newSuggestion);
		
		//Test if it was successfully added
		List<SuggestionBean> suggestions = action.getSuggestionsByDate(new java.sql.Date(date.getTime()), 500L);
		assertEquals(3, suggestions.size());
		assertEquals("Third test suggestion", suggestions.get(0).getSuggestion());
		assertEquals("Fourth test suggestion", suggestions.get(1).getSuggestion());
		assertEquals("Fifth test suggestion", suggestions.get(2).getSuggestion());
	}
}
