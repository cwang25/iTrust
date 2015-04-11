package edu.ncsu.csc.itrust.seleniumtests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

public class CreateHCPTest extends iTrustSeleniumTest {
	public void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.admin1();
		gen.hcp0();
		gen.cptCodes();
	}
	
	public void testCreateValidHCP() throws Exception {
		//login as admin
		WebDriver driver = new HtmlUnitDriver();
		driver = login("9000000001", "pw");
		assertEquals("iTrust - Admin Home", driver.getTitle());
		
		driver.findElement(By.linkText("Add HCP")).click();
		assertEquals("iTrust - Add HCP", driver.getTitle());
		
		WebElement firstName = driver.findElement(By.name("firstName"));
		firstName.sendKeys("Laurie");
		WebElement lastName = driver.findElement(By.name("lastName"));
		lastName.sendKeys("Williams");
		WebElement email = driver.findElement(By.name("email"));
		email.sendKeys("laurie@ncsu.edu");
		email.submit();
		
		driver.findElement(By.linkText("Continue to personnel information.")).click();
		assertEquals("iTrust - Edit Personnel", driver.getTitle());
		
		WebElement streetAddress1, streetAddress2, city, zip, phone;
		Select state; 
		firstName = driver.findElement(By.name("firstName"));
		lastName = driver.findElement(By.name("lastName"));
		streetAddress1 = driver.findElement(By.name("streetAddress1"));
		streetAddress2 = driver.findElement(By.name("streetAddress2"));
		city = driver.findElement(By.name("city"));
		state = new Select(driver.findElement(By.name("state")));
		zip = driver.findElement(By.name("zip"));
		phone = driver.findElement(By.name("phone"));
		
		firstName.clear();
		lastName.clear();
		streetAddress1.clear();
		streetAddress2.clear();
		city.clear();

		zip.clear();
		phone.clear();
		
		firstName.sendKeys("Doctor");
		lastName.sendKeys("Watson");
		streetAddress1.sendKeys("900 Main Campus Dr");
		streetAddress2.sendKeys("Box 2509");
		city.sendKeys("Raleigh");
		state.selectByValue("NC");
		zip.sendKeys("27606-1234");
		phone.sendKeys("919-100-1000");
		phone.submit();
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
	}
	
	public void testEditValidPeronnel() throws Exception {
		//login as admin
		WebDriver driver = new HtmlUnitDriver();
		driver = login("9000000001", "pw");
		assertEquals("iTrust - Admin Home", driver.getTitle());
		
		driver.findElement(By.linkText("Edit Personnel")).click();
		assertEquals("iTrust - Please Select a Personnel", driver.getTitle());
		
		WebElement firstName = driver.findElement(By.name("FIRST_NAME"));
		firstName.sendKeys("Kelly");
		WebElement lastName = driver.findElement(By.name("LAST_NAME"));
		lastName.sendKeys("Doctor");
		WebElement form = driver.findElement(By.id("userSearchForm"));
		form.submit();
		WebElement user = driver.findElement(By.id("9000000000"));
		user.submit();
		assertEquals("iTrust - Edit Personnel", driver.getTitle());
		
		WebElement city = driver.findElement(By.name("city"));
		city.clear();
		city.sendKeys("Brooklyn");
		city.submit();
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
	}
	
	public void testEditHospitalAssignments() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		
		//login as admin
		WebDriver driver = new HtmlUnitDriver();
		driver = login("9000000001", "pw");
		assertEquals("iTrust - Admin Home", driver.getTitle());
		
		driver.findElement(By.linkText("Edit HCP Assignment to Hospital")).click();
		assertEquals("iTrust - Please Select a Personnel", driver.getTitle());
		
		WebElement firstName = driver.findElement(By.name("FIRST_NAME"));
		firstName.sendKeys("Kelly");
		WebElement lastName = driver.findElement(By.name("LAST_NAME"));
		lastName.sendKeys("Doctor");
		WebElement form = driver.findElement(By.id("userSearchForm"));
		form.submit();
		WebElement user = driver.findElement(By.id("9000000000")); //mid of the user search result
		user.submit();
		assertEquals("iTrust - Hospital Staffing Assignments", driver.getTitle());
		
		driver.findElement(By.linkText("Assign")).click();
		assertTrue(driver.getPageSource().contains("HCP has been assigned"));
		
		driver.findElement(By.linkText("Unassign")).click();
		assertTrue(driver.getPageSource().contains("HCP has been unassigned"));
	}
}
