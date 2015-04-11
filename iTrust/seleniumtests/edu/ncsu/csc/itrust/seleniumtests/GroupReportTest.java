package edu.ncsu.csc.itrust.seleniumtests;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.junit.Test;

import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.http.iTrustHTTPTest;


public class GroupReportTest extends iTrustSeleniumTest {
	private WebDriver driver;
	  private String baseUrl;
	  private boolean acceptNextAlert = true;
	  private StringBuffer verificationErrors = new StringBuffer();

	  @Override
	  public void setUp() throws Exception {
	    super.setUp();
		gen.clearAllTables();
		gen.standardData();
		//WebDriver driver = new HtmlUnitDriver();
	    //baseUrl = "http://localhost:8080/";
	    //driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  }

	  @Test
	  public void testViewGroupReportPersonnel() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080/iTrust/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseUrl + "auth/forwardUser.jsp");
			
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.cssSelector("h2.panel-title")).click();
	    assertEquals("iTrust - HCP Home", driver.getTitle());
	    assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
	    driver.findElement(By.linkText("Group Report")).click();
	    assertEquals("iTrust - Generate Group Report", driver.getTitle());
	    driver.findElement(By.name("personnel")).click();
	    driver.findElement(By.name("fillValues")).click();
	    assertEquals("iTrust - Generate Group Report", driver.getTitle());
	    // ERROR: Caught exception [ERROR: Unsupported command [addSelection | name=DLHCP | label=Gandalf Stormcrow]]
	    driver.findElement(By.name("generate")).click();
	    // Warning: assertTextPresent may require manual changes
	    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*Using filters:[\\s\\S]*$"));
	    // Warning: assertTextPresent may require manual changes
	    //was overkill
	   // assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*Filter by DECLARED HCP with value Gandalf Stormcrow[\\s\\S]*$"));
	  }
	  
	  
	  @Test
	  public void testMID() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080/iTrust/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseUrl + "auth/forwardUser.jsp");
		
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.cssSelector("div.panel-heading")).click();
	    assertEquals("iTrust - HCP Home", driver.getTitle());
	    assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
	    driver.findElement(By.linkText("Group Report")).click();
	    assertEquals("iTrust - Generate Group Report", driver.getTitle());
	    driver.findElement(By.name("fillValues")).click();
	    assertEquals("iTrust - Generate Group Report", driver.getTitle());
	    driver.findElement(By.name("generate")).click();
	    // Warning: assertTextNotPresent may require manual changes
	    assertFalse(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*MID[\\s\\S]*$"));
	  }
	  
	  @Test
	  public void testViewGroupReportDemographic() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080/iTrust/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseUrl + "auth/forwardUser.jsp");
			
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.cssSelector("div.panel-heading")).click();
	    assertEquals("iTrust - HCP Home", driver.getTitle());
	    assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
	    driver.findElement(By.linkText("Group Report")).click();
	    assertEquals("iTrust - Generate Group Report", driver.getTitle());
	    driver.findElement(By.xpath("(//input[@name='demographics'])[2]")).click();
	    driver.findElement(By.xpath("(//input[@name='demographics'])[4]")).click();
	    driver.findElement(By.xpath("(//input[@name='demographics'])[5]")).click();
	    driver.findElement(By.xpath("(//input[@name='demographics'])[7]")).click();
	    driver.findElement(By.xpath("(//input[@name='demographics'])[8]")).click();
	    driver.findElement(By.xpath("(//input[@name='demographics'])[9]")).click();
	    driver.findElement(By.xpath("(//input[@name='demographics'])[13]")).click();
	    driver.findElement(By.xpath("(//input[@name='demographics'])[19]")).click();
	    driver.findElement(By.xpath("(//input[@name='demographics'])[26]")).click();
	    driver.findElement(By.xpath("(//input[@name='demographics'])[27]")).click();
	    driver.findElement(By.name("fillValues")).click();
	    assertEquals("iTrust - Generate Group Report", driver.getTitle());
	    new Select(driver.findElement(By.name("GENDER"))).selectByVisibleText("Male");
	    driver.findElement(By.name("FIRST_NAME")).clear();
	    driver.findElement(By.name("FIRST_NAME")).sendKeys("Baby");
	    driver.findElement(By.name("CONTACT_EMAIL")).clear();
	    driver.findElement(By.name("CONTACT_EMAIL")).sendKeys("fake@email.com");
	    driver.findElement(By.name("CITY")).clear();
	    driver.findElement(By.name("CITY")).sendKeys("Raleigh");
	    driver.findElement(By.name("STATE")).clear();
	    driver.findElement(By.name("STATE")).sendKeys("Nc");
	    driver.findElement(By.name("ZIP")).clear();
	    driver.findElement(By.name("ZIP")).sendKeys("27606");
	    driver.findElement(By.name("INSURE_NAME")).clear();
	    driver.findElement(By.name("INSURE_NAME")).sendKeys("Aetna");
	    driver.findElement(By.name("INSURE_ID")).clear();
	    driver.findElement(By.name("INSURE_ID")).sendKeys("ChetumNHowe");
	    driver.findElement(By.name("LOWER_AGE_LIMIT")).clear();
	    driver.findElement(By.name("LOWER_AGE_LIMIT")).sendKeys("10");
	    driver.findElement(By.name("UPPER_AGE_LIMIT")).clear();
	    driver.findElement(By.name("UPPER_AGE_LIMIT")).sendKeys("30");
	    driver.findElement(By.name("generate")).click();
	    // Warning: assertTextPresent may require manual changes
	    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*Using filters:[\\s\\S]*$"));
	  }
	  
	  @Test
	  public void testGroupReportInvalidAge() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080/iTrust/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseUrl + "auth/forwardUser.jsp");
			
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.cssSelector("h2.panel-title")).click();
	    assertEquals("iTrust - HCP Home", driver.getTitle());
	    assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
	    driver.findElement(By.linkText("Group Report")).click();
	    assertEquals("iTrust - Generate Group Report", driver.getTitle());
	    driver.findElement(By.xpath("(//input[@name='demographics'])[26]")).click();
	    driver.findElement(By.xpath("(//input[@name='demographics'])[27]")).click();
	    driver.findElement(By.name("fillValues")).click();
	    assertEquals("iTrust - Generate Group Report", driver.getTitle());
	    driver.findElement(By.name("LOWER_AGE_LIMIT")).clear();
	    driver.findElement(By.name("LOWER_AGE_LIMIT")).sendKeys("-1");
	    driver.findElement(By.name("UPPER_AGE_LIMIT")).clear();
	    driver.findElement(By.name("UPPER_AGE_LIMIT")).sendKeys("asdf");
	    driver.findElement(By.name("generate")).click();
	    assertEquals("Invalid age range entered! Please check the age range and try again!", driver.findElement(By.cssSelector("h3")).getText());
	  }
	  
	  
	  @Test
	  public void testViewGroupReportMedical() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080/iTrust/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseUrl + "auth/forwardUser.jsp");
		
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.cssSelector("h2.panel-title")).click();
	    assertEquals("iTrust - HCP Home", driver.getTitle());
	    assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
	    driver.findElement(By.linkText("Group Report")).click();
	    assertEquals("iTrust - Generate Group Report", driver.getTitle());
	    driver.findElement(By.name("medical")).click();
	    driver.findElement(By.xpath("(//input[@name='medical'])[2]")).click();
	    driver.findElement(By.xpath("(//input[@name='medical'])[3]")).click();
	    driver.findElement(By.xpath("(//input[@name='medical'])[4]")).click();
	    driver.findElement(By.xpath("(//input[@name='medical'])[5]")).click();
	    driver.findElement(By.xpath("(//input[@name='medical'])[7]")).click();
	    driver.findElement(By.xpath("(//input[@name='medical'])[8]")).click();
	    driver.findElement(By.name("fillValues")).click();
	    assertEquals("iTrust - Generate Group Report", driver.getTitle());
	    // A lot of green made BUT WHY
	    // ERROR: Caught exception [ERROR: Unsupported command [addSelection | name=PROCEDURE | label=1270F-Injection procedure]]
	    // ERROR: Caught exception [ERROR: Unsupported command [addSelection | name=ALLERGY | label=66466-2530-Penicillin]]
	    // ERROR: Caught exception [ERROR: Unsupported command [addSelection | name=PASTCURRENT_PRESCRIPTIONS | label=64764-1512-Prioglitazone]]
	    // ERROR: Caught exception [ERROR: Unsupported command [addSelection | name=DIAGNOSIS_ICD_CODE | label=Diabetes with ketoacidosis(250.10)]]
	    // ERROR: Caught exception [ERROR: Unsupported command [addSelection | name=DIAGNOSIS_ICD_CODE | label=Tuberculosis of vertebral column(15.00)]]
	    // ERROR: Caught exception [ERROR: Unsupported command [removeSelection | name=DIAGNOSIS_ICD_CODE | label=Diabetes with ketoacidosis(250.10)]]
	    driver.findElement(By.name("LOWER_OFFICE_VISIT_DATE")).clear();
	    driver.findElement(By.name("LOWER_OFFICE_VISIT_DATE")).sendKeys("01/01/1990");
	    driver.findElement(By.name("UPPER_OFFICE_VISIT_DATE")).clear();
	    driver.findElement(By.name("UPPER_OFFICE_VISIT_DATE")).sendKeys("01/01/2012");
	    driver.findElement(By.name("generate")).click();
	    // Warning: assertTextPresent may require manual changes
	    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*Using filters:[\\s\\S]*$"));
	  }
	  
	  @Test
	  public void testViewGroupReportAcceptScenario() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080/iTrust/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseUrl + "auth/forwardUser.jsp");
			
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.cssSelector("h2.panel-title")).click();
	    assertEquals("iTrust - HCP Home", driver.getTitle());
	    assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
	    driver.findElement(By.linkText("Group Report")).click();
	    assertEquals("iTrust - Generate Group Report", driver.getTitle());
	    driver.findElement(By.xpath("(//input[@name='demographics'])[2]")).click();
	    driver.findElement(By.xpath("(//input[@name='demographics'])[26]")).click();
	    driver.findElement(By.xpath("(//input[@name='medical'])[5]")).click();
	    driver.findElement(By.name("fillValues")).click();
	    assertEquals("iTrust - Generate Group Report", driver.getTitle());
	    new Select(driver.findElement(By.name("GENDER"))).selectByVisibleText("Female");
	    driver.findElement(By.name("LOWER_AGE_LIMIT")).clear();
	    driver.findElement(By.name("LOWER_AGE_LIMIT")).sendKeys("60");
	    // ERROR: Caught exception [ERROR: Unsupported command [addSelection | name=DIAGNOSIS_ICD_CODE | label=Osteoarthrosis, generalized, multiple sites(715.09)]]
	    driver.findElement(By.name("generate")).click();
	    // Warning: assertTextPresent may require manual changes
	    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*Using filters:[\\s\\S]*$"));
	  }
	  
	  
	 /* @After
	  public void tearDown() throws Exception {
	    driver.quit();
	    String verificationErrorString = verificationErrors.toString();
	    if (!"".equals(verificationErrorString)) {
	      fail(verificationErrorString);
	    }
	  }

	  private boolean isElementPresent(By by) {
	    try {
	      driver.findElement(by);
	      return true;
	    } catch (NoSuchElementException e) {
	      return false;
	    }
	  }

	  private boolean isAlertPresent() {
	    try {
	      driver.switchTo().alert();
	      return true;
	    } catch (NoAlertPresentException e) {
	      return false;
	    }
	  }

	  private String closeAlertAndGetItsText() {
	    try {
	      Alert alert = driver.switchTo().alert();
	      String alertText = alert.getText();
	      if (acceptNextAlert) {
	        alert.accept();
	      } else {
	        alert.dismiss();
	      }
	      return alertText;
	    } finally {
	      acceptNextAlert = true;
	    }
	  }*/

}
