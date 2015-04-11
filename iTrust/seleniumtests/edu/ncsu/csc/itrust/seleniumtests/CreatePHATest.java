package edu.ncsu.csc.itrust.seleniumtests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class CreatePHATest extends iTrustSeleniumTest {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.admin1();
		gen.cptCodes();
	}

	/*
	 * Authenticate admin 90000000001 Choose Add PHA option Last name: Blah
	 * First name: Bob Email: bobblah@blarg.com
	 */
	public void testCreateValidPHA() throws Exception {
		// Log in as an Admin.
		WebDriver driver = new HtmlUnitDriver();
		driver = login("9000000001", "pw");

		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");

		// Click on Add PHA.
		driver.findElement(By.linkText("Add PHA")).click();
		assertEquals("iTrust - Add PHA", driver.getTitle());

		// Add the PHA.
		WebElement elem = driver.findElement(By.name("firstName"));
		elem.sendKeys("Bob");
		elem = driver.findElement(By.name("lastName"));
		elem.sendKeys("Blah");
		elem = driver.findElement(By.name("email"));
		elem.sendKeys("bobblah@blarg.com");
		elem.submit();

		// Make sure the text displays.
		List<WebElement> list = driver.findElements(By
				.xpath("//*[contains(text(),'"
						+ "New PHA Bob Blah successfully added!" + "')]"));
		assertTrue("Text not found!", list.size() > 0);
	}

	public void testCreateNullPHA() throws Exception {
		// Log in as an Admin.
		WebDriver driver = new HtmlUnitDriver();
		driver = login("9000000001", "pw");

		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");

		// Click on Add PHA.
		driver.findElement(By.linkText("Add PHA")).click();
		assertEquals("iTrust - Add PHA", driver.getTitle());

		// Add the PHA.
		WebElement elem = driver.findElement(By.name("firstName"));
		elem.submit();

		// Make sure the text displays.
		List<WebElement> list = driver
				.findElements(By
						.xpath("//*[contains(text(),'"
								+ "This form has not been validated correctly."
								+ "')]"));
		assertTrue("Text not found!", list.size() > 0);

		// Make sure nothing happened.
		assertNotLogged(TransactionType.PHA_DISABLE, 9000000001L, 0L, "");
	}

	public void testCreateValidPHA2() throws Exception {
		// Log in as an Admin.
		WebDriver driver = new HtmlUnitDriver();
		driver = login("9000000001", "pw");

		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");

		// Click on Add PHA.
		driver.findElement(By.linkText("Add PHA")).click();
		assertEquals("iTrust - Add PHA", driver.getTitle());

		// Add the PHA.
		WebElement elem = driver.findElement(By.name("firstName"));
		elem.clear();
		elem.sendKeys("Tim");
		elem = driver.findElement(By.name("lastName"));
		elem.clear();
		elem.sendKeys("Agent");
		elem = driver.findElement(By.name("email"));
		elem.clear();
		elem.sendKeys("pha@timagent.com");
		elem.submit();

		// Make sure the text displays.
		List<WebElement> list = driver.findElements(By
				.xpath("//*[contains(text(),'"
						+ "New PHA Tim Agent successfully added!" + "')]"));
		assertTrue("Text not found!", list.size() > 0);

		// Edit the PHA.
		driver.findElement(By.partialLinkText("Continue")).click();
		assertEquals("iTrust - Edit Personnel", driver.getTitle());

		elem = driver.findElement(By.name("streetAddress1"));
		elem.clear();
		elem.sendKeys("98765 Oak Hills Dr");
		elem = driver.findElement(By.name("city"));
		elem.clear();
		elem.sendKeys("Capitol City");
		Select state = new Select(driver.findElement(By.name("state")));
		state.selectByValue("NC");
		elem = driver.findElement(By.name("zip"));
		elem.clear();
		elem.sendKeys("28700-0458");
		elem = driver.findElement(By.name("phone"));
		elem.clear();
		elem.sendKeys("555-877-5100");
		elem.submit();

		// Make sure the text displays.
		list = driver.findElements(By.xpath("//*[contains(text(),'"
				+ "Information Successfully Updated" + "')]"));
		assertTrue("Text not found!", list.size() > 0);
	}

}
