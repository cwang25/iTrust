package edu.ncsu.csc.itrust.selenium;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.enums.TransactionType;

/**
 * Test Diagnosis Trends / Epidemics page
 */
public class ViewDiagnosisStatisticTest extends iTrustSeleniumTest {

	/**
	 * Sets up the test. Clears the tables then adds necessary data
	 */
	@Before
	public void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
		gen.patient_hcp_vists();
		gen.hcp_diagnosis_data();
	}

	/*
	 * Authenticate PHA
	 * MID 7000000001
	 * Password: pw
	 * Choose "Diagnosis Trends"
	 * Enter Fields:
	 * ICDCode: 72.00
	 * ZipCode: 27695
	 * StartDate: 06/28/2011, EndDate: 09/28/2011
	 */
	/**
	 * testViewDiagnosisTrends_PHAView1
	 * @throws Exception
	 */
	@Test
	public void testViewDiagnosisTrends_PHAView1() throws Exception {
		login("7000000001", "pw");
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.xpath("//div[@class='panel-body']/ul/li[2]")).click();
		driver.findElement(By.linkText("Diagnosis Trends")).click();
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		assertFalse(isElementPresent(By.id("diagnosisStatisticsTable")));
		new Select(driver.findElement(By.name("viewSelect"))).selectByValue("trends");
		driver.findElement(By.id("select_View")).click();
		new Select(driver.findElement(By.name("icdCode"))).selectByVisibleText("72.00 - Mumps");
		driver.findElement(By.name("zipCode")).clear();
		driver.findElement(By.name("zipCode")).sendKeys("27695");
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("06/28/2011");
		driver.findElement(By.name("endDate")).clear();
		driver.findElement(By.name("endDate")).sendKeys("09/28/2011");
		driver.findElement(By.id("select_diagnosis")).click();
		assertTrue(isElementPresent(By.id("diagnosisStatisticsTable")));
		assertTrue(driver.findElement(By.id("zip")).getText().equals("0"));
		assertTrue(driver.findElement(By.id("region")).getText().equals("2"));
	}

	/*
	 * Authenticate PHA
	 * MID 7000000001
	 * Password: pw
	 * Choose "Epidemics"
	 * Enter Fields:
	 * Diagnosis: 84.50 Malaria
	 * ZipCode: 12345
	 * StartDate: 1/23/12
	 * Threshold: [leave blank]
	 */
	/**
	 * testViewDiagnosisTrendsEpidemic_InvalidThreshold
	 * @throws Exception
	 */
	@Test
	public void testViewDiagnosisTrendsEpidemic_InvalidThreshold()
			throws Exception {
		login("7000000001", "pw");
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.xpath("//div[@class='panel-body']/ul/li[2]")).click();
		driver.findElement(By.linkText("Diagnosis Trends")).click();
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		new Select(driver.findElement(By.name("viewSelect"))).selectByValue("epidemics");
		driver.findElement(By.id("select_View")).click();
		new Select(driver.findElement(By.name("icdCode"))).selectByVisibleText("84.50 - Malaria");
		driver.findElement(By.name("zipCode")).clear();
		driver.findElement(By.name("zipCode")).sendKeys("12345");
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("01/23/2012");

		driver.findElement(By.id("select_diagnosis")).click();
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		assertLogged(TransactionType.DIAGNOSIS_EPIDEMICS_VIEW, 7000000001L, 0L,	"");
	}

	/*
	 * Authenticate HCP MID 9000000008 Password: pw Choose "Diagnosis Trends"
	 * Enter Fields: ICDCode: 487.00 ZipCode: 27695 StartDate: 08/28/2011,
	 * EndDate: 09/28/2011 Document new office visit Add new diagnosis (487.00)
	 * Choose "Diagnosis Trends" Enter Fields: ICDCode: 487.00 ZipCode: 27695
	 * StartDate: 08/28/2011, EndDate: 09/28/2011
	 */
	/**
	 * testViewDiagnosisTrends_LHCPObserveIncrease
	 * 
	 * @throws Exception
	 */
	@Test
	public void testViewDiagnosisTrends_LHCPObserveIncrease() throws Exception {
		login("9000000008", "pw");

		// Click Diagnosis Trends
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.xpath("//div[@class='panel-body']/ul/li[11]"))
				.click();
		driver.findElement(By.linkText("Diagnosis Trends")).click();

		// View Trend
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		new Select(driver.findElement(By.name("viewSelect"))).selectByValue("trends");
		driver.findElement(By.id("select_View")).click();
		new Select(driver.findElement(By.name("icdCode"))).selectByVisibleText("487.00 - Influenza");
		driver.findElement(By.name("zipCode")).clear();
		driver.findElement(By.name("zipCode")).sendKeys("27695");
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("08/28/2011");
		driver.findElement(By.name("endDate")).sendKeys("09/28/2011");
		driver.findElement(By.id("select_diagnosis")).click();
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		assertLogged(TransactionType.DIAGNOSIS_TRENDS_VIEW, 9000000008L, 0L, "");

		long local1 = Long.parseLong(driver.findElement(By.id("zip")).getText());
		long region1 = Long.parseLong(driver.findElement(By.id("region")).getText());

		// Click Document Office Visit
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
		driver.findElement(By.linkText("Document Office Visit")).click();

		// Search and choose patient 25
		driver.findElement(By.id("searchBox")).clear();
		driver.findElement(By.id("searchBox")).sendKeys("25");
		driver.findElement(By.xpath("//input[@value='25' and @type='button']")).click();

		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-uap/documentOfficeVisit.jsp"));
		assertTrue(contains("Click on an old office visit to modify:"));

		// Click "Yes, Document Office Visit"
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-uap/editOfficeVisit.jsp"));

		driver.findElement(By.name("visitDate")).clear();
		driver.findElement(By.name("visitDate")).sendKeys("09/28/2011");
		driver.findElement(By.name("notes")).clear();
		driver.findElement(By.name("notes")).sendKeys("I like diet-coke");
		driver.findElement(By.id("update")).click();
		assertTrue(contains("Information Successfully Updated"));
		assertLogged(TransactionType.OFFICE_VISIT_CREATE, 9000000008L, 25L, "Office visit");

		new Select(driver.findElement(By.name("ICDCode"))).selectByVisibleText("487.00 - Influenza");
		driver.findElement(By.id("add_diagnosis")).click();
		assertTrue(contains("Diagnosis information successfully updated."));

		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.xpath("//div[@class='panel-body']/ul/li[2]")).click();
		driver.findElement(By.linkText("Diagnosis Trends")).click();

		// View Trend again
		new Select(driver.findElement(By.name("viewSelect"))).selectByValue("trends");
		driver.findElement(By.id("select_View")).click();
		new Select(driver.findElement(By.name("icdCode"))).selectByVisibleText("487.00 - Influenza");
		driver.findElement(By.name("zipCode")).clear();
		driver.findElement(By.name("zipCode")).sendKeys("27606-1234");
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("08/28/2011");
		driver.findElement(By.name("endDate")).sendKeys("09/28/2011");
		driver.findElement(By.id("select_diagnosis")).click();
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		assertLogged(TransactionType.DIAGNOSIS_TRENDS_VIEW, 9000000008L, 0L, "");

		long local2 = Long.parseLong(driver.findElement(By.id("zip")).getText());
		long region2 = Long.parseLong(driver.findElement(By.id("region")).getText());

		assertEquals(local1 + 1, local2);
		assertEquals(region1 + 1, region2);
	}

	/*
	 * Authenticate HCP MID 9000000008 Password: pw Choose "Diagnosis Trends"
	 * Enter Fields: ICDCode: 487.00 ZipCode: 276 StartDate: 08/28/2011,
	 * EndDate: 09/28/2011
	 */
	/**
	 * testViewDiagnosisTrends_InvalidZip
	 * 
	 * @throws Exception
	 */
	public void testViewDiagnosisTrends_InvalidZip() throws Exception {
		login("9000000008", "pw");

		// Click Diagnosis Trends
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.xpath("//div[@class='panel-body']/ul/li[11]")).click();
		driver.findElement(By.linkText("Diagnosis Trends")).click();

		// View Trend
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		new Select(driver.findElement(By.name("viewSelect"))).selectByValue("trends");
		driver.findElement(By.id("select_View")).click();
		new Select(driver.findElement(By.name("icdCode"))).selectByVisibleText("487.00 - Influenza");
		driver.findElement(By.name("zipCode")).clear();
		driver.findElement(By.name("zipCode")).sendKeys("276");
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("08/28/2011");
		driver.findElement(By.name("endDate")).sendKeys("09/28/2011");
		driver.findElement(By.id("select_diagnosis")).click();
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		assertLogged(TransactionType.DIAGNOSIS_TRENDS_VIEW, 9000000008L, 0L, "");

		assertTrue(contains("Information not valid"));
		assertTrue(contains("Zip Code must be 5 digits!"));
	}
	
	/*
	 * Authenticate HCP
	 * MID 9000000008
	 * Password: pw
	 * Choose "Diagnosis Trends"
	 * Enter Fields:
	 * ICDCode: 84.50
	 * ZipCode: 27519
	 * StartDate: 09/28/2011, EndDate: 08/28/2011
	 */
	/**
	 * testViewDiagnosisTrends_InvalidDates
	 * @throws Exception
	 */
	public void testViewDiagnosisTrends_InvalidDates() throws Exception {
		login("9000000008", "pw");

		// Click Diagnosis Trends
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.xpath("//div[@class='panel-body']/ul/li[11]"))
				.click();
		driver.findElement(By.linkText("Diagnosis Trends")).click();

		// View Trend
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		new Select(driver.findElement(By.name("viewSelect"))).selectByValue("trends");
		driver.findElement(By.id("select_View")).click();
		new Select(driver.findElement(By.name("icdCode"))).selectByVisibleText("84.50 - Malaria");
		driver.findElement(By.name("zipCode")).clear();
		driver.findElement(By.name("zipCode")).sendKeys("27519");
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("09/28/2011");
		driver.findElement(By.name("endDate")).sendKeys("08/28/2011");
		driver.findElement(By.id("select_diagnosis")).click();
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		assertLogged(TransactionType.DIAGNOSIS_TRENDS_VIEW, 9000000008L, 0L, "");

		assertTrue(contains("Information not valid"));
		assertTrue(contains("Start date must be before end date!"));		
	}
	
	/*
	 * Authenticate HCP
	 * MID 9000000008
	 * Password: pw
	 * Choose "Diagnosis Trends"
	 * Enter Fields:
	 * ICDCode: 487.00
	 * ZipCode: 27695
	 * StartDate: 08/28/2011, EndDate: 09/28/2011
	 * Enter Fields:
	 * ICDCode: 487.00
	 * ZipCode: 27606
	 * StartDate: 08/28/2011, EndDate: 09/28/2011
	 */
	/**
	 * testViewDiagnosisTrends_SameRegionCount
	 * @throws Exception
	 */
	public void testViewDiagnosisTrends_SameRegionCount() throws Exception {
		login("9000000008", "pw");

		// Click Diagnosis Trends
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.xpath("//div[@class='panel-body']/ul/li[11]")).click();
		driver.findElement(By.linkText("Diagnosis Trends")).click();

		// View Trend
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		new Select(driver.findElement(By.name("viewSelect"))).selectByValue("trends");
		driver.findElement(By.id("select_View")).click();
		
		new Select(driver.findElement(By.name("icdCode"))).selectByVisibleText("84.50 - Malaria");
		driver.findElement(By.name("zipCode")).clear();
		driver.findElement(By.name("zipCode")).sendKeys("27695");
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("06/28/2011");
		driver.findElement(By.name("endDate")).sendKeys("09/28/2011");
		driver.findElement(By.id("select_diagnosis")).click();
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		assertLogged(TransactionType.DIAGNOSIS_TRENDS_VIEW, 9000000008L, 0L, "");
		long region1 = Long.parseLong(driver.findElement(By.id("region")).getText());
		
		new Select(driver.findElement(By.name("icdCode"))).selectByVisibleText("84.50 - Malaria");
		driver.findElement(By.name("zipCode")).clear();
		driver.findElement(By.name("zipCode")).sendKeys("27606");
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("06/28/2011");
		driver.findElement(By.name("endDate")).sendKeys("09/28/2011");
		driver.findElement(By.id("select_diagnosis")).click();
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		assertLogged(TransactionType.DIAGNOSIS_TRENDS_VIEW, 9000000008L, 0L, "");
		long region2 = Long.parseLong(driver.findElement(By.id("region")).getText());
		
		assertEquals(region1, region2);
	}
	
	/*
	 * Authenticate HCP
	 * MID 9000000000
	 * Password: pw
	 * Choose "Diagnosis Trends"
	 * Enter Fields:
	 * ICDCode: 84.50
	 * ZipCode: 27519
	 * StartDate: 09/28/2011, EndDate: 09/28/2011
	 */
	/**
	 * testViewDiagnosisTrends_SameDateStartEnd
	 * @throws Exception
	 */
	public void testViewDiagnosisTrends_SameDateStartEnd() throws Exception {
		login("9000000008", "pw");

		// Click Diagnosis Trends
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.xpath("//div[@class='panel-body']/ul/li[11]")).click();
		driver.findElement(By.linkText("Diagnosis Trends")).click();

		// View Trend
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		new Select(driver.findElement(By.name("viewSelect"))).selectByValue("trends");
		driver.findElement(By.id("select_View")).click();
		new Select(driver.findElement(By.name("icdCode"))).selectByVisibleText("84.50 - Malaria");
		driver.findElement(By.name("zipCode")).clear();
		driver.findElement(By.name("zipCode")).sendKeys("27519");
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("09/28/2011");
		driver.findElement(By.name("endDate")).sendKeys("09/28/2011");
		driver.findElement(By.id("select_diagnosis")).click();
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		assertLogged(TransactionType.DIAGNOSIS_TRENDS_VIEW, 9000000008L, 0L, "");

		long local = Long.parseLong(driver.findElement(By.id("zip")).getText());
		long region = Long.parseLong(driver.findElement(By.id("region")).getText());
		assertEquals(local, 0);
		assertEquals(region, 0);
	}
	
	/*
	 * Authenticate HCP
	 * MID 9000000008
	 * Password: pw
	 * Choose "Diagnosis Trends"
	 * Enter Fields:
	 * ICDCode: 487.00
	 * ZipCode: 27607
	 * StartDate: 08/28/2011, EndDate: 09/28/2011
	 */
	/**
	 * testViewDiagnosisTrends_RegionNotLess
	 * @throws Exception
	 */
	public void testViewDiagnosisTrends_RegionNotLess() throws Exception {
		login("9000000008", "pw");

		// Click Diagnosis Trends
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.xpath("//div[@class='panel-body']/ul/li[11]")).click();
		driver.findElement(By.linkText("Diagnosis Trends")).click();

		// View Trend
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		new Select(driver.findElement(By.name("viewSelect"))).selectByValue("trends");
		driver.findElement(By.id("select_View")).click();
		new Select(driver.findElement(By.name("icdCode"))).selectByVisibleText("487.00 - Influenza");
		driver.findElement(By.name("zipCode")).clear();
		driver.findElement(By.name("zipCode")).sendKeys("27607");
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("08/28/2011");
		driver.findElement(By.name("endDate")).sendKeys("09/28/2011");
		driver.findElement(By.id("select_diagnosis")).click();
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		assertLogged(TransactionType.DIAGNOSIS_TRENDS_VIEW, 9000000008L, 0L, "");

		long local = Long.parseLong(driver.findElement(By.id("zip")).getText());
		long region = Long.parseLong(driver.findElement(By.id("region")).getText());
		assertTrue(local <= region);
	}
	
	/*
	 * Authenticate HCP
	 * MID 9000000008
	 * Password: pw
	 * Choose "Diagnosis Trends"
	 * Enter Fields:
	 * ICDCode: ""
	 * ZipCode: 27695
	 * StartDate: 08/28/2011, EndDate: 09/28/2011
	 */
	/**
	 * testViewDiagnosisTrends_NoDiagnosisSelected
	 * @throws Exception
	 */
	public void testViewDiagnosisTrends_NoDiagnosisSelected() throws Exception {
		login("9000000008", "pw");

		// Click Diagnosis Trends
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.xpath("//div[@class='panel-body']/ul/li[11]")).click();
		driver.findElement(By.linkText("Diagnosis Trends")).click();

		// View Trend
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		new Select(driver.findElement(By.name("viewSelect"))).selectByValue("trends");
		driver.findElement(By.id("select_View")).click();
		driver.findElement(By.name("zipCode")).clear();
		driver.findElement(By.name("zipCode")).sendKeys("27695");
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("06/28/2011");
		driver.findElement(By.name("endDate")).sendKeys("09/28/2011");
		driver.findElement(By.id("select_diagnosis")).click();
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		assertLogged(TransactionType.DIAGNOSIS_TRENDS_VIEW, 9000000008L, 0L, "");
		
		assertTrue(contains("Information not valid"));
		assertTrue(contains("ICDCode must be valid diagnosis!"));
	}
	
	/**
	 * viewDiagnosisEpidemics_NoEpidemicRecords
	 * @throws Exception
	 */
	public void testViewDiagnosisEpidemics_NoEpidemicRecords() throws Exception {
		login("9000000000", "pw");

		// Click Diagnosis Trends
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.xpath("//div[@class='panel-body']/ul/li[11]")).click();
		driver.findElement(By.linkText("Diagnosis Trends")).click();

		// View Trend
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		new Select(driver.findElement(By.name("viewSelect"))).selectByValue("epidemics");
		driver.findElement(By.id("select_View")).click();
		new Select(driver.findElement(By.name("icdCode"))).selectByVisibleText("84.50 - Malaria");
		driver.findElement(By.name("zipCode")).clear();
		driver.findElement(By.name("zipCode")).sendKeys("38201");
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("06/02/2010");
		driver.findElement(By.name("threshold")).clear();
		driver.findElement(By.name("threshold")).sendKeys("110");
		driver.findElement(By.id("select_diagnosis")).click();
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		assertLogged(TransactionType.DIAGNOSIS_EPIDEMICS_VIEW, 9000000000L, 0L, "");
		
		assertTrue(contains("There is no epidemic occurring in the region."));
	}
	
	/**
	 * viewDiagnosisEpidemics_YesEpidemic
	 * @throws Exception
	 */
	public void testViewDiagnosisEpidemics_YesEpidemic() throws Exception {
		gen.influenza_epidemic();
		
		login("9000000007", "pw");

		// Click Diagnosis Trends
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.xpath("//div[@class='panel-body']/ul/li[11]")).click();
		driver.findElement(By.linkText("Diagnosis Trends")).click();

		// View Trend
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		new Select(driver.findElement(By.name("viewSelect"))).selectByValue("epidemics");
		driver.findElement(By.id("select_View")).click();
		new Select(driver.findElement(By.name("icdCode"))).selectByVisibleText("487.00 - Influenza");
		driver.findElement(By.name("zipCode")).clear();
		driver.findElement(By.name("zipCode")).sendKeys("27607");
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("11/02/2011");
		driver.findElement(By.id("select_diagnosis")).click();
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		assertLogged(TransactionType.DIAGNOSIS_EPIDEMICS_VIEW, 9000000007L, 0L, "");
		assertFalse(contains("THERE IS AN EPIDEMIC OCCURRING IN THIS REGION!"));
	}
	
	/**
	 * viewDiagnosisEpidemics_NoEpidemic
	 * @throws Exception
	 */
	public void testViewDiagnosisEpidemics_NoEpidemic() throws Exception {
		gen.influenza_epidemic();
		login("9000000007", "pw");

		// Click Diagnosis Trends
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.xpath("//div[@class='panel-body']/ul/li[11]")).click();
		driver.findElement(By.linkText("Diagnosis Trends")).click();

		// View Trend
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		new Select(driver.findElement(By.name("viewSelect"))).selectByValue("epidemics");
		driver.findElement(By.id("select_View")).click();
		new Select(driver.findElement(By.name("icdCode"))).selectByVisibleText("487.00 - Influenza");
		driver.findElement(By.name("zipCode")).clear();
		driver.findElement(By.name("zipCode")).sendKeys("27607");
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("02/15/2010");
		driver.findElement(By.id("select_diagnosis")).click();
		assertTrue(driver.getCurrentUrl().equals(ADDRESS + "auth/hcp-pha/viewDiagnosisStatistics.jsp"));
		assertLogged(TransactionType.DIAGNOSIS_EPIDEMICS_VIEW, 9000000007L, 0L, "");
		assertTrue(contains("There is no epidemic occurring in the region."));
	}
}
