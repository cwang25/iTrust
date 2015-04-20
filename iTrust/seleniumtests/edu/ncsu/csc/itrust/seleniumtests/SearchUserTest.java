package edu.ncsu.csc.itrust.seleniumtests;

import org.openqa.selenium.By;

public class SearchUserTest extends iTrustSeleniumTest{
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	
	public void testGetPatient() throws Exception {
		driver.get(ADDRESS + "auth/forwardUser.jsp");
		
		
		
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    
	    driver.findElement(By.cssSelector("div.panel-heading")).click();
	    driver.findElement(By.linkText("Basic Health Information")).click();
	    driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("Random Person");
	    clickOnJavascriptElement(By.xpath("//input[@value='1' and @type='button']"));
	    assertTrue(pageContains("Random Person's Basic Adult Health History"));
	  
	}
	
	public void testGetPatient2() throws Exception {
		
		driver.get(ADDRESS + "auth/forwardUser.jsp");
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    
	    driver.findElement(By.cssSelector("div.panel-heading")).click();
	    driver.findElement(By.linkText("Basic Health Information")).click();
	    driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("Andy");
	    clickOnJavascriptElement(By.xpath("//input[@value='2' and @type='button']"));
	    assertTrue(pageContains("Andy Programmer's Basic Adult Health History"));
	}
	
	public void testGetPatient3() throws Exception {
		driver.get(ADDRESS + "auth/forwardUser.jsp");
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    
	    driver.findElement(By.cssSelector("div.panel-heading")).click();
	    driver.findElement(By.linkText("Basic Health Information")).click();
	    driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("Kelly Doctor");
	    //Need to wait for letting javascript update the view, otherwise the result is 7 instead of 0.
	    // 7 is the result when you search by 'K'
	    waitFor(1);
	    assertEquals("Found 0 Records", driver.findElement(By.cssSelector("span.searchResults")).getText());
	}

}
