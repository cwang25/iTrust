package edu.ncsu.csc.itrust.seleniumtests;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;


/**
 * Use Case 34
 * Use Case 60
 */
public class NotificationAreaTest extends iTrustSeleniumTest {
	
	@Override
	protected void setUp() throws Exception{
		super.setUp();
		driver = new HtmlUnitDriver();
		gen.standardData();
		gen.uc60();
	}
	
	public void testPatientViewDeclaredProviderFromNotificationCenter () throws Exception {
		driver.get(ADDRESS);
		driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("1");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("iTrust - Patient Home", driver.getTitle());
		String pageText = driver.findElement(By.tagName("body")).getText();
		assertTrue(pageText.contains("Gandalf Stormcrow"));
		assertTrue(pageText.contains("999-888-7777"));
		assertTrue(pageText.contains("gstormcrow@iTrust.org"));
		driver.quit();
	}

	public void testHCPTelemedicineDetailsFromNotificationCenter () throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat();
		formatter.applyPattern("MM/dd/yyyy");
		//String tomorrow = formatter.format(new Date((new Date()).getTime() + 86400000));
		gen.appointmentCase3();
		gen.remoteMonitoring3();
		driver.get(ADDRESS);
		driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("iTrust - HCP Home", driver.getTitle());
		String pageText = driver.findElement(By.tagName("body")).getText();
		assertTrue(pageText.contains("3 physiological status reports"));
		assertTrue(pageText.contains("0 weight/pedometer status reports"));
		driver.quit();
	}
			
	public void testRepresenteeAppointmentDetailsFromNotificationCenter() throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat();
		formatter.applyPattern("MM/dd/yyyy");
		String tomorrow = formatter.format(new Date((new Date()).getTime() + 86400000));
		gen.appointmentCase3();
		driver.get(ADDRESS);
		driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("1");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("iTrust - Patient Home", driver.getTitle());
		driver.findElement(By.linkText(tomorrow)).click();
				
		assertTrue(driver.getPageSource().contains("Random Person"));
		assertTrue(driver.getPageSource().contains("10:30"));
		assertTrue(driver.getPageSource().contains("Kelly Doctor"));
		assertTrue(driver.getPageSource().contains("45 minutes"));		
		assertTrue(driver.getPageSource().contains("General Checkup after your knee surgery."));
		driver.quit();
	}
	
	public void testUnreadMessagesCount() throws Exception {

		driver.get(ADDRESS);
		driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("iTrust - HCP Home", driver.getTitle());
		String pageText = driver.getPageSource();
		assertTrue(pageText.contains("12"));

		

	}
	
	public void testUnpaidBillsCount() throws Exception {
		driver.get(ADDRESS);
		driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("311");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("iTrust - Patient Home", driver.getTitle());
		String pageText = driver.getPageSource();
		//driver.findElement(By.linkText("1"));
		assertTrue(pageText.contains("2"));
		assertTrue(pageText.contains("new bills."));
	}
}
