package edu.ncsu.csc.itrust.seleniumtests;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class AddFoodDiaryEntryTest extends iTrustSeleniumTest {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.patient20();
		gen.uc68();
	}
	/*
	 * Test ID: AddFoodDiaryEntryToEmptyFoodDiary
	 * UC: UC68
	 */
	public void testAddFoodDiaryEntryToEmptyFoodDiary() throws Exception {
		// login
		login("501", "pw");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
	    driver.findElement(By.linkText("My Food Diary")).click();
	    driver.findElement(By.xpath("(//button[@type='button'])[4]")).click();
	    driver.findElement(By.name("date")).clear();
	    driver.findElement(By.name("date")).sendKeys("2/4/2015");
	    new Select(driver.findElement(By.id("typeOfMeal"))).selectByVisibleText("Dinner");
	    driver.findElement(By.id("nameOfFood")).clear();
	    driver.findElement(By.id("nameOfFood")).sendKeys("Fruity Pebbles");
	    driver.findElement(By.id("numberOfServings")).clear();
	    driver.findElement(By.id("numberOfServings")).sendKeys("7");
	    driver.findElement(By.id("caloriesPerServing")).clear();
	    driver.findElement(By.id("caloriesPerServing")).sendKeys("110");
	    driver.findElement(By.id("gramsOfFat")).clear();
	    driver.findElement(By.id("gramsOfFat")).sendKeys("1");
	    driver.findElement(By.id("milligramsOfSodium")).clear();
	    driver.findElement(By.id("milligramsOfSodium")).sendKeys("170");
	    driver.findElement(By.id("gramsOfCarbs")).clear();
	    driver.findElement(By.id("gramsOfCarbs")).sendKeys("24");
	    driver.findElement(By.id("gramsOfSugar")).clear();
	    driver.findElement(By.id("gramsOfSugar")).sendKeys("11");
	    driver.findElement(By.id("gramsOfFiber")).clear();
	    driver.findElement(By.id("gramsOfFiber")).sendKeys("0");
	    driver.findElement(By.id("gramsOfProtein")).clear();
	    driver.findElement(By.id("gramsOfProtein")).sendKeys("1");
	    driver.findElement(By.id("saveBtn")).click();
		//Quick check if the record is saved and showed in the list.
		assertTrue(driver.getPageSource().contains("Fruity Pebbles"));
	}
	/*
	 * Test ID: AddFoodDiaryEntryToNonEmptyFoodDiary 
	 * UC: UC68
	 */
	public void testAddFoodDiaryEntryToNonEmptyFoodDiary() throws Exception {
		// login
		login("502","pw");
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
	    driver.findElement(By.linkText("My Food Diary")).click();
	    assertTrue(driver.getPageSource().contains("Hot dog"));
		assertTrue(driver.getPageSource().contains("Mango Passionfruit Juice"));
	    driver.findElement(By.xpath("(//button[@type='button'])[8]")).click();
	    driver.findElement(By.name("date")).clear();
	    driver.findElement(By.name("date")).sendKeys("11/12/2014");
	    driver.findElement(By.id("nameOfFood")).clear();
	    driver.findElement(By.id("nameOfFood")).sendKeys("Cookie Dough Ice Cream");
	    driver.findElement(By.id("numberOfServings")).clear();
	    driver.findElement(By.id("numberOfServings")).sendKeys(".5");
	    driver.findElement(By.id("caloriesPerServing")).clear();
	    driver.findElement(By.id("caloriesPerServing")).sendKeys("160");
	    driver.findElement(By.id("gramsOfFat")).clear();
	    driver.findElement(By.id("gramsOfFat")).sendKeys("8");
	    driver.findElement(By.id("milligramsOfSodium")).clear();
	    driver.findElement(By.id("milligramsOfSodium")).sendKeys("45");
	    driver.findElement(By.id("gramsOfCarbs")).clear();
	    driver.findElement(By.id("gramsOfCarbs")).sendKeys("21");
	    driver.findElement(By.id("gramsOfSugar")).clear();
	    driver.findElement(By.id("gramsOfSugar")).sendKeys("16");
	    driver.findElement(By.id("gramsOfFiber")).clear();
	    driver.findElement(By.id("gramsOfFiber")).sendKeys("0");
	    driver.findElement(By.id("gramsOfProtein")).clear();
	    driver.findElement(By.id("gramsOfProtein")).sendKeys("2");
	    driver.findElement(By.id("saveBtn")).click();
		// Quick check if the record is saved and showed in the list.
		// Also the old entries.
		assertTrue(driver.getPageSource().contains("Cookie Dough Ice Cream"));
		assertTrue(driver.getPageSource().contains("Hot dog"));
		assertTrue(driver.getPageSource().contains("Mango Passionfruit Juice"));
	}
	
	/*
	 * Test ID: PatientViewsEmptyFoodDiary
	 * UC: UC68
	 */
	public void testPatientViewsEmptyFoodDiary() throws Exception {
		// login
		login("20","pw");
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
	    driver.findElement(By.linkText("My Food Diary")).click();
		//Verify that no food diaries are shown
		assertTrue(driver.getPageSource().contains("You have no Food diary"));
	} 
	
	/*
	 * Test ID: PatientEntersInvalidCaloriesPerServingInput
	 * UC: UC68
	 */
	public void testPatientEntersInvalidCaloriesPerServingInput() throws Exception {
		// login
		this.login("501", "pw");

		driver.findElement(
				By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("My Food Diary")).click();
		driver.findElement(By.xpath("(//button[@type='button'])[4]")).click();
		driver.findElement(By.name("date")).clear();
		driver.findElement(By.name("date")).sendKeys("2/8/2015");
		new Select(driver.findElement(By.id("typeOfMeal")))
				.selectByVisibleText("Lunch");
		driver.findElement(By.id("nameOfFood")).clear();
		driver.findElement(By.id("nameOfFood")).sendKeys("Cheese Pizza");
		driver.findElement(By.id("numberOfServings")).clear();
		driver.findElement(By.id("numberOfServings")).sendKeys("1");
		driver.findElement(By.id("caloriesPerServing")).clear();
		driver.findElement(By.id("caloriesPerServing")).sendKeys("-350");
		driver.findElement(By.id("gramsOfFat")).clear();
		driver.findElement(By.id("gramsOfFat")).sendKeys("20");
		driver.findElement(By.id("milligramsOfSodium")).clear();
		driver.findElement(By.id("milligramsOfSodium")).sendKeys("540");
		driver.findElement(By.id("gramsOfCarbs")).clear();
		driver.findElement(By.id("gramsOfCarbs")).sendKeys("43");
		driver.findElement(By.id("gramsOfSugar")).clear();
		driver.findElement(By.id("gramsOfSugar")).sendKeys("5");
		driver.findElement(By.id("gramsOfFiber")).clear();
		driver.findElement(By.id("gramsOfFiber")).sendKeys("4");
		driver.findElement(By.id("gramsOfProtein")).clear();
		driver.findElement(By.id("gramsOfProtein")).sendKeys("16");
		driver.findElement(By.id("saveBtn")).click();
		// Check if error message displayed
		assertTrue(driver.getPageSource().contains(
				"Calories per serving has to be 0 or greater"));
	}
	
	/*
	 * Test ID: PatientEntersInvalidServingsNumber
	 * UC: UC68
	 */
	public void testPatientEntersInvalidServingsNumber() throws Exception {
		// login
		this.login("501", "pw");

		driver.findElement(
				By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("My Food Diary")).click();
		driver.findElement(By.xpath("(//button[@type='button'])[4]")).click();
		driver.findElement(By.name("date")).clear();
		driver.findElement(By.name("date")).sendKeys("2/8/2015");
		new Select(driver.findElement(By.id("typeOfMeal")))
				.selectByVisibleText("Lunch");
		driver.findElement(By.id("nameOfFood")).clear();
		driver.findElement(By.id("nameOfFood")).sendKeys("Cheese Pizza");
		driver.findElement(By.id("numberOfServings")).clear();
		driver.findElement(By.id("numberOfServings")).sendKeys("0");
		driver.findElement(By.id("caloriesPerServing")).clear();
		driver.findElement(By.id("caloriesPerServing")).sendKeys("350");
		driver.findElement(By.id("gramsOfFat")).clear();
		driver.findElement(By.id("gramsOfFat")).sendKeys("20");
		driver.findElement(By.id("milligramsOfSodium")).clear();
		driver.findElement(By.id("milligramsOfSodium")).sendKeys("540");
		driver.findElement(By.id("gramsOfCarbs")).clear();
		driver.findElement(By.id("gramsOfCarbs")).sendKeys("43");
		driver.findElement(By.id("gramsOfSugar")).clear();
		driver.findElement(By.id("gramsOfSugar")).sendKeys("5");
		driver.findElement(By.id("gramsOfFiber")).clear();
		driver.findElement(By.id("gramsOfFiber")).sendKeys("4");
		driver.findElement(By.id("gramsOfProtein")).clear();
		driver.findElement(By.id("gramsOfProtein")).sendKeys("16");
		driver.findElement(By.id("saveBtn")).click();
		// Check if error message displayed
		assertTrue(driver.getPageSource().contains(
				"Number of servings has to be a positive number."));
	}

	/*
	 * Test ID: PatientEntersAlphabeticalInputForFat
	 * UC: UC68
	 */
	public void testPatientEntersAlphabeticalInputForFat() throws Exception {
		// login
		this.login("501", "pw");

		driver.findElement(
				By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("My Food Diary")).click();
		driver.findElement(By.xpath("(//button[@type='button'])[4]")).click();
		driver.findElement(By.name("date")).clear();
		driver.findElement(By.name("date")).sendKeys("2/8/2015");
		new Select(driver.findElement(By.id("typeOfMeal")))
				.selectByVisibleText("Lunch");
		driver.findElement(By.id("nameOfFood")).clear();
		driver.findElement(By.id("nameOfFood")).sendKeys("Cheese Pizza");
		driver.findElement(By.id("numberOfServings")).clear();
		driver.findElement(By.id("numberOfServings")).sendKeys("4");
		driver.findElement(By.id("caloriesPerServing")).clear();
		driver.findElement(By.id("caloriesPerServing")).sendKeys("350");
		driver.findElement(By.id("gramsOfFat")).clear();
		driver.findElement(By.id("gramsOfFat")).sendKeys("abc");
		driver.findElement(By.id("milligramsOfSodium")).clear();
		driver.findElement(By.id("milligramsOfSodium")).sendKeys("540");
		driver.findElement(By.id("gramsOfCarbs")).clear();
		driver.findElement(By.id("gramsOfCarbs")).sendKeys("43");
		driver.findElement(By.id("gramsOfSugar")).clear();
		driver.findElement(By.id("gramsOfSugar")).sendKeys("5");
		driver.findElement(By.id("gramsOfFiber")).clear();
		driver.findElement(By.id("gramsOfFiber")).sendKeys("4");
		driver.findElement(By.id("gramsOfProtein")).clear();
		driver.findElement(By.id("gramsOfProtein")).sendKeys("16");
		driver.findElement(By.id("saveBtn")).click();
		// Check if error message displayed
		assertTrue(driver.getPageSource().contains(
				"Grams of fat has to be 0 or greater."));
	}
}
