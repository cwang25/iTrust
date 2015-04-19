package edu.ncsu.csc.itrust.seleniumtests;


import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;

import edu.ncsu.csc.itrust.enums.TransactionType;


public class TelemonitoringUseCaseTest extends iTrustSeleniumTest{

	private HtmlUnitDriver driver;
	private String baseUrl;

	@Before
	public void setUp() throws Exception {
	  super.setUp();
	  gen.clearAllTables();
	  gen.standardData();	
	  driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_24);
	  baseUrl = "http://localhost:8080/iTrust/";
	  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  driver.setJavascriptEnabled(true);
	}
	
	  @Test
	  public void testAddPatientsToMonitor() throws Exception {
	    driver.get(baseUrl);
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("iTrust - HCP Home", driver.getTitle());
	    assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[5]/div")).click();
	    driver.findElement(By.linkText("Edit Patient List")).click();
	    driver.findElement(By.id("searchBox")).sendKeys("2");
	    //driver.findElement(By.xpath("//input[@value='2']")).click();
	    JavascriptExecutor js =(JavascriptExecutor) driver; 
	    js.executeScript("parent.location.href='getPatientID.jsp?UID_PATIENTID=2&forward=hcp/editPatientList.jsp';");
	    driver.findElement(By.name("bloodPressure")).click();
	    driver.findElement(By.name("weight")).click();
	    driver.findElement(By.name("pedometer")).click();
	    assertEquals("Add Andy Programmer", driver.findElement(By.name("fSubmit")).getAttribute("value"));
	    driver.findElement(By.name("fSubmit")).click();
	    assertEquals("Patient Andy Programmer Added", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
	    assertLogged(TransactionType.PATIENT_LIST_ADD, 9000000000L, 2L, "");
	  }
	  
	  @Test
	  public void testRemovePatientsToMonitor() throws Exception {
		gen.remoteMonitoring2();
	    driver.get(baseUrl);
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("iTrust - HCP Home", driver.getTitle());
	    assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[5]/div")).click();
	    driver.findElement(By.linkText("Edit Patient List")).click();
	    JavascriptExecutor js =(JavascriptExecutor) driver; 
	    driver.findElement(By.id("searchBox")).sendKeys("1");
	    js.executeScript("parent.location.href='getPatientID.jsp?UID_PATIENTID=1&forward=hcp/editPatientList.jsp';");
	    driver.findElement(By.name("fSubmit")).click();
	    assertEquals("Patient Random Person Removed", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
	    assertLogged(TransactionType.PATIENT_LIST_REMOVE, 9000000000L, 1L, "");
	  }
	  
	  @Test
	  public void testReportPatientStatus() throws Exception {
		gen.remoteMonitoring2();
	    driver.get(baseUrl);
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("1");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("iTrust - Patient Home", driver.getTitle());
	    assertLogged(TransactionType.HOME_VIEW, 1L, 0L, "");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[6]/div/h2")).click();
	    driver.findElement(By.linkText("Report Telemedicine Status")).click();
	    driver.findElement(By.name("systolicBloodPressure")).clear();
	    driver.findElement(By.name("systolicBloodPressure")).sendKeys("100");
	    driver.findElement(By.name("diastolicBloodPressure")).clear();
	    driver.findElement(By.name("diastolicBloodPressure")).sendKeys("75");
	    driver.findElement(By.name("glucoseLevel")).clear();
	    driver.findElement(By.name("glucoseLevel")).sendKeys("120");
	    driver.findElement(By.name("action")).click();
	    assertEquals("Information Successfully Added", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
	    assertLogged(TransactionType.TELEMEDICINE_DATA_REPORT, 1L, 1L, "");
	  }
	  
	  @Test
	  public void testReportPatientWeightAndPedometer() throws Exception {
		gen.remoteMonitoring2();
	    driver.get(baseUrl);
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("1");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("iTrust - Patient Home", driver.getTitle());
	    assertLogged(TransactionType.HOME_VIEW, 1L, 0L, "");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[6]/div/h2")).click();
	    driver.findElement(By.linkText("Report Telemedicine Status")).click();
	    driver.findElement(By.name("weight")).clear();
	    driver.findElement(By.name("weight")).sendKeys("174");
	    driver.findElement(By.name("pedometerReading")).clear();
	    driver.findElement(By.name("pedometerReading")).sendKeys("8238");
	    driver.findElement(By.name("action")).click();
	    assertEquals("Information Successfully Added", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
	    assertLogged(TransactionType.TELEMEDICINE_DATA_REPORT, 1L, 1L, "");
	  }
	  
	  @Test
	  public void testViewMonitoringList() throws Exception {
		gen.remoteMonitoring3();
	    driver.get(baseUrl);
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("iTrust - HCP Home", driver.getTitle());
	    assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[5]/div/h2")).click();
	    driver.findElement(By.linkText("Monitor Patients")).click();
	    
	    assertEquals("Random Person (MID 1)", driver.findElement(By.linkText("Random Person (MID 1)")).getText());
	    assertTrue(driver.findElement(By.xpath("//div[@id='iTrustContent']/table/tbody/tr[3]/td[2]")).getText().contains("08:00:00.0"));
	    assertEquals("160", driver.findElement(By.xpath("//div[@id='iTrustContent']/table/tbody/tr[3]/td[3]")).getText());
	    assertEquals("110", driver.findElement(By.xpath("//div[@id='iTrustContent']/table/tbody/tr[3]/td[4]")).getText());
	    assertEquals("60", driver.findElement(By.xpath("//div[@id='iTrustContent']/table/tbody/tr[3]/td[5]")).getText());
	    assertEquals("Andy Programmer", driver.findElement(By.xpath("//div[@id='iTrustContent']/table/tbody/tr[3]/td[6]")).getText());
	    String rwColor = driver.findElement(By.xpath("//div[@id='iTrustContent']/table/tbody/tr[3]")).getAttribute("bgcolor");
	    assertEquals("#ffff00", rwColor);
	     
	    
	    assertEquals("Random Person (MID 1)", driver.findElement(By.xpath("(//a[contains(text(),'Random Person (MID 1)')])[2]")).getText());
	    assertTrue(driver.findElement(By.xpath("//div[@id='iTrustContent']/table/tbody/tr[4]/td[2]")).getText().contains("07:15:00"));
	    assertEquals("100", driver.findElement(By.xpath("//div[@id='iTrustContent']/table/tbody/tr[4]/td[3]")).getText());
	    assertEquals("70", driver.findElement(By.xpath("//div[@id='iTrustContent']/table/tbody/tr[4]/td[4]")).getText());
	    assertEquals("90", driver.findElement(By.xpath("//div[@id='iTrustContent']/table/tbody/tr[4]/td[5]")).getText());
	    assertEquals("FirstUAP LastUAP", driver.findElement(By.xpath("//div[@id='iTrustContent']/table/tbody/tr[4]/td[6]")).getText());
	    
	    
	    assertEquals("Random Person (MID 1)", driver.findElement(By.xpath("(//a[contains(text(),'Random Person (MID 1)')])[3]")).getText());
	    assertTrue(driver.findElement(By.xpath("//div[@id='iTrustContent']/table/tbody/tr[5]/td[2]")).getText().contains("05:30:00"));
	    assertEquals("90", driver.findElement(By.xpath("//div[@id='iTrustContent']/table/tbody/tr[5]/td[3]")).getText());
	    assertEquals("60", driver.findElement(By.xpath("//div[@id='iTrustContent']/table/tbody/tr[5]/td[4]")).getText());
	    assertEquals("80", driver.findElement(By.xpath("//div[@id='iTrustContent']/table/tbody/tr[5]/td[5]")).getText());
	    assertEquals("Random Person", driver.findElement(By.xpath("//div[@id='iTrustContent']/table/tbody/tr[5]/td[6]")).getText());
	    
	    
	    assertEquals("Baby Programmer (MID 5)", driver.findElement(By.linkText("Baby Programmer (MID 5)")).getText());
	    assertEquals("No Information Provided", driver.findElement(By.cssSelector("b")).getText());
	    assertEquals("", driver.findElement(By.xpath("//div[@id='iTrustContent']/table/tbody/tr[6]/td[3]")).getText());
	    assertEquals("", driver.findElement(By.xpath("//div[@id='iTrustContent']/table/tbody/tr[6]/td[4]")).getText());
	    assertEquals("", driver.findElement(By.xpath("//div[@id='iTrustContent']/table/tbody/tr[6]/td[5]")).getText());
	    assertEquals("", driver.findElement(By.xpath("//div[@id='iTrustContent']/table/tbody/tr[6]/td[6]")).getText());
	    String rwColor1 = driver.findElement(By.xpath("//div[@id='iTrustContent']/table/tbody/tr[6]")).getAttribute("bgcolor");
	    assertEquals("#ff6666", rwColor1);
	    assertLogged(TransactionType.TELEMEDICINE_DATA_VIEW, 9000000000L, 0L, "");
	  }
	  
	  @Test
	  public void testViewWeightAndPedometerReports() throws Exception {
		gen.remoteMonitoring5();
	    driver.get(baseUrl);
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("iTrust - HCP Home", driver.getTitle());
	    assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[5]/div/h2")).click();
	    driver.findElement(By.linkText("Monitor Patients")).click();
	    assertEquals("Random Person (MID 1)", driver.findElement(By.linkText("Random Person (MID 1)")).getText());
	    assertTrue(driver.findElement(By.xpath("//div[@id='iTrustContent']/table[2]/tbody/tr[3]/td[2]")).getText().contains("07:17:00"));
	    assertEquals("", driver.findElement(By.xpath("//div[@id='iTrustContent']/table[2]/tbody/tr[3]/td[3]")).getText());
	    assertEquals("186.5", driver.findElement(By.xpath("//div[@id='iTrustContent']/table[2]/tbody/tr[3]/td[4]")).getText());
	    assertEquals("", driver.findElement(By.xpath("//div[@id='iTrustContent']/table[2]/tbody/tr[3]/td[5]")).getText());
	    assertEquals("Random Person", driver.findElement(By.xpath("//div[@id='iTrustContent']/table[2]/tbody/tr[3]/td[6]")).getText());
	    String rwColor = driver.findElement(By.xpath("//div[@id='iTrustContent']/table/tbody/tr[3]")).getAttribute("bgcolor");
	    assertEquals("#ffff00", rwColor);
	    assertLogged(TransactionType.TELEMEDICINE_DATA_VIEW, 9000000000L, 0L, "");
	  }
	    
	  @Test
	  public void testUAPReportPatientStatus() throws Exception {
		gen.remoteMonitoringUAP();
	    driver.get(baseUrl);
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("8000000009");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("uappass1");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("iTrust - UAP Home", driver.getTitle());
	    assertLogged(TransactionType.HOME_VIEW, 8000000009L, 0L, "");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[4]/div")).click();
	    driver.findElement(By.linkText("Report Telemedicine Status")).click();
	    assertEquals("iTrust - View Monitored Patients", driver.getTitle());
	    driver.findElement(By.linkText("Andy Programmer")).click();
	    driver.findElement(By.name("systolicBloodPressure")).clear();
	    driver.findElement(By.name("systolicBloodPressure")).sendKeys("100");
	    driver.findElement(By.name("diastolicBloodPressure")).clear();
	    driver.findElement(By.name("diastolicBloodPressure")).sendKeys("75");
	    driver.findElement(By.name("glucoseLevel")).clear();
	    driver.findElement(By.name("glucoseLevel")).sendKeys("120");
	    driver.findElement(By.name("action")).click();
	    assertEquals("Information Successfully Added", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
	    assertLogged(TransactionType.TELEMEDICINE_DATA_REPORT, 8000000009L, 2L, "");
	  }
	  
	  @Test
	  public void testRepresentativeReportPatientStatus() throws Exception {
		gen.remoteMonitoring4();
	    driver.get(baseUrl);
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("2");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("iTrust - Patient Home", driver.getTitle());
	    assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[6]/div/h2")).click();
	    driver.findElement(By.linkText("Report Telemedicine Status")).click();
	    driver.findElement(By.linkText("Random Person")).click();
	    driver.findElement(By.name("glucoseLevel")).clear();
	    driver.findElement(By.name("glucoseLevel")).sendKeys("120");
	    driver.findElement(By.name("action")).click();
	    assertEquals("Information Successfully Added", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
	    assertLogged(TransactionType.TELEMEDICINE_DATA_REPORT, 2L, 1L, "");
	  }
	  
	  @Test
	  public void testRepresentativeReportWeight() throws Exception {
		gen.remoteMonitoring2();
	    driver.get(baseUrl);
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("2");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("iTrust - Patient Home", driver.getTitle());
	    assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[6]/div/h2")).click();
	    driver.findElement(By.linkText("Report Telemedicine Status")).click();
	    driver.findElement(By.linkText("Random Person")).click();
	    driver.findElement(By.name("weight")).clear();
	    driver.findElement(By.name("weight")).sendKeys("174");
	    driver.findElement(By.name("action")).click();
	    assertEquals("Information Successfully Added", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
	    assertLogged(TransactionType.TELEMEDICINE_DATA_REPORT, 2L, 1L, "");
	  }
	  
	  @Test
	  public void testUAPReportPatientPedometerReading() throws Exception {
		gen.remoteMonitoring2();
		gen.remoteMonitoringUAP();
	    driver.get(baseUrl);
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("8000000009");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("uappass1");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("iTrust - UAP Home", driver.getTitle());
	    assertLogged(TransactionType.HOME_VIEW, 8000000009L, 0L, "");
	    
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[4]/div")).click();
	    driver.findElement(By.linkText("Report Telemedicine Status")).click();
	    driver.findElement(By.linkText("Andy Programmer")).click();
	    driver.findElement(By.name("pedometerReading")).clear();
	    driver.findElement(By.name("pedometerReading")).sendKeys("9163");
	    driver.findElement(By.name("action")).click();
	    assertEquals("Information Successfully Added", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
	    assertLogged(TransactionType.TELEMEDICINE_DATA_REPORT, 8000000009L, 2L, "");
	  }
	  /**
		 * testUAPAddPatientToMonitorTest
		 * @throws Exception
		 */
	  @Test
	  public void testUAPAddPatientToMonitorTest() throws Exception {
		  driver = login("8000000009","uappass1");
		  assertLogged(TransactionType.HOME_VIEW, 8000000009L, 0L, "");
		  driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[4]/div")).click();
		  driver.findElement(By.linkText("Edit Patient List")).click();
		  driver.findElement(By.id("searchBox")).clear();
		  driver.findElement(By.id("searchBox")).sendKeys("2");
		  this.clickOnJavascriptElement(By.xpath("//input[@value='2' and @type='button']"));
		  driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		  assertTrue(driver.getPageSource().contains("Patient Andy Programmer Added"));
		  assertLogged(TransactionType.PATIENT_LIST_ADD, 8000000009L, 2L, "");
	  }
	  
	  @Test
	  public void testUAPAddHCPMonitor() throws Exception {
		gen.remoteMonitoring8();
	    driver.get(baseUrl);
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("8000000009");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("uappass1");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("iTrust - UAP Home", driver.getTitle());
	    assertLogged(TransactionType.HOME_VIEW, 8000000009L, 0L, "");
	    
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[4]/div/h2")).click();
	    driver.findElement(By.linkText("Edit Patient List")).click();
	    driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("2");
	    //driver.findElement(By.xpath("//input[@value='2']")).click();
	    JavascriptExecutor js =(JavascriptExecutor) driver; 
	    js.executeScript("parent.location.href='getPatientID.jsp?UID_PATIENTID=2&forward=hcp/editPatientList.jsp';");
//	    driver.findElement(By.id("addREMOVE")).click();
//	    //driver.findElement(By.name("confirmAction")).click();
//	    assertEquals("Patient Andy Programmer Added", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
//	    assertLogged(TransactionType.PATIENT_LIST_ADD, 8000000009L, 2L, "");
//	    
//	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[4]/div")).click();
//	    driver.findElement(By.linkText("Report Telemedicine Status")).click();
//	    assertEquals("Andy Programmer", driver.findElement(By.cssSelector("b")).getText());
//	    assertEquals("iTrust - Report Status", driver.getTitle());
//	    driver.findElement(By.name("systolicBloodPressure")).clear();
//	    driver.findElement(By.name("systolicBloodPressure")).sendKeys("110");
//	    driver.findElement(By.name("diastolicBloodPressure")).clear();
//	    driver.findElement(By.name("diastolicBloodPressure")).sendKeys("85");
//	    driver.findElement(By.name("action")).click();
//	    assertEquals("Information Successfully Added", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
//	    assertLogged(TransactionType.TELEMEDICINE_DATA_REPORT, 8000000009L, 2L, "");
//	    
//	    driver.findElement(By.linkText("Logout")).click();
//	    assertLogged(TransactionType.LOGOUT, 8000000009L, 8000000009L, "");
//	    
//	    driver.findElement(By.id("j_username")).click();
//	    driver.findElement(By.id("j_username")).clear();
//	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
//	    driver.findElement(By.id("j_password")).clear();
//	    driver.findElement(By.id("j_password")).sendKeys("pw");
//	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
//	    assertEquals("iTrust - HCP Home", driver.getTitle());
//	    assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
//	    
//	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[5]/div/h2")).click();
//	    driver.findElement(By.linkText("Monitor Patients")).click();
//	    
//	    assertEquals("Andy Programmer (MID 2)", driver.findElement(By.linkText("Andy Programmer (MID 2)")).getText());
//	    assertEquals("110", driver.findElement(By.xpath("//div[@id='iTrustContent']/table/tbody/tr[3]/td[3]")).getText());
//	    assertEquals("85", driver.findElement(By.xpath("//div[@id='iTrustContent']/table/tbody/tr[3]/td[4]")).getText());
//	    assertEquals("", driver.findElement(By.xpath("//div[@id='iTrustContent']/table/tbody/tr[3]/td[5]")).getText());
//	    assertLogged(TransactionType.TELEMEDICINE_DATA_VIEW, 9000000000L, 0L, "");
	  }
	  
	  @Test
	  public void testUAPAddReportDeleteCannotReport() throws Exception {
		gen.remoteMonitoring8();
	    driver.get(baseUrl);
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("8000000009");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("uappass1");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("iTrust - UAP Home", driver.getTitle());
	    assertLogged(TransactionType.HOME_VIEW, 8000000009L, 0L, "");
	    
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[4]/div/h2")).click();
	    driver.findElement(By.linkText("Edit Patient List")).click();
	    driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("2");
	    //driver.findElement(By.xpath("//input[@value='2']")).click();
	    JavascriptExecutor js =(JavascriptExecutor) driver; 
	    js.executeScript("parent.location.href='getPatientID.jsp?UID_PATIENTID=2&forward=hcp/editPatientList.jsp';");
//	    driver.findElement(By.id("addREMOVE")).click();
//	    //driver.findElement(By.name("confirmAction")).click();
//	    assertEquals("Patient Andy Programmer Added", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
//	    assertLogged(TransactionType.PATIENT_LIST_ADD, 8000000009L, 2L, "");
//	    
//	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[4]/div")).click();
//	    driver.findElement(By.linkText("Report Telemedicine Status")).click();
//	    assertEquals("Andy Programmer", driver.findElement(By.cssSelector("b")).getText());
//	    assertEquals("iTrust - Report Status", driver.getTitle());
//	    driver.findElement(By.name("systolicBloodPressure")).clear();
//	    driver.findElement(By.name("systolicBloodPressure")).sendKeys("100");
//	    driver.findElement(By.name("diastolicBloodPressure")).clear();
//	    driver.findElement(By.name("diastolicBloodPressure")).sendKeys("75");
//	    driver.findElement(By.name("action")).click();
//	    assertEquals("Information Successfully Added", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
//	    assertLogged(TransactionType.TELEMEDICINE_DATA_REPORT, 8000000009L, 2L, "");
//	    
//	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[4]/div/h2")).click();
//	    driver.findElement(By.linkText("Edit Patient List")).click();
//	    driver.findElement(By.id("addREMOVE")).click();
//	    //driver.findElement(By.name("confirmAction")).click();
//	    assertEquals("Patient Andy Programmer Removed", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
//	    assertLogged(TransactionType.PATIENT_LIST_ADD, 8000000009L, 2L, "");
	    
	  }
	  
	  @Test
	  public void testWeightHighLighting() throws Exception {
		gen.remoteMonitoring6();
	    driver.get(baseUrl);
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("iTrust - HCP Home", driver.getTitle());
	    assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
	    
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[5]/div")).click();
	    driver.findElement(By.linkText("Monitor Patients")).click();
	    assertEquals("Random Person (MID 1)", driver.findElement(By.linkText("Random Person (MID 1)")).getText());
	    assertTrue(driver.findElement(By.xpath("//div[@id='iTrustContent']/table[2]/tbody/tr[3]/td[2]")).getText().contains("07:17:00"));
	    assertEquals("70.0", driver.findElement(By.xpath("//div[@id='iTrustContent']/table[2]/tbody/tr[3]/td[3]")).getText());
	    assertEquals("192.5", driver.findElement(By.xpath("//div[@id='iTrustContent']/table[2]/tbody/tr[3]/td[4]")).getText());
	    assertEquals("", driver.findElement(By.xpath("//div[@id='iTrustContent']/table[2]/tbody/tr[3]/td[5]")).getText());
	    assertEquals("Random Person", driver.findElement(By.xpath("//div[@id='iTrustContent']/table[2]/tbody/tr[3]/td[6]")).getText());
	    String rwColor = driver.findElement(By.xpath("//div[@id='iTrustContent']/table[2]/tbody/tr[3]")).getAttribute("bgcolor");
	    assertEquals("#ffff00", rwColor);
	    assertLogged(TransactionType.TELEMEDICINE_DATA_VIEW, 9000000000L, 0L, "");
	  }
	  
	  @Test
	  public void testDetailedExternalData() throws Exception {
		gen.remoteMonitoring6();
	    driver.get(baseUrl);
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("iTrust - HCP Home", driver.getTitle());
	    assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
	    
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[5]/div")).click();
	    driver.findElement(By.linkText("Monitor Patients")).click();
	    driver.findElement(By.linkText("Random Person (MID 1)")).click();
	    driver.findElement(By.name("startDate")).clear();
	    driver.findElement(By.name("startDate")).sendKeys("04/04/2015");
	    driver.findElement(By.name("submit")).click();
	    assertEquals("iTrust - View Additional Info", driver.getTitle());
	    //isElementPresent(By.xpath("//div[@id='iTrustContent']/table[4]/tbody/tr[3]/td"));
	    //assertEquals("Random Person", driver.findElement(By.xpath("//div[@id='iTrustContent']/table/tbody/tr[2]/td[2]")).getText());

        //assertEquals("2015-04-08 07:17:00.0", driver.findElement(By.xpath("//div[@id='iTrustContent']/table[4]/tbody/tr[3]/td[1]")).getText());
//	    assertEquals("2015-04-07 07:17:00.0", driver.findElement(By.xpath("//div[@id='iTrustContent']/table[4]/tbody/tr[3]/td")).getText());
//	    assertEquals("70.0", driver.findElement(By.xpath("//div[@id='iTrustContent']/table[4]/tbody/tr[3]/td[2]")).getText());
//	    assertEquals("192.5", driver.findElement(By.xpath("//div[@id='iTrustContent']/table[4]/tbody/tr[3]/td[3]")).getText());
//	    assertEquals("", driver.findElement(By.xpath("//div[@id='iTrustContent']/table[4]/tbody/tr[3]/td[4]")).getText());
//	    
//	    assertEquals("2015-04-06 07:48:00.0", driver.findElement(By.xpath("//div[@id='iTrustContent']/table[4]/tbody/tr[4]/td")).getText());
//	    assertEquals("70.0", driver.findElement(By.xpath("//div[@id='iTrustContent']/table[4]/tbody/tr[4]/td[2]")).getText());
//	    assertEquals("", driver.findElement(By.xpath("//div[@id='iTrustContent']/table[4]/tbody/tr[4]/td[3]")).getText());
//	    assertEquals("8153", driver.findElement(By.xpath("//div[@id='iTrustContent']/table[4]/tbody/tr[4]/td[4]")).getText());
//	    assertEquals("8153", driver.findElement(By.xpath("//div[@id='iTrustContent']/table[4]/tbody/tr[4]/td[4]")).getText());
//	    
//	    assertEquals("2015-04-05 08:19:00.0", driver.findElement(By.xpath("//div[@id='iTrustContent']/table[4]/tbody/tr[5]/td")).getText());
//	    assertEquals("70.0", driver.findElement(By.xpath("//div[@id='iTrustContent']/table[4]/tbody/tr[5]/td[2]")).getText());
//	    assertEquals("180.0", driver.findElement(By.xpath("//div[@id='iTrustContent']/table[4]/tbody/tr[5]/td[3]")).getText());
//	    assertEquals("", driver.findElement(By.xpath("//div[@id='iTrustContent']/table[4]/tbody/tr[5]/td[4]")).getText());
//	    assertLogged(TransactionType.TELEMEDICINE_DATA_VIEW, 9000000000L, 0L, "");
	  }
	  
	  @Test
	  public void testReportPatientHeight() throws Exception {
		gen.remoteMonitoring7();
	    driver.get(baseUrl);
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("1");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("iTrust - Patient Home", driver.getTitle());
	    assertLogged(TransactionType.HOME_VIEW, 1L, 0L, "");
	    
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[6]/div/h2")).click();
	    driver.findElement(By.linkText("Report Telemedicine Status")).click();
	    driver.findElement(By.name("height")).clear();
	    driver.findElement(By.name("height")).sendKeys("73.2");
	    driver.findElement(By.name("action")).click();
	    assertEquals("Information Successfully Added", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
	    assertLogged(TransactionType.TELEMEDICINE_DATA_REPORT, 1L, 1L, "");
	    
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[6]/div/h2")).click();
	    driver.findElement(By.linkText("Report Telemedicine Status")).click();
	    driver.findElement(By.name("height")).clear();
	    driver.findElement(By.name("height")).sendKeys("73.2");
	    driver.findElement(By.name("action")).click();
	    assertEquals("Invalid entry: Patient height entries for today cannot exceed 1.", driver.findElement(By.cssSelector("span.iTrustError")).getText());
	  }
	  
}
