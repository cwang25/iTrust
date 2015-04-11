package edu.ncsu.csc.itrust.seleniumtests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AuditPatientTest extends iTrustSeleniumTest{

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
		gen.patientDeactivate();
	}
	
	public void testHCPDeactivatePatient() throws Exception {
		WebDriver driver = login("9000000000", "pw");	
		
		WebElement element;
		//go to the basic health information search for patient page
		element = driver.findElement(By.linkText("Audit Patients"));
		element.click();
		
		//use the old search to go to the patients page
		element = driver.findElement(By.name("UID_PATIENTID"));
		element.sendKeys("2");
		element = driver.findElement(By.id("mainForm"));
		element.submit();
		
		//confirm we are on the right page
		assertEquals(ADDRESS + "auth/hcp/auditPage.jsp", driver.getCurrentUrl());
		
		//fill out understand text box and submit
		element = driver.findElement(By.name("understand"));
		element.sendKeys("I UNDERSTAND");
		element.submit();
		
		assertTrue(driver.getPageSource().contains("Patient Successfully Deactivated"));

	}
	
	public void testHCPDeactivatePatientWrongConfirmation() throws Exception{
		WebDriver driver = login("9000000000", "pw");	
		
		WebElement element;
		//go to the basic health information search for patient page
		element = driver.findElement(By.linkText("Audit Patients"));
		element.click();
		
		//use the old search to go to the patients page
		element = driver.findElement(By.name("UID_PATIENTID"));
		element.sendKeys("2");
		element = driver.findElement(By.id("mainForm"));
		element.submit();
		
		//confirm we are on the right page
		assertEquals(ADDRESS + "auth/hcp/auditPage.jsp", driver.getCurrentUrl());
		
		//fill out understand text box and submit
		element = driver.findElement(By.name("understand"));
		element.sendKeys("iunderstand");
		element.submit();
		
		assertTrue(driver.getPageSource().contains("You must type \"I UNDERSTAND\" in the textbox."));
	}
	
	public void testHCPActivatePatient() throws Exception {
		WebDriver driver = login("9000000000", "pw");	
		
		WebElement element;
		//go to the basic health information search for patient page
		element = driver.findElement(By.linkText("Audit Patients"));
		element.click();
		
		//use the old search to go to the patients page
		element = driver.findElement(By.name("UID_PATIENTID"));
		element.sendKeys("314159");
		element = driver.findElement(By.id("mainForm"));
		element.submit();
		
		//confirm we are on the right page
		assertEquals(ADDRESS + "auth/hcp/auditPage.jsp", driver.getCurrentUrl());
		
		//fill out understand text box and submit
		element = driver.findElement(By.name("understand"));
		element.sendKeys("I UNDERSTAND");
		element.submit();
		
		assertTrue(driver.getPageSource().contains("Patient Successfully Activated"));
	
	}
	public void testHCPActivatePatientWrongConfirmation() throws Exception {
		WebDriver driver = login("9000000000", "pw");	
		
		WebElement element;
		//go to the basic health information search for patient page
		element = driver.findElement(By.linkText("Audit Patients"));
		element.click();
		
		//use the old search to go to the patients page
		element = driver.findElement(By.name("UID_PATIENTID"));
		element.sendKeys("314159");
		element = driver.findElement(By.id("mainForm"));
		element.submit();
		
		//confirm we are on the right page
		assertEquals(ADDRESS + "auth/hcp/auditPage.jsp", driver.getCurrentUrl());
		
		//fill out understand text box and submit
		element = driver.findElement(By.name("understand"));
		element.sendKeys("WOAH");
		element.submit();
		
		assertTrue(driver.getPageSource().contains("You must type \"I UNDERSTAND\" in the textbox."));
	
	}
}
