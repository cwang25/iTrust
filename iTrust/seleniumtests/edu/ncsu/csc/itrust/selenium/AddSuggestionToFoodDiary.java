/**
 * 
 */
package edu.ncsu.csc.itrust.selenium;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

/**
 * @author nishant
 *
 */
public class AddSuggestionToFoodDiary extends iTrustSeleniumTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		super.setUp();
		gen.standardData();
	}

	/**
	 * Test normal viewing of suggestions with on errors.
	 * @throws Exception
	 */
	@Test
	public void testViewSuggestion() throws Exception {
		login("9900000012", "pw");
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("Patient Food Diary")).click();
		driver.findElement(By.id("searchBox")).clear();
		driver.findElement(By.id("searchBox")).sendKeys("500");
		waitFor(2);
		driver.findElement(By.xpath("//input[@value='500' and @type='button']")).click();
		driver.findElement(By.id("toggle1")).click();
		driver.findElement(By.id("suggestionText")).clear();
		driver.findElement(By.id("suggestionText")).sendKeys("This is a test suggestion");
		driver.findElement(By.id("addNewSuggestion")).click();
		clickOnNonJavascriptElement(driver.findElement(By.id("logoutBtn")));
		driver.findElement(By.id("j_username")).click();
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("500");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("My Food Diary")).click();
		driver.findElement(By.id("toggle1")).click();
		driver.findElement(By.id("toggle1")).click();
		driver.findElement(By.id("toggle1")).click();
		driver.findElement(By.id("toggle1")).click();
		clickOnNonJavascriptElement(driver.findElement(By.id("logoutBtn")));
	}

	/**
	 * Test adding multiple suggestions to same date
	 * @throws Exception
	 */
	@Test
	public void testAddingMultipleSuggestions() throws Exception {
		login("9900000012", "pw");
		driver.findElement(By.cssSelector("div.panel-heading")).click();
		driver.findElement(By.linkText("Patient Food Diary")).click();
		driver.findElement(By.id("searchBox")).clear();
		driver.findElement(By.id("searchBox")).sendKeys("500");
		waitFor(1);
		driver.findElement(By.xpath("//input[@value='500' and @type='button']")).click();
		driver.findElement(By.id("toggle1")).click();
		driver.findElement(By.id("suggestionText")).clear();
		driver.findElement(By.id("suggestionText")).sendKeys("TestSuggestion1");
		driver.findElement(By.id("addNewSuggestion")).click();
		assertTrue(contain("Your Suggestion has been added to the patient's Food Diary."));
		driver.findElement(By.id("toggle1")).click();
		driver.findElement(By.id("suggestionText")).clear();
		driver.findElement(By.id("suggestionText")).sendKeys("TestSuggestion2");
		driver.findElement(By.id("addNewSuggestion")).click();
		assertTrue(contain("Your Suggestion has been added to the patient's Food Diary."));
		logout();
		driver.findElement(By.id("j_username")).click();
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("500");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("My Food Diary")).click();
		driver.findElement(By.id("toggle1")).click();
		assertTrue(driver.findElement(By.id("tarea1")).getValue().contains("TestSuggestion1"));
		assertTrue(driver.findElement(By.id("tarea1")).getValue().contains("TestSuggestion2"));
	}

	/**
	 * Test adding a suggestion to multiple dates.
	 * @throws Exception
	 */
	@Test
	public void testAddSuggestionsToMultipleDates() throws Exception {
		login("9900000012", "pw");
		driver.findElement(By.cssSelector("div.panel-heading")).click();
		driver.findElement(By.linkText("Patient Food Diary")).click();
		driver.findElement(By.id("searchBox")).clear();
		driver.findElement(By.id("searchBox")).sendKeys("500");
		waitFor(1);
		driver.findElement(By.xpath("//input[@value='500' and @type='button']")).click();
		driver.findElement(By.id("toggle1")).click();
		driver.findElement(By.id("suggestionText")).clear();
		driver.findElement(By.id("suggestionText")).sendKeys("AprilSuggestion");
		driver.findElement(By.id("addNewSuggestion")).click();
		assertTrue(contain("Your Suggestion has been added to the patient's Food Diary."));
		driver.findElement(By.id("toggle2")).click();
		driver.findElement(By.id("suggestionText2")).clear();
		driver.findElement(By.id("suggestionText2")).sendKeys("MaySuggestion");
		driver.findElement(By.name("submitMe")).click();
		assertTrue(contain("Your Suggestion has been added to the patient's Food Diary."));
		logout();
		driver.findElement(By.id("j_username")).click();
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("500");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("My Food Diary")).click();
		driver.findElement(By.id("toggle1")).click();
		assertTrue(driver.findElement(By.id("tarea1")).getValue().contains("AprilSuggestion"));
		driver.findElement(By.id("toggle2")).click();
		assertTrue(driver.findElement(By.id("tarea2")).getValue().contains("MaySuggestion"));
	}
}