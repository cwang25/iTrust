package edu.ncsu.csc.itrust.seleniumtests;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import edu.ncsu.csc.itrust.enums.TransactionType;

public class BMICalculationTest extends iTrustSeleniumTest{
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
		
	}
	
	public void testUnderweightEC() throws Exception {
		//Login as HCP Kelly Doctor
		login("9000000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		WebElement element;
		List<WebElement> elements;
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		//go to the basic health information search for patient page
		element = driver.findElement(By.linkText("Basic Health Information"));
		element.click();
		
		//use the old search to go to the patients page
		//use the old search to go to the patients page
		driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("106");
	    //waitFor(1);
	    this.clickOnJavascriptElement(By.xpath("//input[@value='106' and @type='button']"));
		
		//Verify Basic Health Information page
		assertEquals(ADDRESS + "auth/hcp-uap/viewBasicHealth.jsp", driver.getCurrentUrl());
		
		//check to see table exists and has right size
		elements = driver.findElements(By.xpath("//*[@id='HealthRecordsTable']/tbody/tr"));
		assertEquals(5,elements.size());
		
		//Underweight Equivalence Class - Row 3
		//Office visit date
		elements = driver.findElements(By.xpath("//*[@id='HealthRecordsTable']/tbody/tr[5]/td"));
		assertEquals("01/01/2013", elements.get(0).getText());
		//Patient BMI
		elements = driver.findElements(By.xpath("//*[@id='HealthRecordsTable']/tbody/tr[5]/td"));
		assertEquals("9.0", elements.get(3).getText());
		//Patient Weight Status
		elements = driver.findElements(By.xpath("//*[@id='HealthRecordsTable']/tbody/tr[5]/td"));
		assertEquals("Underweight", elements.get(4).getText());
		
		//Underweight Boundary Value (Low) - Row 2
		//Office visit date
		elements = driver.findElements(By.xpath("//*[@id='HealthRecordsTable']/tbody/tr[4]/td"));
		assertEquals("01/02/2013", elements.get(0).getText());
		//Patient BMI
		elements = driver.findElements(By.xpath("//*[@id='HealthRecordsTable']/tbody/tr[4]/td"));
		assertEquals("0.1", elements.get(3).getText());
		//Patient Weight Status
		elements = driver.findElements(By.xpath("//*[@id='HealthRecordsTable']/tbody/tr[4]/td"));
		assertEquals("Underweight", elements.get(4).getText());
		
		//Underweight Boundary Value (High) - Row 1
		//Office visit date
		elements = driver.findElements(By.xpath("//*[@id='HealthRecordsTable']/tbody/tr[3]/td"));
		assertEquals("01/03/2013", elements.get(0).getText());
		//Patient BMI
		elements = driver.findElements(By.xpath("//*[@id='HealthRecordsTable']/tbody/tr[3]/td"));
		assertEquals("18.4", elements.get(3).getText());
		//Patient Weight Status
		elements = driver.findElements(By.xpath("//*[@id='HealthRecordsTable']/tbody/tr[3]/td"));
		assertEquals("Underweight", elements.get(4).getText());
		
	}
	
	public void testNormalEC() throws Exception {
		//Login as HCP Kelly Doctor
		login("9000000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		WebElement element;
		List<WebElement> elements;
		
		//go to the basic health information search for patient page
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		element = driver.findElement(By.linkText("Basic Health Information"));
		element.click();
		
		//use the old search to go to the patients page
		driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("107");
	    //waitFor(1);
	    
	    this.clickOnJavascriptElement(By.xpath("//input[@value='107' and @type='button']"));
	    
		//Verify Basic Health Information page
		assertEquals(ADDRESS + "auth/hcp-uap/viewBasicHealth.jsp", driver.getCurrentUrl());
		
		//check to see table exists and has right size
		elements = driver.findElements(By.xpath("//*[@id='HealthRecordsTable']/tbody/tr"));
		assertEquals(5,elements.size());
		
		//Normal Weight Equivalence Class - Row 3
		//Office visit date
		elements = driver.findElements(By.xpath("//*[@id='HealthRecordsTable']/tbody/tr[5]/td"));
		assertEquals("01/01/2013", elements.get(0).getText());
		//Patient BMI
		elements = driver.findElements(By.xpath("//*[@id='HealthRecordsTable']/tbody/tr[5]/td"));
		assertEquals("21.75", elements.get(3).getText());
		//Patient Weight Status
		elements = driver.findElements(By.xpath("//*[@id='HealthRecordsTable']/tbody/tr[5]/td"));
		assertEquals("Normal", elements.get(4).getText());
		
		//Normal weight Boundary Value (Low) - Row 2
		//Office visit date
		elements = driver.findElements(By.xpath("//*[@id='HealthRecordsTable']/tbody/tr[4]/td"));
		assertEquals("01/02/2013", elements.get(0).getText());
		//Patient BMI
		elements = driver.findElements(By.xpath("//*[@id='HealthRecordsTable']/tbody/tr[4]/td"));
		assertEquals("18.5", elements.get(3).getText());
		//Patient Weight Status
		elements = driver.findElements(By.xpath("//*[@id='HealthRecordsTable']/tbody/tr[4]/td"));
		assertEquals("Normal", elements.get(4).getText());
		
		//Normal Weight Boundary Value (High) - Row 1
		//Office visit date
		elements = driver.findElements(By.xpath("//*[@id='HealthRecordsTable']/tbody/tr[3]/td"));
		assertEquals("01/03/2013", elements.get(0).getText());
		//Patient BMI
		elements = driver.findElements(By.xpath("//*[@id='HealthRecordsTable']/tbody/tr[3]/td"));
		assertEquals("24.9", elements.get(3).getText());
		//Patient Weight Status
		elements = driver.findElements(By.xpath("//*[@id='HealthRecordsTable']/tbody/tr[3]/td"));
		assertEquals("Normal", elements.get(4).getText());
		
		
	}
	
	public void testOverweightEC() throws Exception {
		//Login as HCP Kelly Doctor
		login("9000000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		WebElement element;
		List<WebElement> elements;
		
		//go to the basic health information search for patient page
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		element = driver.findElement(By.linkText("Basic Health Information"));
		element.click();
		
		//use the old search to go to the patients page
		driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("108");
	    //waitFor(1);
	    this.clickOnJavascriptElement(By.xpath("//input[@value='108' and @type='button']"));
	    
		//Verify Basic Health Information page
		assertEquals(ADDRESS + "auth/hcp-uap/viewBasicHealth.jsp", driver.getCurrentUrl());
		
		//check to see table exists and has right size
		elements = driver.findElements(By.xpath("//*[@id='HealthRecordsTable']/tbody/tr"));
		assertEquals(5,elements.size());
		
		//Normal Weight Equivalence Class - Row 3
		//Office visit date
		elements = driver.findElements(By.xpath("//*[@id='HealthRecordsTable']/tbody/tr[5]/td"));
		assertEquals("01/01/2013", elements.get(0).getText());
		//Patient BMI
		elements = driver.findElements(By.xpath("//*[@id='HealthRecordsTable']/tbody/tr[5]/td"));
		assertEquals("27.5", elements.get(3).getText());
		//Patient Weight Status
		elements = driver.findElements(By.xpath("//*[@id='HealthRecordsTable']/tbody/tr[5]/td"));
		assertEquals("Overweight", elements.get(4).getText());
		
		//Overweight Boundary Value (Low) - Row 2
		//Office visit date
		elements = driver.findElements(By.xpath("//*[@id='HealthRecordsTable']/tbody/tr[4]/td"));
		assertEquals("01/02/2013", elements.get(0).getText());
		//Patient BMI
		elements = driver.findElements(By.xpath("//*[@id='HealthRecordsTable']/tbody/tr[4]/td"));
		assertEquals("25.0", elements.get(3).getText());
		//Patient Weight Status
		elements = driver.findElements(By.xpath("//*[@id='HealthRecordsTable']/tbody/tr[4]/td"));
		assertEquals("Overweight", elements.get(4).getText());
		
		//Overweight Boundary Value (High) - Row 1
		//Office visit date
		elements = driver.findElements(By.xpath("//*[@id='HealthRecordsTable']/tbody/tr[3]/td"));
		assertEquals("01/03/2013", elements.get(0).getText());
		//Patient BMI
		elements = driver.findElements(By.xpath("//*[@id='HealthRecordsTable']/tbody/tr[3]/td"));
		assertEquals("29.9", elements.get(3).getText());
		//Patient Weight Status
		elements = driver.findElements(By.xpath("//*[@id='HealthRecordsTable']/tbody/tr[3]/td"));
		assertEquals("Overweight", elements.get(4).getText());
	}
	

}
