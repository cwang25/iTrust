package edu.ncsu.csc.itrust.seleniumtests;


import java.util.concurrent.TimeUnit;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.enums.TransactionType;

/**
 */
public class PersonnelHTTPTest extends iTrustSeleniumTest {
	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
		driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080/iTrust/auth/forwardUser.jsp";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	/**
	 * testViewPrescriptionRecords
	 * @throws Exception
	 */
	@Test
	public void testViewPrescriptionRecords() throws Exception {
		driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertTextPresent("Welcome, Kelly Doctor", driver);
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("All Patients")).click();
		assertLogged(TransactionType.PATIENT_LIST_VIEW, 9000000000L, 0L, "");
		assertEquals("iTrust - View All Patients", driver.getTitle());
		driver.findElement(By.linkText("Andy Programmer")).click();
		assertLogged(TransactionType.PATIENT_HEALTH_INFORMATION_VIEW, 9000000000L, 2L, "");
		assertEquals("iTrust - Edit Personal Health Record", driver.getTitle());
		driver.findElement(By.linkText("Prescriptions")).click();
		assertLogged(TransactionType.PRESCRIPTION_REPORT_VIEW, 9000000000L, 2L, "");
		assertEquals("iTrust - Get Prescription Report", driver.getTitle());

		assertTextPresent("00904-2407", driver);
		assertTextPresent("Tetracycline", driver);
		assertTextPresent("10/10/2006 to 10/11/2006", driver);
		assertTextPresent("Kelly Doctor", driver);
		assertTextPresent("00904-2407", driver);
		assertTextPresent("10/10/2006 to 10/11/2006", driver);
		assertTextPresent("64764-1512", driver);
		assertTextPresent("10/10/2006 to 10/11/2020", driver);
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
}
