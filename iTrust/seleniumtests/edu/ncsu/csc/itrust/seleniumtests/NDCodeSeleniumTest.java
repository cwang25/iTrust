package edu.ncsu.csc.itrust.seleniumtests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class NDCodeSeleniumTest extends iTrustSeleniumTest {

	/*
	 * The URL for iTrust, change as needed
	 */
	/**ADDRESS*/
	public static final String ADDRESS = "http://localhost:8080/iTrust/";
	

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.admin1();
		gen.ndCodes();
		gen.ndCodes1();
		gen.ndCodes2();
		gen.ndCodes3();
		gen.ndCodes4();
		driver = new HtmlUnitDriver();
		// turn off htmlunit warnings
	    java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
	    java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);
	}
	
	public void testRemoveNDCode() throws Exception {
		driver.get(ADDRESS);
		driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000001");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals(driver.getTitle(), "iTrust - Admin Home");
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
		driver.findElement(By.linkText("Edit ND Codes")).click();
		assertEquals(driver.getTitle(), "iTrust - Maintain ND Codes");
		driver.findElement(By.name("code1")).clear();
		driver.findElement(By.name("code1")).sendKeys("08109");
		driver.findElement(By.name("code2")).clear();
		driver.findElement(By.name("code2")).sendKeys("6");
		driver.findElement(By.name("description")).clear();
		driver.findElement(By.name("description")).sendKeys("Aspirin");
		driver.findElement(By.name("deleteND")).click();
		assertLogged(TransactionType.DRUG_CODE_REMOVE, 9000000001L, 0L, "081096");
		assertTrue(driver.getPageSource().contains("Success: 081096 - Aspirin removed"));
	}

	public void testUpdateNDCode() throws Exception {
		driver.get(ADDRESS);
		driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000001");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals(driver.getTitle(), "iTrust - Admin Home");
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
		driver.findElement(By.linkText("Edit ND Codes")).click();
		assertEquals(driver.getTitle(), "iTrust - Maintain ND Codes");
		driver.findElement(By.name("code1")).clear();
		driver.findElement(By.name("code1")).sendKeys("00060");
		driver.findElement(By.name("code2")).clear();
		driver.findElement(By.name("code2")).sendKeys("431");
		driver.findElement(By.name("description")).clear();
		driver.findElement(By.name("description")).sendKeys("Benzoyl Peroxidez");
		driver.findElement(By.name("update")).click();
		assertLogged(TransactionType.DRUG_CODE_EDIT, 9000000001L, 0L, "00060431");
		assertTrue(driver.getPageSource().contains("Success: 1 row(s) updated"));
	}
}
