package edu.ncsu.csc.itrust.seleniumtests;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.enums.TransactionType;

/*
 * This is the Selenium equivalent of CreditCardValidatorTest.java
 */
public class CreditCardValidatorSTest extends iTrustSeleniumTest{
	@Override
	protected void setUp() throws Exception {
		super.setUp(); // clear tables is called in super
		gen.clearAllTables();
		gen.patient1();
		gen.standardData();
	}
	
	
	@Test
	public void testGoodMasterCards() throws Exception {
		login("1", "pw");

		String title = driver.getTitle();
		assertEquals("iTrust - Patient Home", title);
		assertLogged(TransactionType.HOME_VIEW, 1L, 0L, "");
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("My Demographics")).click();
		assertLogged(TransactionType.DEMOGRAPHICS_VIEW, 1L, 1L, "");
	    Select oSelection = new Select(driver.findElement(By.name("creditCardType")));
        oSelection.selectByVisibleText("Mastercard");
		driver.findElement(By.name("creditCardNumber")).clear();
		driver.findElement(By.name("creditCardNumber")).sendKeys("5593090746812380");
		driver.findElement(By.name("action")).click();
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
		assertLogged(TransactionType.DEMOGRAPHICS_EDIT, 1L, 1L, "");
		
		driver.findElement(By.name("creditCardNumber")).clear();
		driver.findElement(By.name("creditCardNumber")).sendKeys("5437693863890467");
		driver.findElement(By.name("action")).click();
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
		assertLogged(TransactionType.DEMOGRAPHICS_EDIT, 1L, 1L, "");
		
		driver.findElement(By.name("creditCardNumber")).clear();
		driver.findElement(By.name("creditCardNumber")).sendKeys("5343017708937494");
		driver.findElement(By.name("action")).click();
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
		assertLogged(TransactionType.DEMOGRAPHICS_EDIT, 1L, 1L, "");
	}
	
	@Test
	public void testBadMasterCards() throws Exception {
		login("1", "pw");

		String title = driver.getTitle();
		assertEquals("iTrust - Patient Home", title);
		assertLogged(TransactionType.HOME_VIEW, 1L, 0L, "");
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("My Demographics")).click();
		assertLogged(TransactionType.DEMOGRAPHICS_VIEW, 1L, 1L, "");
	    Select oSelection = new Select(driver.findElement(By.name("creditCardType")));
        oSelection.selectByVisibleText("Mastercard");
		driver.findElement(By.name("creditCardNumber")).clear();
		driver.findElement(By.name("creditCardNumber")).sendKeys("1593090746812380");
		driver.findElement(By.name("action")).click();
		assertTrue(driver.getPageSource().contains("not properly filled in: [Credit Card Number]"));
		assertNotLogged(TransactionType.DEMOGRAPHICS_EDIT, 1L, 1L, "");
		
	    Select oSelection2 = new Select(driver.findElement(By.name("creditCardType")));
        oSelection2.selectByVisibleText("Mastercard");
		driver.findElement(By.name("creditCardNumber")).clear();
		driver.findElement(By.name("creditCardNumber")).sendKeys("4539592576502361"); // Legit Visa
		driver.findElement(By.name("action")).click();
		assertTrue(driver.getPageSource().contains("not properly filled in: [Credit Card Number]"));
		assertNotLogged(TransactionType.DEMOGRAPHICS_EDIT, 1L, 1L, "");
		
	}
	
	@Test
	public void testGoodVisas() throws Exception {
		login("1", "pw");

		String title = driver.getTitle();
		assertEquals("iTrust - Patient Home", title);
		assertLogged(TransactionType.HOME_VIEW, 1L, 0L, "");
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("My Demographics")).click();
		assertLogged(TransactionType.DEMOGRAPHICS_VIEW, 1L, 1L, "");
	    Select oSelection = new Select(driver.findElement(By.name("creditCardType")));
        oSelection.selectByVisibleText("Visa");
		driver.findElement(By.name("creditCardNumber")).clear();
		driver.findElement(By.name("creditCardNumber")).sendKeys("4539592576502361");
		driver.findElement(By.name("action")).click();
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
		assertLogged(TransactionType.DEMOGRAPHICS_EDIT, 1L, 1L, "");
		
		driver.findElement(By.name("creditCardNumber")).clear();
		driver.findElement(By.name("creditCardNumber")).sendKeys("4716912133362668");
		driver.findElement(By.name("action")).click();
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
		assertLogged(TransactionType.DEMOGRAPHICS_EDIT, 1L, 1L, "");
		
		driver.findElement(By.name("creditCardNumber")).clear();
		driver.findElement(By.name("creditCardNumber")).sendKeys("4485333709241203");
		driver.findElement(By.name("action")).click();
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
		assertLogged(TransactionType.DEMOGRAPHICS_EDIT, 1L, 1L, "");
	}
	
	@Test
	public void testBadVisas() throws Exception {
		login("1", "pw");

		String title = driver.getTitle();
		assertEquals("iTrust - Patient Home", title);
		assertLogged(TransactionType.HOME_VIEW, 1L, 0L, "");
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("My Demographics")).click();
		assertLogged(TransactionType.DEMOGRAPHICS_VIEW, 1L, 1L, "");
	    Select oSelection = new Select(driver.findElement(By.name("creditCardType")));
        oSelection.selectByVisibleText("Visa");
		driver.findElement(By.name("creditCardNumber")).clear();
		driver.findElement(By.name("creditCardNumber")).sendKeys("5593090746812380"); // Legit Mastercard
		driver.findElement(By.name("action")).click();
		assertTrue(driver.getPageSource().contains("not properly filled in: [Credit Card Number]"));
		assertNotLogged(TransactionType.DEMOGRAPHICS_EDIT, 1L, 1L, "");
		
		//I don't know why but some reason in Selenium you have to
		//choose the card type again whereas the httpunit didn't have to
		
	    Select oSelection2 = new Select(driver.findElement(By.name("creditCardType")));
        oSelection2.selectByVisibleText("Visa");
		driver.findElement(By.name("creditCardNumber")).clear();
		driver.findElement(By.name("creditCardNumber")).sendKeys("6437693863890467"); 
		driver.findElement(By.name("action")).click();
		assertTrue(driver.getPageSource().contains("not properly filled in: [Credit Card Number]"));
		assertNotLogged(TransactionType.DEMOGRAPHICS_EDIT, 1L, 1L, "");
		
	}
	
	@Test
	public void testGoodDiscovers() throws Exception {
		login("1", "pw");

		String title = driver.getTitle();
		assertEquals("iTrust - Patient Home", title);
		assertLogged(TransactionType.HOME_VIEW, 1L, 0L, "");
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("My Demographics")).click();
		assertLogged(TransactionType.DEMOGRAPHICS_VIEW, 1L, 1L, "");
	    Select oSelection = new Select(driver.findElement(By.name("creditCardType")));
        oSelection.selectByVisibleText("Discover");
		driver.findElement(By.name("creditCardNumber")).clear();
		driver.findElement(By.name("creditCardNumber")).sendKeys("6011263089803439");
		driver.findElement(By.name("action")).click();
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
		assertLogged(TransactionType.DEMOGRAPHICS_EDIT, 1L, 1L, "");
		
		driver.findElement(By.name("creditCardNumber")).clear();
		driver.findElement(By.name("creditCardNumber")).sendKeys("6011953266156193");
		driver.findElement(By.name("action")).click();
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
		assertLogged(TransactionType.DEMOGRAPHICS_EDIT, 1L, 1L, "");
		
		driver.findElement(By.name("creditCardNumber")).clear();
		driver.findElement(By.name("creditCardNumber")).sendKeys("6011726402628022");
		driver.findElement(By.name("action")).click();
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
		assertLogged(TransactionType.DEMOGRAPHICS_EDIT, 1L, 1L, "");
	}
	
	@Test
	public void testBadDiscovers() throws Exception {
		login("1", "pw");

		String title = driver.getTitle();
		assertEquals("iTrust - Patient Home", title);
		assertLogged(TransactionType.HOME_VIEW, 1L, 0L, "");
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("My Demographics")).click();
		assertLogged(TransactionType.DEMOGRAPHICS_VIEW, 1L, 1L, "");
	    Select oSelection = new Select(driver.findElement(By.name("creditCardType")));
        oSelection.selectByVisibleText("Discover");
		driver.findElement(By.name("creditCardNumber")).clear();
		driver.findElement(By.name("creditCardNumber")).sendKeys("5593090746812380"); // Legit Mastercard
		driver.findElement(By.name("action")).click();
		assertTrue(driver.getPageSource().contains("not properly filled in: [Credit Card Number]"));
		assertNotLogged(TransactionType.DEMOGRAPHICS_EDIT, 1L, 1L, "");
		
	    Select oSelection2 = new Select(driver.findElement(By.name("creditCardType")));
        oSelection2.selectByVisibleText("Discover");
		driver.findElement(By.name("creditCardNumber")).clear();
		driver.findElement(By.name("creditCardNumber")).sendKeys("6437693863890467"); 
		driver.findElement(By.name("action")).click();
		assertTrue(driver.getPageSource().contains("not properly filled in: [Credit Card Number]"));
		assertNotLogged(TransactionType.DEMOGRAPHICS_EDIT, 1L, 1L, "");
		
	}
	
	@Test
	public void testGoodAmex() throws Exception {
		login("1", "pw");

		String title = driver.getTitle();
		assertEquals("iTrust - Patient Home", title);
		assertLogged(TransactionType.HOME_VIEW, 1L, 0L, "");
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("My Demographics")).click();
		assertLogged(TransactionType.DEMOGRAPHICS_VIEW, 1L, 1L, "");
	    Select oSelection = new Select(driver.findElement(By.name("creditCardType")));
        oSelection.selectByVisibleText("American Express");
		driver.findElement(By.name("creditCardNumber")).clear();
		driver.findElement(By.name("creditCardNumber")).sendKeys("343570480641495");
		driver.findElement(By.name("action")).click();
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
		assertLogged(TransactionType.DEMOGRAPHICS_EDIT, 1L, 1L, "");
		
		driver.findElement(By.name("creditCardNumber")).clear();
		driver.findElement(By.name("creditCardNumber")).sendKeys("377199947956764");
		driver.findElement(By.name("action")).click();
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
		assertLogged(TransactionType.DEMOGRAPHICS_EDIT, 1L, 1L, "");
		
		driver.findElement(By.name("creditCardNumber")).clear();
		driver.findElement(By.name("creditCardNumber")).sendKeys("344558915054011");
		driver.findElement(By.name("action")).click();
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
		assertLogged(TransactionType.DEMOGRAPHICS_EDIT, 1L, 1L, "");
	}
	
	@Test
	public void testBadAmex() throws Exception {
		login("1", "pw");

		String title = driver.getTitle();
		assertEquals("iTrust - Patient Home", title);
		assertLogged(TransactionType.HOME_VIEW, 1L, 0L, "");
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("My Demographics")).click();
		assertLogged(TransactionType.DEMOGRAPHICS_VIEW, 1L, 1L, "");
	    Select oSelection = new Select(driver.findElement(By.name("creditCardType")));
        oSelection.selectByVisibleText("American Express");
		driver.findElement(By.name("creditCardNumber")).clear();
		driver.findElement(By.name("creditCardNumber")).sendKeys("5593090746812380"); // Legit Mastercard
		driver.findElement(By.name("action")).click();
		assertTrue(driver.getPageSource().contains("not properly filled in: [Credit Card Number]"));
		assertNotLogged(TransactionType.DEMOGRAPHICS_EDIT, 1L, 1L, "");
		
	    Select oSelection2 = new Select(driver.findElement(By.name("creditCardType")));
        oSelection2.selectByVisibleText("American Express");
		driver.findElement(By.name("creditCardNumber")).clear();
		driver.findElement(By.name("creditCardNumber")).sendKeys("6437693863890467"); 
		driver.findElement(By.name("action")).click();
		assertTrue(driver.getPageSource().contains("not properly filled in: [Credit Card Number]"));
		assertNotLogged(TransactionType.DEMOGRAPHICS_EDIT, 1L, 1L, "");
		
	}
	
	public void testEmptyTypeEmptyNumber() throws Exception {
		login("1", "pw");

		String title = driver.getTitle();
		assertEquals("iTrust - Patient Home", title);
		assertLogged(TransactionType.HOME_VIEW, 1L, 0L, "");
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("My Demographics")).click();
		assertLogged(TransactionType.DEMOGRAPHICS_VIEW, 1L, 1L, "");

		driver.findElement(By.name("creditCardNumber")).clear();
		driver.findElement(By.name("action")).click();
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
		assertLogged(TransactionType.DEMOGRAPHICS_EDIT, 1L, 1L, "");
		
	}
	
	public void testEmptyTypeFilledNumber() throws Exception {
		login("1", "pw");

		String title = driver.getTitle();
		assertEquals("iTrust - Patient Home", title);
		assertLogged(TransactionType.HOME_VIEW, 1L, 0L, "");
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("My Demographics")).click();
		assertLogged(TransactionType.DEMOGRAPHICS_VIEW, 1L, 1L, "");
		

		driver.findElement(By.name("creditCardNumber")).clear();
		driver.findElement(By.name("creditCardNumber")).sendKeys("6437693863890467"); 
		driver.findElement(By.name("action")).click();
		assertTrue(driver.getPageSource().contains("not properly filled in: [Credit Card Type]"));
		assertNotLogged(TransactionType.DEMOGRAPHICS_EDIT, 1L, 1L, "");
		
		driver.findElement(By.name("creditCardNumber")).clear();
		driver.findElement(By.name("creditCardNumber")).sendKeys("6437693863890467"); 
		driver.findElement(By.name("action")).click();
		assertTrue(driver.getPageSource().contains("not properly filled in: [Credit Card Type]"));
		assertNotLogged(TransactionType.DEMOGRAPHICS_EDIT, 1L, 1L, "");

		
	}
}
