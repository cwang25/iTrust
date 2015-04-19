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

	@Test
	public void testNutritionistFilterSuccess() throws Exception {
		driver.setJavascriptEnabled(true);
		login("9900000012", "pw");
		driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Patient Food Diary")).click();
	    driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("500");
	    driver.findElement(By.xpath("//input[@value='500' and @type='button']")).click();
	    driver.findElement(By.id("dateRangeFrom")).clear();
	    driver.findElement(By.id("dateRangeFrom")).sendKeys("04/13/2014");
	    driver.findElement(By.id("dateFilterSubmit")).click();
	    assertFalse(driver.findElementsByXPath("//*[contains(text(), '5/21/2013')]").get(0).isDisplayed());
	    driver.setJavascriptEnabled(false);
	}
	
	@Test
	public void testNutritionistFilterRangeSuccess() throws Exception {
		driver.setJavascriptEnabled(true);
		login("9900000012", "pw");
		driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Patient Food Diary")).click();
	    driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("500");
	    driver.findElement(By.xpath("//input[@value='500' and @type='button']")).click();
	    driver.findElement(By.id("dateRangeCheckbox")).click();
	    driver.findElement(By.id("dateRangeFrom")).clear();
	    driver.findElement(By.id("dateRangeFrom")).sendKeys("04/13/2014");
	    driver.findElement(By.id("dateRangeTo")).clear();
	    driver.findElement(By.id("dateRangeTo")).sendKeys("05/22/2013");
	    driver.findElement(By.id("dateFilterSubmit")).click();
	    assertFalse(driver.findElementsByXPath("//*[contains(text(), '5/21/2013')]").get(0).isDisplayed());
	    driver.setJavascriptEnabled(false);
	}
	
	@Test
	public void testNutritionistDateValidation() throws Exception {
		driver.setJavascriptEnabled(true);
		login("9900000012", "pw");
		driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Patient Food Diary")).click();
	    driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("500");
	    clickOnJavascriptElement(By.xpath("//input[@value='500' and @type='button']"));
	    driver.findElement(By.id("dateRangeFrom")).clear();
	    driver.findElement(By.id("dateRangeFrom")).sendKeys("04/13/14");
	    driver.findElement(By.id("dateFilterSubmit")).click();
	    assertTrue(pageContains("Invalid date. Please make sure your dates are valid and in MM/dd/yyyy format."));
	    driver.setJavascriptEnabled(false);
	}
}
