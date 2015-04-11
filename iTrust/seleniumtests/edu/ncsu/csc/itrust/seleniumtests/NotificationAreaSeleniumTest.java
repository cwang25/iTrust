package edu.ncsu.csc.itrust.seleniumtests;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NotificationAreaSeleniumTest extends iTrustSeleniumTest {
	
	/*
	 * The URL for iTrust, change as needed
	 */
	/**ADDRESS*/
	public static final String ADDRESS = "http://localhost:8080/iTrust/";
	private WebDriver driver;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
		gen.uc60();
		// turn off htmlunit warnings
	    java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
	    java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);
	}
	
	public void testPatientViewDeclaredProviderFromNotificationCenter() throws Exception {
		driver = login("2", "pw");
		assertEquals(driver.getTitle(), "iTrust - Patient Home");
		assertTrue(driver.getPageSource().contains("Gandalf Stormcrow"));
		assertTrue(driver.getPageSource().contains("999-888-7777"));
		assertTrue(driver.getPageSource().contains("gstormcrow@iTrust.org"));
	}
	
	public void testHCPTelemedicineDetailsFromNotificationCenter () throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat();
		formatter.applyPattern("MM/dd/yyyy");
		//String tomorrow = formatter.format(new Date((new Date()).getTime() + 86400000));
		gen.appointmentCase3();
		gen.remoteMonitoring3();
		driver = login("9000000000", "pw");
		assertEquals(driver.getTitle(), "iTrust - HCP Home");
		assertTrue(driver.getPageSource().contains("3 physiological status reports"));
		assertTrue(driver.getPageSource().contains("0 weight/pedometer status reports"));
	}
	
	public void testRepresenteeAppointmentDetailsFromNotificationCenter() throws Exception {
		driver = login("2", "pw");
		assertEquals(driver.getTitle(), "iTrust - Patient Home");
		driver.findElement(By.linkText("04/16/2015")).click();
		assertTrue(driver.getPageSource().contains("Andy Programmer"));
		assertTrue(driver.getPageSource().contains("Gandalf Stormcrow"));
		assertTrue(driver.getPageSource().contains("General Checkup"));
		assertTrue(driver.getPageSource().contains("04/16/2015 09:30 AM"));
		assertTrue(driver.getPageSource().contains("45 minutes"));
		assertTrue(driver.getPageSource().contains("No Comment"));
	}

	public void testUnreadMessagesCount() throws Exception {
		driver = login("9000000000", "pw");
		assertEquals(driver.getTitle(), "iTrust - HCP Home");
		assertTrue(driver.getPageSource().contains("12"));
	}
	
	public void testUnpaidBillsCount() throws Exception {
		driver = login("311", "pw");
		assertEquals(driver.getTitle(), "iTrust - Patient Home");
		assertTrue(driver.getPageSource().contains("1"));
		assertTrue(driver.getPageSource().contains("new bill."));
	}
}
