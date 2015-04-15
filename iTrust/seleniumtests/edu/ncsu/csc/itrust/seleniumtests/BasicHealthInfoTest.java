package edu.ncsu.csc.itrust.seleniumtests;

import org.openqa.selenium.*;

import edu.ncsu.csc.itrust.enums.TransactionType;
 
public class BasicHealthInfoTest extends iTrustSeleniumTest {
 
	@Override
	protected void setUp() throws Exception{
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	
	public void testBasicHealthViewed() throws Exception{
		login("9000000000", "pw");
		
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("Basic Health Information")).click();
		
		//use the old search to go to the patients page
		driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("2");
	    //waitFor(1);
	    driver.findElement(By.xpath("//input[@value='2' and @type='button']")).click();
		
		assertEquals(ADDRESS + "auth/hcp-uap/viewBasicHealth.jsp", driver.getCurrentUrl());
		
		logout();
		
		assertEquals(ADDRESS + "auth/forwardUser.jsp", driver.getCurrentUrl());
		
		
		login("2", "pw");
		
		assertTrue(driver.getPageSource().contains("Kelly Doctor"));
		assertTrue(driver.getPageSource().contains("viewed your health records history today at"));
	}
	
	public void testBasicHealthSmokingStatus() throws Exception {
		login("9000000000", "pw");
		
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div")).click();
		driver.findElement(By.linkText("Document Office Visit")).click();

		assertEquals(ADDRESS + "auth/getPatientID.jsp?forward=/iTrust/auth/hcp-uap/documentOfficeVisit.jsp", driver.getCurrentUrl());
		
		//use the old search to go to the patients page
		driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("2");
	    //waitFor(1);
	    driver.findElement(By.xpath("//input[@value='2' and @type='button']")).click();
		
		assertEquals(ADDRESS + "auth/hcp-uap/documentOfficeVisit.jsp", driver.getCurrentUrl());
		
		driver.findElement(By.cssSelector("input[value='Yes, Document Office Visit']")).submit();
		
		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		
		driver.findElement(By.name("update")).submit();
		
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
		assertLogged(TransactionType.OFFICE_VISIT_CREATE, 9000000000L, 2L, "");
		
		assertTrue(driver.getPageSource().contains("1 - Current every day smoker"));
		assertTrue(driver.getPageSource().contains("2 - Current some day smoker"));
		assertTrue(driver.getPageSource().contains("3 - Former smoker"));
		assertTrue(driver.getPageSource().contains("4 - Never smoker"));
		assertTrue(driver.getPageSource().contains("5 - Smoker, current status unknown"));
		assertTrue(driver.getPageSource().contains("9 - Unknown if ever smoked"));
	}
}