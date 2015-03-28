package edu.ncsu.csc.itrust.http;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.gargoylesoftware.htmlunit.ConfirmHandler;
import com.gargoylesoftware.htmlunit.Page;
import com.meterware.httpunit.Button;
import com.meterware.httpunit.DialogResponder;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class EditFoodDiaryEntryTest extends iTrustHTTPTest {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.uc70();
	}

	/**
	 * Test ID: EditFoodDiaryEntryWithValidValues 
	 * UC: UC70
	 */
	public void testEditFoodDiaryEntryWithValidValues() throws Exception {
		// login
		gen.transactionLog();
		WebConversation wc = login("701", "pw");// log in as Derek Margan.
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 701L, 0L, "");
		// Click view Food Diary.
		wr = wr.getLinkWith("My Food Diary").click();
		Button btn = (Button) wr.getElementsWithName("editBtn")[0];
		btn.click();
		wr = wc.getCurrentPage();
		wr.getForms()[0].setParameter("numberOfServings", "3");
		wr.getForms()[0].setParameter("caloriesPerServing", "1327");
		wr.getForms()[0].setParameter("gramsOfFat", "62.5");
		wr.getForms()[0].setParameter("milligramsOfSodium", "687");
		wr.getForms()[0].setParameter("gramsOfCarbs", "176.4");
		wr.getForms()[0].setParameter("gramsOfSugar", "112.4");
		wr.getForms()[0].setParameter("gramsOfProtein", "15.6");
		wr.getForms()[0].submit();
		wr = wc.getCurrentPage();

		// Quick check if the record is editted and saved and showed in the
		// list.
		// Check if those new values are in the table or not.
		assertTrue(wr.getText().contains("1327"));
		assertTrue(wr.getText().contains("687"));
	}

	/**
	 * Test ID: EditFoodDiaryEntryWithInvalidValues 
	 * UC: UC70
	 */
	public void testEditFoodDiaryEntryWithInvalidValues() throws Exception {
		// login
		gen.transactionLog();
		WebConversation wc = login("702", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 702L, 0L, "");
		// Click view Food Diary.
		wr = wr.getLinkWith("My Food Diary").click();
		Button btn = (Button) wr.getElementsWithName("editBtn")[0];
		btn.click();
		wr = wc.getCurrentPage();
		wr.getForms()[0].setParameter("numberOfServings", "-17");
		wr.getForms()[0].submit();
		wr = wc.getCurrentPage();
		// Check if error message displayed
		assertTrue(wr.getText().contains(
				"Number of servings has to be a positive number"));
		// Check if the record remains the same.
		assertTrue(wr.getText().contains("53"));
		assertTrue(wr.getText().contains("7420"));
	}

	/**
	 * Test ID: DeleteFoodDiaryEntry 
	 * UC: UC70
	 */
	public void testDeleteFoodDiaryEntry() throws Exception {
		// login
		gen.transactionLog();
		WebConversation wc = login("703", "pw");// log in as Jeannifer Jareau.
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 703L, 0L, "");
		// Click view Food Diary.
		wr = wr.getLinkWith("My Food Diary").click();
		wc.setDialogResponder(new DialogResponder() {
			@Override
			public boolean getConfirmation(String arg0) {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public String getUserResponse(String arg0, String arg1) {
				// TODO Auto-generated method stub
				return null;
			}
		});
		Button btn = (Button) wr.getElementsWithName("deleteBtn")[0];
		btn.click();
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("Your Food Diary has been deleted"));
		assertTrue(wr.getText().contains("View My Food Diary"));
	}

	/**
	 * Test ID: DeleteAllFoodDiaryEntries 
	 * UC: UC70
	 */
	public void testDeleteAllFoodDiaryEntries() throws Exception {
		// login
		gen.transactionLog();
		WebConversation wc = login("703", "pw");// log in as Jeannifer Jareau.
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 703L, 0L, "");
		// Click view Food Diary.
		wr = wr.getLinkWith("My Food Diary").click();
		wc.setDialogResponder(new DialogResponder() {
			@Override
			public boolean getConfirmation(String arg0) {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public String getUserResponse(String arg0, String arg1) {
				// TODO Auto-generated method stub
				return null;
			}
		});
		int totalRecords = wr.getElementsWithName("deleteBtn").length;
		for(int i = 0 ; i < totalRecords ; i++){
			Button btn = (Button) wr.getElementsWithName("deleteBtn")[0];
			btn.click();
			wr = wc.getCurrentPage();
			assertTrue(wr.getText().contains("Your Food Diary has been deleted"));
			assertTrue(wr.getText().contains("View My Food Diary"));
		}
		assertTrue(wr.getText().contains("You have no Food diary record"));
		assertTrue(wr.getText().contains("View My Food Diary"));
	}
	
	/**
	 * Test ID: DeleteFoodDiaryEntryNotConfirm 
	 * UC: UC70
	 */
	public void testDeleteFoodDiaryEntryNotConfirm() throws Exception {
		// login
		gen.transactionLog();
		WebConversation wc = login("703", "pw");// log in as Jeannifer Jareau.
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 703L, 0L, "");
		// Click view Food Diary.
		wr = wr.getLinkWith("My Food Diary").click();
		wc.setDialogResponder(new DialogResponder() {
			@Override
			public boolean getConfirmation(String arg0) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public String getUserResponse(String arg0, String arg1) {
				// TODO Auto-generated method stub
				return null;
			}
		});
		int beforeDeleteBtnNum =  wr.getElementsWithName("deleteBtn").length;
		Button btn = (Button) wr.getElementsWithName("deleteBtn")[0];
		btn.click();
		wr = wc.getCurrentPage();
		// Check if error message displayed
		assertFalse(wr.getText().contains("Your Food Diary has been deleted"));
		assertTrue(wr.getElementsWithName("deleteBtn").length == beforeDeleteBtnNum);
	}
	
	/**
	 * Test ID: UndoDeleteFoodDiaryEntry 
	 * UC: UC70
	 */
	public void testUndoDeleteFoodDiaryEntry() throws Exception {
		// login
		gen.transactionLog();
		WebConversation wc = login("703", "pw");// log in as Jeannifer Jareau.
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 703L, 0L, "");
		// Click view Food Diary.
		wr = wr.getLinkWith("My Food Diary").click();
		wc.setDialogResponder(new DialogResponder() {
			@Override
			public boolean getConfirmation(String arg0) {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public String getUserResponse(String arg0, String arg1) {
				// TODO Auto-generated method stub
				return null;
			}
		});
		int tableRowNum = wr.getTableWithID("fTable").getRowCount();
		Button btn = (Button) wr.getElementsWithName("deleteBtn")[0];
		btn.click();
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("Your Food Diary has been deleted"));
		assertTrue(wr.getText().contains("View My Food Diary"));
		//make sure breakfast has been deleted
		int deletedRowsNum  = wr.getTableWithID("fTable").getRowCount();
		//make sure the table rows did get reduced due to the deletion.
		assertTrue(tableRowNum > deletedRowsNum);
		Button undoBtn = (Button) wr.getElementsWithName("undoBtn")[0];
		undoBtn.click();
		wr = wc.getCurrentPage();
		int restoredRowNum = wr.getTableWithID("fTable").getRowCount();
		//make sure if the Breakfast record is restored back.

		assertTrue(restoredRowNum == tableRowNum);
	}
}
