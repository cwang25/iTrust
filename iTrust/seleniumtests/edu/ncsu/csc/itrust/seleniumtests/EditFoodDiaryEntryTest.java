package edu.ncsu.csc.itrust.seleniumtests;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.openqa.selenium.By;

import com.gargoylesoftware.htmlunit.ConfirmHandler;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.meterware.httpunit.Button;
import com.meterware.httpunit.DialogResponder;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

import org.openqa.selenium.Alert;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class EditFoodDiaryEntryTest extends iTrustSeleniumTest {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.uc70();
	}

	/**
	 * Test ID: EditFoodDiaryEntryWithValidValues UC: UC70
	 */
	public void testEditFoodDiaryEntryWithValidValues() throws Exception {
		// login
		gen.transactionLog();
		login("701", "pw");// log in as Derek Margan
		driver.findElement(
				By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("My Food Diary")).click();
		driver.findElement(By.name("editBtn")).click();
		// driver.setJavascriptEnabled(false);
		// driver.findElement(By.id("saveBtn")).click();
		// System.out.println(driver.getPageSource());
		driver.findElement(By.id("numberOfServings")).clear();
		driver.findElement(By.id("numberOfServings")).sendKeys("3");
		driver.findElement(By.id("caloriesPerServing")).clear();
		driver.findElement(By.id("caloriesPerServing")).sendKeys("1327");
		driver.findElement(By.id("gramsOfFat")).clear();
		driver.findElement(By.id("gramsOfFat")).sendKeys("62.5");
		driver.findElement(By.id("milligramsOfSodium")).clear();
		driver.findElement(By.id("milligramsOfSodium")).sendKeys("687");
		driver.findElement(By.id("gramsOfCarbs")).clear();
		driver.findElement(By.id("gramsOfCarbs")).sendKeys("176.4");
		driver.findElement(By.id("gramsOfSugar")).clear();
		driver.findElement(By.id("gramsOfSugar")).sendKeys("112.4");
		driver.findElement(By.id("gramsOfFiber")).clear();
		driver.findElement(By.id("gramsOfFiber")).sendKeys("15.6");
		driver.findElement(By.id("gramsOfProtein")).clear();
		driver.findElement(By.id("gramsOfProtein")).sendKeys("15.6");
		driver.findElement(By.id("saveBtn")).click();
		// Quick check if the record is editted and saved and showed in the
		// list.
		// Check if those new values are in the table or not.
		assertTrue(driver.getPageSource().contains("1327"));
		assertTrue(driver.getPageSource().contains(
				"Your Food Diary has been updated"));
	}

	/**
	 * Test ID: EditFoodDiaryEntryWithInvalidValues UC: UC70
	 */
	public void testEditFoodDiaryEntryWithInvalidValues() throws Exception {
		// login
		// login
		gen.transactionLog();
		login("702", "pw");// log in as Derek Margan
		driver.findElement(
				By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("My Food Diary")).click();
		driver.findElement(By.name("editBtn")).click();
		// driver.setJavascriptEnabled(false);
		// driver.findElement(By.id("saveBtn")).click();
		// System.out.println(driver.getPageSource());
		driver.findElement(By.id("numberOfServings")).clear();
		driver.findElement(By.id("numberOfServings")).sendKeys("-3");
		driver.findElement(By.id("caloriesPerServing")).clear();
		driver.findElement(By.id("caloriesPerServing")).sendKeys("1327");
		driver.findElement(By.id("gramsOfFat")).clear();
		driver.findElement(By.id("gramsOfFat")).sendKeys("62.5");
		driver.findElement(By.id("milligramsOfSodium")).clear();
		driver.findElement(By.id("milligramsOfSodium")).sendKeys("687");
		driver.findElement(By.id("gramsOfCarbs")).clear();
		driver.findElement(By.id("gramsOfCarbs")).sendKeys("176.4");
		driver.findElement(By.id("gramsOfSugar")).clear();
		driver.findElement(By.id("gramsOfSugar")).sendKeys("112.4");
		driver.findElement(By.id("gramsOfFiber")).clear();
		driver.findElement(By.id("gramsOfFiber")).sendKeys("15.6");
		driver.findElement(By.id("gramsOfProtein")).clear();
		driver.findElement(By.id("gramsOfProtein")).sendKeys("15.6");
		driver.findElement(By.id("saveBtn")).click();

		// Check if error message displayed
		assertTrue(driver.getPageSource().contains(
				"Number of servings has to be a positive number"));
		// Check if the record remains the same.
		assertTrue(driver.getPageSource().contains("53"));
		assertTrue(driver.getPageSource().contains("7420"));
	}

	/**
	 * Test ID: DeleteFoodDiaryEntry UC: UC70
	 */
	public void testDeleteFoodDiaryEntry() throws Exception {
		// login
		gen.transactionLog();
		login("703", "pw");// log in as Jeannifer Jareau.
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
	    driver.findElement(By.linkText("My Food Diary")).click();
	    //According to http://stackoverflow.com/questions/8837754/webdriver-and-popups-in-htmlunitdriver
	    //Selenium HtmlUnitDriver is lack of supporting alert dialog.
	    //this is a walk around to test the website.
	    driver.executeScript("function runDeleteRecord(index){document.getElementById('operationMode').value = 'delete';document.getElementById('selectedIndex').value = ''+index;document.getElementById('saveBtn').click();}");
	    driver.findElement(By.name("deleteBtn")).click();
		assertTrue(driver.getPageSource().contains("Your Food Diary has been deleted"));
		assertTrue(driver.getPageSource().contains("View My Food Diary"));
	}

	/**
	 * Test ID: DeleteAllFoodDiaryEntries UC: UC70
	 */
	public void testDeleteAllFoodDiaryEntries() throws Exception {
		// login
		gen.transactionLog();
		login("703", "pw");// log in as Jeannifer Jareau.
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
	    driver.findElement(By.linkText("My Food Diary")).click();
	    driver.executeScript("window.confirm = function(msg) { return true; }");
	    driver.findElement(By.name("deleteBtn")).click();
	    driver.findElement(By.name("deleteBtn")).click();
		assertTrue(driver.getPageSource().contains("You have no Food diary record"));
		assertTrue(driver.getPageSource().contains("View My Food Diary"));
	}

	/**
	 * Test ID: DeleteFoodDiaryEntryNotConfirm UC: UC70
	 */
	public void testDeleteFoodDiaryEntryNotConfirm() throws Exception {
		// login
		// login
		gen.transactionLog();
		login("703", "pw");// log in as Jeannifer Jareau.
		driver.findElement(
				By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("My Food Diary")).click();
		// According to
		// http://stackoverflow.com/questions/8837754/webdriver-and-popups-in-htmlunitdriver
		// Selenium HtmlUnitDriver is lack of supporting alert dialog.
		// this is a walk around to test the website.
		driver.executeScript("window.confirm = function(msg) { return false; }");
		driver.findElement(By.name("deleteBtn")).click();
		// Check if error message displayed
		//System.out.println(driver.getPageSource());
		assertFalse(driver.getPageSource().contains("Your Food Diary has been deleted"));
	}

	/**
	 * Test ID: UndoDeleteFoodDiaryEntry UC: UC70
	 */
	public void testUndoDeleteFoodDiaryEntry() throws Exception {
		// login
		gen.transactionLog();
		// login
		gen.transactionLog();
		login("703", "pw");// log in as Jeannifer Jareau.
		driver.findElement(
				By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("My Food Diary")).click();
		TableElement table1 = new TableElement(driver.findElement(By.id("foodDiaryTable")));
		int tableRowNum = table1.getRowSize();
		// According to
		// http://stackoverflow.com/questions/8837754/webdriver-and-popups-in-htmlunitdriver
		// Selenium HtmlUnitDriver is lack of supporting alert dialog.
		// this is a walk around to test the website.
		driver.executeScript("function runDeleteRecord(index){document.getElementById('operationMode').value = 'delete';document.getElementById('selectedIndex').value = ''+index;document.getElementById('saveBtn').click();}");
		driver.findElement(By.name("deleteBtn")).click();
		assertTrue(driver.getPageSource().contains(
				"Your Food Diary has been deleted"));
		assertTrue(driver.getPageSource().contains("Vi"
				+ "ew My Food Diary"));
		TableElement table2 = new TableElement(driver.findElement(By.id("foodDiaryTable")));
		int deletedRowsNum = table2.getRowSize();
		assertTrue(tableRowNum > deletedRowsNum);
		driver.findElement(By.name("undoBtn")).click();
		TableElement table3 = new TableElement (driver.findElement(By.id("foodDiaryTable")));
		int restoredRowNum = table3.getRowSize();
		// make sure if the Breakfast record is restored back.

		assertTrue(restoredRowNum == tableRowNum);
	}
}
