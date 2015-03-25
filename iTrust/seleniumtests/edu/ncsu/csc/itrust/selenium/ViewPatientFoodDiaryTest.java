package edu.ncsu.csc.itrust.selenium;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.By;

import edu.ncsu.csc.itrust.selenium.iTrustSeleniumTest;

public class ViewPatientFoodDiaryTest extends iTrustSeleniumTest {
	
	public ViewPatientFoodDiaryTest() throws Exception {
		setUp(); // call base test class setup()
	}
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		gen.standardData();
	}
	
	@Test
	public void testViewPatientFoodDiary() throws Exception {
		login("9900000012", "pw");
		assertTrue(contain("Welcome, Spencer Reid"));
		driver.get(ADDRESS+"auth/hcp-uap/viewPatientFoodDiary.jsp");
	    driver.findElement(By.id("searchBox")).sendKeys("500");
	    waitFor(2); // wait 2 seconds
	    driver.findElement(By.xpath("//input[@value='500' and @type='button']")).click();
	    assertTrue(contain("4/13/2014"));
	    assertTrue(contain("Oreos"));
	    assertTrue(contain("5/21/2013"));
	    assertTrue(contain("Cheese and Bean Dip"));
	    assertTrue(isElementPresent(By.id("toggle1")));
	    driver.findElement(By.id("toggle1")).click();
	    waitFor(2);
	    driver.findElement(By.id("suggestionText")).clear();
	    driver.findElement(By.id("suggestionText")).sendKeys("Selenium Test Suggestion");
	    driver.findElementByName("submitMe").click();
	    assertTrue(contain("Your Suggestion has been added to the patient's Food Diary."));
	}
	
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
}
