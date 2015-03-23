package edu.ncsu.csc.itrust.http;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class AddFoodDiaryEntryTest extends iTrustHTTPTest {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.patient20();
		gen.uc68();
	}
	/*
	 * Test ID: AddFoodDiaryEntryToEmptyFoodDiary
	 * UC: UC68
	 */
	public void testAddFoodDiaryEntryToEmptyFoodDiary() throws Exception {
		// login
		gen.transactionLog();
		WebConversation wc = login("501", "pw");// log in as Derek Margan.
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 501L, 0L, "");
		// Click view Food Diary.
		wr = wr.getLinkWith("My Food Diary").click();
		wr.getForms()[0].setParameter("date", "2/4/2015");
		wr.getForms()[0].setParameter("nameOfFood", "Fruity Pebbles");
		wr.getForms()[0].setParameter("typeOfMeal", "DINNER");
		wr.getForms()[0].setParameter("numberOfServings", "7");
		wr.getForms()[0].setParameter("caloriesPerServing", "110");
		wr.getForms()[0].setParameter("gramsOfFat", "1");
		wr.getForms()[0].setParameter("milligramsOfSodium", "170");
		wr.getForms()[0].setParameter("gramsOfCarbs", "24");
		wr.getForms()[0].setParameter("gramsOfSugar", "11");
		wr.getForms()[0].setParameter("gramsOfFiber", "0");
		wr.getForms()[0].setParameter("gramsOfProtein", "1");
		wr.getForms()[0].submit();
		wr = wc.getCurrentPage();
		//Quick check if the record is saved and showed in the list.
		assertTrue(wr.getText().contains("Fruity Pebbles"));
	}
	/*
	 * Test ID: AddFoodDiaryEntryToNonEmptyFoodDiary 
	 * UC: UC68
	 */
	public void testAddFoodDiaryEntryToNonEmptyFoodDiary() throws Exception {
		// login
		gen.transactionLog();
		WebConversation wc = login("502", "pw");// log in as Jeannifer Jareau.
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 502L, 0L, "");
		// Click view Food Diary.
		wr = wr.getLinkWith("My Food Diary").click();
		//Check if there are old entries.
		assertTrue(wr.getText().contains("Hot dog"));
		assertTrue(wr.getText().contains("Mango Passionfruit Juice"));
		wr.getForms()[0].setParameter("date", "11/12/2014");
		wr.getForms()[0].setParameter("nameOfFood", "Cookie Dough Ice Cream");
		wr.getForms()[0].setParameter("typeOfMeal", "SNACK");
		wr.getForms()[0].setParameter("numberOfServings", ".5");
		wr.getForms()[0].setParameter("caloriesPerServing", "160");
		wr.getForms()[0].setParameter("gramsOfFat", "8");
		wr.getForms()[0].setParameter("milligramsOfSodium", "45");
		wr.getForms()[0].setParameter("gramsOfCarbs", "21");
		wr.getForms()[0].setParameter("gramsOfSugar", "16");
		wr.getForms()[0].setParameter("gramsOfFiber", "0");
		wr.getForms()[0].setParameter("gramsOfProtein", "2");
		wr.getForms()[0].submit();
		wr = wc.getCurrentPage();
		// Quick check if the record is saved and showed in the list.
		// Also the old entries.
		assertTrue(wr.getText().contains("Cookie Dough Ice Cream"));
		assertTrue(wr.getText().contains("Hot dog"));
		assertTrue(wr.getText().contains("Mango Passionfruit Juice"));
	}
	
	/*
	 * Test ID: PatientViewsEmptyFoodDiary
	 * UC: UC68
	 */
	public void testPatientViewsEmptyFoodDiary() throws Exception {
		// login
		gen.transactionLog();
		WebConversation wc = login("20", "pw");// log in as Koopa Bowser.
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 20L, 0L, "");
		// Click view Food Diary.
		wr = wr.getLinkWith("My Food Diary").click();
		wr = wc.getCurrentPage();
		//Verify that no food diaries are shown
		assertTrue(wr.getText().contains("You have no Food diary"));
	} 
	
	/*
	 * Test ID: PatientEntersInvalidCaloriesPerServingInput
	 * UC: UC68
	 */
	public void testPatientEntersInvalidCaloriesPerServingInput() throws Exception {
		// login
		gen.transactionLog();
		WebConversation wc = login("501", "pw");// log in as Jeannifer Jareau.
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 501L, 0L, "");
		// Click view Food Diary.
		wr = wr.getLinkWith("My Food Diary").click();
		// WebForm patientForm = wr.getForms()[0];
		wr.getForms()[0].setParameter("date", "2/8/2015");
		wr.getForms()[0].setParameter("nameOfFood", "Cheese Pizza");
		wr.getForms()[0].setParameter("typeOfMeal", "LUNCH");
		wr.getForms()[0].setParameter("numberOfServings", "4");
		wr.getForms()[0].setParameter("caloriesPerServing", "-350");
		wr.getForms()[0].setParameter("gramsOfFat", "13");
		wr.getForms()[0].setParameter("milligramsOfSodium", "540");
		wr.getForms()[0].setParameter("gramsOfCarbs", "43");
		wr.getForms()[0].setParameter("gramsOfSugar", "5");
		wr.getForms()[0].setParameter("gramsOfFiber", "4");
		wr.getForms()[0].setParameter("gramsOfProtein", "16");
		wr.getForms()[0].submit();
		wr = wc.getCurrentPage();
		// Check if error message displayed
		assertTrue(wr.getText().contains("Calories per serving has to be 0 or greater"));
	}
	
	/*
	 * Test ID: PatientEntersInvalidServingsNumber
	 * UC: UC68
	 */
	public void testPatientEntersInvalidServingsNumber() throws Exception {
		// login
		gen.transactionLog();
		WebConversation wc = login("501", "pw");// log in as Jeannifer Jareau.
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 501L, 0L, "");
		// Click view Food Diary.
		wr = wr.getLinkWith("My Food Diary").click();
		// WebForm patientForm = wr.getForms()[0];
		
		wr.getForms()[0].setParameter("date", "2/8/2015");
		wr.getForms()[0].setParameter("nameOfFood", "Cheese Pizza");
		wr.getForms()[0].setParameter("typeOfMeal", "LUNCH");
		wr.getForms()[0].setParameter("numberOfServings", "0");
		wr.getForms()[0].setParameter("caloriesPerServing", "350");
		wr.getForms()[0].setParameter("gramsOfFat", "13");
		wr.getForms()[0].setParameter("milligramsOfSodium", "540");
		wr.getForms()[0].setParameter("gramsOfCarbs", "43");
		wr.getForms()[0].setParameter("gramsOfSugar", "5");
		wr.getForms()[0].setParameter("gramsOfFiber", "4");
		wr.getForms()[0].setParameter("gramsOfProtein", "16");
		wr.getForms()[0].submit();
		wr = wc.getCurrentPage();

		// Check if error message displayed
		assertTrue(wr.getText().contains("Number of servings has to be a positive number."));
	}
	
	/*
	 * Test ID: PatientEntersAlphabeticalInputForFat
	 * UC: UC68
	 */
	public void testPatientEntersAlphabeticalInputForFat() throws Exception {
		// login
		gen.transactionLog();
		WebConversation wc = login("501", "pw");// log in as Jeannifer Jareau.
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 501L, 0L, "");
		// Click view Food Diary.
		wr = wr.getLinkWith("My Food Diary").click();

		wr.getForms()[0].setParameter("date", "2/8/2015");
		wr.getForms()[0].setParameter("nameOfFood", "Cheese Pizza");
		wr.getForms()[0].setParameter("typeOfMeal", "LUNCH");
		wr.getForms()[0].setParameter("numberOfServings", "4");
		wr.getForms()[0].setParameter("caloriesPerServing", "350");
		wr.getForms()[0].setParameter("gramsOfFat", "abc");
		wr.getForms()[0].setParameter("milligramsOfSodium", "540");
		wr.getForms()[0].setParameter("gramsOfCarbs", "43");
		wr.getForms()[0].setParameter("gramsOfSugar", "5");
		wr.getForms()[0].setParameter("gramsOfFiber", "4");
		wr.getForms()[0].setParameter("gramsOfProtein", "16");
		wr.getForms()[0].submit();
		wr = wc.getCurrentPage();
		// Check if error message displayed
		assertTrue(wr.getText().contains("Grams of fat has to be 0 or greater."));
	}
}
