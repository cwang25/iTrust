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
	
	@Before
	public void setUp() throws Exception{
		// Create a new instance of the html unit driver
		super.setUp();
		gen.standardData();
	}
	
	@Test
	public void testSubmitAndReceiveConsultation() throws Exception {
		String expectedTitle = "iTrust - HCP Home";

		login("9000000000", "pw");
		// get the title of the page
		String actualTitle = driver.getTitle();
		// verify title
		assertEquals(actualTitle, expectedTitle);
	}
	
	@Test
	public void testSubmitAndEditConsultation() throws Exception {
		String expectedTitle = "iTrust - HCP Home";

		login("9000000000", "pw");
		// get the title of the page
		String actualTitle = driver.getTitle();
		// verify title
		assertEquals(actualTitle, expectedTitle);
	}
	
	@Test
	public void testReceiveAndEditConsultation() throws Exception {
		String expectedTitle = "iTrust - HCP Home";

		login("9000000000", "pw");
		// get the title of the page
		String actualTitle = driver.getTitle();
		// verify title
		assertEquals(actualTitle, expectedTitle);
	}
}
