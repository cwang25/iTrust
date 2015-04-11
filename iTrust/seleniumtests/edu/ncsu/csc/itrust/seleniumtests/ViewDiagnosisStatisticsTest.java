package edu.ncsu.csc.itrust.seleniumtests;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.enums.TransactionType;

/**
 * @author Brantley
 *
 */
public class ViewDiagnosisStatisticsTest extends iTrustSeleniumTest {
  
  @Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		
		gen.standardData();
		gen.patient_hcp_vists();
		gen.hcp_diagnosis_data();
	  }
  
  	@Test
	public void testViewDiagnosisTrends_PHAView1() throws Exception {
  	//This logs us into iTrust and returns the HtmlUnitDriver for use in this case
		HtmlUnitDriver driver = super.login("7000000001", "pw");

	    assertEquals("iTrust - PHA Home", driver.getTitle());
	    driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Diagnosis Trends")).click();
	    assertEquals("iTrust - View Diagnosis Statistics", driver.getTitle());
	    new Select(driver.findElement(By.name("viewSelect"))).selectByVisibleText("Trends");
	    driver.findElement(By.id("select_View")).click();
	    assertEquals("iTrust - View Diagnosis Statistics", driver.getTitle());
	    new Select(driver.findElement(By.name("icdCode"))).selectByVisibleText("72.00 - Mumps");
	    driver.findElement(By.name("zipCode")).clear();
	    driver.findElement(By.name("zipCode")).sendKeys("27695");
	    driver.findElement(By.name("startDate")).clear();
	    driver.findElement(By.name("startDate")).sendKeys("06/28/2011");
	    driver.findElement(By.name("endDate")).clear();
	    driver.findElement(By.name("endDate")).sendKeys("09/28/2011");
	    driver.findElement(By.id("select_diagnosis")).click();
	    assertEquals("iTrust - View Diagnosis Statistics", driver.getTitle());
	    assertEquals("72.00", driver.findElement(By.xpath("//table[@id='diagnosisStatisticsTable']/tbody/tr[2]/td")).getText());
	    assertEquals("27695", driver.findElement(By.xpath("//table[@id='diagnosisStatisticsTable']/tbody/tr[2]/td[2]")).getText());
	    assertEquals("0", driver.findElement(By.xpath("//table[@id='diagnosisStatisticsTable']/tbody/tr[2]/td[3]")).getText());
	    assertEquals("2", driver.findElement(By.xpath("//table[@id='diagnosisStatisticsTable']/tbody/tr[2]/td[4]")).getText());
	    assertEquals("06/28/2011", driver.findElement(By.xpath("//table[@id='diagnosisStatisticsTable']/tbody/tr[2]/td[5]")).getText());
	    assertEquals("09/28/2011", driver.findElement(By.xpath("//table[@id='diagnosisStatisticsTable']/tbody/tr[2]/td[6]")).getText());  			
  	}
  	
  	@Test
	public void testViewDiagnosisTrendsEpidemic_InvalidThreshold() throws Exception {
  	//This logs us into iTrust and returns the HtmlUnitDriver for use in this case
		HtmlUnitDriver driver = super.login("7000000001", "pw");

	    assertEquals("iTrust - PHA Home", driver.getTitle());
	    driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Diagnosis Trends")).click();
	    assertEquals("iTrust - View Diagnosis Statistics", driver.getTitle());
	    new Select(driver.findElement(By.name("viewSelect"))).selectByVisibleText("Epidemics");
	    driver.findElement(By.id("select_View")).click();
	    assertEquals("iTrust - View Diagnosis Statistics", driver.getTitle());
	    new Select(driver.findElement(By.name("icdCode"))).selectByVisibleText("84.50 - Malaria");
	    driver.findElement(By.name("zipCode")).clear();
	    driver.findElement(By.name("zipCode")).sendKeys("12345");
	    driver.findElement(By.name("startDate")).clear();
	    driver.findElement(By.name("startDate")).sendKeys("01/23/2012");
	    driver.findElement(By.id("select_diagnosis")).click();
	    assertEquals("Information not valid", driver.findElement(By.cssSelector("#iTrustContent > h2")).getText());
	    assertEquals("Threshold must be an integer.", driver.findElement(By.cssSelector("div.errorList")).getText());   
  	}
  	
  	@Test
  	public void testViewDiagnosisTrends_LHCPObserveIncrease() throws Exception {
  		//This logs us into iTrust and returns the HtmlUnitDriver for use in this case
		HtmlUnitDriver driver = super.login("9000000008", "pw");
  			
  	    assertEquals("iTrust - HCP Home", driver.getTitle());
  	    assertLogged(TransactionType.HOME_VIEW, 9000000008L, 0L, "");
  	    driver.findElement(By.linkText("Diagnosis Trends")).click();
  	    assertEquals("iTrust - View Diagnosis Statistics", driver.getTitle());
  	    new Select(driver.findElement(By.name("viewSelect"))).selectByVisibleText("Trends");
  	    driver.findElement(By.id("select_View")).click();
  	    assertEquals("iTrust - View Diagnosis Statistics", driver.getTitle());
  	    new Select(driver.findElement(By.name("icdCode"))).selectByVisibleText("487.00 - Influenza");
  	    driver.findElement(By.name("zipCode")).clear();
  	    driver.findElement(By.name("zipCode")).sendKeys("27695");
  	    driver.findElement(By.name("startDate")).clear();
  	    driver.findElement(By.name("startDate")).sendKeys("08/28/2011");
  	    driver.findElement(By.name("endDate")).clear();
  	    driver.findElement(By.name("endDate")).sendKeys("09/28/2011");
  	    driver.findElement(By.id("select_diagnosis")).click();
  	    assertEquals("0", driver.findElement(By.xpath("//table[@id='diagnosisStatisticsTable']/tbody/tr[2]/td[3]")).getText());
  	    assertEquals("4", driver.findElement(By.xpath("//table[@id='diagnosisStatisticsTable']/tbody/tr[2]/td[4]")).getText());
  	    assertLogged(TransactionType.DIAGNOSIS_TRENDS_VIEW, 9000000008L, 0L, "");
  	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
  	    driver.findElement(By.linkText("Document Office Visit")).click();
  	    driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.name("UID_PATIENTID")).sendKeys("25");
	    driver.findElement(By.xpath("//input[@value='25']")).submit();
	    assertEquals("iTrust - Document Office Visit", driver.getTitle());   
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("iTrust - Document Office Visit", driver.getTitle());
  	    driver.findElement(By.name("visitDate")).clear();
  	    driver.findElement(By.name("visitDate")).sendKeys("09/28/2011");
  	    driver.findElement(By.name("notes")).clear();
  	    driver.findElement(By.name("notes")).sendKeys("I like diet-coke");
  	    driver.findElement(By.id("update")).click();
  	    assertEquals("Information Successfully Updated", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
  	    assertLogged(TransactionType.OFFICE_VISIT_CREATE, 9000000008L, 25L, "Office visit");
  	    new Select(driver.findElement(By.name("ICDCode"))).selectByVisibleText("487.00 - Influenza");
  	    driver.findElement(By.id("add_diagnosis")).click();
  	    assertEquals("Diagnosis information successfully updated.", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
  	    driver.findElement(By.cssSelector("h2.panel-title")).click();
  	    driver.findElement(By.linkText("Diagnosis Trends")).click();
  	    new Select(driver.findElement(By.name("viewSelect"))).selectByVisibleText("Trends");
  	    driver.findElement(By.id("select_View")).click();
  	    new Select(driver.findElement(By.name("icdCode"))).selectByVisibleText("487.00 - Influenza");
  	    driver.findElement(By.name("zipCode")).clear();
  	    driver.findElement(By.name("zipCode")).sendKeys("27606-1234");
  	    driver.findElement(By.name("startDate")).clear();
  	    driver.findElement(By.name("startDate")).sendKeys("08/28/2011");
  	    driver.findElement(By.name("endDate")).clear();
  	    driver.findElement(By.name("endDate")).sendKeys("09/28/2011");
  	    driver.findElement(By.id("select_diagnosis")).click();
  	    assertEquals("iTrust - View Diagnosis Statistics", driver.getTitle());
  	    assertLogged(TransactionType.DIAGNOSIS_TRENDS_VIEW, 9000000008L, 0L, "");
  	    assertEquals("1", driver.findElement(By.xpath("//table[@id='diagnosisStatisticsTable']/tbody/tr[2]/td[3]")).getText());
  	    assertEquals("5", driver.findElement(By.xpath("//table[@id='diagnosisStatisticsTable']/tbody/tr[2]/td[4]")).getText());
  	}
  	
  	@Test
  	public void testViewDiagnosisTrends_InvalidZip() throws Exception {
  		//This logs us into iTrust and returns the HtmlUnitDriver for use in this case
		HtmlUnitDriver driver = super.login("9000000008", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000008L, 0L, "");
  	  	assertEquals("iTrust - HCP Home", driver.getTitle());
  	    driver.findElement(By.cssSelector("h2.panel-title")).click();
  	    driver.findElement(By.linkText("Diagnosis Trends")).click();
  	    assertEquals("iTrust - View Diagnosis Statistics", driver.getTitle());
  	    new Select(driver.findElement(By.name("viewSelect"))).selectByVisibleText("Trends");
  	    driver.findElement(By.id("select_View")).click();
  	    assertEquals("iTrust - View Diagnosis Statistics", driver.getTitle());
  	    new Select(driver.findElement(By.name("icdCode"))).selectByVisibleText("487.00 - Influenza");
  	    driver.findElement(By.name("zipCode")).clear();
  	    driver.findElement(By.name("zipCode")).sendKeys("276");
  	    driver.findElement(By.name("startDate")).clear();
  	    driver.findElement(By.name("startDate")).sendKeys("08/28/2011");
  	    driver.findElement(By.name("endDate")).clear();
  	    driver.findElement(By.name("endDate")).sendKeys("09/28/2011");
  	    driver.findElement(By.id("select_diagnosis")).click();
  	    assertLogged(TransactionType.DIAGNOSIS_TRENDS_VIEW, 9000000008L, 0L, "");
  	    assertEquals("Information not valid", driver.findElement(By.cssSelector("#iTrustContent > h2")).getText());
  	    assertEquals("Zip Code must be 5 digits!", driver.findElement(By.cssSelector("div.errorList")).getText());
  	}
  	
  	@Test
  	public void testViewDiagnosisTrends_InvalidDates() throws Exception {
		//This logs us into iTrust and returns the HtmlUnitDriver for use in this case
		HtmlUnitDriver driver = super.login("9000000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
  		assertEquals("iTrust - HCP Home", driver.getTitle());
  	    driver.findElement(By.cssSelector("h2.panel-title")).click();
  	    driver.findElement(By.linkText("Diagnosis Trends")).click();
  	    assertEquals("iTrust - View Diagnosis Statistics", driver.getTitle());
  	    new Select(driver.findElement(By.name("viewSelect"))).selectByVisibleText("Trends");
  	    driver.findElement(By.id("select_View")).click();
  	    assertEquals("iTrust - View Diagnosis Statistics", driver.getTitle());
  	    new Select(driver.findElement(By.name("icdCode"))).selectByVisibleText("84.50 - Malaria");
  	    driver.findElement(By.name("zipCode")).clear();
  	    driver.findElement(By.name("zipCode")).sendKeys("27519");
  	    driver.findElement(By.name("startDate")).clear();
  	    driver.findElement(By.name("startDate")).sendKeys("09/28/2011");
  	    driver.findElement(By.name("endDate")).clear();
  	    driver.findElement(By.name("endDate")).sendKeys("08/28/2011");
  	    driver.findElement(By.id("select_diagnosis")).click();
  	    assertLogged(TransactionType.DIAGNOSIS_TRENDS_VIEW, 9000000000L, 0L, "");
  	    assertEquals("Information not valid", driver.findElement(By.cssSelector("#iTrustContent > h2")).getText());
  	    assertEquals("Start date must be before end date!", driver.findElement(By.cssSelector("div.errorList")).getText());
  	}
  	
  	@Test
  	public void testViewDiagnosisTrends_SameRegionCount() throws Exception {
  		//This logs us into iTrust and returns the HtmlUnitDriver for use in this case
		HtmlUnitDriver driver = super.login("9000000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		assertEquals("iTrust - HCP Home", driver.getTitle());
	    driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Diagnosis Trends")).click();
	    new Select(driver.findElement(By.name("viewSelect"))).selectByVisibleText("Trends");
	    driver.findElement(By.id("select_View")).click();
	    new Select(driver.findElement(By.name("icdCode"))).selectByVisibleText("84.50 - Malaria");
	    driver.findElement(By.name("zipCode")).clear();
	    driver.findElement(By.name("zipCode")).sendKeys("27695");
	    driver.findElement(By.name("startDate")).clear();
	    driver.findElement(By.name("startDate")).sendKeys("06/28/2011");
	    driver.findElement(By.name("endDate")).clear();
	    driver.findElement(By.name("endDate")).sendKeys("09/28/2011");
	    driver.findElement(By.id("select_diagnosis")).click();
	    assertLogged(TransactionType.DIAGNOSIS_TRENDS_VIEW, 9000000000L, 0L, "");
	    assertEquals("5", driver.findElement(By.xpath("//table[@id='diagnosisStatisticsTable']/tbody/tr[2]/td[4]")).getText());
	    driver.findElement(By.name("zipCode")).clear();
	    driver.findElement(By.name("zipCode")).sendKeys("27606");
	    driver.findElement(By.id("select_diagnosis")).click();
	    assertLogged(TransactionType.DIAGNOSIS_TRENDS_VIEW, 9000000000L, 0L, "");
	    assertEquals("5", driver.findElement(By.xpath("//table[@id='diagnosisStatisticsTable']/tbody/tr[2]/td[4]")).getText());
  	}
  	
  	@Test
  	public void testViewDiagnosisTrends_SameDateStartEnd() throws Exception {
  		//This logs us into iTrust and returns the HtmlUnitDriver for use in this case
		HtmlUnitDriver driver = super.login("9000000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
	    assertEquals("iTrust - HCP Home", driver.getTitle());
	    driver.findElement(By.cssSelector("div.panel-heading")).click();
	    driver.findElement(By.linkText("Diagnosis Trends")).click();
	    assertEquals("iTrust - View Diagnosis Statistics", driver.getTitle());
	    new Select(driver.findElement(By.name("viewSelect"))).selectByVisibleText("Trends");
	    driver.findElement(By.id("select_View")).click();
	    assertEquals("iTrust - View Diagnosis Statistics", driver.getTitle());
	    new Select(driver.findElement(By.name("icdCode"))).selectByVisibleText("84.50 - Malaria");
	    driver.findElement(By.name("zipCode")).clear();
	    driver.findElement(By.name("zipCode")).sendKeys("27519");
	    driver.findElement(By.name("startDate")).clear();
	    driver.findElement(By.name("startDate")).sendKeys("09/28/2011");
	    driver.findElement(By.name("endDate")).clear();
	    driver.findElement(By.name("endDate")).sendKeys("09/28/2011");
	    driver.findElement(By.id("select_diagnosis")).click();
	    assertLogged(TransactionType.DIAGNOSIS_TRENDS_VIEW, 9000000000L, 0L, "");
	    assertEquals("0", driver.findElement(By.xpath("//table[@id='diagnosisStatisticsTable']/tbody/tr[2]/td[3]")).getText());
	    assertEquals("0", driver.findElement(By.xpath("//table[@id='diagnosisStatisticsTable']/tbody/tr[2]/td[4]")).getText());
  	}
  	
  	@Test
	public void testViewDiagnosisTrends_RegionNotLess() throws Exception {
  		//This logs us into iTrust and returns the HtmlUnitDriver for use in this case
		HtmlUnitDriver driver = super.login("9000000008", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000008L, 0L, "");
	    assertEquals("iTrust - HCP Home", driver.getTitle());
	    driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Diagnosis Trends")).click();
	    assertEquals("iTrust - View Diagnosis Statistics", driver.getTitle());
	    new Select(driver.findElement(By.name("viewSelect"))).selectByVisibleText("Trends");
	    driver.findElement(By.id("select_View")).click();
	    new Select(driver.findElement(By.name("icdCode"))).selectByVisibleText("487.00 - Influenza");
	    driver.findElement(By.name("zipCode")).clear();
	    driver.findElement(By.name("zipCode")).sendKeys("27607");
	    driver.findElement(By.name("startDate")).clear();
	    driver.findElement(By.name("startDate")).sendKeys("08/28/2011");
	    driver.findElement(By.name("endDate")).clear();
	    driver.findElement(By.name("endDate")).sendKeys("09/28/2011");
	    driver.findElement(By.id("select_diagnosis")).click();
	    assertLogged(TransactionType.DIAGNOSIS_TRENDS_VIEW, 9000000008L, 0L, "");
	    assertEquals("3", driver.findElement(By.xpath("//table[@id='diagnosisStatisticsTable']/tbody/tr[2]/td[3]")).getText());
	    assertEquals("4", driver.findElement(By.xpath("//table[@id='diagnosisStatisticsTable']/tbody/tr[2]/td[4]")).getText());
  	}
  	
  	@Test
	public void testViewDiagnosisTrends_NoDiagnosisSelected() throws Exception {
  		//This logs us into iTrust and returns the HtmlUnitDriver for use in this case
		HtmlUnitDriver driver = super.login("7000000001", "pw");
		assertLogged(TransactionType.HOME_VIEW, 7000000001L, 0L, "");
		assertEquals("iTrust - PHA Home", driver.getTitle());
	    driver.findElement(By.linkText("Diagnosis Trends")).click();
	    assertEquals("iTrust - View Diagnosis Statistics", driver.getTitle());
	    new Select(driver.findElement(By.name("viewSelect"))).selectByVisibleText("Trends");
	    driver.findElement(By.id("select_View")).click();
	    assertEquals("iTrust - View Diagnosis Statistics", driver.getTitle());
	    driver.findElement(By.name("zipCode")).clear();
	    driver.findElement(By.name("zipCode")).sendKeys("27695");
	    driver.findElement(By.name("startDate")).clear();
	    driver.findElement(By.name("startDate")).sendKeys("06/28/2011");
	    driver.findElement(By.name("endDate")).clear();
	    driver.findElement(By.name("endDate")).sendKeys("09/28/2011");
	    driver.findElement(By.id("select_diagnosis")).click();
	    assertLogged(TransactionType.DIAGNOSIS_TRENDS_VIEW, 7000000001L, 0L, "");
	    assertEquals("Information not valid", driver.findElement(By.cssSelector("#iTrustContent > h2")).getText());
	    assertEquals("ICDCode must be valid diagnosis!", driver.findElement(By.cssSelector("div.errorList")).getText());
  	}
  	
  	@Test
	public void testViewDiagnosisEpidemics_NoEpidemicRecords() throws Exception {
  		//This logs us into iTrust and returns the HtmlUnitDriver for use in this case
		HtmlUnitDriver driver = super.login("9000000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("Diagnosis Trends")).click();
		new Select(driver.findElement(By.name("viewSelect"))).selectByVisibleText("Epidemics");
		driver.findElement(By.id("select_View")).click();
		new Select(driver.findElement(By.name("icdCode"))).selectByVisibleText("84.50 - Malaria");
		driver.findElement(By.name("zipCode")).clear();
		driver.findElement(By.name("zipCode")).sendKeys("38201");
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("06/02/2010");
		driver.findElement(By.name("threshold")).clear();
		driver.findElement(By.name("threshold")).sendKeys("110");
		driver.findElement(By.id("select_diagnosis")).click();
		assertLogged(TransactionType.DIAGNOSIS_EPIDEMICS_VIEW, 9000000000L, 0L, "");
		assertEquals("There is no epidemic occurring in the region.", driver.findElement(By.cssSelector("center")).getText());
  	}
  	
  	@Test
	public void testViewDiagnosisEpidemics_YesEpidemic() throws Exception {
		gen.influenza_epidemic();
  		//This logs us into iTrust and returns the HtmlUnitDriver for use in this case
		HtmlUnitDriver driver = super.login("9000000007", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000007L, 0L, "");
	    assertEquals("iTrust - HCP Home", driver.getTitle());
	    driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Diagnosis Trends")).click();
	    new Select(driver.findElement(By.name("viewSelect"))).selectByVisibleText("Epidemics");
	    driver.findElement(By.id("select_View")).click();
	    new Select(driver.findElement(By.name("icdCode"))).selectByVisibleText("487.00 - Influenza");
	    driver.findElement(By.name("zipCode")).clear();
	    driver.findElement(By.name("zipCode")).sendKeys("27607");
	    driver.findElement(By.name("startDate")).clear();
	    driver.findElement(By.name("startDate")).sendKeys("11/02/2011");
	    driver.findElement(By.id("select_diagnosis")).click();
	    assertLogged(TransactionType.DIAGNOSIS_EPIDEMICS_VIEW, 9000000007L, 0L, "");
	    assertEquals("THERE IS AN EPIDEMIC OCCURRING IN THIS REGION!", driver.findElement(By.cssSelector("font")).getText());
	    gen.clearAllTables();
		gen.standardData();
	}
  	
  	@Test
	public void testViewDiagnosisEpidemics_NoEpidemic() throws Exception {
		gen.influenza_epidemic();
  		//This logs us into iTrust and returns the HtmlUnitDriver for use in this case
		HtmlUnitDriver driver = super.login("7000000001", "pw");
		assertLogged(TransactionType.HOME_VIEW, 7000000001L, 0L, "");
	    assertEquals("iTrust - PHA Home", driver.getTitle());
	    driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Diagnosis Trends")).click();
	    new Select(driver.findElement(By.name("viewSelect"))).selectByVisibleText("Epidemics");
	    driver.findElement(By.id("select_View")).click();
	    new Select(driver.findElement(By.name("icdCode"))).selectByVisibleText("487.00 - Influenza");
	    driver.findElement(By.name("zipCode")).clear();
	    driver.findElement(By.name("zipCode")).sendKeys("27607");
	    driver.findElement(By.name("startDate")).clear();
	    driver.findElement(By.name("startDate")).sendKeys("02/15/2010");
	    driver.findElement(By.id("select_diagnosis")).click();
	    assertLogged(TransactionType.DIAGNOSIS_EPIDEMICS_VIEW, 7000000001L, 0L, "");
	    assertEquals("There is no epidemic occurring in the region.", driver.findElement(By.cssSelector("center")).getText());
	    gen.clearAllTables();
		gen.standardData();
	}
}
