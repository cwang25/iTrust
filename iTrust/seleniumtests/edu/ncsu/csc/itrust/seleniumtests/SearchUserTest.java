package edu.ncsu.csc.itrust.seleniumteststests;

import org.openqa.selenium.By;

public class SearchUserTest extends iTrustSeleniumTest{
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	
	public void testGetPatient() throws Exception {
		driver.get(baseUrl + "auth/forwardUser.jsp");
		
		
		
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    
	    driver.findElement(By.cssSelector("div.panel-heading")).click();
	    driver.findElement(By.linkText("Basic Health Information")).click();
	    driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("Random Person");

	    try {
	      assertEquals("Random", driver.findElement(By.xpath("//div[@id='searchTarget']/table/tbody/tr[2]/td[2]")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	    try {
	      assertEquals("Person", driver.findElement(By.xpath("//div[@id='searchTarget']/table/tbody/tr[2]/td[3]")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	}
	
	public void testGetPatient2() throws Exception {
		
		driver.get(baseUrl + "auth/forwardUser.jsp");
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    
	    driver.findElement(By.cssSelector("div.panel-heading")).click();
	    driver.findElement(By.linkText("Basic Health Information")).click();
	    driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("Andy");
	    try {
	      assertEquals("Andy", driver.findElement(By.xpath("//div[@id='searchTarget']/table/tbody/tr[2]/td[2]")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	    try {
	      assertEquals("Programmer", driver.findElement(By.xpath("//div[@id='searchTarget']/table/tbody/tr[2]/td[3]")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	}
	
	public void testGetPatient3() throws Exception {
		driver.get(baseUrl + "auth/forwardUser.jsp");
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    
	    driver.findElement(By.cssSelector("div.panel-heading")).click();
	    driver.findElement(By.linkText("Basic Health Information")).click();
	    driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("Kelly Doctor");
	    try {
	      assertEquals("Found 0 Records", driver.findElement(By.cssSelector("span.searchResults")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	}

}
