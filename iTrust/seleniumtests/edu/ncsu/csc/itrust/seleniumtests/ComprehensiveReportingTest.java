package edu.ncsu.csc.itrust.seleniumtests;

import org.junit.*;
import org.openqa.selenium.*;

public class ComprehensiveReportingTest extends iTrustSeleniumTest {
	@Before
	public void setUp() throws Exception{
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}

	@Test
	public void testComprehensiveAcceptanceSuccess() throws Exception {
		String expectedTitle = "iTrust - HCP Home";

		login("9000000000", "pw");
		// get the title of the page
		String actualTitle = driver.getTitle();
		// verify title
		assertEquals(actualTitle, expectedTitle);

		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("My Report Requests")).click();
		Assert.assertTrue(driver.getPageSource().contains("Report Requests"));
		driver.findElement(By.linkText("Add a new Report Request")).click();
		driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("2");
	    //waitFor(1);
	    driver.findElement(By.xpath("//input[@value='2' and @type='button']")).click();
		Assert.assertTrue(driver.getPageSource().contains(
				"Report Request Accepted"));

	}

	@Test
	public void testHCPChoosesInvalidPatient() throws Exception {
		String expectedTitle = "iTrust - HCP Home";

		login("9000000000", "pw");
		// get the title of the page
		String actualTitle = driver.getTitle();
		// verify title
		assertEquals(actualTitle, expectedTitle);
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("My Report Requests")).click();
		Assert.assertTrue(driver.getPageSource().contains("Report Requests"));
		driver.findElement(By.linkText("Add a new Report Request")).click();
		driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("260");
	    waitFor(1);
	    Assert.assertTrue(driver.getPageSource().contains("Found 0 Records"));
	}
}
