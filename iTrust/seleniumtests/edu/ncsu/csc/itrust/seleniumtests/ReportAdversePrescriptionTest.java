package edu.ncsu.csc.itrust.seleniumtests;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedCondition;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class ReportAdversePrescriptionTest extends iTrustSeleniumTest {
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.hcp0();
		gen.ovMed();
		gen.patient2();
		gen.patient1();
	}

	@Test
	public void testReport() throws Exception {
		driver.get("http://localhost:8080/iTrust/");
		driver.findElement(By.id("j_username")).sendKeys("2");
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[4]/div/h2")).click();
	    driver.findElement(By.linkText("Prescription Records")).click();
	    
	    driver.findElement(By.name("mine")).click();
	    
	    driver.findElement(By.name("checking0")).clear();
	    driver.findElement(By.name("checking0")).sendKeys("Y");
	    
	    driver.findElement(By.name("adevent")).click();
	    driver.findElement(By.name("Comment")).sendKeys("YO THIS HURTS.");
	    driver.findElement(By.name("addReport")).click();
	    
	   assertLogged(TransactionType.ADVERSE_EVENT_REPORT, 2L, 0, "");
	}
	
	@Test
	public void testReportAdverseEventsButton() throws Exception {
		driver.get("http://localhost:8080/iTrust/");
		driver.findElement(By.id("j_username")).sendKeys("2");
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[4]/div/h2")).click();
	    driver.findElement(By.linkText("Prescription Records")).click();
	    
	    driver.findElement(By.name("mine")).click();
	    
	    driver.findElement(By.name("checking0")).clear();
	    driver.findElement(By.name("checking0")).sendKeys("Y");
	    driver.findElement(By.name("adevent")).click();
	    
	    assertEquals("iTrust - Report Adverse Event", driver.getTitle());
	    
	}
	

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
}
