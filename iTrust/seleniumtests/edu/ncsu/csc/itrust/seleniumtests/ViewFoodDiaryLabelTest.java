package edu.ncsu.csc.itrust.seleniumtests;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.action.AddFoodDiaryAction;
import edu.ncsu.csc.itrust.beans.FoodDiaryBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.enums.MealType;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class ViewFoodDiaryLabelTest extends iTrustSeleniumTest {

	
	private WebDriver driver;
	private Select hos;

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	
	private AddFoodDiaryAction action;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		disableLogger();
		gen.clearAllTables();
		gen.standardData();
		driver = new HtmlUnitDriver();
		//go to iTrust home page
		driver.get("http://localhost:8080/iTrust/");
		
		FoodDiaryBean fdbean = new FoodDiaryBean();
		
		fdbean.setCaloriesServing(15);
		fdbean.setDate("02/02/2015");
		fdbean.setGramsCarbs(25);
		fdbean.setGramsFat(10);
		fdbean.setGramsFiber(3);
		fdbean.setGramsProtien(6);
		fdbean.setGramsSugar(12);
		fdbean.setMeal(MealType.Breakfast);
		fdbean.setMID(1l);
		fdbean.setMiliGramSodium(100);
		fdbean.setNameOfFood("Not Poptarts");
		fdbean.setNumberOfServing(2);
		action = new AddFoodDiaryAction(factory, 1);
		action.addFoodDiary(fdbean);
	}
	
	@Test
	public void testLabelExists() throws Exception {
		//Login as patient 1
		driver.get("http://localhost:8080/iTrust/");
		driver.findElement(By.id("j_username")).sendKeys("1");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		//Navigate to food diary
		driver.findElement(By.linkText("View Food Diary")).click();
		
		assertTrue(driver.getPageSource().contains("Add/Change Label"));
			
	}
	
	@Test
	public void testValidLabel() throws Exception {
		//Login as patient 1
		driver.get("http://localhost:8080/iTrust/");
		driver.findElement(By.id("j_username")).sendKeys("1");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		//Navigate to food diary
		driver.findElement(By.linkText("View Food Diary")).click();
		
		assertTrue(driver.getPageSource().contains("Add/Change Label"));
		//Set a label
		driver.findElement(By.name("Label1")).clear();
		driver.findElement(By.name("Label1")).sendKeys("Test Label");
		driver.findElement(By.name("submit1")).click();
		//Check to see if the label is there
		
		assertTrue(driver.getPageSource().contains("Test Label"));
	}
	
	@Test
	public void testValidLabelPersistant() throws Exception {
		//Login as patient 1
		driver.get("http://localhost:8080/iTrust/");
		driver.findElement(By.id("j_username")).sendKeys("1");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		//Navigate to food diary
		driver.findElement(By.linkText("View Food Diary")).click();
		
		assertTrue(driver.getPageSource().contains("Add/Change Label"));
		//Set a label
		driver.findElement(By.name("Label1")).clear();
		driver.findElement(By.name("Label1")).sendKeys("Test Label2");
		driver.findElement(By.name("submit1")).click();
		//Check to see if the label is there
		
		assertTrue(driver.getPageSource().contains("Test Label2"));
		//Logout, then navigate back to the same page.
		driver.get("http://localhost:8080/iTrust/logout.jsp");
		
		driver.get("http://localhost:8080/iTrust/");
		driver.findElement(By.id("j_username")).sendKeys("1");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		//Navigate to food diary
		driver.findElement(By.linkText("View Food Diary")).click();
		
		assertTrue(driver.getPageSource().contains("Test Label2"));
	}
	
	@Test
	public void testInvalidLabel() throws Exception {
		//Login as patient 1
		driver.get("http://localhost:8080/iTrust/");
		driver.findElement(By.id("j_username")).sendKeys("1");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		//Navigate to food diary
		driver.findElement(By.linkText("View Food Diary")).click();
		
		assertTrue(driver.getPageSource().contains("Add/Change Label"));
		
		String invalid = "Far too much text.";
		
		for (invalid.length(); invalid.length() < 1000; invalid += invalid);
				
		driver.findElement(By.name("Label1")).clear();
		driver.findElement(By.name("Label1")).sendKeys(invalid);
		driver.findElement(By.name("submit1")).click();
		//Check to see if the label is there
		assertTrue(driver.getPageSource().contains("exception"));
		
	}
	
	@Test
	public void testLabelUpdate() throws Exception {
		//Login as patient 1
		driver.get("http://localhost:8080/iTrust/");
		driver.findElement(By.id("j_username")).sendKeys("1");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		//Navigate to food diary
		driver.findElement(By.linkText("View Food Diary")).click();
		
		assertTrue(driver.getPageSource().contains("Add/Change Label"));
		//Set a label
		driver.findElement(By.name("Label1")).clear();
		driver.findElement(By.name("Label1")).sendKeys("Test Label2");
		driver.findElement(By.name("submit1")).click();
		//Check to see if the label is there
		
		assertTrue(driver.getPageSource().contains("Test Label2"));
		//Update the label
		driver.findElement(By.name("Label1")).clear();
		driver.findElement(By.name("Label1")).sendKeys("Test Label3");
		driver.findElement(By.name("submit1")).click();

		assertTrue(driver.getPageSource().contains("Test Label3"));
		assertFalse(driver.getPageSource().contains("Test Label2"));
		//Logout, then navigate back to the same page.
		driver.get("http://localhost:8080/iTrust/logout.jsp");
		
		driver.get("http://localhost:8080/iTrust/");
		driver.findElement(By.id("j_username")).sendKeys("1");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		//Navigate to food diary
		driver.findElement(By.linkText("View Food Diary")).click();
		
		assertTrue(driver.getPageSource().contains("Test Label3"));

		driver.findElement(By.name("Label1")).clear();
		driver.findElement(By.name("Label1")).sendKeys("Test Label4");
		driver.findElement(By.name("submit1")).click();
		
		assertTrue(driver.getPageSource().contains("Test Label4"));
		assertFalse(driver.getPageSource().contains("Test Label3"));
	
	}
}
