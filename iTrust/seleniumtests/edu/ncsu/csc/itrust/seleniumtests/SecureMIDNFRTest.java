package edu.ncsu.csc.itrust.seleniumtests;

import org.openqa.selenium.*;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class SecureMIDNFRTest extends iTrustSeleniumTest {
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.ndCodes();
		gen.uap1();
		gen.patient2();
		gen.patient1();
		gen.patient5();
		gen.hcp0();
	}
	
	public void testMIDShown1() throws Exception{
		// login uap
		login("8000000009", "uappass1");
		assertEquals("iTrust - UAP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 8000000009L, 0L, "");	
		
		// choose Edit Patient
		driver.findElement(By.id("edit")).click();
		driver.findElement(By.linkText("Edit Patient")).click();
		
//		// choose patient 2
//		driver.findElement(By.id("searchBox")).clear();
//	    driver.findElement(By.id("searchBox")).sendKeys("2" + Keys.ENTER);
//	    driver.findElement(By.xpath("//input[@value='2']")).click();
		
		//HTMLDriver for selenium does not work with the search box element on getPatientID.jsp
		//Manually redirecting the page to the correct patient.
		driver.get(ADDRESS + "/auth/getPatientID.jsp?UID_PATIENTID=2&forward=hcp-uap/editPatient.jsp");
	    
		//confirm no MID in url
		assertEquals(ADDRESS + "auth/hcp-uap/editPatient.jsp", driver.getCurrentUrl());
		assertLogged(TransactionType.DEMOGRAPHICS_VIEW, 8000000009L, 2L, "");
	}
	
	public void testMIDShown2() throws Exception{
		// login hcp
		login("9000000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		// choose Edit Patient
		driver.findElement(By.id("patientinfo")).click();
		driver.findElement(By.linkText("Patient Information")).click();
		
//		// choose patient 2
//		driver.findElement(By.id("searchBox")).clear();
//	    driver.findElement(By.id("searchBox")).sendKeys("2" + Keys.ENTER);
//	    driver.findElement(By.xpath("//input[@value='2']")).click();
		
		//HTMLDriver for selenium does not work with the search box element on getPatientID.jsp
		//Manually redirecting the page to the correct patient.
		driver.get(ADDRESS + "/auth/getPatientID.jsp?UID_PATIENTID=2&forward=hcp-uap/editPatient.jsp");
	    
		//confirm no MID in url
		assertEquals(ADDRESS + "auth/hcp-uap/editPatient.jsp", driver.getCurrentUrl());
		assertLogged(TransactionType.DEMOGRAPHICS_VIEW, 9000000000L, 2L, "");
	}
	
	public void testMIDShown3() throws Exception {
		//log in as hcp
		login("9000000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		//go to edit phr
		driver.findElement(By.id("patientinfo")).click();
		driver.findElement(By.linkText("PHR Information")).click();
		
		//select patient 2
		//driver.findElement(By.id("searchBox")).clear();
	    //driver.findElement(By.id("searchBox")).sendKeys("2" + Keys.ENTER);
		//driver.findElement(By.xpath("//input[@value='2']")).click();
		
		//HTMLDriver for selenium does not work with the search box element on getPatientID.jsp
		//Manually redirecting the page to the correct patient.
	    driver.get(ADDRESS + "/auth/getPatientID.jsp?UID_PATIENTID=2&forward=hcp-uap/editPHR.jsp");
		
		//find the baby programmer link
		driver.findElement(By.linkText("Baby Programmer")).click();
		
		//make sure there's no MID in the url
		assertEquals(ADDRESS + "auth/hcp-uap/editPHR.jsp?relative=1", driver.getCurrentUrl());
		assertLogged(TransactionType.PATIENT_HEALTH_INFORMATION_VIEW, 9000000000L, 2L, "");
	}
}