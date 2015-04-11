package edu.ncsu.csc.itrust.seleniumteststests;


import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class SecurePasswordTest extends iTrustSeleniumTest{

	
	private HtmlUnitDriver driver;
	private String baseUrl;

	@Before
	public void setUp() throws Exception {
	  super.setUp();
	  gen.clearAllTables();
	  gen.standardData();	
	  driver = new HtmlUnitDriver(BrowserVersion.INTERNET_EXPLORER_8);
	  baseUrl = "http://localhost:8080/iTrust/";
	  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	
	  @Test
	  public void testLoginHash() throws Exception {
	    driver.get(baseUrl);
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("iTrust - HCP Home", driver.getTitle());
	    assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
	    driver.findElement(By.linkText("Display Database")).click();
		WebElement baseTable = driver.findElement(By.id("users"));
		List<WebElement> tableRows = baseTable.findElements(By.tagName("tr"));
		tableRows.get(1).getText();
	  }
	  
	  @Test
	  public void testResetPassword() throws Exception {
	    driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
	    driver.findElement(By.linkText("Reset Password")).click();
	    driver.findElement(By.name("mid")).clear();
	    driver.findElement(By.name("mid")).sendKeys("1");
	    driver.findElement(By.cssSelector("td > input[type=\"submit\"]")).click();
	    driver.findElement(By.name("answer")).clear();
	    driver.findElement(By.name("answer")).sendKeys("blue");
	    driver.findElement(By.name("password")).clear();
	    driver.findElement(By.name("password")).sendKeys("newPw12345");
	    driver.findElement(By.name("confirmPassword")).clear();
	    driver.findElement(By.name("confirmPassword")).sendKeys("newPw12345");
	    driver.findElement(By.cssSelector("td > input[type=\"submit\"]")).click();
	    assertLogged(TransactionType.PASSWORD_RESET, 1L, 1L, "");
	    assertEquals("Password changed", driver.findElement(By.cssSelector("form > table > tbody > tr > td")).getText());
	    driver.findElement(By.linkText("Display Database")).click();
	  }
	  
}
