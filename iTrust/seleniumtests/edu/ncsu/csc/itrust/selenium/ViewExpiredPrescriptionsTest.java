package edu.ncsu.csc.itrust.selenium;

import org.openqa.selenium.By;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;

import edu.ncsu.csc.itrust.enums.TransactionType;
//import com.meterware.httpunit.WebTable;

public class ViewExpiredPrescriptionsTest extends iTrustSeleniumTest {
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.icd9cmCodes();
		gen.ndCodes();
		gen.uap1();
		gen.patient2();
		gen.patient1();
		gen.patient4();
		gen.patient9();
		gen.hcp0();
		gen.hcp1();
		gen.hcp2();
		gen.clearLoginFailures();
		gen.hcp3();
	}

	/*
	 * Authenticate Patient
	 * MID: 2
	 * Password: pw
	 * Choose option My Expired Prescription Reports
	 */
	public void testViewExpired1() throws Exception {
		// login patient 2
		login("2", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div")).click();
	    driver.findElement(By.linkText("My Expired Prescription Reports")).click();
	    assertTrue(pageContains("00904-2407"));
		assertFalse(pageContains("9000000000"));
	    driver.findElement(By.linkText("Kelly Doctor")).click();
	    assertTrue(pageContains("surgeon"));
		assertTrue(pageContains("4321 My Road St"));
		assertTrue(pageContains("New York"));
		assertTrue(pageContains("NY"));
		assertTrue(pageContains("10453"));
		assertTrue(pageContains("999-888-7777"));
		assertTrue(pageContains("kdoctor@iTrust.org"));
		assertFalse(pageContains("9000000000"));
		assertLogged(TransactionType.EXPIRED_PRESCRIPTION_VIEW, 2L, 2L, "");
		
	}
	
	/*
	 * Authenticate Patient
	 * MID: 99
	 * Password: pw
	 * Choose option My Expired Prescription Reports
	 */
	public void testViewExpired2() throws Exception {
		// login patient 9
		login("99", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 99L, 0L, "");
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div")).click();
		// click on My Expired Prescription Reports
	    driver.findElement(By.linkText("My Expired Prescription Reports")).click();
		assertTrue(pageContains("00904-2407"));
		assertTrue(pageContains("08109-6"));
		assertFalse(pageContains("9000000000"));
		assertFalse(pageContains("9900000000"));
		// click on a particular office visit to check medication and diagnoses
	    driver.findElement(By.linkText("Tester Arehart")).click();
		assertTrue(pageContains("Neurologist"));
		assertTrue(pageContains("2110 Thanem Circle"));
		assertTrue(pageContains("Raleigh"));
		assertTrue(pageContains("NC"));
		assertTrue(pageContains("999-888-7777"));
		assertTrue(pageContains("tarehart@iTrust.org"));
		assertFalse(pageContains("9900000000"));
		assertLogged(TransactionType.EXPIRED_PRESCRIPTION_VIEW, 99L, 99L, "");
	}
	
	/*
	 * Authenticate Patient
	 * MID: 99
	 * Password: pw
	 * Choose option My Expired Prescription Reports
	 */
	public void testViewExpired3() throws Exception {
		// login patient 9
		login("99", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 99L, 0L, "");
		
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div")).click();
		// click on My Expired Prescription Reports
	    driver.findElement(By.linkText("My Expired Prescription Reports")).click();
		assertTrue(pageContains("00904-2407"));
		assertTrue(pageContains("08109-6"));
		assertFalse(pageContains("9000000000"));
		assertFalse(pageContains("9900000000"));
		assertLogged(TransactionType.EXPIRED_PRESCRIPTION_VIEW, 99L, 99L, "");
		// click on a particular office visit to check medication and diagnoses
	    driver.findElement(By.linkText("Kelly Doctor")).click();
		assertTrue(pageContains("surgeon"));
		assertTrue(pageContains("4321 My Road St"));
		assertTrue(pageContains("New York"));
		assertTrue(pageContains("NY"));
		assertTrue(pageContains("10453"));
		assertTrue(pageContains("999-888-7777"));
		assertTrue(pageContains("kdoctor@iTrust.org"));
		assertFalse(pageContains("9000000000"));		
	}
	
	/*
	 * Make sure that missing contact info is represented as blanks.
	 * MID: 99
	 * Password: pw
	 * Choose option My Expired Prescription Reports
	 */
	public void testViewExpired4() throws Exception {
		// login patient 9
		login("99", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 99L, 0L, "");
		
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div")).click();
		// click on My Expired Prescription Reports
	    driver.findElement(By.linkText("My Expired Prescription Reports")).click();
		assertTrue(pageContains("00904-2407"));
		assertTrue(pageContains("08109-6"));
		assertFalse(pageContains("9000000000"));
		assertFalse(pageContains("9900000000"));
		assertLogged(TransactionType.EXPIRED_PRESCRIPTION_VIEW, 99L, 99L, "");
		// click on a particular office visit to check medication and diagnoses
	    driver.findElement(By.linkText("Jimmy Incomplete")).click();

		assertFalse(pageContains("null"));
		assertFalse(pageContains("AK"));
		assertFalse(pageContains("9990000000"));
	}

}
