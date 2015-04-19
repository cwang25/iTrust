package edu.ncsu.csc.itrust.seleniumtests;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class PHIRecordTest extends iTrustSeleniumTest{
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	
	 /**
	 * Authenticate HCP
	 * MID: 9000000000
	 * Password: pw
	 * Choose Edit Basic Health History
	 * enter 0000000002 and confirm
	 * Enter fields:
	 * Height: 0
	 * Weight: 0
	 * Blood Pressure: 999/000
	 * Smokes: Y
	 * HDL: 50
	 * LDL: 200
	 * Triglycerides: 200
	 * Confirm and approve entries
	 */
	/**
	 * testCreatePHIRecord
	 * @throws Exception
	 */
	public void testCreatePHIRecord() throws Exception {
		driver.get(ADDRESS + "auth/forwardUser.jsp");
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    
	    driver.get(ADDRESS + "/iTrust/auth/hcp/home.jsp");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("Document Office Visit")).click();
	    driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("2");
	    driver.findElement(By.xpath("//input[@value='2' and @type='button']")).click();
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.id("update")).click();
	    try {
	      assertEquals("Information Successfully Updated", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	    driver.findElement(By.name("height")).clear();
	    driver.findElement(By.name("height")).sendKeys("0");
	    driver.findElement(By.name("weight")).clear();
	    driver.findElement(By.name("weight")).sendKeys("0");
	    new Select(driver.findElement(By.id("isSmoker"))).selectByVisibleText("1 - Current every day smoker");
	    new Select(driver.findElement(By.id("householdSmokingStatus"))).selectByVisibleText("1 - non-smoking household");
	    driver.findElement(By.name("bloodPressureN")).clear();
	    driver.findElement(By.name("bloodPressureN")).sendKeys("999");
	    driver.findElement(By.name("bloodPressureD")).clear();
	    driver.findElement(By.name("bloodPressureD")).sendKeys("0");
	    driver.findElement(By.name("bloodPressureD")).clear();
	    driver.findElement(By.name("bloodPressureD")).sendKeys("000");
	    driver.findElement(By.name("cholesterolHDL")).clear();
	    driver.findElement(By.name("cholesterolHDL")).sendKeys("50");
	    driver.findElement(By.name("cholesterolLDL")).clear();
	    driver.findElement(By.name("cholesterolLDL")).sendKeys("200");
	    driver.findElement(By.id("addHR")).click();
	    try {
	      assertEquals("Information not valid", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[3]/h2")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	    assertTrue(driver.findElement(By.cssSelector("div.errorList")).getText().contains("Height must be greater than 0\nWeight must be greater than 0"));
	}

	/*
	 * Authenticate HCP
	 * MID: 9000000000
	 * Password: pw
	 * Choose Edit Basic Health History
	 * enter 0000000002 and confirm
	 * Enter fields:
	 * Height: 10 inches
	 * Weight: 400 pounds
	 * Blood Pressure: 999/000
	 * Smokes: Y
	 * Household Smoking Status: Non-smoking household
	 * HDL: 50
	 * LDL: 200
	 * Triglycerides: 200
	 * Confirm and approve entries
	 */
	/**
	 * testCreatePHIRecord1
	 * @throws Exception
	 */
	public void testCreatePHIRecord1() throws Exception {
		driver.get(ADDRESS + "auth/forwardUser.jsp");
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    
	    driver.get(ADDRESS + "/iTrust/auth/hcp/home.jsp");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("Document Office Visit")).click();
	    driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("2");
	    driver.findElement(By.xpath("//input[@value='2' and @type='button']")).click();
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    
	    driver.findElement(By.id("update")).click();
	    try {
	      assertEquals("Information Successfully Updated", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	    driver.findElement(By.name("height")).clear();
	    driver.findElement(By.name("height")).sendKeys("10");
	    driver.findElement(By.name("weight")).clear();
	    driver.findElement(By.name("weight")).sendKeys("400");
	    new Select(driver.findElement(By.id("isSmoker"))).selectByVisibleText("1 - Current every day smoker");
	    new Select(driver.findElement(By.id("householdSmokingStatus"))).selectByVisibleText("1 - non-smoking household");
	    driver.findElement(By.name("bloodPressureN")).clear();
	    driver.findElement(By.name("bloodPressureN")).sendKeys("999");
	    driver.findElement(By.name("bloodPressureD")).clear();
	    driver.findElement(By.name("bloodPressureD")).sendKeys("000");
	    driver.findElement(By.name("cholesterolHDL")).clear();
	    driver.findElement(By.name("cholesterolHDL")).sendKeys("50");
	    driver.findElement(By.name("cholesterolLDL")).clear();
	    driver.findElement(By.name("cholesterolLDL")).sendKeys("200");
	    driver.findElement(By.name("cholesterolTri")).clear();
	    driver.findElement(By.name("cholesterolTri")).sendKeys("200");
	    driver.findElement(By.id("addHR")).click();
	    try {
	      assertEquals("Health information successfully updated.", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	    
	}

	/*
	 * Authenticate HCP
	 * MID: 9000000000
	 * Password: pw
	 * Choose Edit Basic Health History
	 * enter 0000000002 and confirm
	 * Enter fields:
	 * Height: **
	 * Weight: 400 pounds
	 * Blood Pressure: 999/000
	 * Smokes: Y
	 * Household Smoking Status: Non-smoking household
	 * HDL: 50
	 * LDL: 200
	 * Triglycerides: 200
	 * Confirm and approve entries
	 */
	/**
	 * testCreatePHIRecord6
	 * @throws Exception
	 */
	public void testCreatePHIRecord6() throws Exception {
		driver.get(ADDRESS + "auth/forwardUser.jsp");
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    
	    driver.get(ADDRESS + "/iTrust/auth/hcp/home.jsp");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("Document Office Visit")).click();
	    driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("2");
	    driver.findElement(By.xpath("//input[@value='2' and @type='button']")).click();
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    
	    driver.findElement(By.id("update")).click();
	    try {
	      assertEquals("Information Successfully Updated", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	    
	    driver.findElement(By.name("height")).clear();
	    driver.findElement(By.name("height")).sendKeys("**");
	    driver.findElement(By.name("weight")).clear();
	    driver.findElement(By.name("weight")).sendKeys("400");
	    driver.findElement(By.name("bloodPressureN")).clear();
	    driver.findElement(By.name("bloodPressureN")).sendKeys("999");
	    driver.findElement(By.name("bloodPressureD")).clear();
	    driver.findElement(By.name("bloodPressureD")).sendKeys("000");
	    driver.findElement(By.name("cholesterolHDL")).clear();
	    driver.findElement(By.name("cholesterolHDL")).sendKeys("50");
	    driver.findElement(By.name("cholesterolLDL")).clear();
	    driver.findElement(By.name("cholesterolLDL")).sendKeys("200");
	    driver.findElement(By.name("cholesterolTri")).clear();
	    driver.findElement(By.name("cholesterolTri")).sendKeys("200");
	    new Select(driver.findElement(By.id("isSmoker"))).selectByVisibleText("1 - Current every day smoker");
	    new Select(driver.findElement(By.id("householdSmokingStatus"))).selectByVisibleText("1 - non-smoking household");
	    driver.findElement(By.id("addHR")).click();
	    try {
	      assertEquals("Information not valid", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[3]/h2")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	    try {
	      assertEquals("Height: Up to 3-digit number + up to 1 decimal place", driver.findElement(By.cssSelector("div.errorList")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	}

	/*
	 * Authenticate HCP
	 * MID 9000000000
	 * Password: pw
	 * Choose Chronic Disease Risks
	 * Select and confirm patient 2.
	 */
	/**
	 * testDetectExistingHeartDiseaseRiskTest
	 * @throws Exception
	 */
	public void testDetectExistingHeartDiseaseRiskTest() throws Exception {
		driver.get(ADDRESS + "auth/forwardUser.jsp");
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    
	    driver.findElement(By.cssSelector("div.panel-heading")).click();
	    driver.findElement(By.linkText("Chronic Disease Risks")).click();
	    driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("2");
	    driver.findElement(By.xpath("//input[@value='2' and @type='button']")).click();
	    try {
	      assertEquals("Heart Disease", driver.findElement(By.cssSelector("td.subHeaderVertical")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	    try {
	      assertEquals("Patient is male\nPatient's body mass index is over 30\nPatient has hypertension\nPatient has bad cholesterol\nPatient is or was a smoker\nPatient has had related diagnoses\nPatient has a family history of this disease", driver.findElement(By.xpath("//div[@id='iTrustContent']/table/tbody/tr[3]/td")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }

	    try {
	      assertEquals("Type 2 Diabetes", driver.findElement(By.xpath("//div[@id='iTrustContent']/table/tbody/tr[4]/td")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	    try {
	      assertEquals("Patient's body mass index is over 25\nPatient has hypertension\nPatient has bad cholesterol\nPatient has had related diagnoses\nPatient has a family history of this disease", driver.findElement(By.xpath("//div[@id='iTrustContent']/table/tbody/tr[5]/td")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	}
	
	/**
	 * testNoHealthRecordException
	 * @throws Exception
	 */
	public void testNoHealthRecordException() throws Exception{
		driver.get(ADDRESS + "auth/forwardUser.jsp");
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    
	    driver.findElement(By.cssSelector("div.panel-heading")).click();
	    driver.findElement(By.linkText("Chronic Disease Risks")).click();
	    driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("4");
	    driver.findElement(By.xpath("//input[@value='4' and @type='button']")).click();
	    assertTrue(pageContains("No Data"));
	}

	/*
	 * Choose Add Patient option.
	 * Last name: Kent
	 * First name: Clark
	 * Contact email: clark@ncsu.edu
	 * Street address 1: 1100 Main Campus Dr
	 * Street address 2: Rm 500
	 * City: Raleigh
	 * State: NC
	 * Zip code: 27606-4321
	 * Phone: 919-555-2000
	 * Emergency contact name: Lana Lang
	 * Emergency contact phone: 919-400-4000 
	 * Insurance company name: BlueCross
	 * Insurance company address 1: 1000 Walnut Street
	 * Insurance company address 2: Room 5454
	 * Insurance company city: Cary
	 * Insurance company state: NC 
	 * Insurance company zip code: 27513-9999
	 * Insurance company phone: 919-300-3000
	 * Insurance identification: BLUE0000000001
	 */
	/**
	 * testCreatePatient1
	 * @throws Exception
	 */
	public void testCreatePatient1() throws Exception {
		driver.get(ADDRESS + "auth/forwardUser.jsp");
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("8000000009");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("uappass1");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    
	    driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Add Patient")).click();
	    driver.findElement(By.name("firstName")).clear();
	    driver.findElement(By.name("firstName")).sendKeys("Clark");
	    driver.findElement(By.name("lastName")).clear();
	    driver.findElement(By.name("lastName")).sendKeys("Kent");
	    driver.findElement(By.name("email")).clear();
	    driver.findElement(By.name("email")).sendKeys("clark@ncsu.edu");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    try {
	      assertEquals("New patient Clark Kent successfully added!", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	    driver.findElement(By.linkText("Continue to patient information.")).click();
	    driver.findElement(By.name("streetAddress1")).clear();
	    driver.findElement(By.name("streetAddress1")).sendKeys("1100 Main Campus Dr");
	    driver.findElement(By.name("streetAddress2")).clear();
	    driver.findElement(By.name("streetAddress2")).sendKeys("Rm 500");
	    driver.findElement(By.name("city")).clear();
	    driver.findElement(By.name("city")).sendKeys("Raleigh");
	    driver.findElement(By.name("zip")).clear();
	    driver.findElement(By.name("zip")).sendKeys("27606-4321");
	    new Select(driver.findElement(By.name("state"))).selectByVisibleText("North Carolina");
	    driver.findElement(By.name("phone")).clear();
	    driver.findElement(By.name("phone")).sendKeys("919-555-2000");
	    driver.findElement(By.name("emergencyName")).clear();
	    driver.findElement(By.name("emergencyName")).sendKeys("Lana Lang");
	    driver.findElement(By.name("emergencyPhone")).clear();
	    driver.findElement(By.name("emergencyPhone")).sendKeys("919-400-4000");
	    driver.findElement(By.name("icName")).clear();
	    driver.findElement(By.name("icName")).sendKeys("BlueCross");
	    driver.findElement(By.name("icAddress1")).clear();
	    driver.findElement(By.name("icAddress1")).sendKeys("1000 Walnut Street");
	    driver.findElement(By.name("icAddress2")).clear();
	    driver.findElement(By.name("icAddress2")).sendKeys("Room 5454");
	    driver.findElement(By.name("icCity")).clear();
	    driver.findElement(By.name("icCity")).sendKeys("Cary");
	    new Select(driver.findElement(By.name("icState"))).selectByVisibleText("North Carolina");
	    driver.findElement(By.name("icZip")).clear();
	    driver.findElement(By.name("icZip")).sendKeys("27513-9999");
	    driver.findElement(By.name("icPhone")).clear();
	    driver.findElement(By.name("icPhone")).sendKeys("919-300-3000");
	    driver.findElement(By.name("icID")).clear();
	    driver.findElement(By.name("icID")).sendKeys("BLUE0000000001");
	    driver.findElement(By.name("action")).click();
	    try {
	      assertEquals("Information Successfully Updated", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	}

	/*
	 * Auuthenticate UAP
	 * MID 8000000009
	 * Password: uappass1
	 * Choose Edit Basic Health History
	 * enter 0000000002 and confirm
	 * Enter fields:
	 * Height: 10 inches
	 * Weight: 400 pounds
	 * Blood Pressure: 999/000
	 * Smokes: Y
	 * Household Smoking Status: Non-smoking household
	 * HDL: 50
	 * LDL: 200
	 * Triglycerides: 200
	 * Confirm and approve entries
	 */
	/**
	 * testCreatePHIRecord2
	 * @throws Exception
	 */
	public void testCreatePHIRecord2() throws Exception {
		driver.get(ADDRESS + "auth/forwardUser.jsp");
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("8000000009");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("uappass1");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[5]/div")).click();
	    driver.findElement(By.linkText("Document Office Visit")).click();
	    driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("2");
	    driver.findElement(By.xpath("//input[@value='2' and @type='button']")).click();
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    
	    driver.findElement(By.id("update")).click();
	    try {
	      assertEquals("Information Successfully Updated", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	    driver.findElement(By.name("height")).clear();
	    driver.findElement(By.name("height")).sendKeys("10");
	    driver.findElement(By.name("weight")).clear();
	    driver.findElement(By.name("weight")).sendKeys("400");
	    new Select(driver.findElement(By.id("isSmoker"))).selectByVisibleText("1 - Current every day smoker");
	    new Select(driver.findElement(By.id("householdSmokingStatus"))).selectByVisibleText("1 - non-smoking household");
	    driver.findElement(By.name("bloodPressureN")).clear();
	    driver.findElement(By.name("bloodPressureN")).sendKeys("999");
	    driver.findElement(By.name("bloodPressureD")).clear();
	    driver.findElement(By.name("bloodPressureD")).sendKeys("000");
	    driver.findElement(By.name("cholesterolHDL")).clear();
	    driver.findElement(By.name("cholesterolHDL")).sendKeys("50");
	    driver.findElement(By.name("cholesterolLDL")).clear();
	    driver.findElement(By.name("cholesterolLDL")).sendKeys("200");
	    driver.findElement(By.name("cholesterolTri")).clear();
	    driver.findElement(By.name("cholesterolTri")).sendKeys("200");
	    driver.findElement(By.id("addHR")).click();
	    try {
	      assertEquals("Health information successfully updated.", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	}

	/*
	 * Auuthenticate UAP
	 * MID 8000000009
	 * Password: pw
	 * Choose Edit Basic Health History
	 * enter 0000000002 and confirm
	 * Enter fields:
	 * Height: 10 inches
	 * Weight: 400 pounds
	 * Blood Pressure: 999/000
	 * Smokes: Y
	 * Household Smoking Status: Non-smoking household
	 * HDL: 150
	 * LDL: 200
	 * Triglycerides: 200
	 * Confirm and approve entries
	 */
	/**
	 * testCreatePHIRecord3
	 * @throws Exception
	 */
	public void testCreatePHIRecord3() throws Exception {
		driver.get(ADDRESS + "auth/forwardUser.jsp");
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("8000000009");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("uappass1");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[5]/div")).click();
	    driver.findElement(By.linkText("Document Office Visit")).click();
	    driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("2");
	    driver.findElement(By.xpath("//input[@value='2' and @type='button']")).click();
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    
	    driver.findElement(By.id("update")).click();
	    try {
	      assertEquals("Information Successfully Updated", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	    
	    driver.findElement(By.name("height")).clear();
	    driver.findElement(By.name("height")).sendKeys("");
	    driver.findElement(By.name("height")).clear();
	    driver.findElement(By.name("height")).sendKeys("10");
	    driver.findElement(By.name("weight")).clear();
	    driver.findElement(By.name("weight")).sendKeys("400");
	    new Select(driver.findElement(By.id("isSmoker"))).selectByVisibleText("1 - Current every day smoker");
	    new Select(driver.findElement(By.id("householdSmokingStatus"))).selectByVisibleText("1 - non-smoking household");
	    driver.findElement(By.name("bloodPressureN")).clear();
	    driver.findElement(By.name("bloodPressureN")).sendKeys("999");
	    driver.findElement(By.name("bloodPressureD")).clear();
	    driver.findElement(By.name("bloodPressureD")).sendKeys("000");
	    driver.findElement(By.name("cholesterolHDL")).clear();
	    driver.findElement(By.name("cholesterolHDL")).sendKeys("150");
	    driver.findElement(By.name("cholesterolLDL")).clear();
	    driver.findElement(By.name("cholesterolLDL")).sendKeys("200");
	    driver.findElement(By.name("cholesterolTri")).clear();
	    driver.findElement(By.name("cholesterolTri")).sendKeys("200");
	    driver.findElement(By.id("addHR")).click();
	    try {
	      assertEquals("Information not valid", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[3]/h2")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	    try {
	      assertEquals("Cholesterol HDL must be an integer in [0,89]", driver.findElement(By.cssSelector("div.errorList")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	}

	/*
	 * Auuthenticate UAP
	 * MID 8000000009
	 * Password: uappass1
	 * Choose Edit Basic Health History
	 * enter 0000000002 and confirm
	 * Enter fields:
	 * Height: 10 inches
	 * Weight: <'>
	 * Blood Pressure: 999/000
	 * Smokes: Y
	 * Household Smoking Status: Non-smoking household
	 * HDL: 50
	 * LDL: 200
	 * Triglycerides: 200
	 * Confirm and approve entries
	*/
	/**
	 * testCreatePHIRecord4
	 * @throws Exception
	 */
	public void testCreatePHIRecord4() throws Exception {
		driver.get(ADDRESS + "auth/forwardUser.jsp");
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("8000000009");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("uappass1");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[5]/div")).click();
	    driver.findElement(By.linkText("Document Office Visit")).click();
	    driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("2");
	    driver.findElement(By.xpath("//input[@value='2' and @type='button']")).click();
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    
	    driver.findElement(By.id("update")).click();
	    try {
	      assertEquals("Information Successfully Updated", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	    
	    driver.findElement(By.name("height")).clear();
	    driver.findElement(By.name("height")).sendKeys("10");
	    driver.findElement(By.name("weight")).clear();
	    driver.findElement(By.name("weight")).sendKeys("<'>");
	    driver.findElement(By.name("bloodPressureN")).clear();
	    driver.findElement(By.name("bloodPressureN")).sendKeys("999");
	    driver.findElement(By.name("bloodPressureD")).clear();
	    driver.findElement(By.name("bloodPressureD")).sendKeys("000");
	    driver.findElement(By.name("cholesterolHDL")).clear();
	    driver.findElement(By.name("cholesterolHDL")).sendKeys("50");
	    driver.findElement(By.name("cholesterolLDL")).clear();
	    driver.findElement(By.name("cholesterolLDL")).sendKeys("200");
	    driver.findElement(By.name("cholesterolTri")).clear();
	    driver.findElement(By.name("cholesterolTri")).sendKeys("200");
	    new Select(driver.findElement(By.id("isSmoker"))).selectByVisibleText("1 - Current every day smoker");
	    new Select(driver.findElement(By.id("householdSmokingStatus"))).selectByVisibleText("1 - non-smoking household");
	    driver.findElement(By.id("addHR")).click();
	    try {
	      assertEquals("Information not valid", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[3]/h2")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	    try {
	      assertEquals("Weight: Up to 4-digit number + up to 1 decimal place", driver.findElement(By.cssSelector("div.errorList")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	}

	/*
	 * Auuthenticate UAP
	 * MID 8000000009
	 * Password: uappass1
	 * Choose Edit Basic Health History
	 * enter 0000000002 and confirm
	 * Enter fields:
	 * Height: 10 inches
	 * Weight: 40000 pounds
	 * Blood Pressure: 999/000
	 * Smokes: Y
	 * Household Smoking Status: Non-smoking household
	 * HDL: 50
	 * LDL: 200
	 * Triglycerides: 200
	 * Confirm and approve entries
	 */
	/**
	 * testCreatePHIRecord5
	 * @throws Exception
	 */
	public void testCreatePHIRecord5() throws Exception {
		driver.get(ADDRESS + "auth/forwardUser.jsp");
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("8000000009");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("uappass1");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[5]/div")).click();
	    driver.findElement(By.linkText("Document Office Visit")).click();
	    driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("2");
	    driver.findElement(By.xpath("//input[@value='2' and @type='button']")).click();
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    
	    driver.findElement(By.id("update")).click();
	    try {
	      assertEquals("Information Successfully Updated", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	    
	    driver.findElement(By.name("height")).clear();
	    driver.findElement(By.name("height")).sendKeys("10");
	    driver.findElement(By.name("weight")).clear();
	    driver.findElement(By.name("weight")).sendKeys("40000");
	    new Select(driver.findElement(By.id("isSmoker"))).selectByVisibleText("1 - Current every day smoker");
	    new Select(driver.findElement(By.id("householdSmokingStatus"))).selectByVisibleText("1 - non-smoking household");
	    driver.findElement(By.name("bloodPressureN")).clear();
	    driver.findElement(By.name("bloodPressureN")).sendKeys("999");
	    driver.findElement(By.name("bloodPressureD")).clear();
	    driver.findElement(By.name("bloodPressureD")).sendKeys("000");
	    driver.findElement(By.name("cholesterolHDL")).clear();
	    driver.findElement(By.name("cholesterolHDL")).sendKeys("50");
	    driver.findElement(By.name("cholesterolLDL")).clear();
	    driver.findElement(By.name("cholesterolLDL")).sendKeys("200");
	    driver.findElement(By.name("cholesterolTri")).clear();
	    driver.findElement(By.name("cholesterolTri")).sendKeys("200");
	    driver.findElement(By.id("addHR")).click();
	    try {
	      assertEquals("Information not valid", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[3]/h2")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	    try {
	      assertEquals("Weight: Up to 4-digit number + up to 1 decimal place", driver.findElement(By.cssSelector("div.errorList")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	}
	
	

}
