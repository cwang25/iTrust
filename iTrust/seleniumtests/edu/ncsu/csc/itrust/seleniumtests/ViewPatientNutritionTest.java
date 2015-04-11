package edu.ncsu.csc.itrust.seleniumtests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;


public class ViewPatientNutritionTest extends iTrustSeleniumTest{
	WebDriver driver;
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
		driver = new HtmlUnitDriver();
	}
	
	public void testPatientViewsMyNutrition() throws Exception{
		//Login as Baby Programmer
		WebDriver driver = login("5","pw");
		
		assertEquals(driver.getTitle(), "iTrust - Patient Home");
		
		driver.findElement(By.partialLinkText("Nutrition")).click();
		
		assertEquals(driver.getTitle(), "iTrust - View My Nutrition");
	}
	
	public void testNutritionistViewsPatientNutrition() throws Exception {
		//Login as Spencer Reid
		driver.get(ADDRESS + "/auth/forwardUser.jsp");
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000006900");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		
		assertEquals(driver.getTitle(), "iTrust - HCP Home");
		
		driver.findElement(By.partialLinkText("Patient Nutrition")).click();
		
		assertEquals(driver.getTitle(), "iTrust - Please Select a Patient");
		
		driver.get(ADDRESS + "/auth/getPatientID.jsp?UID_PATIENTID=5&forward=hcp/viewPatientNutrition.jsp");

		assertEquals("iTrust - View Patient Nutrition", driver.getTitle());
		
	}
}
