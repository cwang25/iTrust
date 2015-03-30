package edu.ncsu.csc.itrust.selenium;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.enums.TransactionType;

/**
 * @author nishant
 *
 */
public class ViewHealthRecordsHistoryTest extends iTrustSeleniumTest{

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		super.setUp();
		gen.standardData();
	}

	@Test
	public void testOfficeVisit4MonthOldViewHealthMetrics() throws Exception{
		login("8000000011", "pw");
		
		assertLogged(TransactionType.HOME_VIEW, 8000000011L, 0L, "");
		
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div")).click();
	    driver.findElement(By.linkText("Document Office Visit")).click();
	    
	    
	    driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("101");
	    waitFor(1);
	    driver.findElement(By.xpath("//input[@value='101' and @type='button']")).click();
	    
	    assertEquals(ADDRESS + "auth/hcp-uap/documentOfficeVisit.jsp", driver.getCurrentUrl());
	    
	    
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    
	    assertEquals("iTrust - Document Office Visit", driver.getTitle());
	    
	    driver.findElement(By.name("visitDate")).clear();
	    driver.findElement(By.name("visitDate")).sendKeys("10/01/2013");
	    new Select(driver.findElement(By.name("hospitalID"))).selectByVisibleText("Central Hospital");
	    driver.findElement(By.name("notes")).clear();
	    driver.findElement(By.name("notes")).sendKeys("Brynn can start eating rice cereal mixed with breast milk or formula once a day.");
	    driver.findElement(By.id("update")).click();
	    
	    assertTrue(pageContains("Information Successfully Updated"));
	    
	    driver.findElement(By.name("height")).clear();
	    driver.findElement(By.name("height")).sendKeys("22.3");
	    driver.findElement(By.name("weight")).clear();
	    driver.findElement(By.name("weight")).sendKeys("16.5");
	    driver.findElement(By.name("headCircumference")).clear();
	    driver.findElement(By.name("headCircumference")).sendKeys("16.1");
	    new Select(driver.findElement(By.id("householdSmokingStatus"))).selectByVisibleText("1 - non-smoking household");
	    driver.findElement(By.id("addHR")).click();
	    assertTrue(pageContains("Health information successfully updated."));
	    
	    driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Basic Health Information")).click();
	  
	    //Verify Basic Health Information page
  		assertEquals(ADDRESS + "auth/hcp-uap/viewBasicHealth.jsp",driver.getCurrentUrl());
	    
	    //Verify adult health record table displays
  		WebElement hrTable = driver.findElement(By.id("HealthRecordsTable"));
  		List<WebElement> tableRows = hrTable.findElements(By.tagName("tr"));
  		List<WebElement> entriesToCheck = tableRows.get(2).findElements(By.tagName("td"));
  		//Verify the table has the provided information: Header, field descriptions, 1 row of health records
  		assertEquals(3, tableRows.size());
  		//Verify table contents
  		
  		//Row 1 values
  		//Office visit date
  		assertEquals("10/01/2013", entriesToCheck.get(0).getText());
  		//Patient length
  		assertEquals("22.3in", entriesToCheck.get(1).getText());
  		//Patient weight
  		assertEquals("16.5lbs", entriesToCheck.get(2).getText());
  		//Patient head circumference
  		assertEquals("16.1in", entriesToCheck.get(4).getText());
  		//Patient household smoking status
  		assertEquals("Non-smoking household", entriesToCheck.get(5).getText());
	}
	
	/**
	 * testOfficeVisit24YrOldViewHealthMetrics
	 * @throws Exception
	 */
	@Test
	public void testOfficeVisit24YrOldViewHealthMetrics() throws Exception{
		//Login as Patient Thane Ross (MID 105)
		login("105", "pw");
		assertLogged(TransactionType.HOME_VIEW, 105L, 0L, "");
		
		//Click View My Records link
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("View My Records")).click();
		
		//Verify Basic Health Information page
		assertEquals(ADDRESS + "auth/patient/viewMyRecords.jsp", driver.getCurrentUrl());
		
		//Verify adult health record table displays
		WebElement hrTable = driver.findElement(By.id("HealthRecordsTable"));
		List<WebElement> tableRows = hrTable.findElements(By.tagName("tr"));
		List<WebElement> entriesToCheck = tableRows.get(2).findElements(By.tagName("td"));
		//Check that the table has 3 rows
		assertEquals(3, tableRows.size());
		//Office visit date
		assertEquals("10/25/2013", entriesToCheck.get(0).getText());
		//Patient height
		assertEquals("73.1in", entriesToCheck.get(1).getText());
		//Patient weight
		assertEquals("210.1lbs", entriesToCheck.get(2).getText());
		//Patient BMI
		assertEquals("27.64", entriesToCheck.get(3).getText());
		//Check weight status
		assertEquals("Overweight", entriesToCheck.get(4).getText());
		//Patient blood pressure
		assertEquals("160/100 mmHg", entriesToCheck.get(5).getText());
		//Patient smoking status
		assertEquals("N", entriesToCheck.get(6).getText());
		//Patient household smoking status
		assertEquals("Non-smoking household", entriesToCheck.get(7).getText());
		//Patient HDL levels
		assertEquals("37 mg/dL", entriesToCheck.get(8).getText());
		//Patient LDL levels
		assertEquals("141 mg/dL", entriesToCheck.get(9).getText());
		//Patient triglycerides
		assertEquals("162 mg/dL", entriesToCheck.get(10).getText());
		
		//Log out
		logout();
	}
	
	/**
	 * testHCPLoggingAction
	 * @throws Exception
	 */
	@Test
	public void testHCPLoggingAction() throws Exception{
		//Login as HCP Shelly Vang
		login("8000000011", "pw");	
		assertLogged(TransactionType.HOME_VIEW, 8000000011L, 0L, "");
		
		//Click Basic Health Information link
		driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Basic Health Information")).click();
		//Search for patient MID 102
	    driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("102");
	    waitFor(1);
		//Click on first MID button
	    driver.findElement(By.xpath("//input[@value='102' and @type='button']")).click();
		//Verify Basic Health Information page
		assertEquals(ADDRESS + "auth/hcp-uap/viewBasicHealth.jsp", driver.getCurrentUrl());
		
		logout();
		assertEquals("iTrust - Login", driver.getTitle());
		
		login("102", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertTrue(pageContains("Shelly Vang"));
		assertTrue(pageContains("viewed your health records history"));
		
		//Logout
		logout();
	}
	
	/**
	 * testViewHealthMetricsByHCP
	 * @throws Exception
	 */
	@Test
	public void testViewHealthMetricsByHCP() throws Exception{
		//Login as HCP Shelly Vang
		login("8000000011", "pw");	
		assertLogged(TransactionType.HOME_VIEW, 8000000011L, 0L, "");
		
		//Click Basic Health Information link
		driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Basic Health Information")).click();
		//Search for Caldwell Hudson (MID 102)
	    driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("102");
	    waitFor(1);
		//Click on first MID button
	    driver.findElement(By.xpath("//input[@value='102' and @type='button']")).click();
		//Verify Basic Health Information page
		assertEquals(ADDRESS + "auth/hcp-uap/viewBasicHealth.jsp", driver.getCurrentUrl());

		//Verify baby health record table displays
		WebElement hrTable = driver.findElement(By.id("HealthRecordsTable"));
		List<WebElement> tableRows = hrTable.findElements(By.tagName("tr"));
		List<WebElement> infantRow1 = tableRows.get(2).findElements(By.tagName("td"));
		List<WebElement> infantRow2 = tableRows.get(3).findElements(By.tagName("td"));
		List<WebElement> infantRow3 = tableRows.get(4).findElements(By.tagName("td"));
		List<WebElement> infantRow4 = tableRows.get(5).findElements(By.tagName("td"));
		List<WebElement> infantRow5 = tableRows.get(6).findElements(By.tagName("td"));
		//Verify the table has the provided information: Header, field descriptions, 3 rows of health records
		assertEquals(7, tableRows.size());

		//Verify table contents
		
		//Row 1 values
		//Office visit date
		assertEquals("10/28/2013", infantRow1.get(0).getText());
		//Patient length
		assertEquals("34.7in", infantRow1.get(1).getText());
		//Patient weight
		assertEquals("30.2lbs", infantRow1.get(2).getText());
		//Patient head circumference
		assertEquals("19.4in", infantRow1.get(4).getText());
		//Patient household smoking status
		assertEquals("Indoor smokers", infantRow1.get(5).getText());
		
		//Row 2 values
		//Office visit date
		assertEquals("02/02/2012", infantRow2.get(0).getText());
		//Patient length
		assertEquals("25.7in", infantRow2.get(1).getText());
		//Patient weight
		assertEquals("15.8lbs", infantRow2.get(2).getText());
		//Patient head circumference
		assertEquals("17.1in", infantRow2.get(4).getText());
		//Patient household smoking status
		assertEquals("Indoor smokers", infantRow2.get(5).getText());
		
		//Row 3 values
		//Office visit date
		assertEquals("12/01/2011", infantRow3.get(0).getText());
		//Patient length
		assertEquals("22.5in", infantRow3.get(1).getText());
		//Patient weight
		assertEquals("12.1lbs", infantRow3.get(2).getText());
		//Patient head circumference
		assertEquals("16.3in", infantRow3.get(4).getText());
		//Patient household smoking status
		assertEquals("Indoor smokers", infantRow3.get(5).getText());
		
		//Row 4 values
		//Office visit date
		assertEquals("11/01/2011", infantRow4.get(0).getText());
		//Patient length
		assertEquals("21.1in", infantRow4.get(1).getText());
		//Patient weight
		assertEquals("10.3lbs", infantRow4.get(2).getText());
		//Patient head circumference
		assertEquals("15.3in", infantRow4.get(4).getText());
		//Patient household smoking status
		assertEquals("Indoor smokers", infantRow4.get(5).getText());
		
		//Row 5 values
		//Office visit date
		assertEquals("10/01/2011", infantRow5.get(0).getText());
		//Patient length
		assertEquals("19.6in", infantRow5.get(1).getText());
		//Patient weight
		assertEquals("8.3lbs", infantRow5.get(2).getText());
		//Patient head circumference
		assertEquals("14.5in", infantRow5.get(4).getText());
		//Patient household smoking status
		assertEquals("Indoor smokers", infantRow5.get(5).getText());	
		
		logout();
	}
	
	/**
	 * testOfficeVisit5YrOldViewHealthMetrics
	 * @throws Exception
	 */
	@Test
	public void testOfficeVisit5YrOldViewHealthMetrics() throws Exception{
		//Login as HCP Shelly Vang
		login("8000000011", "pw");	
		assertLogged(TransactionType.HOME_VIEW, 8000000011L, 0L, "");
		
		//Click Basic Health Information link
		driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Basic Health Information")).click();
		//Search for Fulton Gray (MID 103)
	    driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("103");
	    waitFor(1);
		//Click on first MID button
	    driver.findElement(By.xpath("//input[@value='103' and @type='button']")).click();
		//Verify Basic Health Information page
		assertEquals(ADDRESS + "auth/hcp-uap/viewBasicHealth.jsp", driver.getCurrentUrl());
		
		//Verify youth health record table displays
		WebElement hrTable = driver.findElement(By.id("HealthRecordsTable"));
		List<WebElement> tableRows = hrTable.findElements(By.tagName("tr"));
		List<WebElement> infantRow1 = tableRows.get(2).findElements(By.tagName("td"));
		List<WebElement> youthRow1 = tableRows.get(5).findElements(By.tagName("td"));
		List<WebElement> youthRow2 = tableRows.get(6).findElements(By.tagName("td"));
		//Verify the table has the provided information: Header, field descriptions, 3 rows of health records
		assertEquals(7, tableRows.size());
		
		//Verify table contents
		
		//Row 1 youth values
		//Office visit date
		assertEquals("10/14/2013", youthRow1.get(0).getText());
		//Patient length
		assertEquals("42.9in", youthRow1.get(1).getText());
		//Patient weight
		assertEquals("37.9lbs", youthRow1.get(2).getText());
		//Patient blood pressure
		assertEquals("95/65 mmHg", youthRow1.get(4).getText());
		//Patient household smoking status
		assertEquals("Outdoor smokers", youthRow1.get(5).getText());
		
		//Row 2 youth values
		//Office visit date
		assertEquals("10/15/2012", youthRow2.get(0).getText());
		//Patient length
		assertEquals("41.3in", youthRow2.get(1).getText());
		//Patient weight
		assertEquals("35.8lbs", youthRow2.get(2).getText());
		//Patient blood pressure
		assertEquals("95/65 mmHg", youthRow2.get(4).getText());
		//Patient household smoking status
		assertEquals("Indoor smokers", youthRow2.get(5).getText());
		
	
		//Row 1 baby values
		//Office visit date
		assertEquals("10/01/2011", infantRow1.get(0).getText());
		//Patient length
		assertEquals("39.3in", infantRow1.get(1).getText());
		//Patient weight
		assertEquals("36.5lbs", infantRow1.get(2).getText());
		//Patient head circumference
		assertEquals("19.9in", infantRow1.get(4).getText());
		//Patient household smoking status
		assertEquals("Indoor smokers", infantRow1.get(5).getText());
		
		//Log out
		logout();
	}
	
	/**
	 * testNoHealthRecordsExistByHCP
	 * @throws Exception
	 */
	@Test
	public void testNoHealthRecordsExistByHCP() throws Exception{
		//Login as HCP Shelly Vang
		login("8000000011", "pw");	
		assertLogged(TransactionType.HOME_VIEW, 8000000011L, 0L, "");
		
		//Click Basic Health Information link
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("Basic Health Information")).click();
		//Search for Brynn McClain (MID 101)
		driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("101");
	    waitFor(1);
		//Click on first MID button
	    driver.findElement(By.xpath("//input[@value='101' and @type='button']")).click();
		//Verify Basic Health Information page
		assertEquals(ADDRESS + "auth/hcp-uap/viewBasicHealth.jsp", driver.getCurrentUrl());
		
		//Verify that message displays to user
		assertTrue(pageContains("No health records available"));
		
	}
	
	/**
	 * testNoHealthRecordsExistByPatient
	 * @throws Exception
	 */
	@Test
	public void testNoHealthRecordsExistByPatient() throws Exception{
		//Login as Patient Brynn McClain (MID 101)
		login("101", "pw");	
		assertLogged(TransactionType.HOME_VIEW, 101L, 0L, "");
		
		//Click View My Records link
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
		driver.findElement(By.linkText("View My Records")).click();
		//Verify Basic Health Information page
		assertEquals(ADDRESS + "auth/patient/viewMyRecords.jsp", driver.getCurrentUrl());
		
		//Verify that message displays to user
		assertTrue(pageContains("No health records available"));
	}
}
