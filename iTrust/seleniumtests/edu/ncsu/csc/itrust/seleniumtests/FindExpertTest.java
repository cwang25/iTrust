package edu.ncsu.csc.itrust.seleniumtests;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.junit.Test;

import edu.ncsu.csc.itrust.enums.TransactionType;

//import com.sun.org.apache.bcel.internal.generic.Select;

public class FindExpertTest extends iTrustSeleniumTest {
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
		login("9000000001", "pw");
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
	    List<WebElement> elems = driver.findElements(By.xpath("//input[@value='9000000000']"));
	    System.out.println(elems.size());
	    
	    elems.get(1).click();
	    assertEquals("iTrust - Edit Personnel", driver.getTitle());
	    driver.findElement(By.name("phone")).clear();
	    driver.findElement(By.name("phone")).sendKeys("919-100-1000");
	    driver.findElement(By.name("action")).click();
	    assertEquals("Information Successfully Updated", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
	    assertLogged(TransactionType.LHCP_EDIT, 9000000001L, 9000000000L, "");
	    //here is the log out
	    logout();
	    assertEquals("iTrust - Login", driver.getTitle());
	    login("1","pw");
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
