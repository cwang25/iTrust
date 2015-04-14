package edu.ncsu.csc.itrust.seleniumtests;

import java.util.concurrent.TimeUnit;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class ViewAllPatientsUseCaseTest extends iTrustSeleniumTest {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		super.setUp();
		driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080/iTrust";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testViewAllPatients() throws Exception {

		// This logs us into iTrust and returns the HtmlUnitDriver for use in
		// this case
		HtmlUnitDriver driver = super.login("9000000000", "pw");

		// Make sure we were able to log in
		assertEquals("iTrust - HCP Home", driver.getTitle());
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("All Patients")).click();
		String text = driver.findElement(By.cssSelector("table.fTable")).getText();
	    assertTrue("Text not found!", text.contains("10/10/2008"));
	    assertTrue("Text not found!", text.contains("09/14/2009"));
	    assertTrue("Text not found!", text.contains("344 Bob Street"));
	    assertTrue("Text not found!", text.contains("Raleigh NC 27607"));
	    
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
