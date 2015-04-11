package edu.ncsu.csc.itrust.seleniumteststests;

import java.util.concurrent.TimeUnit;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import com.gargoylesoftware.htmlunit.BrowserVersion;

import edu.ncsu.csc.itrust.enums.TransactionType;
  
public class RequestRecordsReleaseTest extends iTrustSeleniumTest {
	
	private HtmlUnitDriver driver;
	private String baseUrl;

	@Before
	public void setUp() throws Exception {
	  super.setUp();
	  gen.clearAllTables();
	  gen.standardData();	
	  driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_3_6);
	  baseUrl = "http://localhost:8080/iTrust/";
	  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	
    @Test
  public void testPatientRequestNewRecordsRelease() throws Exception {
    driver.get(baseUrl);
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("102");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("pw");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    assertLogged(TransactionType.HOME_VIEW, 102L, 0L, "");
    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
    driver.findElement(By.linkText("Request Records Release")).click();
    assertEquals("iTrust - Records Release Request History", driver.getTitle());
    driver.findElement(By.id("submitReq")).click();
    assertEquals("iTrust - Records Release Request", driver.getTitle());
    final Select selectBox = new Select(driver.findElement(By.name("releaseHospital")));
    selectBox.selectByValue("1");
    driver.findElement(By.id("recFirstName")).clear();
    driver.findElement(By.id("recFirstName")).sendKeys("Mike");
    driver.findElement(By.id("recLastName")).clear();
    driver.findElement(By.id("recLastName")).sendKeys("Myers");
    driver.findElement(By.id("recPhone")).clear();
    driver.findElement(By.id("recPhone")).sendKeys("919-123-1234");
    driver.findElement(By.id("recEmail")).clear();
    driver.findElement(By.id("recEmail")).sendKeys("mike.myers@hospital.org");
    driver.findElement(By.id("recHospitalName")).clear();
    driver.findElement(By.id("recHospitalName")).sendKeys("Testing Hospital");
    driver.findElement(By.id("recHospitalAddress1")).clear();
    driver.findElement(By.id("recHospitalAddress1")).sendKeys("101 Testing Hospital Drive");
    driver.findElement(By.id("recHospitalAddress2")).clear();
    driver.findElement(By.id("recHospitalAddress2")).sendKeys(" ");
    driver.findElement(By.id("recHospitalCity")).clear();
    driver.findElement(By.id("recHospitalCity")).sendKeys("Raleigh");
    driver.findElement(By.id("recHospitalState")).clear();
    driver.findElement(By.id("recHospitalState")).sendKeys("NC");
    driver.findElement(By.id("recHospitalZip")).clear();
    driver.findElement(By.id("recHospitalZip")).sendKeys("27606");
    driver.findElement(By.id("releaseJustification")).clear();
    driver.findElement(By.id("releaseJustification")).sendKeys("Annual records request");
    driver.findElement(By.id("verifyForm")).click();
    driver.findElement(By.id("digitalSig")).clear();
    driver.findElement(By.id("digitalSig")).sendKeys("Caldwell Hudson");
    driver.findElement(By.id("submit")).click();
	assertLogged(TransactionType.PATIENT_RELEASE_HEALTH_RECORDS, 102L, 102L, "");
    assertEquals("Request successfully sent", driver.findElement(By.cssSelector("i")).getText());
    assertEquals("Pending", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/span[2]/b/i")).getText());
    assertEquals("Patient name: Caldwell Hudson\n \n Release hospital: Health Institute Dr. E\n \n Recipient doctor information: First name: Mike Last name: Myers Phone number: 919-123-1234 Email address: mike.myers@hospital.org \n Recipient hospital information: Hospital: Testing Hospital Hospital address: 101 Testing Hospital Drive , Raleigh, NC 27606 \n Release justification: Annual records request", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[2]")).getText());
    assertEquals("First name: Mike", driver.findElement(By.cssSelector("dd")).getText());
    assertEquals("Last name: Myers", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[2]/dl/dd[2]")).getText());
    assertEquals("Phone number: 919-123-1234", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[2]/dl/dd[3]")).getText());
    assertEquals("Email address: mike.myers@hospital.org", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[2]/dl/dd[4]")).getText());
    assertEquals("Hospital: Testing Hospital", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[2]/dl[2]/dd")).getText());
    assertEquals("Hospital address: 101 Testing Hospital Drive , Raleigh, NC 27606", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[2]/dl[2]/dd[2]")).getText());
    assertEquals("Annual records request", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[2]/dl[3]/dd")).getText());
  }
    
	  @Test
	  public void testMedicalRecordsReleasePatientNoSignature() throws Exception {
	    driver.get(baseUrl);
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("102");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertLogged(TransactionType.HOME_VIEW, 102L, 0L, "");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("Request Records Release")).click();
	    driver.findElement(By.id("submitReq")).click();
	    driver.findElement(By.id("recFirstName")).clear();
	    driver.findElement(By.id("recFirstName")).sendKeys("Mike");
	    driver.findElement(By.id("recLastName")).clear();
	    driver.findElement(By.id("recLastName")).sendKeys("Myers");
	    driver.findElement(By.id("recPhone")).clear();
	    driver.findElement(By.id("recPhone")).sendKeys("919-123-1234");
	    driver.findElement(By.id("recEmail")).clear();
	    driver.findElement(By.id("recEmail")).sendKeys("mike.myers@hospital.org");
	    driver.findElement(By.id("recHospitalName")).clear();
	    driver.findElement(By.id("recHospitalName")).sendKeys("Testing Hospital");
	    driver.findElement(By.id("recHospitalAddress1")).clear();
	    driver.findElement(By.id("recHospitalAddress1")).sendKeys("101 Testing Hospital Drive");
	    driver.findElement(By.id("recHospitalAddress2")).clear();
	    driver.findElement(By.id("recHospitalAddress2")).sendKeys("");
	    driver.findElement(By.id("recHospitalCity")).clear();
	    driver.findElement(By.id("recHospitalCity")).sendKeys("Raleigh");
	    driver.findElement(By.id("recHospitalState")).clear();
	    driver.findElement(By.id("recHospitalState")).sendKeys("NC");
	    driver.findElement(By.id("recHospitalZip")).clear();
	    driver.findElement(By.id("recHospitalZip")).sendKeys("27606");
	    driver.findElement(By.id("releaseJustification")).clear();
	    driver.findElement(By.id("releaseJustification")).sendKeys("Annual records request");
	    driver.findElement(By.id("submit")).click();
	  }
	  
	  @Test
	  public void testMedicalRecordsReleasePatientNotAllFields() throws Exception {
	    driver.get(baseUrl);
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("102");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertLogged(TransactionType.HOME_VIEW, 102L, 0L, "");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("Request Records Release")).click();
	    assertEquals("iTrust - Records Release Request History", driver.getTitle());
	    driver.findElement(By.id("submitReq")).click();
	    assertEquals("iTrust - Records Release Request", driver.getTitle());
	    driver.findElement(By.id("verifyForm")).click();
	    driver.findElement(By.id("digitalSig")).clear();
	    driver.findElement(By.id("digitalSig")).sendKeys("Caldwell Hudson");
	    driver.findElement(By.id("submit")).click();
	    assertEquals("iTrust - Records Release Request", driver.getTitle());
	  }
	  
	  @Test
	  public void testPatientViewApprovedRequest() throws Exception {
	    driver.get(baseUrl);
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("102");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertLogged(TransactionType.HOME_VIEW, 102L, 0L, "");
	    driver.findElement(By.linkText("Request Records Release")).click();
	    driver.findElement(By.xpath("(//a[contains(text(),'View')])[6]")).click();
	    assertEquals("Approved", driver.findElement(By.cssSelector("span > b > i")).getText());
	    assertEquals("Patient name: Caldwell Hudson\n \n Release hospital: Health Institute Dr. E\n \n Recipient doctor information: First name: Monica Last name: Brown Phone number: 329-818-7734 Email address: monica.brown@hartfordradiology.com \n Recipient hospital information: Hospital: Hartford Radiology Ltd. Hospital address: 8941 Hargett Way, Hartford, CT 01243 \n Release justification:", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[2]")).getText());
	    assertEquals("First name: Monica", driver.findElement(By.cssSelector("dd")).getText());
	    assertEquals("Last name: Brown", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[2]/dl/dd[2]")).getText());
	    assertEquals("Phone number: 329-818-7734", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[2]/dl/dd[3]")).getText());
	    assertEquals("Email address: monica.brown@hartfordradiology.com", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[2]/dl/dd[4]")).getText());
	    assertEquals("Hospital: Hartford Radiology Ltd.", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[2]/dl[2]/dd")).getText());
	    assertEquals("Hospital address: 8941 Hargett Way, Hartford, CT 01243", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[2]/dl[2]/dd[2]")).getText());
	  }
	  
	  
	 @Test
	  public void testHCPApprovesRequest() throws Exception {
	    driver.get(baseUrl);
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
	    driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Records Release Requests")).click();
	    assertEquals("iTrust - Records Release Requests", driver.getTitle());
	    assertEquals("08/08/2010", driver.findElement(By.xpath("//table[@id='requestHistory']/tbody/tr[6]/td")).getText());
	    driver.findElement(By.xpath("(//a[contains(text(),'View')])[10]")).click();
	    assertEquals("Pending", driver.findElement(By.cssSelector("span > b > i")).getText());
	    assertEquals("Patient name: Fozzie Bear", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[2]/span")).getText());
	    assertEquals("Patient name: Fozzie Bear\n \n Release hospital: Health Institute Dr. E\n \n Recipient doctor information: First name: Brian Last name: McIntyre Phone number: 744-239-9117 Email address: mcintyre@kellerheart.com \n Recipient hospital information: Hospital: Keller Drive Heart Specialists Hospital address: 622 Center Wood Avenue, Savannah, GA 42991 \n Release justification:", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[2]")).getText());
	    assertEquals("First name: Brian", driver.findElement(By.cssSelector("dd")).getText());
	    assertEquals("Last name: McIntyre", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[2]/dl/dd[2]")).getText());
	    assertEquals("Phone number: 744-239-9117", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[2]/dl/dd[3]")).getText());
	    assertEquals("Email address: mcintyre@kellerheart.com", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[2]/dl/dd[4]")).getText());
	    assertEquals("Hospital: Keller Drive Heart Specialists", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[2]/dl[2]/dd")).getText());
	    assertEquals("Hospital address: 622 Center Wood Avenue, Savannah, GA 42991", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[2]/dl[2]/dd[2]")).getText());
	    driver.findElement(By.id("Approve")).click();
	    assertEquals("Status: Approved", driver.findElement(By.cssSelector("#iTrustContent > div")).getText());
	    assertLogged(TransactionType.HCP_RELEASE_APPROVAL, 9000000000L, 22L, "");
	  }
	 
	  @Test
	  public void testHCPDeniesRequest() throws Exception {
	    driver.get(baseUrl);
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
	    driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Records Release Requests")).click();
	    assertEquals("iTrust - Records Release Requests", driver.getTitle());
	    assertEquals("10/18/2013", driver.findElement(By.xpath("//table[@id='requestHistory']/tbody/tr[2]/td")).getText());
	    driver.findElement(By.xpath("(//a[contains(text(),'View')])[6]")).click();
	    assertEquals("Pending", driver.findElement(By.cssSelector("span > b > i")).getText());
	    assertEquals("First name: Michael", driver.findElement(By.cssSelector("dd")).getText());
	    assertEquals("Last name: Garrison", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[2]/dl/dd[2]")).getText());
	    assertEquals("Phone number: 528-912-9103", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[2]/dl/dd[3]")).getText());
	    assertEquals("Email address: mkgarrison@fairfaxchiro.com", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[2]/dl/dd[4]")).getText());
	    assertEquals("Hospital: Fairfax Chiropractic", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[2]/dl[2]/dd")).getText());
	    assertEquals("Hospital address: 72 Waywind Street, Hartford, CT 01241", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[2]/dl[2]/dd[2]")).getText());
	    assertEquals("Major back pain referral", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[2]/dl[3]/dd")).getText());
	    assertEquals("iTrust - Records Release Request", driver.getTitle());
	    driver.findElement(By.id("Deny")).click();
	    assertEquals("Status: Denied", driver.findElement(By.cssSelector("#iTrustContent > div")).getText());
	    assertLogged(TransactionType.HCP_RELEASE_DENIAL, 9000000000L, 102L, "");
	  }

	  
	  @Test
	  public void testUAPDeniesRequest() throws Exception {
	    driver.get(baseUrl);
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("8000000009");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("uappass1");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertLogged(TransactionType.HOME_VIEW, 8000000009L, 0L, "");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("Records Release Requests")).click();
	    assertEquals("iTrust - Records Release Requests", driver.getTitle());
	    assertEquals("11/23/2013", driver.findElement(By.cssSelector("td")).getText());
	    driver.findElement(By.linkText("View")).click();
	    assertEquals("Pending", driver.findElement(By.cssSelector("span > b > i")).getText());
	    assertEquals("Patient name: Fozzie Bear\n \n Release hospital: Health Institute Dr. E\n \n Recipient doctor information: First name: Connor Last name: DunBar Phone number: 919-733-1991 Email address: c.dunbar@rexhospital.org \n Recipient hospital information: Hospital: Rex Hospital Hospital address: 1829 Lake Boone Trail, Raleigh, NC 27612 \n Release justification: Blood test requested from specialist", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[2]")).getText());
	    assertEquals("First name: Connor", driver.findElement(By.cssSelector("dd")).getText());
	    assertEquals("Last name: DunBar", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[2]/dl/dd[2]")).getText());
	    assertEquals("Phone number: 919-733-1991", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[2]/dl/dd[3]")).getText());
	    assertEquals("Email address: c.dunbar@rexhospital.org", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[2]/dl/dd[4]")).getText());
	    assertEquals("Recipient hospital information:", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[2]/b[3]")).getText());
	    assertEquals("Hospital: Rex Hospital", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[2]/dl[2]/dd")).getText());
	    assertEquals("Hospital address: 1829 Lake Boone Trail, Raleigh, NC 27612", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[2]/dl[2]/dd[2]")).getText());
	    assertEquals("Blood test requested from specialist", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[2]/dl[3]/dd")).getText());
	    assertEquals("iTrust - Records Release Request", driver.getTitle());
	    driver.findElement(By.id("Deny")).click();
	    assertEquals("Denied", driver.findElement(By.cssSelector("span > b > i")).getText());
	    assertLogged(TransactionType.UAP_RELEASE_DENIAL, 8000000009L, 22L, "");
	  }
	  
	  @Test
	  public void testUAPViewsApprovedRequest() throws Exception {
	    driver.get(baseUrl);
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("8000000009");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("uappass1");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertLogged(TransactionType.HOME_VIEW, 8000000009L, 0L, "");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div")).click();
	    driver.findElement(By.linkText("Records Release Requests")).click();
	    assertEquals("iTrust - Records Release Requests", driver.getTitle());
	    assertEquals("05/03/2008", driver.findElement(By.xpath("//table[@id='requestHistory']/tbody/tr[7]/td")).getText());
	    driver.findElement(By.xpath("(//a[contains(text(),'View')])[10]")).click();
	    assertEquals("Approved", driver.findElement(By.cssSelector("span > b > i")).getText());
	    assertEquals("Patient name: Random Person\n \n Release hospital: Health Institute Dr. E\n \n Recipient doctor information: First name: Harold Last name: McClain Phone number: 916-991-4124 Email address: hmcclain@easternhealth.com \n Recipient hospital information: Hospital: East Health Services Hospital address: 9002 Asheville Avenue, Cary, NC 27511 \n Release justification: Referred for services", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[2]")).getText());
	    assertEquals("First name: Harold", driver.findElement(By.cssSelector("dd")).getText());
	    assertEquals("Last name: McClain", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[2]/dl/dd[2]")).getText());
	    assertEquals("Phone number: 916-991-4124", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[2]/dl/dd[3]")).getText());
	    assertEquals("Email address: hmcclain@easternhealth.com", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[2]/dl/dd[4]")).getText());
	    assertEquals("Hospital: East Health Services", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[2]/dl[2]/dd")).getText());
	    assertEquals("Hospital address: 9002 Asheville Avenue, Cary, NC 27511", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[2]/dl[2]/dd[2]")).getText());
	    assertEquals("Referred for services", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[2]/dl[3]/dd")).getText());
	  }
	  
	  @Test
	  public void testViewDependents() throws Exception {
	    driver.get(baseUrl);
	    driver.findElement(By.id("j_username")).click();
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("2");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("Request Records Release")).click();
	    driver.findElement(By.id("submitDep")).click();
	  }
	  
	  @Test
	  public void testInvalidInputSQLInjection() throws Exception {
	    driver.get(baseUrl);
	    driver.findElement(By.id("j_username")).click();
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("102");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertLogged(TransactionType.HOME_VIEW, 102L, 0L, "");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("Request Records Release")).click();
	    driver.findElement(By.id("submitReq")).click();
	    driver.findElement(By.id("recFirstName")).clear();
	    driver.findElement(By.id("recFirstName")).sendKeys("\\'");
	    driver.findElement(By.id("recLastName")).clear();
	    driver.findElement(By.id("recLastName")).sendKeys("\\'");
	    driver.findElement(By.id("recPhone")).clear();
	    driver.findElement(By.id("recPhone")).sendKeys("\\'");
	    driver.findElement(By.id("recEmail")).clear();
	    driver.findElement(By.id("recEmail")).sendKeys("\\'");
	    driver.findElement(By.id("recHospitalName")).clear();
	    driver.findElement(By.id("recHospitalName")).sendKeys("\\'");
	    driver.findElement(By.id("recHospitalAddress1")).clear();
	    driver.findElement(By.id("recHospitalAddress1")).sendKeys("\\'");
	    driver.findElement(By.id("recHospitalAddress2")).clear();
	    driver.findElement(By.id("recHospitalAddress2")).sendKeys("\\'");
	    driver.findElement(By.id("recHospitalCity")).clear();
	    driver.findElement(By.id("recHospitalCity")).sendKeys("\\'");
	    driver.findElement(By.id("recHospitalState")).clear();
	    driver.findElement(By.id("recHospitalState")).sendKeys("\\'");
	    driver.findElement(By.id("recHospitalZip")).clear();
	    driver.findElement(By.id("recHospitalZip")).sendKeys("\\'");
	    driver.findElement(By.id("releaseJustification")).clear();
	    driver.findElement(By.id("releaseJustification")).sendKeys("Annual records request");
	    driver.findElement(By.id("submit")).click();
	  }

}

