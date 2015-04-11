package edu.ncsu.csc.itrust.seleniumtests;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.enums.TransactionType;


public class LabProceduresTest extends iTrustSeleniumTest {
	private HtmlUnitDriver driver;
	private StringBuffer verificationErrors = new StringBuffer();
	public static final String ADDRESS = "http://localhost:8080/iTrust/";
	
	public void setUp() throws Exception {
		gen.clearAllTables();
		gen.uap1();
		gen.hcp0();
		gen.patient2();
		gen.patient4();
		gen.loincs();
		gen.labProcedures();
		driver = new HtmlUnitDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void testAddLabProcedureWithLabTech() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		
		driver = login("9000000000", "pw");
		driver.setJavascriptEnabled(true);
		assertEquals("iTrust - HCP Home", driver.getTitle());
		// click Document Office Visit
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[4]/div/h2")).click();
	    driver.findElement(By.linkText("Document Office Visit")).click();
	    // select the patient
	    driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("2");
	    driver.findElement(By.xpath("//*[@id=\"searchTarget\"]/table/tbody/tr[2]/td[1]/input")).click();
	    assertEquals(ADDRESS + "auth/hcp-uap/documentOfficeVisit.jsp", driver.getCurrentUrl());
		// Select the office visit from specific date
	    driver.findElement(By.linkText("06/10/2007")).click();
		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		
		/*
		 * For the line below, I don't really know why but if I don't change the wait time, selenium will wait
		 * the whole 10 seconds to select one option. No matter how small I change the wait time to, it will still
		 * correctly select the option. So I decided to change it to 0.5 second.
		 */
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		
	    new Select(driver.findElement(By.name("loinc"))).selectByVisibleText("10666-6 - Fungus identified - Prid - Pt - Tissue - Nominal - Fontana-Masson stain");
	    new Select(driver.findElement(By.name("labTech"))).selectByVisibleText("Lab Dude -- blood -- 1: 3; 2: 0; 3: 16;");
	    new Select(driver.findElement(By.name("labProcPriority"))).selectByVisibleText("1");
	    driver.findElement(By.id("add_labProcedure")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// check updated page
	    assertTrue(driver.getPageSource().contains("Lab Procedure information successfully updated."));
		assertLogged(TransactionType.LAB_PROCEDURE_ADD, 9000000000L, 2L, "");
		
		List<WebElement> element = driver.findElements(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr"));
		
		// Get row containing procedure.
		int rowi = 0;
		for (; rowi < element.size(); rowi++) {
			if (element.get(rowi).getText().startsWith("10666-6")) {
				break;
			}
		}
		assertTrue(rowi < element.size());
		// Verify data in table
		rowi++;//the xpath index starts with 1 not 0, so it needs to add one to itself.
		assertEquals("Lab Dude", driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr["+rowi+"]/td[2]")).getText());
		assertEquals("In Transit", driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr["+rowi+"]/td[3]")).getText()); // status
		assertEquals("", driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr["+rowi+"]/td[6]")).getText()); // commentary
		assertEquals("", driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr["+rowi+"]/td[7]")).getText()); // numerical results
		assertEquals("", driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr["+rowi+"]/td[8]")).getText()); // lower bound
		assertEquals("", driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr["+rowi+"]/td[9]")).getText()); // upper bound
		assertTrue(driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr["+rowi+"]/td[11]")).getText().contains("Remove")); // action
		assertTrue(driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr["+rowi+"]/td[11]")).getText().contains("Reassign")); // action
	}
	
	/**
	 * Tests adding a lab procedure with no lab tech selected. Verifies that an error message is displayed.
	 * @throws Exception
	 */
	public void testAddLabProcedureWithoutLabTech() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		
		driver = login("9000000000", "pw");
		driver.setJavascriptEnabled(true);
		assertEquals("iTrust - HCP Home", driver.getTitle());
		// click Document Office Visit
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[4]/div/h2")).click();
	    driver.findElement(By.linkText("Document Office Visit")).click();
	    // select the patient
	    driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("2");
	    driver.findElement(By.xpath("//*[@id=\"searchTarget\"]/table/tbody/tr[2]/td[1]/input")).click();
	    assertEquals(ADDRESS + "auth/hcp-uap/documentOfficeVisit.jsp", driver.getCurrentUrl());
		// Select the office visit from specific date
	    driver.findElement(By.linkText("06/10/2007")).click();
		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		
	    new Select(driver.findElement(By.name("loinc"))).selectByVisibleText("10666-6 - Fungus identified - Prid - Pt - Tissue - Nominal - Fontana-Masson stain");
	    new Select(driver.findElement(By.name("labProcPriority"))).selectByVisibleText("1");
		driver.findElement(By.id("add_labProcedure")).click();

		// check updated page
		assertFalse(driver.getPageSource().contains("Lab Procedure information successfully updated.")); // make sure successful message was not displayed
		assertTrue(driver.getPageSource().contains("A lab tech must be selected before adding a laboratory procedure.")); // fail message was displayed
	}

	/**
	 * testReassignLabProcedure
	 * @throws Exception
	 */
	public void testReassignLabProcedure() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		
		driver = login("9000000000", "pw");
		driver.setJavascriptEnabled(true);
		assertEquals("iTrust - HCP Home", driver.getTitle());
		// click Document Office Visit
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[4]/div/h2")).click();
	    driver.findElement(By.linkText("Document Office Visit")).click();
	    // select the patient
	    driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("2");
	    driver.findElement(By.xpath("//*[@id=\"searchTarget\"]/table/tbody/tr[2]/td[1]/input")).click();
	    assertEquals(ADDRESS + "auth/hcp-uap/documentOfficeVisit.jsp", driver.getCurrentUrl());
		// Select the office visit from specific date
	    driver.findElement(By.linkText("06/10/2007")).click();
		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		
		// Click the Reassign link.
		assertEquals("Nice Guy", driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr[3]/td[2]")).getText());
		assertTrue(driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr[3]/td[11]")).getText().contains("Reassign"));
		driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr[3]/td[11]/a[2]")).click();
		
		assertEquals("iTrust - Reassign Lab Procedure", driver.getTitle());
		
		// Change the currently-assigned lab tech
		new Select(driver.findElement(By.name("newLabTech"))).selectByVisibleText("Lab Dude -- blood");
	    new Select(driver.findElement(By.name("labProcPriority"))).selectByVisibleText("1");
		driver.findElement(By.id("setLabTech")).click();
		assertLogged(TransactionType.LAB_RESULTS_REASSIGN, 9000000000L, 2L, "");
		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		
		// Ensure the lab tech has been changed by logging in on more time
		driver = login("9000000000", "pw");
		driver.setJavascriptEnabled(true);
		assertEquals("iTrust - HCP Home", driver.getTitle());
		// click Document Office Visit
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[4]/div/h2")).click();
	    driver.findElement(By.linkText("Document Office Visit")).click();
	    // select the patient
	    driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("2");
	    driver.findElement(By.xpath("//*[@id=\"searchTarget\"]/table/tbody/tr[2]/td[1]/input")).click();
	    assertEquals(ADDRESS + "auth/hcp-uap/documentOfficeVisit.jsp", driver.getCurrentUrl());
		// Select the office visit from specific date
	    driver.findElement(By.linkText("06/10/2007")).click();
		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		
		assertEquals("Lab Dude", driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr[3]/td[2]")).getText());
	}
	
	/**
	 * testPatientViewLabProcedureResults
	 * @throws Exception
	 */
	public void testPatientViewLabProcedureResults() throws Exception {
		gen.clearAllTables();
		gen.standardData();

		driver = login("22", "pw");
		driver.setJavascriptEnabled(false);
		assertEquals("iTrust - Patient Home", driver.getTitle());
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("My Lab Procedures")).click();

		assertEquals("iTrust - View Lab Procedures", driver.getTitle());
		assertEquals(51,driver.findElements(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr")).size());

		int numericalColumn = 5 + 1; // because xpath index starts with 1 not 0!
		assertEquals("",driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr[14]/td["+ numericalColumn + "]")).getText());
		assertEquals("",driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr[13]/td["+ numericalColumn + "]")).getText());
		assertEquals("",driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr[12]/td["+ numericalColumn + "]")).getText());
		
		assertEquals("7", driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr[11]/td["+ numericalColumn + "]")).getText());
		assertEquals("", driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr[10]/td["+ numericalColumn + "]")).getText());
		assertEquals("", driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr[9]/td["+ numericalColumn + "]")).getText());
		
		assertEquals("", driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr[8]/td["+ numericalColumn + "]")).getText());
		assertEquals("5.23", driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr[7]/td["+ numericalColumn + "]")).getText());
		assertEquals("", driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr[6]/td["+ numericalColumn + "]")).getText());
		
		assertEquals("", driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr[5]/td["+ numericalColumn + "]")).getText());
		assertEquals("", driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr[4]/td["+ numericalColumn + "]")).getText());
		assertEquals("18", driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr[3]/td["+ numericalColumn + "]")).getText());
		
		int statusColumn = 3+1;
		assertEquals("In Transit", driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr[14]/td["+ statusColumn + "]")).getText());
		assertEquals("Received", driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr[13]/td["+ statusColumn + "]")).getText());
		assertEquals("Pending", driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr[12]/td["+ statusColumn + "]")).getText());
		
		assertEquals("Completed", driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr[11]/td["+ statusColumn + "]")).getText());
		assertEquals("In Transit", driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr[10]/td["+ statusColumn + "]")).getText());
		assertEquals("Received", driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr[9]/td["+ statusColumn + "]")).getText());
		
		assertEquals("Pending", driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr[8]/td["+ statusColumn + "]")).getText());
		assertEquals("Completed", driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr[7]/td["+ statusColumn + "]")).getText());
		assertEquals("In Transit", driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr[6]/td["+ statusColumn + "]")).getText());
		
		assertEquals("Received", driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr[5]/td["+ statusColumn + "]")).getText());
		assertEquals("Pending", driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr[4]/td["+ statusColumn + "]")).getText());
		assertEquals("Completed", driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr[3]/td["+ statusColumn + "]")).getText());
	}
	
	/**
	 * testPatient_LabProcedureView
	 * @throws Exception
	 */
	public void testPatient_LabProcedureView() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		gen.patientLabProcedures();
		
		driver = login("2", "pw");
		driver.setJavascriptEnabled(false);
		assertEquals("iTrust - Patient Home", driver.getTitle());
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("My Lab Procedures")).click();
		
		assertEquals("iTrust - View Lab Procedures", driver.getTitle());
		assertEquals(4,driver.findElements(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr")).size());
		
		assertEquals("Kelly Doctor", driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr[3]/td[1]")).getText());
		assertEquals("11/20/2011", driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr[3]/td[2]")).getText());
		assertEquals("Microscopic Observation", driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr[3]/td[3]")).getText());
		assertEquals("Completed", driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr[3]/td[4]")).getText());
		
		assertEquals("Its all done", driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr[3]/td[5]")).getText());
		assertEquals("85", driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr[3]/td[6]")).getText());
		assertEquals("grams", driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr[3]/td[7]")).getText());
		assertEquals("Normal", driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr[3]/td[8]")).getText());
	}
	
	/**
	 * testPatient_LabProcedureView2
	 * @throws Exception
	 */
	public void testPatient_LabProcedureView2() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		gen.patientLabProcedures();
		
		driver = login("1", "pw");
		driver.setJavascriptEnabled(false);
		assertEquals("iTrust - Patient Home", driver.getTitle());
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("My Lab Procedures")).click();
		
		assertEquals("iTrust - View Lab Procedures", driver.getTitle());
		assertEquals(5,driver.findElements(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr")).size());
		
		assertEquals("Kelly Doctor", driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr[3]/td[1]")).getText());
		assertEquals("10/20/2011", driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr[3]/td[2]")).getText());
		assertEquals("Specimen volume", driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr[3]/td[3]")).getText());
		assertEquals("Completed", driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr[3]/td[4]")).getText());
		
		assertEquals("", driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr[3]/td[5]")).getText());
		assertEquals("79", driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr[3]/td[6]")).getText());
		assertEquals("ml", driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr[3]/td[7]")).getText());
		assertEquals("Abnormal", driver.findElement(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr[3]/td[8]")).getText());
	}
	
	/**
	 * testPatient_LabProcedureViewChart
	 * @throws Exception
	 */
	public void testPatient_LabProcedureViewChart() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		gen.patientLabProcedures();
		
		driver = login("21", "pw");
		driver.setJavascriptEnabled(false);
		assertEquals("iTrust - Patient Home", driver.getTitle());
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("My Lab Procedures")).click();
		
		assertEquals("iTrust - View Lab Procedures", driver.getTitle());
		assertEquals(5,driver.findElements(By.xpath("//*[@id=\"labProceduresTable\"]/tbody/tr")).size());
		driver.findElement(By.id("viewResultsChart")).click();
		assertEquals("Lab Procedure Results Chart", driver.getTitle());
	}
	
	/*
	 * Authenticate HCP 9000000000 and Patient 2. HCP 9000000000 has ordered lab procedure 10763-1 for patient
	 * 1 in an office visit on 7/20/2007. InputLabResults has successfully passed. All lab procedure test data
	 * is in database. HCP 9000000000 has authenticated successfully 1. The HCP chooses to view laboratory
	 * procedure results and selects patient 2 2. The LCHP sorts by dates of the last status update. 3. The
	 * LHCP chooses the top procedure (the procedure from InputLabResults). 4. The LHCP allows viewing access
	 * to the laboratory results.
	 */
	/**
	 * testHcpLabProc
	 * @throws Exception
	 */
	public void testHcpLabProc() throws Exception {
		// login hcp
		driver = login("9000000000", "pw");
		driver.setJavascriptEnabled(true);
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		// click on Edit ND Codes
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[4]/div/h2")).click();
	    driver.findElement(By.linkText("Laboratory Procedures")).click();
	    // select the patient
	    driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("2");
	    driver.findElement(By.xpath("//*[@id=\"searchTarget\"]/table/tbody/tr[2]/td[1]/input")).click();
		// add the codes and description
		assertEquals("iTrust - View Laboratory Procedures", driver.getTitle());
		driver.findElement(By.linkText("Allow/Disallow Viewing")).click();
		assertLogged(TransactionType.LAB_RESULTS_VIEW, 9000000000L, 2L, "");
	}
	
	/**
	 * testPatientViewLabResults
	 * @throws Exception
	 */
	public void testPatientViewLabResults() throws Exception {
		driver = login("2", "pw");
		driver.setJavascriptEnabled(false);
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		
		driver.findElement(By.xpath("//*[@id=\"iTrustMenu\"]/div/div[4]/div[1]/h2")).click();
		driver.findElement(By.linkText("View My Records")).click();
		assertEquals("iTrust - View My Records", driver.getTitle());
		assertLogged(TransactionType.MEDICAL_RECORD_VIEW, 2L, 2L, "");
	}
	
	/**
	 * testLabProcedureInTransitToReceived
	 * @throws Exception
	 */
	public void testLabProcedureInTransitToReceived() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		
		driver = login("5000000001", "pw");
		driver.setJavascriptEnabled(false);
		assertEquals("iTrust - Lab Tech Home", driver.getTitle());
		
		driver.findElement(By.xpath("//*[@id=\"iTrustMenu\"]/div/div/div[1]/h2")).click();
		driver.findElement(By.linkText("All Lab Procedures")).click();
		assertEquals("iTrust - View Laboratory Procedures", driver.getTitle());
		
		// Ensure the table of received lab procedures has only one entry
		assertEquals(3, driver.findElements(By.xpath("//*[@id=\"receivedTable\"]/tbody/tr")).size());
		assertEquals("Beaker Beaker", driver.findElement(By.xpath("//*[@id=\"receivedTable\"]/tbody/tr[3]/td[5]")).getText());
		
		// Check table of in-transit lab procedures
		assertEquals(20, driver.findElements(By.xpath("//*[@id=\"inTransitTable\"]/tbody/tr")).size());
		String labProcID = driver.findElement(By.xpath("//*[@id=\"inTransitTable\"]/tbody/tr[3]/td[1]")).getText(); // lab proc id is auto-assigned
		// Click "Set to Received" on Lab Procedure
		assertEquals("Set to Received", driver.findElement(By.xpath("//*[@id=\"inTransitTable\"]/tbody/tr[3]/td[7]/button")).getText());
		driver.findElement(By.xpath("//*[@id=\"inTransitTable\"]/tbody/tr[3]/td[7]/button")).click();
		
		assertEquals("iTrust - View Laboratory Procedures", driver.getTitle());
		
		// Ensure the table of received lab procedures contains the modified lab procedure.
		assertEquals(4, driver.findElements(By.xpath("//*[@id=\"receivedTable\"]/tbody/tr")).size());
		assertEquals(labProcID, driver.findElement(By.xpath("//*[@id=\"receivedTable\"]/tbody/tr[3]/td[1]")).getText()); // check that the lab proc is now here
		
		// Check that table of in-transit lab procedures has no lab procedures
		assertEquals(19, driver.findElements(By.xpath("//*[@id=\"inTransitTable\"]/tbody/tr")).size());
	}
	
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
}
