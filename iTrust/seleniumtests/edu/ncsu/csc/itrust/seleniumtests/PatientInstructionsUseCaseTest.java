package edu.ncsu.csc.itrust.seleniumteststests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.meterware.httpunit.TableRow;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class PatientInstructionsUseCaseTest extends iTrustSeleniumTest {

	/**
	 * setUp
	 */
	@Before
	public void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	
	@Test
	public void testSelectPatientFromSearch() throws Exception {
		driver.setJavascriptEnabled(true);
		gen.hcp4();
		login("9000000004", "pw", "HCP");
		driver.get(ADDRESS + "auth/getPatientID.jsp");
		selectPatientFromSearch("1");
	}
	
	/**
	 * testAcceptanceScenario1
	 * @throws Exception
	 */
	@Test
	public void testAcceptanceScenario1() throws Exception {
		gen.hcp4();

		login("9000000004", "pw", "HCP");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		// click Document Office Visit
		driver.setJavascriptEnabled(true);
		driver.findElement(By.linkText("Document Office Visit")).click();		
		selectPatientFromSearch("1");
		driver.setJavascriptEnabled(false);
		
		// click Yes, Document Office Visit
		driver.findElement(By.id("formMain")).findElements(By.tagName("input")).get(1).submit();;
		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		
		// add a new office visit
		driver.findElement(By.id("mainForm")).clear();
		WebElement dateBox = driver.findElement(By.name("visitDate"));
		dateBox.clear(); // Remove pre filled date
		dateBox.sendKeys("01/28/2011");
		new Select(driver.findElement(By.name("hospitalID"))).selectByVisibleText("Health Institute Mr. Barry");
		driver.findElement(By.name("notes")).sendKeys("Has flu.");
		driver.findElement(By.name("update")).submit();
		
		// Check that it was created
		System.out.println(driver.findElement(By.tagName("body")).getText());
		assertTrue(driver.findElement(By.tagName("body")).getText().contains("Information Successfully Updated"));
		
		// Add instructions 
		WebElement form = driver.findElement(By.id("patientInstructionsForm"));
		form.findElement(By.name("name")).sendKeys("Flu Diet");
		form.findElement(By.name("url")).sendKeys("http://www.webmd.com/cold-and-flu/flu-guide/what-to-eat-when-you-have-the-flu");
		form.findElement(By.name("comment")).sendKeys("Eat a healthy diet to help you get over the flu faster! Take your vitamins and drink lots of fluids");
		form.submit();
		
		// check updated page
		assertTrue(driver.getPageSource().contains("Patient-Specific Instructions information successfully updated."));
		assertLogged(TransactionType.PATIENT_INSTRUCTIONS_ADD, 9000000004L, 1L, "");

		WebElement tbl = driver.findElement(By.id("patientInstructionsTable"));
		assertTrue(tbl.findElements(By.tagName("tr")).get(2).getText().contains("Flu Diet"));
	}
	
	/**
	 * testAcceptanceScenario2
	 * @throws Exception
	*/
	 
	@Test
	public void testAcceptanceScenario2() throws Exception {
		driver.setJavascriptEnabled(true);
		gen.hcp4();
		gen.uc44_acceptance_scenario_2();
		
		login("9000000004", "pw", "HCP");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		// click Document Office Visit
		driver.findElement(By.linkText("Document Office Visit")).click();
		
		// select the patient
		selectPatientFromSearch("1");
		assertEquals(ADDRESS + "auth/hcp-uap/documentOfficeVisit.jsp", driver.getCurrentUrl());
		// Select the office visit from yesterday
		driver.findElement(By.partialLinkText("1/28/2011")).click();

		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		
		// Verify instructions are visible
		String rowData = getTableRow("patientInstructionsTable", 2);
		assertTrue(rowData.contains("Flu Diet"));
		
		
		// Click the remove link
		assertEquals(8, tableRows("patientInstructionsTable"));
		assertEquals(4, tableColumns("patientInstructionsTable"));
		
		clickTableButton("patientInstructionsTable", 2, "Remove");
		
		assertEquals("iTrust - Document Office Visit",  driver.getTitle());
		assertTrue(driver.getPageSource().contains("Patient-Specific Instructions information successfully updated."));
		// verify the instructions have been deleted
		assertFalse(driver.getPageSource().contains("Flu Diet"));

	}

	
	/**
	 * testAcceptanceScenario3
	 * @throws Exception
	 */
	@Test
	public void testAcceptanceScenario3() throws Exception {
		driver.setJavascriptEnabled(true);
		gen.clearAllTables();
		gen.standardData();
		gen.uc44_acceptance_scenario_3();
		
		login("9000000003", "pw", "hcp");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		// click Document Office Visit
		driver.findElement(By.linkText("Document Office Visit")).click();
		
		// select the patient
		selectPatientFromSearch("5");
		assertEquals(ADDRESS + "auth/hcp-uap/documentOfficeVisit.jsp", driver.getCurrentUrl());
		// Select the office visit from yesterday
		driver.findElement(By.partialLinkText("1/28/2011")).click();

		assertEquals("iTrust - Document Office Visit",  driver.getTitle());
		
		WebElement editForm = driver.findElementById("patientInstructionsForm");
		editForm.findElement(By.name("name")).sendKeys("Infant Milestones - 1 to 6 months.");
		editForm.findElement(By.name("url")).sendKeys("http://www.babycenter.com/0_milestone-chart-1-to-6-months_1496585.bc");
		editForm.findElement(By.name("comment")).sendKeys("Watch for Baby Programmer rolling over soon. Make sure to prevent falls from furniture.");
		editForm.submit();
		
		
		// check updated page
		assertTrue(driver.getPageSource().contains("Patient-Specific Instructions information successfully updated."));
		assertLogged(TransactionType.PATIENT_INSTRUCTIONS_ADD, 9000000003L, 5L, "");

		assertTrue(driver.getPageSource().contains("Infant Milestones - 1 to 6 months."));
		
	}
	
	/**
	 * testAcceptanceScenario4
	 * @throws Exception
	 */
	@Test
	public void testAcceptanceScenario4() throws Exception {
		driver.setJavascriptEnabled(true);
		gen.hcp4();

		login("9000000004", "pw", "hcp");
		assertEquals("iTrust - HCP Home",  driver.getTitle());
		// click Document Office Visit
		driver.findElement(By.linkText("Document Office Visit")).click();
		
		// select the patient
		selectPatientFromSearch("2");
		assertEquals(ADDRESS + "auth/hcp-uap/documentOfficeVisit.jsp", driver.getCurrentUrl());
		
		// click Yes, Document Office Visit
		driver.findElement(By.id("formMain")).findElements(By.tagName("input")).get(1).submit();;
		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		
		// add a new office visit
		
		WebElement editForm = driver.findElementById("mainForm");
		editForm.findElement(By.name("visitDate")).clear();
		editForm.findElement(By.name("visitDate")).sendKeys("01/20/2011");
		editForm.findElement(By.name("hospitalID")).sendKeys("2");
		editForm.findElement(By.name("notes")).sendKeys("Wrist Pain");
		editForm.findElement(By.id("update")).click();
		
		// Check that it was created
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
		
		// Add instructions 
		editForm = driver.findElementById("patientInstructionsForm");
		editForm.findElement(By.name("name")).sendKeys("Carpal Tunnel Syndrome Exercises");
		editForm.findElement(By.name("url")).sendKeys("http://www.eatonhand.com/hw/ctexercise.htm");
		editForm.findElement(By.name("comment")).sendKeys("Remember to try all the exercises. Use the following order: #1, #2 and #6; #3 - #5 are 'optional'. If you have any questions, please let me know!");
		editForm.submit();
		
		
		// check updated page
		assertTrue(driver.getPageSource().contains("Patient-Specific Instructions information successfully updated."));
		assertLogged(TransactionType.PATIENT_INSTRUCTIONS_ADD, 9000000004L, 2L, "");
		
		assertTrue(driver.getPageSource().contains("Carpal Tunnel Syndrome Exercises"));
	}
	
	/**
	 * testAcceptanceScenario5
	 * @throws Exception
	 */
	@Test
	@SuppressWarnings("deprecation")
	public void testAcceptanceScenario5() throws Exception {
		driver.setJavascriptEnabled(true);
		gen.hcp5();
		gen.uc44_acceptance_scenario_5();
		
		login("2", "pw", "patient");
		assertEquals("iTrust - Patient Home",  driver.getTitle());
		// click Patient Specific Instructions
		driver.findElement(By.linkText("Patient Specific Instructions")).click();
		
		assertEquals("iTrust - View Patient-Specific Instructions",  driver.getTitle());
		
		int heartburnRow = 0;
		int veinRow = 0;
		if (getTableCell("patientInstructionsTable", 2, 2).contains("Heartburn Information")) {
			heartburnRow = 2;
		} else if (getTableCell("patientInstructionsTable", 3, 2).contains("Heartburn Information")) {
			heartburnRow = 3;
		} else if (getTableCell("patientInstructionsTable", 4, 2).contains("Heartburn Information")) {
			heartburnRow = 4;
		} else {
			fail("\"Heartburn Information\" not found in table.");
		}
		
		if (getTableCell("patientInstructionsTable", 2, 2).contains("Glucose Testing Information")) {
			fail("\"Glucose Testing Information\" was found in table.");
		} else if (getTableCell("patientInstructionsTable", 3, 2).contains("Glucose Testing Information")) {
			fail("\"Glucose Testing Information\" was found in table.");
		}
		
		if (getTableCell("patientInstructionsTable", 2, 2).contains("Vein Procedure Resource")) {
			veinRow = 2;
		} else if (getTableCell("patientInstructionsTable", 3, 2).contains("Vein Procedure Resource")) {
			veinRow = 3;
		} else {
			fail("\"Vein Procedure Resource\" not found in table.");
		}
		// verify information in patient instructions table
		assertTrue(getTableCell("patientInstructionsTable", heartburnRow, 2).contains("Heartburn Information"));		
		assertTrue(getTableCell("patientInstructionsTable", heartburnRow, 1).contains("Sarah Soulcrusher"));
		assertTrue(getTableCell("patientInstructionsTable", heartburnRow, 0).contains("06/10/2007"));
		
		assertTrue(getTableCell("patientInstructionsTable", veinRow, 2).contains("Vein Procedure Resource"));
		assertTrue(getTableCell("patientInstructionsTable", veinRow, 1).contains("Kelly Doctor"));
		assertTrue(getTableCell("patientInstructionsTable", veinRow, 0).contains("06/09/2007"));
		
		// verify link address
		assertEquals("http://www.mayoclinic.com/health/sclerotherapy/MY01302",
				driver.findElement(By.linkText("Vein Procedure Resource")).getAttribute("href"));
	}
	
	/**
	 * testLinkToOfficeVisit
	 * @throws Exception
	 */
	@Test
	public void testLinkToOfficeVisit() throws Exception {
		gen.hcp4();
		gen.uc44_acceptance_scenario_2();
		
		login("1", "pw", "patient");
		assertEquals("iTrust - Patient Home",  driver.getTitle());
		// click Patient Specific instructions
		driver.findElement(By.linkText("Patient Specific Instructions")).click();

		assertEquals("iTrust - View Patient-Specific Instructions", driver.getTitle());
		
		// Follow link to office visit page
		driver.findElement(By.partialLinkText("1/28/2011")).click();
		assertEquals("iTrust - View Office Visit Details", driver.getTitle());
	}
	
	/**
	 * testMissingField
	 * @throws Exception
	 */
	@Test
	public void testMissingField() throws Exception {
		driver.setJavascriptEnabled(true);
		gen.hcp4();

		login("9000000004", "pw", "HCP");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		// click Document Office Visit
		driver.findElement(By.partialLinkText("Document Office Visit")).click();
		
		// select the patient
		selectPatientFromSearch("2");
		assertEquals(ADDRESS + "auth/hcp-uap/documentOfficeVisit.jsp", driver.getCurrentUrl());
		
		// click Yes, Document Office Visit
		driver.findElement(By.id("formMain")).findElements(By.tagName("input")).get(1).submit();;
		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		
		// add a new office visit
		WebElement editForm = driver.findElementById("mainForm");
		editForm.findElement(By.name("visitDate")).clear();
		editForm.findElement(By.name("visitDate")).sendKeys("01/20/2011");
		editForm.findElement(By.name("hospitalID")).sendKeys("2");
		editForm.findElement(By.name("notes")).sendKeys("Wrist Pain");
		editForm.findElement(By.id("update")).click();
		
		// Check that it was created
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
		
		// Add instructions, but with a field missing
		editForm = driver.findElementById("patientInstructionsForm");
		editForm.findElement(By.name("name")).sendKeys("Carpal Tunnel Syndrome Exercises");
		editForm.findElement(By.name("url")).sendKeys("http://www.eatonhand.com/hw/ctexercise.htm");
		editForm.submit();
		
		// check for error page
		assertTrue(driver.getPageSource().contains("Comments: Up to 500 alphanumeric characters, with space, and other punctuation"));
		
		// check that form fields still contain prior values
		assertTrue(driver.getPageSource().contains("Carpal Tunnel Syndrome Exercises"));
		assertTrue(driver.getPageSource().contains("http://www.eatonhand.com/hw/ctexercise.htm"));
	}
	
	/**
	 * testTooManyCharacters
	 * @throws Exception
	 */
	@Test
	public void testTooManyCharacters() throws Exception {
		driver.setJavascriptEnabled(true);
		gen.hcp4();

		login("9000000004", "pw", "HCP");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		// click Document Office Visit
		driver.findElement(By.partialLinkText("Document Office Visit")).click();
		
		// select the patient
		selectPatientFromSearch("2");
		assertEquals(ADDRESS + "auth/hcp-uap/documentOfficeVisit.jsp", driver.getCurrentUrl());
		
		// click Yes, Document Office Visit
		driver.findElement(By.id("formMain")).findElements(By.tagName("input")).get(1).submit();;
		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		
		// add a new office visit
		WebElement editForm = driver.findElementById("mainForm");
		editForm.findElement(By.name("visitDate")).clear();
		editForm.findElement(By.name("visitDate")).sendKeys("01/20/2011");
		editForm.findElement(By.name("hospitalID")).sendKeys("2");
		editForm.findElement(By.name("notes")).sendKeys("Wrist Pain");
		editForm.findElement(By.id("update")).click();
		
		// Check that it was created
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
		
		// Add instructions, but with the name is too long
		editForm = driver.findElementById("patientInstructionsForm");
		editForm.findElement(By.name("name")).sendKeys("This is a very long name. In fact it is too long for Patient Specific Instructions. The name must be less than 100 characters.");
		editForm.findElement(By.name("url")).sendKeys("http://www.eatonhand.com/hw/ctexercise.htm");
		editForm.findElement(By.name("comment")).sendKeys("Remember to try all the exercises. Use the following order: #1, #2 and #6; #3 - #5 are 'optional'. If you have any questions, please let me know!");
		editForm.submit();
		
		// check for error page
		assertTrue(driver.getPageSource().contains("Name: Up to 100 alphanumeric characters, with space, and other punctuation"));

		// check that form fields still contain prior values
		assertTrue(driver.getPageSource().contains("This is a very long name. In fact it is too long for Patient Specific Instructions. The name must be less than 100 characters."));
		assertTrue(driver.getPageSource().contains("http://www.eatonhand.com/hw/ctexercise.htm"));
		assertTrue(driver.getPageSource().contains("If you have any questions, please let me know!"));
	}
	
	/**
	 * testModifiedDate
	 * @throws Exception
	 */
	@Test
	public void testModifiedDate() throws Exception {
		driver.setJavascriptEnabled(true);
		gen.hcp4();
		gen.uc44_acceptance_scenario_2();
		
		login("9000000004", "pw", "HCP");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		// click Document Office Visit
		driver.findElement(By.partialLinkText("Document Office Visit")).click();
		
		// select the patient
		selectPatientFromSearch("1");
		assertEquals(ADDRESS + "auth/hcp-uap/documentOfficeVisit.jsp", driver.getCurrentUrl());
		// Select the office visit from yesterday
		driver.findElement(By.partialLinkText("1/28/2011")).click();

		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		
		// Verify instructions are visible
		assertTrue(getTableRow("patientInstructionsTable", 2).contains("Flu Diet"));
		
		// Click the edit link
		assertEquals(8, tableRows("patientInstructionsTable"));
		assertEquals(4, tableColumns("patientInstructionsTable"));
		clickTableButton("patientInstructionsTable", 2, "Edit");

		assertEquals("iTrust - Document Office Visit", driver.getTitle());

		// check that the form now contains the values we're editing
		assertTrue(driver.getPageSource().contains("Flu Diet"));
		assertTrue(driver.getPageSource().contains("http://www.webmd.com/cold-and-flu/flu-guide/what-to-eat-when-you-have-the-flu" 
					 ));
		assertTrue(driver.getPageSource().contains("Eat a healthy diet to help you get over the flu faster! Take your vitamins and drink lots of fluids"
				     ));
		
		WebElement editForm = driver.findElementById("patientInstructionsForm");
		editForm.findElement(By.name("comment")).sendKeys("I hate the flu!");
		editForm.submit();
		
		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		
		// Verify instructions are visible
		assertTrue(getTableRow("patientInstructionsTable", 2).contains("Flu Diet"));
		// verify the table is the same size as before
		assertEquals(8, tableRows("patientInstructionsTable"));
		assertEquals(4, tableColumns("patientInstructionsTable"));
		
		// verify that the comments have changed
		assertTrue(driver.getPageSource().contains("I hate the flu!"));
	}
	
	/**
	 * testSecondEntry
	 * @throws Exception
	 */
	@Test
	public void testSecondEntry() throws Exception {
		driver.setJavascriptEnabled(true);
		gen.hcp4();
		gen.uc44_acceptance_scenario_2();
		
		login("9000000004", "pw", "HCP");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		// click Document Office Visit
		driver.findElement(By.partialLinkText("Document Office Visit")).click();
		
		// select the patient
		selectPatientFromSearch("1");
		assertEquals(ADDRESS + "auth/hcp-uap/documentOfficeVisit.jsp", driver.getCurrentUrl());
		// Select the office visit from yesterday
		driver.findElement(By.partialLinkText("1/28/2011")).click();

		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		
		WebElement editForm = driver.findElementById("patientInstructionsForm");
		editForm.findElement(By.name("name")).sendKeys("How to get rid of the flu in 5 minutes.");
		editForm.findElement(By.name("url")).sendKeys("http://www.example.com");
		editForm.findElement(By.name("comment")).sendKeys("You should try this miracle cure. It is not bogus or anything.");
		editForm.submit();

		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		
		// Verify instructions are visible
		String rowData = getTableRow("patientInstructionsTable", 2);
		assertTrue(rowData.contains("Flu Diet"));
		
		String rowData2 = getTableRow("patientInstructionsTable", 3);
		assertTrue(rowData2.contains("How to get rid of the flu in 5 minutes."));
		
		// verify the table has one more row then before
		assertEquals(9, tableRows("patientInstructionsTable"));
		assertEquals(4, tableColumns("patientInstructionsTable"));
		
	}


}
