package edu.ncsu.csc.itrust.seleniumtests;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.junit.Test;

import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.http.iTrustHTTPTest;

//import com.sun.org.apache.bcel.internal.generic.Select;

public class FindExpertTest extends iTrustSeleniumTest {
	 private WebDriver driver;
	  private String baseUrl;
	  private boolean acceptNextAlert = true;
	  private StringBuffer verificationErrors = new StringBuffer();

	  @Override
	  public void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
		//WebDriver driver = new HtmlUnitDriver();
	    //baseUrl = "http://localhost:8080/";
	    //driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  }

	  @Test
	  public void testEditAndFindExpert() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080/iTrust/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseUrl + "auth/forwardUser.jsp");
		
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000001");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
	    assertEquals("iTrust - Admin Home", driver.getTitle());
	    assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
	    driver.findElement(By.linkText("Edit Personnel")).click();
	    assertEquals("iTrust - Please Select a Personnel", driver.getTitle());
	    driver.findElement(By.name("FIRST_NAME")).clear();
	    driver.findElement(By.name("FIRST_NAME")).sendKeys("Kelly");
	    driver.findElement(By.name("LAST_NAME")).clear();
	    driver.findElement(By.name("LAST_NAME")).sendKeys("Doctor");
	    driver.findElement(By.xpath("//input[@value='User Search']")).click();
	    driver.findElement(By.xpath("(//input[@value='9000000000'])[2]")).click();
	    assertEquals("iTrust - Edit Personnel", driver.getTitle());
	    driver.findElement(By.name("phone")).clear();
	    driver.findElement(By.name("phone")).sendKeys("919-100-1000");
	    driver.findElement(By.name("action")).click();
	    assertEquals("Information Successfully Updated", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
	    assertLogged(TransactionType.LHCP_EDIT, 9000000001L, 9000000000L, "");
	    //here is the log out
	    driver.findElement(By.cssSelector("a[href='/iTrust/logout.jsp']")).click();
	    assertEquals("iTrust - Login", driver.getTitle());
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("1");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
	    assertEquals("iTrust - Patient Home", driver.getTitle());
	    driver.findElement(By.linkText("Find an Expert")).click();
	    assertEquals("iTrust - Find an Expert", driver.getTitle());
	    new Select(driver.findElement(By.name("specialty"))).selectByVisibleText("Surgeon");
	    driver.findElement(By.name("findExpert")).click();
	    // Warning: assertTextPresent may require manual changes
	    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*Kelly Doctor[\\s\\S]*$"));
	  }

	  /*@After
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
