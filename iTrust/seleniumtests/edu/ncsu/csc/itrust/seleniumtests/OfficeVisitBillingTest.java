
package edu.ncsu.csc.itrust.seleniumtests;


import org.junit.*;


import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;


import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;


public class OfficeVisitBillingTest extends iTrustSeleniumTest{
  private WebDriver driver;
  private String baseUrl;
  private StringBuffer verificationErrors = new StringBuffer();
  protected TestDataGenerator generator = new TestDataGenerator();

  //@Before
  @Override 
  public void setUp() throws Exception {
	super.setUp();
	gen.uc60();
    driver = new HtmlUnitDriver();
    baseUrl = "http://localhost:8080";
  }

  @Test
  public void testPaymentLogging() throws Exception {
	driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("311");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("pw");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.findElement(By.cssSelector("div.panel-heading")).click();
    driver.findElement(By.linkText("My Bills")).click();
    //assertTrue(driver.getPageSource().contains("Payment Information"));
    driver.findElement(By.linkText("12/02/2013")).click();
    //driver.get(baseUrl + "/iTrust/auth/patient/payBill.jsp?billID=82");
    driver.findElement(By.id("Ins")).click();
    driver.findElement(By.name("insID")).clear();
    driver.findElement(By.name("insID")).sendKeys("Sean Ford");
    driver.findElement(By.name("insHolder")).clear();
    driver.findElement(By.name("insHolder")).sendKeys("2324198");
    driver.findElement(By.name("insProvider")).clear();
    driver.findElement(By.name("insProvider")).sendKeys("Blue Cross");
    driver.findElement(By.name("insAdd1")).clear();
    driver.findElement(By.name("insAdd1")).sendKeys("123 Fake Street");
    driver.findElement(By.name("insCity")).clear();
    driver.findElement(By.name("insCity")).sendKeys("Raleigh");
    driver.findElement(By.name("insState")).clear();
    driver.findElement(By.name("insState")).sendKeys("NC");
    driver.findElement(By.name("insZip")).clear();
    driver.findElement(By.name("insZip")).sendKeys("27606");
    driver.findElement(By.name("insPhone")).clear();
    driver.findElement(By.name("insPhone")).sendKeys("555-555-5555");
    //assertTrue(driver.getPageSource().contains("Payment Information"));
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    assertTrue(driver.getPageSource().contains("Payment Information"));
    //assertLogged(TransactionType.HOME_VIEW, 311L, 311L, "");
	//assertLogged(TransactionType.PATIENT_SUBMITS_INSURANCE, 311L, 311L, "");//?

  }

  @Test
  public void testBadPolicyID() throws Exception {
	driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("311");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("pw");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.findElement(By.cssSelector("div.panel-heading")).click();
    driver.findElement(By.linkText("My Bills")).click();
    driver.findElement(By.linkText("12/02/2013")).click();

    driver.findElement(By.id("Ins")).click();
    driver.findElement(By.name("insID")).clear();
    driver.findElement(By.name("insID")).sendKeys("!@##%()");
    driver.findElement(By.name("insHolder")).clear();
    driver.findElement(By.name("insHolder")).sendKeys("2324198");
    driver.findElement(By.name("insProvider")).clear();
    driver.findElement(By.name("insProvider")).sendKeys("Blue Cross");
    driver.findElement(By.name("insAdd1")).clear();
    driver.findElement(By.name("insAdd1")).sendKeys("123 Fake Street");
    driver.findElement(By.name("insCity")).clear();
    driver.findElement(By.name("insCity")).sendKeys("Raleigh");
    driver.findElement(By.name("insState")).clear();
    driver.findElement(By.name("insState")).sendKeys("NC");
    driver.findElement(By.name("insZip")).clear();
    driver.findElement(By.name("insZip")).sendKeys("27606");
    driver.findElement(By.name("insPhone")).clear();
    driver.findElement(By.name("insPhone")).sendKeys("555-555-5555");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    assertTrue(driver.getPageSource().contains("Insurance IDs must consist of alphanumeric characters."));
    driver.findElement(By.linkText("My Bills")).click();
    assertTrue(driver.getPageSource().contains("Unsubmitted"));
    
  }
  public void testViewBilling() throws Exception {
	driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("311");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("pw");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.findElement(By.cssSelector("div.panel-heading")).click();
    driver.findElement(By.linkText("My Bills")).click();
    assertTrue(driver.getPageSource().contains("03/08/2012"));
    assertTrue(driver.getPageSource().contains("Kelly Doctor"));
    assertTrue(driver.getPageSource().contains("Submitted"));
    assertTrue(driver.getPageSource().contains("12/02/2013"));
    assertTrue(driver.getPageSource().contains("Meredith Palmer"));
    assertTrue(driver.getPageSource().contains("Unsubmitted"));
    driver.findElement(By.linkText("12/02/2013")).click();
    assertTrue(driver.getPageSource().contains("Central Hospital"));
    assertTrue(driver.getPageSource().contains("General Checkup"));
    assertTrue(driver.getPageSource().contains("Sean needs to lower his sodium intake."));
  }
  public void testInsurance() throws Exception {
	driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("9000000011");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("pw");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	driver.get(baseUrl + "/iTrust/auth/getPatientID.jsp?UID_PATIENTID=309&forward=/iTrust/auth/hcp-uap/documentOfficeVisit.jsp");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.findElement(By.name("visitDate")).clear();
    driver.findElement(By.name("visitDate")).sendKeys("02/06/2014");
    driver.findElement(By.name("notes")).clear();
    driver.findElement(By.name("notes")).sendKeys("Patient seems to be doing well, Rob is encouraged to consume more iron.");
    new Select(driver.findElement(By.name("hospitalID"))).selectByVisibleText("Ninja Hospital");
    driver.findElement(By.name("isBilled")).click();
    new Select(driver.findElement(By.name("apptType"))).selectByVisibleText("General Checkup");
    driver.findElement(By.id("update")).click();
    driver.get(baseUrl + "/iTrust/logout.jsp");
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("309");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("pw");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
    driver.findElement(By.linkText("My Bills")).click();
    driver.findElement(By.linkText("02/06/2014")).click();
    driver.findElement(By.name("insID")).clear();
    driver.findElement(By.name("insID")).sendKeys("Rob Peterson");
    driver.findElement(By.name("insHolder")).clear();
    driver.findElement(By.name("insHolder")).sendKeys("2324199");
    driver.findElement(By.name("insProvider")).clear();
    driver.findElement(By.name("insProvider")).sendKeys("Blue Cross");
    driver.findElement(By.name("insAdd1")).clear();
    driver.findElement(By.name("insAdd1")).sendKeys("123 Fake Street");
    driver.findElement(By.name("insCity")).clear();
    driver.findElement(By.name("insAdd1")).sendKeys("123 Faker Street");
    driver.findElement(By.name("insCity")).clear();
    driver.findElement(By.name("insCity")).sendKeys("Raleigh");
    driver.findElement(By.name("insState")).clear();
    driver.findElement(By.name("insState")).sendKeys("NC");
    driver.findElement(By.name("insZip")).clear();
    driver.findElement(By.name("insZip")).sendKeys("27606");
    driver.findElement(By.name("insPhone")).clear();
    driver.findElement(By.name("insPhone")).sendKeys("555-555-5555");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    
    assertTrue(driver.getPageSource().contains("Payment Information"));
	//assertLogged(TransactionType.PATIENT_SUBMITS_INSURANCE, 309L, 309L, ""); //assertLogged not working...
  }
  public void testCreditCard() throws Exception {
	driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("9000000011");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("pw");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	driver.get(baseUrl + "/iTrust/auth/getPatientID.jsp?UID_PATIENTID=309&forward=/iTrust/auth/hcp-uap/documentOfficeVisit.jsp");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.findElement(By.name("visitDate")).clear();
    driver.findElement(By.name("visitDate")).sendKeys("02/06/2014");
    driver.findElement(By.name("notes")).clear();
    driver.findElement(By.name("notes")).sendKeys("Patient seems to be doing well, Rob is encouraged to consume more iron.");
    new Select(driver.findElement(By.name("hospitalID"))).selectByVisibleText("Ninja Hospital");
    driver.findElement(By.name("isBilled")).click();
    new Select(driver.findElement(By.name("apptType"))).selectByVisibleText("General Checkup");
    driver.findElement(By.id("update")).click();
    driver.get(baseUrl + "/iTrust/logout.jsp");
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("309");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("pw");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
    driver.findElement(By.linkText("My Bills")).click();
    driver.findElement(By.linkText("02/06/2014")).click();
    driver.findElement(By.name("type")).clear();
    driver.findElement(By.name("type")).sendKeys("CC");
    driver.findElement(By.name("ccHolder")).clear();
    driver.findElement(By.name("ccHolder")).sendKeys("Theresa Clar");
    driver.findElement(By.name("billAddress")).clear();
    driver.findElement(By.name("billAddress")).sendKeys("123 Fake Street, Raleigh, NC 27607");
    new Select(driver.findElement(By.name("ccType"))).selectByVisibleText("MasterCard");
    driver.findElement(By.name("ccNumber")).clear();
    driver.findElement(By.name("ccNumber")).sendKeys("5593090746812380");
    driver.findElement(By.name("cvv")).clear();
    driver.findElement(By.name("cvv")).sendKeys("000");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    
    assertTrue(driver.getPageSource().contains("Payment Information"));
	//assertLogged(TransactionType.PATIENT_PAYS_BILLS, 310L, 310L, ""); //assertLogged not working...
  }
  public void testCancelPayment() throws Exception {
	driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("311");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("pw");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.findElement(By.cssSelector("div.panel-heading")).click();
    driver.findElement(By.linkText("My Bills")).click();
    assertTrue(driver.getPageSource().contains("03/08/2012"));
    assertTrue(driver.getPageSource().contains("Kelly Doctor"));
    assertTrue(driver.getPageSource().contains("Submitted"));
    assertTrue(driver.getPageSource().contains("12/02/2013"));
    assertTrue(driver.getPageSource().contains("Meredith Palmer"));
    assertTrue(driver.getPageSource().contains("Unsubmitted"));
    driver.findElement(By.linkText("12/02/2013")).click();
    driver.findElement(By.id("Ins")).click();
    driver.findElement(By.name("insID")).clear();
    driver.findElement(By.name("insID")).sendKeys("Sean Ford");
    driver.findElement(By.name("insHolder")).clear();
    driver.findElement(By.name("insHolder")).sendKeys("2324198");
    driver.findElement(By.name("insProvider")).clear();
    driver.findElement(By.name("insProvider")).sendKeys("Blue Cross");
    driver.findElement(By.name("insAdd1")).clear();
    driver.findElement(By.name("insAdd1")).sendKeys("123 Fake Street");
    driver.findElement(By.name("insCity")).clear();
    driver.findElement(By.name("insCity")).sendKeys("Raleigh");
    driver.findElement(By.name("insState")).clear();
    driver.findElement(By.name("insState")).sendKeys("NC");
    driver.findElement(By.name("insZip")).clear();
    driver.findElement(By.name("insZip")).sendKeys("27606");
    driver.findElement(By.name("insPhone")).clear();
    driver.findElement(By.name("insPhone")).sendKeys("555-555-5555");
    driver.findElement(By.tagName("button")).click();
    driver.findElement(By.linkText("My Bills")).click();
    assertTrue(driver.getPageSource().contains("Unsubmitted"));
  }
  public void testBillNotification() throws Exception {
	driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("310");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("pw");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    
    assertFalse(driver.getPageSource().contains("new bill"));
    driver.get(baseUrl + "/iTrust/logout.jsp");
    
	driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("9000000011");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("pw");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	driver.get(baseUrl + "/iTrust/auth/getPatientID.jsp?UID_PATIENTID=310&forward=/iTrust/auth/hcp-uap/documentOfficeVisit.jsp");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.findElement(By.name("visitDate")).clear();
    driver.findElement(By.name("visitDate")).sendKeys("01/21/2014");
    driver.findElement(By.name("notes")).clear();
    driver.findElement(By.name("notes")).sendKeys("Theresa has been complaining of extreme fatigue. Bloodwork sent in for analysis.");
    new Select(driver.findElement(By.name("hospitalID"))).selectByVisibleText("Ninja Hospital");
    driver.findElement(By.name("isBilled")).click();
    new Select(driver.findElement(By.name("apptType"))).selectByVisibleText("General Checkup");
    driver.findElement(By.id("update")).click();
    driver.get(baseUrl + "/iTrust/logout.jsp");
    
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("310");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("pw");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    
    assertTrue(driver.getPageSource().contains("new bill"));
  }
  public void testVisitNotBilled() throws Exception {
		driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000011");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.get(baseUrl + "/iTrust/auth/getPatientID.jsp?UID_PATIENTID=310&forward=/iTrust/auth/hcp-uap/documentOfficeVisit.jsp");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.name("visitDate")).clear();
	    driver.findElement(By.name("visitDate")).sendKeys("01/21/2014");
	    driver.findElement(By.name("notes")).clear();
	    driver.findElement(By.name("notes")).sendKeys("Theresa has been complaining of extreme fatigue. Bloodwork sent in for analysis.");
	    new Select(driver.findElement(By.name("hospitalID"))).selectByVisibleText("Ninja Hospital");
	    new Select(driver.findElement(By.name("apptType"))).selectByVisibleText("General Checkup");
	    driver.findElement(By.id("update")).click();
	    driver.get(baseUrl + "/iTrust/logout.jsp");
	    
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("310");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.linkText("My Bills")).click();
	    assertFalse(driver.getPageSource().contains("1/21/2014"));
	    driver.findElement(By.linkText("View My Records")).click();
	    assertTrue(driver.getPageSource().contains("Jan 21, 2014"));
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
