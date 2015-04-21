package edu.ncsu.csc.itrust.seleniumtests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.Select;
import edu.ncsu.csc.itrust.beans.FoodDiaryLabelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.FoodDiaryLabelDAO;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

/**
 * Class that tests Food Diary Suggestion functionality
 *
 */
public class FoodDiarySuggestionsTest extends iTrustSeleniumTest {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private FoodDiaryLabelDAO  fdlDAO = factory.getFoodDiaryLabelDAO();
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
		//Login as Spencer Reid
		login("9900000012", "pw");
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("Patient Food Diary")).click();
		//Search for patient 500 Aaron Hotchner
		driver.findElement(By.id("searchBox")).clear();
		driver.findElement(By.id("searchBox")).sendKeys("500");
		//waitFor(2);
		clickOnJavascriptElement(By.xpath("//input[@value='500' and @type='button']"));
		//Click on suggestion plus button
		driver.findElement(By.id("toggle1")).click();
		//Add suggestion
		driver.findElement(By.id("suggestionText1")).clear();
		driver.findElement(By.id("suggestionText1")).sendKeys("This is a test suggestion");
		driver.findElement(By.id("addNewSuggestion1")).click();
		//Logout
		clickOnNonJavascriptElement(driver.findElement(By.id("logoutBtn")));
		
		//Login as Aaron Hotchner (500)
		driver.findElement(By.id("j_username")).click();
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("500");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		//Click on 'View' panel
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		//Click on 'My Food Diary'
		driver.findElement(By.linkText("My Food Diary")).click();
		driver.findElement(By.id("toggle1")).click();
		assertTrue(driver.findElement(By.id("tarea1")).getText().contains("This is a test suggestion"));
		driver.findElement(By.id("toggle1")).click();
		//Logout
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
		//waitFor(1);
		clickOnJavascriptElement(By.xpath("//input[@value='500' and @type='button']"));
		driver.findElement(By.id("toggle1")).click();
		driver.findElement(By.id("suggestionText1")).clear();
		driver.findElement(By.id("suggestionText1")).sendKeys("TestSuggestion1");
		driver.findElement(By.id("addNewSuggestion1")).click();
		assertTrue(pageContains("Your Suggestion has been added to the patient's Food Diary."));
		driver.findElement(By.id("toggle1")).click();
		driver.findElement(By.id("suggestionText1")).clear();
		driver.findElement(By.id("suggestionText1")).sendKeys("TestSuggestion2");
		driver.findElement(By.id("addNewSuggestion1")).click();
		assertTrue(pageContains("Your Suggestion has been added to the patient's Food Diary."));
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
		assertTrue(driver.findElement(By.id("tarea1")).getText().contains("TestSuggestion1"));
		assertTrue(driver.findElement(By.id("tarea1")).getText().contains("TestSuggestion2"));
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
		//waitFor(1);
		clickOnJavascriptElement(By.xpath("//input[@value='500' and @type='button']"));
		driver.findElement(By.id("toggle1")).click();
		driver.findElement(By.id("suggestionText1")).clear();
		driver.findElement(By.id("suggestionText1")).sendKeys("AprilSuggestion");
		driver.findElement(By.id("addNewSuggestion1")).click();
		assertTrue(pageContains("Your Suggestion has been added to the patient's Food Diary."));
		driver.findElement(By.id("toggle2")).click();
		System.out.println(driver.getPageSource());
		driver.findElement(By.id("suggestionText2")).clear();
		driver.findElement(By.id("suggestionText2")).sendKeys("MaySuggestion");
		driver.findElement(By.id("addNewSuggestion2")).click();
		assertTrue(pageContains("Your Suggestion has been added to the patient's Food Diary."));
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
		assertTrue(driver.findElement(By.id("tarea1")).getText().contains("AprilSuggestion"));
		driver.findElement(By.id("toggle2")).click();
		assertTrue(driver.findElement(By.id("tarea2")).getText().contains("MaySuggestion"));
	}
	
	/**
	 * Test editing a suggestion
	 * @throws Exception 
	 */
	@Test
	public void testEditSuggestion() throws Exception{
		//Login as Spencer Reid
		login("9900000012", "pw");
		//Click on Patient -> Patient Food Diary
		driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Patient Food Diary")).click();
	    //Enter 500 in search box
	    driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("500");
	    clickOnJavascriptElement(By.xpath("//input[@value='500' and @type='button']"));
	    //Click add suggestion plus button
	    driver.findElement(By.id("toggle1")).click();
	    //Add a new suggestion
	    driver.findElement(By.id("suggestionText1")).clear();
	    driver.findElement(By.id("suggestionText1")).sendKeys("This is a test suggestion that I will edit.");
	    driver.findElement(By.id("addNewSuggestion1")).click();
	    //Click add suggestion plus button
	    driver.findElement(By.id("toggle1")).click();
	    //Select the newly added suggestion
	    new Select(driver.findElement(By.id("savedSuggestionList1"))).selectByVisibleText("This is a test suggestion");
	    driver.findElement(By.id("tarea1")).clear();
	    driver.findElement(By.id("tarea1")).sendKeys("This is a test suggestion that I will edit. I edited it.");
	    //Click update
	    driver.findElement(By.id("updateSuggestion1")).click();
	    driver.executeScript("window.confirm = function(msg){return true;}");
	    assertTrue(pageContains("I edited it"));
	}
	
	@Test
	public void testDeleteSuggestion() throws Exception{
		//Log in as Spencer Reif
		login("9900000012", "pw");
		//Select 'View'
		driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Patient Food Diary")).click();
	    //Search for patient 500
	    driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("500");
	    clickOnJavascriptElement(By.xpath("//input[@value='500' and @type='button']"));
	    //Click on + button for adding suggestion and add suggestion
	    driver.findElement(By.id("toggle1")).click();
	    driver.findElement(By.id("suggestionText1")).clear();
	    driver.findElement(By.id("suggestionText1")).sendKeys("Add suggestion for deleting");
	    driver.findElement(By.id("addNewSuggestion1")).click();
	    //Select suggestion and delete it
	    driver.findElement(By.id("toggle1")).click();
	    new Select(driver.findElement(By.id("savedSuggestionList1"))).selectByVisibleText("Add suggestion for deleti");
	    driver.findElement(By.id("removeSuggestion1")).click();
	    JavascriptExecutor js = (JavascriptExecutor)driver;
	    js.executeScript("window.confirm = function(msg){return true;}");

	    //Selenium throws exception. So we check label has been deleted like this:
	    List<FoodDiaryLabelBean> labels = fdlDAO.getAllFoodDiaryLabels(500L);
	    for(FoodDiaryLabelBean label : labels){
	    	assertNotEquals("Add suggestion for deleting", label.getLabel());
	    }
	}
}