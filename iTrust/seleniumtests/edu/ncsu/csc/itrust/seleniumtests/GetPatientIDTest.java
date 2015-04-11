package edu.ncsu.csc.itrust.seleniumtests;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.junit.Test;

public class GetPatientIDTest {
	private WebDriver driver;
	  private String baseUrl;
	  private boolean acceptNextAlert = true;
	  private StringBuffer verificationErrors = new StringBuffer();

	  @Before
	  public void setUp() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
	    baseUrl = "http://localhost:8080/";
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  }

	  @Test
	  public void testSelectPatientButton() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080/iTrust/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseUrl + "auth/forwardUser.jsp");
		
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000003");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("iTrust - HCP Home", driver.getTitle());
	    driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Patient Information")).click();
	    assertEquals("iTrust - Please Select a Patient", driver.getTitle());
	    assertThat("HTTP Status 500", is(not(driver.findElement(By.cssSelector("body > div.container-fluid")).getText())));
	    assertThat("java.lang.NumberFormatException", is(not(driver.findElement(By.cssSelector("body > div.container-fluid")).getText())));
	    assertEquals("iTrust - Please Select a Patient", driver.getTitle());
	    assertThat("HTTP Status 500", is(not(driver.findElement(By.cssSelector("body > div.container-fluid")).getText())));
	    assertThat("java.lang.NumberFormatException", is(not(driver.findElement(By.cssSelector("body > div.container-fluid")).getText())));
	    assertThat("Viewing information for <b>null</b>", is(not(driver.findElement(By.cssSelector("body > div.container-fluid")).getText())));
	  }

	/*  @After
	  public void tearDown() throws Exception {
	    driver.quit();
	    String verificationErrorString = verificationErrors.toString();
	    if (!"".equals(verificationErrorString)) {
	      fail(verificationErrorString);
	    }
	  }

	  private boolean isElementPresent(By by) {
	    try {
	      driver.findElement(by);
	      return true;
	    } catch (NoSuchElementException e) {
	      return false;
	    }
	  }

	  private boolean isAlertPresent() {
	    try {
	      driver.switchTo().alert();
	      return true;
	    } catch (NoAlertPresentException e) {
	      return false;
	    }
	  }

	  private String closeAlertAndGetItsText() {
	    try {
	      Alert alert = driver.switchTo().alert();
	      String alertText = alert.getText();
	      if (acceptNextAlert) {
	        alert.accept();
	      } else {
	        alert.dismiss();
	      }
	      return alertText;
	    } finally {
	      acceptNextAlert = true;
	    }
	  }*/
}
