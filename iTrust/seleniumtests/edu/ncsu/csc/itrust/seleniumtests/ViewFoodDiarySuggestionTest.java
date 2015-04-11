package edu.ncsu.csc.itrust.seleniumtests;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.action.AddFoodDiaryAction;
import edu.ncsu.csc.itrust.action.AddSuggestionsForFoodDiaryAction;
import edu.ncsu.csc.itrust.beans.FoodDiaryBean;
import edu.ncsu.csc.itrust.beans.FoodDiarySuggestion;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.FoodDiaryDAO;
import edu.ncsu.csc.itrust.enums.MealType;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class ViewFoodDiarySuggestionTest extends iTrustSeleniumTest {

	private WebDriver driver;
	private Select hos;

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	
	private AddSuggestionsForFoodDiaryAction action;
	
	private AddFoodDiaryAction initAction;
	
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
		fdbean.setDate("04/04/2015");
		fdbean.setGramsCarbs(25);
		fdbean.setGramsFat(10);
		fdbean.setGramsFiber(3);
		fdbean.setGramsProtien(6);
		fdbean.setGramsSugar(12);
		fdbean.setMeal(MealType.Breakfast);
		fdbean.setMID(5l);
		fdbean.setMiliGramSodium(100);
		fdbean.setNameOfFood("Not Poptarts");
		fdbean.setNumberOfServing(2);
		initAction = new AddFoodDiaryAction(factory, 5);
		initAction.addFoodDiary(fdbean);
		
		FoodDiarySuggestion suggestion = new FoodDiarySuggestion();
		suggestion.setDate("04/04/2015");
		suggestion.setMID(5l);
		suggestion.setSuggestion("Try increasing positive ion intake.");
		
		FoodDiaryDAO foodDiaryDAO = this.factory.getFoodDiaryDAO();
		
		foodDiaryDAO.addSugggestion(suggestion);
		
		}
	
	@Test
	public void testSuggestionExists() throws Exception {
		//Login as patient 5
		driver.get("http://localhost:8080/iTrust/");
		driver.findElement(By.id("j_username")).sendKeys("5");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		//Navigate to food diary
		driver.findElement(By.linkText("View Food Diary")).click();
		
		assertTrue(driver.getPageSource().contains("Try increasing positive ion intake."));
	}
	
	@Test
	public void testNutritionistSuggestionSubmitVisible() throws Exception {
		//Login as 1340, nutritionist
		driver.get("http://localhost:8080/iTrust/");
		driver.findElement(By.id("j_username")).sendKeys("1340");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		//Navigate to food diary
		driver.findElement(By.linkText("View Patient Food Diary")).click();
		
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("5");
		driver.findElement(By.xpath("//input[@value='5']")).submit();

		assertEquals("iTrust - Patient Food Diary",driver.getTitle());
		
		assertTrue(driver.getPageSource().contains("Add Suggestion"));
		
	}

	@Test
	public void testNutritionistSuggestionSubmit() throws Exception {
		//Login as 1340, nutritionist
		driver.get("http://localhost:8080/iTrust/");
		driver.findElement(By.id("j_username")).sendKeys("1340");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		//Navigate to food diary
		driver.findElement(By.linkText("View Patient Food Diary")).click();
		
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("5");
		driver.findElement(By.xpath("//input[@value='5']")).submit();

		assertEquals("iTrust - Patient Food Diary",driver.getTitle());
		
		assertTrue(driver.getPageSource().contains("Add Suggestion"));
		
		driver.findElement(By.name("Suggestion")).sendKeys("Test Suggestion Please Ignore");
		driver.findElement(By.name("submit")).submit();
		
		//Log out and find the patient
		driver.get("http://localhost:8080/iTrust/logout.jsp");
		
		//Login as patient 5
		driver.get("http://localhost:8080/iTrust/");
		driver.findElement(By.id("j_username")).sendKeys("5");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		//Go to food diary
		driver.findElement(By.linkText("View Food Diary")).click();
		//Old entry should still be there
		assertTrue(driver.getPageSource().contains("Try increasing positive ion intake."));
		//Check for new entry
		assertTrue(driver.getPageSource().contains("Test Suggestion Please Ignore."));
		
	}
	
	
	
	
}
