package edu.ncsu.csc.itrust.seleniumtests;

import org.junit.Assert;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

public class ConsultationTest extends iTrustSeleniumTest{
	
	private WebDriver driver;

	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() {
		// Create a new instance of the html unit driver
		driver = new HtmlUnitDriver();

		// Navigate to desired web page
		driver.get("http://localhost:8080/iTrust/");
	}
	
	@Test
	public void testSubmitAndReceiveConsultation() throws Exception {
		String expectedTitle = "iTrust - HCP Home";

		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		// get the title of the page
		String actualTitle = driver.getTitle();
		// verify title
		assertEquals(actualTitle, expectedTitle);
	}
	
	@Test
	public void testSubmitAndEditConsultation() throws Exception {
		String expectedTitle = "iTrust - HCP Home";

		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		// get the title of the page
		String actualTitle = driver.getTitle();
		// verify title
		assertEquals(actualTitle, expectedTitle);
	}
	
	@Test
	public void testReceiveAndEditConsultation() throws Exception {
		String expectedTitle = "iTrust - HCP Home";

		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		// get the title of the page
		String actualTitle = driver.getTitle();
		// verify title
		assertEquals(actualTitle, expectedTitle);
	}
}
