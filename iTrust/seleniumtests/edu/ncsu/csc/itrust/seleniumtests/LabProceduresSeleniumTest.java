package edu.ncsu.csc.itrust.seleniumtests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class LabProceduresSeleniumTest extends iTrustSeleniumTest {
	
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
		gen.uap1();
		gen.hcp0();
		gen.patient2();
		gen.patient4();
		gen.loincs();
		gen.labProcedures();
		// turn off htmlunit warnings
	    java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
	    java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);
	}
	
	public void testAddLabProcedureWithLabTech() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		
		driver = login("9000000000", "pw");
		assertEquals(driver.getTitle(), "iTrust - HCP Home");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div")).click();
		driver.findElement(By.linkText("Document Office Visit")).click();
	    driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("2");
		driver.findElement(By.xpath("//input[@value='2' and @type='button']")).click();
		driver.get(ADDRESS + "/auth/hcp-uap/editOfficeVisit.jsp?ovID=955");
		assertEquals(driver.getTitle(), "iTrust - Document Office Visit");
		selectComboValue("loinc", "10666-6", driver);
		selectComboValue("labTech", "5000000001", driver);
		selectComboValue("labProcPriority", "1", driver);
		driver.findElement(By.id("add_labProcedure")).click();
		assertTrue(driver.getPageSource().contains("Lab Procedure information successfully updated."));
		assertLogged(TransactionType.LAB_PROCEDURE_ADD, 9000000000L, 2L, "");
		assertTrue(driver.getPageSource().contains("Lab Dude"));
		assertTrue(driver.getPageSource().contains("In Transit"));
		assertTrue(driver.getPageSource().contains("Remove"));
		assertTrue(driver.getPageSource().contains("Reassign"));
	}
	
	public void testAddLabProcedureWithoutLabTech() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		
		driver = login("9000000000", "pw");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div")).click();
		assertEquals(driver.getTitle(), "iTrust - HCP Home");
		driver.findElement(By.linkText("Document Office Visit")).click();
	    driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("2");
		driver.findElement(By.xpath("//input[@value='2' and @type='button']")).click();
		driver.get(ADDRESS + "/auth/hcp-uap/editOfficeVisit.jsp?ovID=955");
		assertEquals(driver.getTitle(), "iTrust - Document Office Visit");
		selectComboValue("loinc", "10666-6", driver);
		selectComboValue("labProcPriority", "1", driver);
		driver.findElement(By.id("add_labProcedure")).click();
		assertTrue(driver.getPageSource().contains("A lab tech must be selected before adding a laboratory procedure."));
	}
	
	public void testReassignLabProcedure() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		
		driver = login("9000000000", "pw");
		assertEquals(driver.getTitle(), "iTrust - HCP Home");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div")).click();
		driver.findElement(By.linkText("Document Office Visit")).click();
	    driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("2");
		driver.findElement(By.xpath("//input[@value='2' and @type='button']")).click();
		driver.get(ADDRESS + "/auth/hcp-uap/editOfficeVisit.jsp?ovID=955");
		assertEquals(driver.getTitle(), "iTrust - Document Office Visit");
		//driver.findElement(By.id("editLabProcReassign")).click();
		//assertEquals(driver.getTitle(), "iTrust - Reassign Lab Procedure");
		//selectComboValue("newLabTech", "5000000001", driver);
		//selectComboValue("labProcPriority", "1", driver);
		//driver.findElement(By.id("setLabTech")).click();
		
		//assertLogged(TransactionType.LAB_RESULTS_REASSIGN, 9000000000L, 2L, "");
		assertEquals(driver.getTitle(), "iTrust - Document Office Visit");
		assertTrue(driver.getPageSource().contains("Lab Dude"));		
	}
	
	public void testPatientViewLabProcedureResults() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		
		driver = login("22", "pw");
		assertEquals(driver.getTitle(), "iTrust - Patient Home");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("My Lab Procedures")).click();
		assertEquals(driver.getTitle(), "iTrust - View Lab Procedures");
		assertTrue(driver.getPageSource().contains("7"));
		assertTrue(driver.getPageSource().contains("5.23"));
		assertTrue(driver.getPageSource().contains("18"));
		
		assertTrue(driver.getPageSource().contains("In Transit"));
		assertTrue(driver.getPageSource().contains("Received"));
		assertTrue(driver.getPageSource().contains("Pending"));
		
		assertTrue(driver.getPageSource().contains("Completed"));
		assertTrue(driver.getPageSource().contains("In Transit"));
		assertTrue(driver.getPageSource().contains("Received"));
		
		assertTrue(driver.getPageSource().contains("Pending"));
		assertTrue(driver.getPageSource().contains("Completed"));
		assertTrue(driver.getPageSource().contains("In Transit"));
		
		assertTrue(driver.getPageSource().contains("Received"));
		assertTrue(driver.getPageSource().contains("Pending"));
		assertTrue(driver.getPageSource().contains("Completed"));
	}
	
	public void testPatient_LabProcedureView() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		gen.patientLabProcedures();
		
		driver = login("2", "pw");
		assertEquals(driver.getTitle(), "iTrust - Patient Home");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("My Lab Procedures")).click();
		assertEquals(driver.getTitle(), "iTrust - View Lab Procedures");
		
		assertTrue(driver.getPageSource().contains("Kelly Doctor"));
		assertTrue(driver.getPageSource().contains("11/20/2011"));
		assertTrue(driver.getPageSource().contains("Microscopic Observation"));
		assertTrue(driver.getPageSource().contains("Completed"));
		
		assertTrue(driver.getPageSource().contains("Its all done"));
		assertTrue(driver.getPageSource().contains("85"));
		assertTrue(driver.getPageSource().contains("grams"));
		assertTrue(driver.getPageSource().contains("Normal"));
	}
	
	public void testPatient_LabProcedureView2() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		gen.patientLabProcedures();
		
		driver = login("1", "pw");
		assertEquals(driver.getTitle(), "iTrust - Patient Home");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("My Lab Procedures")).click();
		assertEquals(driver.getTitle(), "iTrust - View Lab Procedures");
		
		assertTrue(driver.getPageSource().contains("Kelly Doctor"));
		assertTrue(driver.getPageSource().contains("10/20/2011"));
		assertTrue(driver.getPageSource().contains("Specimen volume"));
		assertTrue(driver.getPageSource().contains("Completed"));
		
		assertTrue(driver.getPageSource().contains(""));
		assertTrue(driver.getPageSource().contains("79"));
		assertTrue(driver.getPageSource().contains("ml"));
		assertTrue(driver.getPageSource().contains("Abnormal"));
	}
	
	public void testPatient_LabProcedureViewChart() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		gen.patientLabProcedures();
		
		driver = login("21", "pw");
		assertEquals(driver.getTitle(), "iTrust - Patient Home");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("My Lab Procedures")).click();
		assertEquals(driver.getTitle(), "iTrust - View Lab Procedures");
		
		driver.findElement(By.id("viewResultsChart")).click();
		assertEquals(driver.getTitle(), "Lab Procedure Results Chart");
	}
	
	public void testHcpLabProc() throws Exception {
		driver = login("9000000000", "pw");
		assertEquals(driver.getTitle(), "iTrust - HCP Home");
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
		driver.findElement(By.linkText("Laboratory Procedures")).click();
	    driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("2");
		driver.findElement(By.xpath("//input[@value='2' and @type='button']")).click();
		
		assertEquals(driver.getTitle(), "iTrust - View Laboratory Procedures");
		driver.findElement(By.linkText("Allow/Disallow Viewing")).click();
		assertLogged(TransactionType.LAB_RESULTS_VIEW, 9000000000L, 2L, "");
	}
	
	public void testPatientViewLabResults() throws Exception {
		driver = login("2", "pw");
		assertEquals(driver.getTitle(), "iTrust - Patient Home");
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
		driver.findElement(By.linkText("View My Records")).click();
		assertEquals(driver.getTitle(), "iTrust - View My Records");
		assertLogged(TransactionType.MEDICAL_RECORD_VIEW, 2L, 2L, "");
	}
	
	public void testLabProcedureInTransitToReceived() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		
		driver = login("5000000001", "pw");
		assertEquals(driver.getTitle(), "iTrust - Lab Tech Home");
		
	    driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("All Lab Procedures")).click();
		assertEquals(driver.getTitle(), "iTrust - View Laboratory Procedures");
		
		for (int i = 0; i < 18; i++) {
			assertTrue(driver.getPageSource().contains("Beaker Beaker"));
			driver.findElement(By.name("inTransitSubmitBtn")).click();
			assertEquals(driver.getTitle(), "iTrust - View Laboratory Procedures");
		}
		assertTrue(driver.getPageSource().contains("There are no lab procedures in transit"));
	}

}
