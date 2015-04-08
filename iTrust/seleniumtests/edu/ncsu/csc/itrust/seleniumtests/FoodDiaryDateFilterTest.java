package edu.ncsu.csc.itrust.seleniumtests;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

public class FoodDiaryDateFilterTest extends iTrustSeleniumTest {
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		gen.standardData();
		gen.uc73();
	}
	
	@Test
	public void testFilterSuccess() throws Exception {
		login("500", "pw");
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div")).click();
	    driver.findElement(By.linkText("My Food Diary")).click();
	    driver.findElement(By.id("dateRangeFrom")).clear();
	    driver.findElement(By.id("dateRangeFrom")).sendKeys("04/13/2014");
	    driver.findElement(By.id("dateFilterSubmit")).click();
	    assertFalse(driver.findElementsByXPath("//*[contains(text(), '5/21/2013')]").get(0).isDisplayed());
	}
	
	@Test
	public void testFilterRangeSuccess() throws Exception {
		login("500", "pw");
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div")).click();
		driver.findElement(By.linkText("My Food Diary")).click();
	    driver.findElement(By.id("dateRangeCheckbox")).click();
	    driver.findElement(By.id("dateRangeFrom")).clear();
	    driver.findElement(By.id("dateRangeFrom")).sendKeys("04/13/2014");
	    driver.findElement(By.id("dateRangeTo")).clear();
	    driver.findElement(By.id("dateRangeTo")).sendKeys("05/22/2013");
	    driver.findElement(By.id("dateFilterSubmit")).click();
	    assertFalse(driver.findElementsByXPath("//*[contains(text(), '5/21/2013')]").get(0).isDisplayed());
	}
	
	@Test
	public void testFilterDateValidation() throws Exception {
		login("500", "pw");
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div")).click();
	    driver.findElement(By.linkText("My Food Diary")).click();
	    driver.findElement(By.id("dateRangeFrom")).clear();
	    driver.findElement(By.id("dateRangeFrom")).sendKeys("04/13/14");
	    driver.findElement(By.id("dateFilterSubmit")).click();
	    assertTrue(pageContains("Invalid date. Please make sure your dates are valid and in MM/dd/yyyy format."));
	}
}
