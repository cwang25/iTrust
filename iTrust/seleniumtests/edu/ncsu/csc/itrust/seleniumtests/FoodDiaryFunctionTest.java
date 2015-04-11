package edu.ncsu.csc.itrust.seleniumtests;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.junit.Test;

import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.http.iTrustHTTPTest;

public class FoodDiaryFunctionTest extends iTrustSeleniumTest {
	//private WebDriver driver = new HtmlUnitDriver();
	private String baseUrl;
	//private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Override
	public void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	@Test
	public void testEntryAddToEmpty1() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080/iTrust/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseUrl + "auth/forwardUser.jsp");
		
		driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("97"); //Derek Morgan
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("iTrust - Patient Home", driver.getTitle());
	    assertLogged(TransactionType.HOME_VIEW, 97L, 0L, "");
	    driver.findElement(By.linkText("Food Diary")).click();
	    assertEquals("iTrust - My Food Diary", driver.getTitle());
	    
	    //work around
	    //driver.findElement(By.id("addEntry")).click();
	    driver.get(baseUrl + "auth/patient/addFoodDiary.jsp?add=true");
	    
	    System.out.println(driver.getTitle());
	    assertEquals("iTrust - Add Food Diary", driver.getTitle());
	    driver.findElement(By.name("mealDate")).clear();
	    driver.findElement(By.name("mealDate")).sendKeys("2005-11-21");
	    new Select(driver.findElement(By.name("mealList"))).selectByVisibleText("Dinner");
	    driver.findElement(By.name("foodName")).clear();
	    driver.findElement(By.name("foodName")).sendKeys("Fruity Pebbles");
	    driver.findElement(By.name("servings")).clear();
	    driver.findElement(By.name("servings")).sendKeys("7");
	    driver.findElement(By.name("calories")).clear();
	    driver.findElement(By.name("calories")).sendKeys("110");
	    driver.findElement(By.name("fat")).clear();
	    driver.findElement(By.name("fat")).sendKeys("1");
	    driver.findElement(By.name("sodium")).clear();
	    driver.findElement(By.name("sodium")).sendKeys("170");
	    driver.findElement(By.name("carbs")).clear();
	    driver.findElement(By.name("carbs")).sendKeys("24");
	    driver.findElement(By.name("fiber")).clear();
	    driver.findElement(By.name("fiber")).sendKeys("0");
	    driver.findElement(By.name("sugar")).clear();
	    driver.findElement(By.name("sugar")).sendKeys("11");
	    driver.findElement(By.name("protein")).clear();
	    driver.findElement(By.name("protein")).sendKeys("1");
	    driver.findElement(By.id("addDiary")).click();
	    
	    assertEquals("iTrust - My Food Diary", driver.getTitle());
	    assertEquals("Food Diary sucessfully updated!", driver.findElement(By.cssSelector("h2.iTrustError")).getText());
	    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*Fruity Pebbles[\\s\\S]*$"));
	    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*Dinner[\\s\\S]*$"));
	    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*11/21/2005[\\s\\S]*$"));
	    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*7[\\s\\S]*$"));
	    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*110[\\s\\S]*$"));
	    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*1[\\s\\S]*$"));
	    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*170[\\s\\S]*$"));
	    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*24[\\s\\S]*$"));
	    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*0[\\s\\S]*$"));
	    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*11[\\s\\S]*$"));
	    
	}
	
}
