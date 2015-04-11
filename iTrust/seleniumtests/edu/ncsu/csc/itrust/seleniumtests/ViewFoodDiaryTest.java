package edu.ncsu.csc.itrust.seleniumteststests;

import static org.junit.Assert.*;
import hirondelle.date4j.DateTime;

import java.util.List;
import java.util.TimeZone;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class ViewFoodDiaryTest extends iTrustSeleniumTest {

	@Override
	public void setUp() throws Exception {
		super.setUp();
		driver.setJavascriptEnabled(true);
	}

	@Test
	public void testViewFoodDiaryHCPSuccess() throws Exception {
		gen.hcp11(); //HCP Spencer Reid
		gen.patient32(); //Aaron Hotchner

		login("9000000014", "pw", "HCP");

		driver.setJavascriptEnabled(true);

		driver.findElementByLinkText("View Patient Food Diaries").click();

		// transition to patient search
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());

		// Search for "Aaron Hotchner" (id 32)
		selectPatientFromSearch("32");

		assertTitleEquals("iTrust - View Food Diary");

		driver.findElementById("filterShowAll").click();

		assertTitleEquals("iTrust - View Food Diary");
		assertTrue(driver.getPageSource().contains("Aaron Hotchner"));

		WebElement table = driver.findElement(By.className("fTable"));
		assertTrue(table.getText().contains("Cheese and Bean Dip"));
		assertTrue(table.getText().contains("Oreos"));
		assertTrue(table.getText().contains("05/21/2013"));
		assertTrue(table.getText().contains("04/13/2014"));
	}

	@Test
	public void testViewFoodDiaryHCPNonSpecialist() throws Exception {
		gen.hcp0(); //Kelly Doctor

		login("9000000000", "pw", "HCP");

		driver.findElementByLinkText("View Patient Food Diaries").click();

		assertTrue(driver.findElementByTagName("body").getText().contains("Only a nutrition specialist can view food diaries."));
	}

	@Test
	public void testPatientViewLabel() throws Exception {
		gen.patient33();

		login("33", "pw", "Patient");
		driver.findElementByLinkText("My Food Diary").click();
		driver.findElementById("filterShowAll").click();

		WebElement table = driver.findElement(By.id("diaryTable"));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		assertEquals(4, rows.size());
		WebElement row = rows.get(1);
		assertTrue(row.getText().contains("Chocolate Shake"));
		assertTrue(row.getText().contains("Lunch"));
		row = rows.get(2);
		assertTrue(row.getText().contains("No Label"));
	}

	@Test
	public void testPatientViewCommentThatDoesNotExist() throws Exception {
		gen.patient33();

		login("33", "pw", "Patient");
		driver.findElementByLinkText("My Food Diary").click();
		driver.findElementById("filterShowAll").click();

		WebElement table = driver.findElement(By.id("diaryTable"));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		assertEquals(4, rows.size());
		WebElement row = rows.get(1);
		assertTrue(row.getText().contains("Chocolate Shake"));
		assertTrue(row.getText().contains("Lunch"));
		row = rows.get(2);
		assertTrue(row.getText().contains("No comment."));
	}

	@Test
	public void testPatientViewCommentThatDoesExist() throws Exception {
		gen.patient32();

		login("32", "pw", "Patient");
		driver.findElementByLinkText("My Food Diary").click();
		driver.findElementById("filterShowAll").click();

		WebElement table = driver.findElement(By.id("diaryTable"));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		assertEquals(7, rows.size());
		WebElement row = rows.get(1);
		assertTrue(row.getText().contains("Oreos"));
		assertTrue(row.getText().contains("Snack"));
		row = rows.get(4);
		assertTrue(row.getText().contains("Cheese and Bean Dip"));
		assertTrue(row.getText().contains("Breakfast"));
		row = rows.get(2);
		assertTrue(row.getText().contains("No comment."));
		row = rows.get(5);
		assertTrue(row.getText().contains("Good job!"));
	}

	@Test
	public void testViewPatientLabelThatDoesNotExist() throws Exception {
		gen.hcp11();
		gen.patient32();

		login("9000000014", "pw", "HCP");
		driver.findElementByLinkText("View Patient Food Diaries").click();
		selectPatientFromSearch("32");
		driver.findElementById("filterShowAll").click();


		WebElement table = driver.findElement(By.id("diaryTable"));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		assertEquals(7, rows.size());
		WebElement row = rows.get(1);
		assertTrue(row.getText().contains("Oreos"));
		assertTrue(row.getText().contains("Snack"));
		row = rows.get(4);
		assertTrue(row.getText().contains("Cheese and Bean Dip"));
		assertTrue(row.getText().contains("Breakfast"));
		row = rows.get(2);
		assertTrue(row.getText().contains("No Label"));
	}

	@Test
	public void testViewPatientLabelThatDoesExist() throws Exception {
		gen.hcp11();
		gen.patient32();

		login("9000000014", "pw", "HCP");
		driver.findElementByLinkText("View Patient Food Diaries").click();
		selectPatientFromSearch("32");
		driver.findElementById("filterShowAll").click();


		WebElement table = driver.findElement(By.id("diaryTable"));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		assertEquals(7, rows.size());
		WebElement row = rows.get(1);
		assertTrue(row.getText().contains("Oreos"));
		assertTrue(row.getText().contains("Snack"));
		row = rows.get(4);
		assertTrue(row.getText().contains("Cheese and Bean Dip"));
		assertTrue(row.getText().contains("Breakfast"));
		row = rows.get(2);
		assertTrue(row.getText().contains("No Label"));
		row = rows.get(5);
		assertTrue(row.getText().contains("Atkin's Diet"));
	}

	@Test
	public void testViewPatientCommentThatDoesNotExist() throws Exception {
		gen.hcp11();
		gen.patient32();

		login("9000000014", "pw", "HCP");
		driver.findElementByLinkText("View Patient Food Diaries").click();
		assertTitleEquals("iTrust - Please Select a Patient");
		selectPatientFromSearch("32");
		driver.findElementById("filterShowAll").click();

		WebElement table = driver.findElement(By.id("diaryTable"));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		assertEquals(7, rows.size());
		WebElement row = rows.get(1);
		assertTrue(row.getText().contains("Oreos"));
		assertTrue(row.getText().contains("Snack"));
		row = rows.get(4);
		assertTrue(row.getText().contains("Cheese and Bean Dip"));
		assertTrue(row.getText().contains("Breakfast"));
		row = rows.get(2);
		assertTrue(row.getText().contains("No comment."));
	}

	@Test
	public void testPatientViewSpecificDates() throws Exception {
		gen.patient32();

		login("32", "pw", "Patient");
		driver.findElementByLinkText("My Food Diary").click();

		driver.findElementById("filterStart").clear();
		driver.findElementById("filterEnd").clear();

		driver.findElementById("filterStart").sendKeys("04/10/2014");
		driver.findElementById("filterEnd").sendKeys("04/15/2014");
		driver.findElementById("filterSubmit").click();

		WebElement table = driver.findElement(By.id("diaryTable"));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		assertEquals(4, rows.size());
		assertTrue(rows.get(1).getText().contains("04/13/2014"));
		assertFalse(table.getText().contains("05/21/2013"));
	}

	@Test
	public void testPatientFilterFutureDate() throws Exception {
		gen.patient32();

		login("32", "pw", "Patient");
		driver.findElementByLinkText("My Food Diary").click();

		driver.findElementById("filterStart").clear();
		driver.findElementById("filterEnd").clear();

		driver.findElementById("filterStart").sendKeys("04/10/2014");
		driver.findElementById("filterEnd").sendKeys("12/12/2099");
		driver.findElementById("filterSubmit").click();

		assertEquals(DateTime.today(TimeZone.getDefault()).format("MM/DD/YYYY"), driver.findElementById("filterEnd").getAttribute("value"));

		WebElement table = driver.findElement(By.id("diaryTable"));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		assertEquals(4, rows.size());
		assertTrue(rows.get(1).getText().contains("04/13/2014"));
		assertFalse(table.getText().contains("05/21/2013"));
	}

	@Test
	public void testPatientFilterDefaultRange() throws Exception {
		gen.patient32();

		login("32", "pw", "Patient");
		driver.findElementByLinkText("My Food Diary").click();

		assertEquals(DateTime.today(TimeZone.getDefault()).minusDays(5).format("MM/DD/YYYY"), driver.findElementById("filterStart").getAttribute("value"));
		assertEquals(DateTime.today(TimeZone.getDefault()).format("MM/DD/YYYY"), driver.findElementById("filterEnd").getAttribute("value"));

		assertTrue(driver.findElementByClassName("well-lg").getText().contains("There are no entries in your food diary for the given date range."));
	}

	@Test
	public void testHCPFilterFoodDiary() throws Exception {
		gen.hcp11();
		gen.patient32();

		login("9000000014", "pw", "HCP");
		driver.findElementByLinkText("View Patient Food Diaries").click();

		selectPatientFromSearch("32");

		driver.findElementById("filterStart").clear();
		driver.findElementById("filterEnd").clear();

		driver.findElementById("filterStart").sendKeys("05/01/2013");
		driver.findElementById("filterEnd").sendKeys("05/31/2013");
		driver.findElementById("filterSubmit").click();

		WebElement table = driver.findElement(By.id("diaryTable"));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		assertEquals(4, rows.size());
		assertTrue(rows.get(1).getText().contains("05/21/2013"));
		assertFalse(table.getText().contains("04/13/2014"));
	}

	@Test
	public void testFilterFoodDiaryPersistance() throws Exception {
		gen.hcp11();
		gen.patient32();

		login("9000000014", "pw", "HCP");
		driver.findElementByLinkText("View Patient Food Diaries").click();

		selectPatientFromSearch("32");

		driver.findElementById("filterStart").clear();
		driver.findElementById("filterEnd").clear();

		driver.findElementById("filterStart").sendKeys("05/01/2013");
		driver.findElementById("filterEnd").sendKeys("05/31/2013");
		driver.findElementById("filterSubmit").click();

		expandNavHeading(NavigationHeading.Info);
		driver.findElementByLinkText("Patient Information").click();

		expandNavHeading(NavigationHeading.Nutrition);
		driver.findElementByLinkText("View Patient Food Diaries").click();

		assertTitleEquals("iTrust - View Food Diary");

		assertEquals("05/01/2013", driver.findElementById("filterStart").getAttribute("value"));
		assertEquals("05/31/2013", driver.findElementById("filterEnd").getAttribute("value"));

		WebElement table = driver.findElement(By.id("diaryTable"));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		assertEquals(4, rows.size());
		assertTrue(rows.get(1).getText().contains("05/21/2013"));
		assertFalse(table.getText().contains("04/13/2014"));
	}

	@Test
	public void testHCPFilterFoodDiaryMinimumDate() throws Exception {
		gen.hcp11();
		gen.patient32();

		login("9000000014", "pw", "HCP");
		driver.findElementByLinkText("View Patient Food Diaries").click();

		selectPatientFromSearch("32");

		WebElement startField = driver.findElementById("filterStart");
		new Actions(driver).click(startField).sendKeys("01/01/0001" + Keys.ENTER).click(driver.findElementById("filterEnd")).perform();

		assertEquals(DateTime.today(TimeZone.getDefault()).format("MM/DD/YYYY"), driver.findElementById("filterStart").getAttribute("value"));
	}

}
