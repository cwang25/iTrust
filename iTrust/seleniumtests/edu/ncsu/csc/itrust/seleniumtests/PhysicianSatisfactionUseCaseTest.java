package edu.ncsu.csc.itrust.seleniumtests;


import java.util.concurrent.TimeUnit;

import org.junit.*;

import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.enums.TransactionType;

/**
 * Use Case 25
 */
public class PhysicianSatisfactionUseCaseTest extends iTrustSeleniumTest {
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		super.setUp();
		gen.standardData();		
	}


	/*
	 * 	Preconditions: Patient 2 is in the system and has authenticated successfully. 
	 *  HCP 9000000000 is in the system with address 4321 My Road St, PO BOX 2, CityName, NY, 12345-1234 and physician type Surgeon. 
	 *  Patient 2 has had 2 office visits with HCP 9000000000, and no other office visits are in the system. 
	 *  TakeSatisfactionSurveySuccess and TakeSatisfactionSurveySuccess2 have passed successfully.

	 *  1. Patient 2 chooses to view satisfaction survey results.
	 *  2. Patient 2 inputs Surgeon for physician type and zip code 12377.
	 *  3. Submit.
	 * 
	 */
	@Test
	public void testSearchForHCPSurveyResults1() throws Exception {
		gen.surveyResults();
		
		login("2", "pw");
		assertTrue(driver.getTitle().equals("iTrust - Patient Home"));
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[7]/div/h2")).click();
		driver.findElement(By.linkText("Satisfaction Survey Results")).click();
		assertEquals("Search HCP Survey Results",
				driver.findElement(By.cssSelector("#iTrustContent > div > h2"))
						.getText());
		driver.findElement(By.name("hcpZip")).clear();
		driver.findElement(By.name("hcpZip")).sendKeys("10453");
		new Select(driver.findElement(By.name("hcpSpecialty")))
				.selectByVisibleText("Surgeon");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertTextPresent("Kelly Doctor", driver);
		assertTextPresent("4321 My Road St", driver);
		assertTextPresent("PO BOX 2", driver);
		assertTextPresent("New York", driver);
		assertTextPresent("NY", driver);
		assertTextPresent("10453", driver);
		assertTextPresent("surgeon", driver);
		assertTextPresent("na", driver);
		assertTextPresent("25.00", driver);
		assertTextPresent("37.50", driver);
		assertTextPresent("1.50", driver);
		assertTextPresent("4.50", driver);
		assertTextPresent("1%", driver);
		assertLogged(TransactionType.SATISFACTION_SURVEY_VIEW, 2L, 0L, "");
	}

	@Test
	public void testSearchForHCPSurveyResults2() throws Exception {
		gen.surveyResults();
		login("8000000009", "uappass1");
		assertTrue(driver.getTitle().equals("iTrust - UAP Home"));
		driver.findElement(
				By.xpath("//div[@id='iTrustMenu']/div/div[5]/div/h2")).click();
		driver.findElement(By.linkText("Satisfaction Survey Results")).click();
		driver.findElement(By.name("hcpZip")).clear();
		driver.findElement(By.name("hcpZip")).sendKeys("27613");
		new Select(driver.findElement(By.name("hcpSpecialty")))
				.selectByVisibleText("Heart Specialist");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertTextPresent("Bad Doctor", driver);
		assertTextPresent("Avenue 1", driver);
		assertTextPresent("Avenue 2", driver);
		assertTextPresent("Raleigh", driver);
		assertTextPresent("NC", driver);
		assertTextPresent("27613", driver);
		assertTextPresent("Heart Specialist", driver);
		assertTextPresent("na", driver);
		assertTextPresent("20.00", driver);
		assertTextPresent("30.00", driver);
		assertTextPresent("1.00", driver);
		assertTextPresent("2.00", driver);
		assertTextPresent("75%", driver);
		assertLogged(TransactionType.SATISFACTION_SURVEY_VIEW, 8000000009L, 0L,
				"");
	}

	public void testSearchForHCPSurveyResults3() throws Exception {
		gen.surveyResults();
		login("9000000000", "pw");
		assertTrue(driver.getTitle().equals("iTrust - HCP Home"));
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[9]/div"))
				.click();
		driver.findElement(By.linkText("Satisfaction Survey Results")).click();
		assertEquals("Search HCP Survey Results",
				driver.findElement(By.cssSelector("#iTrustContent > div > h2"))
						.getText());
		driver.findElement(By.name("hcpZip")).clear();
		driver.findElement(By.name("hcpZip")).sendKeys("27613-1234");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertTextPresent("Good Doctor", driver);
		assertTextPresent("Street 1", driver);
		assertTextPresent("Street 2", driver);
		assertTextPresent("Raleigh", driver);
		assertTextPresent("NC", driver);
		assertTextPresent("27613", driver);
		assertTextPresent("None", driver);
		assertTextPresent("na", driver);
		assertTextPresent("10.00", driver);
		assertTextPresent("10.00", driver);
		assertTextPresent("4.67", driver);
		assertTextPresent("5.00", driver);
		assertTextPresent("50%", driver);

		assertTextPresent("Bad Doctor", driver);
		assertTextPresent("Avenue 1", driver);
		assertTextPresent("Avenue 2", driver);
		assertTextPresent("Raleigh", driver);
		assertTextPresent("NC", driver);
		assertTextPresent("27613", driver);
		assertTextPresent("Heart Specialist", driver);
		assertTextPresent("na", driver);
		assertTextPresent("20.00", driver);
		assertTextPresent("30.00", driver);
		assertTextPresent("1.00", driver);
		assertTextPresent("2.00", driver);
		assertTextPresent("75%", driver);

		assertLogged(TransactionType.SATISFACTION_SURVEY_VIEW, 9000000000L, 0L,
				"");
	}

	@Test
	public void testSearchByHospitalSurveyResults1() throws Exception {
		gen.surveyResults();
		
		login("2", "pw");
		assertTrue(driver.getTitle().equals("iTrust - Patient Home"));
		driver.findElement(
				By.xpath("//div[@id='iTrustMenu']/div/div[7]/div/h2")).click();
		driver.findElement(By.linkText("Satisfaction Survey Results")).click();
		assertTextPresent("Search HCP Survey Results", driver);
		new Select(driver.findElement(By.name("hcpHospitalID")))
				.selectByVisibleText("8181818181");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertTextPresent("Kelly Doctor", driver);
		assertTextPresent("Good Doctor", driver);
		assertTextPresent("Bad Doctor", driver);
		assertLogged(TransactionType.SATISFACTION_SURVEY_VIEW, 2L, 0L, "");
	}

	@Test
	public void testSearchByHospitalSurveyResults2() throws Exception {
		gen.surveyResults();
		login("8000000009", "uappass1");
		assertTrue(driver.getTitle().equals("iTrust - UAP Home"));
		driver.findElement(
				By.xpath("//div[@id='iTrustMenu']/div/div[5]/div/h2")).click();
		driver.findElement(By.linkText("Satisfaction Survey Results")).click();
		new Select(driver.findElement(By.name("hcpHospitalID")))
				.selectByVisibleText("9191919191");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertTextPresent("Kelly Doctor", driver);
		assertTextPresent("Good Doctor", driver);
		assertLogged(TransactionType.SATISFACTION_SURVEY_VIEW, 8000000009L, 0L,
				"");
	}

	@Test
	public void testSearchByHospitalSurveyResults3() throws Exception {
		gen.surveyResults();
		
		login("9000000000", "pw");
		assertTrue(driver.getTitle().equals("iTrust - HCP Home"));
		driver.findElement(
				By.xpath("//div[@id='iTrustMenu']/div/div[9]/div/h2")).click();
		driver.findElement(By.linkText("Satisfaction Survey Results")).click();
		new Select(driver.findElement(By.name("hcpHospitalID")))
				.selectByVisibleText("8181818181");
		new Select(driver.findElement(By.name("hcpSpecialty")))
				.selectByVisibleText("Heart Specialist");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertTextPresent("LHCP Search Results:", driver);
		assertTextPresent("Bad Doctor", driver);
		assertLogged(TransactionType.SATISFACTION_SURVEY_VIEW, 9000000000L, 0L,
				"");
	}

	@Test
	public void testSurveyResultsNoInput() throws Exception {
		login("2", "pw");
		assertTrue(driver.getTitle().equals("iTrust - Patient Home"));
		driver.findElement(
				By.xpath("//div[@id='iTrustMenu']/div/div[7]/div/h2")).click();
		driver.findElement(By.linkText("Satisfaction Survey Results")).click();
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertTextPresent("You must enter either a zip code or a hospital ID.",
				driver);
	}

	@Test
	public void testSurveyResultsTooMuchInput() throws Exception {
		login("2", "pw");
		assertTrue(driver.getTitle().equals("iTrust - Patient Home"));
		driver.findElement(
				By.xpath("//div[@id='iTrustMenu']/div/div[7]/div/h2")).click();
		driver.findElement(By.linkText("Satisfaction Survey Results")).click();
		driver.findElement(By.name("hcpZip")).clear();
		driver.findElement(By.name("hcpZip")).sendKeys("27613");
		new Select(driver.findElement(By.name("hcpHospitalID")))
				.selectByVisibleText("1");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertTextPresent(
				"Data for both Zip Code and Hospital ID is not allowed.  Please choose either Zip or Hospital ID.",
				driver);
	}

	@Test
	public void testSurveyResultsZipCodeFormat1() throws Exception {
		login("2", "pw");
		assertTrue(driver.getTitle().equals("iTrust - Patient Home"));
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[7]/div/h2")).click();
		driver.findElement(By.linkText("Satisfaction Survey Results")).click();
		driver.findElement(By.name("hcpZip")).clear();
		driver.findElement(By.name("hcpZip")).sendKeys("123");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertTextPresent("Zip Code: xxxxx or xxxxx-xxxx", driver);
	}

	@Test
	public void testSurveyResultsZipCodeFormat2() throws Exception {
		login("2", "pw");
		assertTrue(driver.getTitle().equals("iTrust - Patient Home"));
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[7]/div/h2")).click();
		driver.findElement(By.linkText("Satisfaction Survey Results")).click();
		driver.findElement(By.name("hcpZip")).clear();
		driver.findElement(By.name("hcpZip")).sendKeys("123456");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertTextPresent("Zip Code: xxxxx or xxxxx-xxxx", driver);
	}

	@Test
	public void testSurveyResultsZipCodeFormat3() throws Exception {
		login("2", "pw");
		assertTrue(driver.getTitle().equals("iTrust - Patient Home"));
		driver.findElement(
				By.xpath("//div[@id='iTrustMenu']/div/div[7]/div/h2")).click();
		driver.findElement(By.linkText("Satisfaction Survey Results")).click();
		driver.findElement(By.name("hcpZip")).clear();
		driver.findElement(By.name("hcpZip")).sendKeys("abc");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertTextPresent("Zip Code: xxxxx or xxxxx-xxxx", driver);
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
}
