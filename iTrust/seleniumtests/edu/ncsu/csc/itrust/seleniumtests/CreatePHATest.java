package edu.ncsu.csc.itrust.seleniumtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

public class CreatePHATest extends iTrustSeleniumTest{
	@Before
	public void setUp() throws Exception {
		super.setUp();
		gen.admin1();
	}

	@Test
	public void testCreateValidPHA() throws Exception {
		String expectedTitle = "iTrust - Admin Home";

		login("9000000001", "pw");
		// get the title of the page
		String actualTitle = driver.getTitle();
		// verify title
		assertEquals(actualTitle, expectedTitle);
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("Add PHA")).click();
		assertEquals(driver.getTitle(), "iTrust - Add PHA");
		driver.findElement(By.name("firstName")).clear();
		driver.findElement(By.name("firstName")).sendKeys("Bob");
		driver.findElement(By.name("lastName")).clear();
		driver.findElement(By.name("lastName")).sendKeys("Blah");
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys("bobblah@blarg.com");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		Assert.assertTrue(driver.getPageSource().contains(
				"New PHA Bob Blah succesfully added!"));
	}

	@Test
	public void testCreateNullPHA() throws Exception {
		String expectedTitle = "iTrust - Admin Home";

		login("9000000001", "pw");
		// get the title of the page
		String actualTitle = driver.getTitle();
		// verify title
		assertEquals(actualTitle, expectedTitle);

		driver.findElement(By.cssSelector("div.panel-heading")).click();
		driver.findElement(By.linkText("Add PHA")).click();
		assertEquals(driver.getTitle(), "iTrust - Add PHA");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		Assert.assertTrue(driver
				.getPageSource()
				.contains(
						"This form has not been validated correctly. The following field are not properly filled in: [First name: Up to 20 Letters, space, ' and -, Last name: Up to 20 Letters, space, ' and -, Email: Up to 30 alphanumeric characters and symbols . and _ @]"));

	}

	@Test
	public void testCreateValidPHA2() throws Exception {
		String expectedTitle = "iTrust - Admin Home";

		login("9000000001", "pw");
		// get the title of the page
		String actualTitle = driver.getTitle();
		// verify title
		assertEquals(actualTitle, expectedTitle);

		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("Add PHA")).click();
		assertEquals(driver.getTitle(), "iTrust - Add PHA");
		driver.findElement(By.name("firstName")).clear();
		driver.findElement(By.name("firstName")).sendKeys("Tim");
		driver.findElement(By.name("lastName")).clear();
		driver.findElement(By.name("lastName")).sendKeys("Agent");
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys("pha@timagent.com");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.findElement(By.linkText("Continue to personnel information."))
				.click();
		assertEquals(driver.getTitle(), "iTrust - Edit Personnel");
		driver.findElement(By.name("streetAddress1")).clear();
		driver.findElement(By.name("streetAddress1")).sendKeys(
				"98765 Oak Hills Dr");
		driver.findElement(By.name("city")).clear();
		driver.findElement(By.name("city")).sendKeys("Capitol City");
		new Select(driver.findElement(By.name("state")))
				.selectByVisibleText("North Carolina");
		driver.findElement(By.name("zip")).clear();
		driver.findElement(By.name("zip")).sendKeys("28700-0458");
		driver.findElement(By.name("phone")).clear();
		driver.findElement(By.name("phone")).sendKeys("555-877-5100");
		driver.findElement(By.name("action")).click();		
		Assert.assertTrue(driver.getPageSource().contains(
				"Information Successfully Updated"));
	}
}