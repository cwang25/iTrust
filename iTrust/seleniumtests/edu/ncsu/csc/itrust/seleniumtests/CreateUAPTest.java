package edu.ncsu.csc.itrust.seleniumtests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

public class CreateUAPTest extends iTrustSeleniumTest {

	public void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.admin1();
		gen.hcp0();
		gen.cptCodes();
	}
	
	public void testCreateUAP1() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		driver = login("9000000000", "pw");
		driver.findElement(By.linkText("UAP")).click();
		assertEquals("iTrust - Add UAP", driver.getTitle());
		
		WebElement firstName = driver.findElement(By.name("firstName"));
		firstName.sendKeys("Drake");
		WebElement lastName = driver.findElement(By.name("lastName"));
		lastName.sendKeys("Ramoray");
		WebElement email = driver.findElement(By.name("email"));
		email.sendKeys("drake@drake.com");
		WebElement form = driver.findElement(By.name("formIsFilled"));
		form.submit();
		
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
		streetAddress1.sendKeys("1234 Varsity Ln");
		streetAddress2.sendKeys("2nd Lane");
		city.sendKeys("Cary");
		state.selectByValue("NC");
		zip.sendKeys("12345-1234");
		phone.sendKeys("704-100-1000");
		phone.submit();
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
	}
}
