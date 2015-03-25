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
public class AddSuggestionToFoodDiary extends iTrustSeleniumTest{

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		super.setUp();
		gen.standardData();
	}

	
	@Test
	  public void testSelenium() throws Exception {
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
}
