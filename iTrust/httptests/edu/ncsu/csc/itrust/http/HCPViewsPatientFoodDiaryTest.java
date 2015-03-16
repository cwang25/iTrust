package edu.ncsu.csc.itrust.http;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.meterware.httpunit.Button;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class HCPViewsPatientFoodDiaryTest extends iTrustHTTPTest {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		//gen.standardData();
		gen.uc69();
		gen.hcp0();
	}
	
	/*
	 * Test ID: HCPViewsPatientFoodDiary
	 * UC: UC69 
	 */
	public void testHCPViewsPatientFoodDiary() throws Exception {
		//login
		gen.transactionLog();
		WebConversation wc = login("9900000012","pw");//log in as Nutritionist.
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9900000012L, 0L, "");
		//Click view Food Diary.
		wr = wr.getLinkWith("Patient Food Diary").click();
		//WebForm patientForm = wr.getForms()[0];
		wr.getForms()[1].setParameter("FIRST_NAME", "Aaron");
		wr.getForms()[1].setParameter("LAST_NAME", "Hotchner");
		wr.getForms()[1].getButtons()[0].click();
		wr = wc.getCurrentPage();
		//Because of testing restriction, will need to click on [2] form button in order to get the correct page.
		wr.getForms()[2].getButtons()[0].click();
		wr = wc.getCurrentPage();
		assertEquals(ADDRESS + "auth/hcp-uap/viewPatientFoodDiary.jsp", wr.getURL().toString());
		wr = wr.getLinkWith("Logout").click();
		assertEquals(ADDRESS + "auth/forwardUser.jsp", wr.getURL().toString());
		
		wc = login("500", "pw");
		wr = wc.getCurrentPage();
		String s = wr.getText();

		assertTrue(s.contains("Spencer Reid</a> viewed your food diaries"));

	}
	/*
	 * Test ID: HCPViewsPatientFoodDiaryByMID
	 * UC: UC69
	 */
	public void testHCPViewsPatientFoodDiaryByMID() throws Exception {
		//login
		gen.transactionLog();
		WebConversation wc = login("9900000012","pw");//log in as Nutritionist.
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9900000012L, 0L, "");
		//Click view Food Diary.
		wr = wr.getLinkWith("Patient Food Diary").click();
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "500");
		patientForm.getButtons()[1].click();
		wr = wc.getCurrentPage();
		assertEquals(ADDRESS + "auth/hcp-uap/viewPatientFoodDiary.jsp", wr.getURL().toString());
		wr = wr.getLinkWith("Logout").click();
		assertEquals(ADDRESS + "auth/forwardUser.jsp", wr.getURL().toString());
		
		wc = login("500", "pw");
		wr = wc.getCurrentPage();
		String s = wr.getText();

		assertTrue(s.contains("Spencer Reid</a> viewed your food diaries"));

	}
	/*
	 * Test ID: WrongHCPViewsPatientFoodDiary
	 * UC: UC69
	 */
	public void testWrongHCPViewsPatientFoodDiary() throws Exception {
		//login
		gen.transactionLog();
		WebConversation wc = login("9000000000","pw");//log in as Kelly.
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		//Click view Food Diary.
		wr = wr.getLinkWith("Patient Food Diary").click();
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "500");
		patientForm.getButtons()[1].click();
		wr = wc.getCurrentPage();
		assertEquals(ADDRESS + "auth/hcp-uap/viewPatientFoodDiary.jsp", wr.getURL().toString());
		assertTrue(wr.getText().contains("This function is only available to Nutritionist specialty HCP"));

	}

	/*
	 * Test ID: HCPViewsPatientFoodDiaryWithNoRecord 
	 * UC: UC69
	 */
	public void testHCPViewsPatientFoodDiaryWithNoRecord() throws Exception {
		// login
		gen.transactionLog();
		WebConversation wc = login("9900000012", "pw");// log in as Nutritionist
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9900000012L, 0L, "");
		// Click view Food Diary.
		wr = wr.getLinkWith("Patient Food Diary").click();
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID",
				"503");
		patientForm.getButtons()[1].click();
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("The patient has no Food diary"));
	}
}
