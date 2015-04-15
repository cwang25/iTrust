package edu.ncsu.csc.itrust.seleniumtests;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.*;

public class MyWeightLogTest extends iTrustSeleniumTest {

	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();

	}

	public void testAddValidWeightLogEntry() throws Exception {
		login("500", "pw");
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("My Weight Log")).click();
		driver.findElement(By.id("showNewLogForm")).click();
		driver.findElement(By.name("weight")).clear();
		driver.findElement(By.name("weight")).sendKeys("170");
		driver.findElement(By.name("chest")).clear();
		driver.findElement(By.name("chest")).sendKeys("30.8");
		driver.findElement(By.name("waist")).clear();
		driver.findElement(By.name("waist")).sendKeys("20.9");
		driver.findElement(By.name("upperarm")).clear();
		driver.findElement(By.name("upperarm")).sendKeys("12.3");
		driver.findElement(By.name("forearm")).clear();
		driver.findElement(By.name("forearm")).sendKeys("11.5");
		driver.findElement(By.name("thigh")).clear();
		driver.findElement(By.name("thigh")).sendKeys("20.0");
		driver.findElement(By.name("calves")).clear();
		driver.findElement(By.name("calves")).sendKeys("14.7");
		driver.findElement(By.name("neck")).clear();
		driver.findElement(By.name("neck")).sendKeys("19.5");
		driver.findElement(By.id("saveBtn")).click();
		assertTextInBody("Log successfully added!");
	}
	
	public void testAddInvalidWeightLogEntry() throws Exception {
		login("500", "pw");
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("My Weight Log")).click();
		driver.findElement(By.id("showNewLogForm")).click();
		driver.findElement(By.name("weight")).clear();
		driver.findElement(By.name("weight")).sendKeys("-170");
		driver.findElement(By.name("chest")).clear();
		driver.findElement(By.name("chest")).sendKeys("30.8");
		driver.findElement(By.name("waist")).clear();
		driver.findElement(By.name("waist")).sendKeys("20.9");
		driver.findElement(By.name("upperarm")).clear();
		driver.findElement(By.name("upperarm")).sendKeys("12.3");
		driver.findElement(By.name("forearm")).clear();
		driver.findElement(By.name("forearm")).sendKeys("-11.5");
		driver.findElement(By.name("thigh")).clear();
		driver.findElement(By.name("thigh")).sendKeys("20.0");
		driver.findElement(By.name("calves")).clear();
		driver.findElement(By.name("calves")).sendKeys("14.7");
		driver.findElement(By.name("neck")).clear();
		driver.findElement(By.name("neck")).sendKeys("19.5");
		driver.findElement(By.id("saveBtn")).click();
		assertTextInBody("Weight must be greater than 0");
		assertTextInBody("Forearm measurement must be greather than 0.");
	}
	
	  public void testHCPViewPatientWeightLog() throws Exception {
		  login("9900000012", "pw");
		    driver.findElement(By.cssSelector("div.panel-heading")).click();
		    driver.findElement(By.linkText("Patient Weight Log")).click();
		    driver.findElement(By.id("searchBox")).clear();
		    driver.findElement(By.id("searchBox")).sendKeys("500");
		    // doesn't seem to pass unless I wait - bmander4
		    waitFor(1);
			driver.findElement(By.xpath("//input[@value='500' and @type='button']")).click();
		    assertTextInBody("160.0"); // check weight entry exists
		    assertTextInBody("14.3"); // check calves entry exists
		    assertTextInBody("2014-05-11"); // check date entry exists
		  }
}
