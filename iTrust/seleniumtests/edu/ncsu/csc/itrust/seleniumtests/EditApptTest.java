package edu.ncsu.csc.itrust.seleniumtests;

import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class EditApptTest extends iTrustSeleniumTest{
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
	super.setUp();
	gen.standardData();
  }

  @Test
  public void testSetPassedDate() throws Exception {
	driver.get(ADDRESS);
	driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("9000000000");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("pw");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    assertEquals("iTrust - HCP Home", driver.getTitle());
    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
    driver.findElement(By.linkText("View My Appointments")).click();
    assertLogged(TransactionType.APPOINTMENT_ALL_VIEW, 9000000000L, 0L, "");
    driver.findElement(By.xpath("(//a[contains(text(),'Edit/Remove')])[5]")).click();
    // Warning: assertTextPresent may require manual changes
    Assert.assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Andy Programmer"));
    driver.findElement(By.xpath("//form[@id='mainForm']/div/table/tbody/tr[4]/td/input")).clear();
    driver.findElement(By.xpath("//form[@id='mainForm']/div/table/tbody/tr[4]/td/input")).sendKeys("10/10/2009");
    driver.findElement(By.id("changeButton")).click();
    assertNotLogged(TransactionType.APPOINTMENT_EDIT, 9000000000L, 100L, "");
  }

  @Test
  public void testRemoveAppt() throws Exception {
    driver.get(ADDRESS);
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("9000000000");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("pw");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    assertEquals("iTrust - HCP Home", driver.getTitle());
    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
    driver.findElement(By.linkText("View My Appointments")).click();
    assertLogged(TransactionType.APPOINTMENT_ALL_VIEW, 9000000000L, 0L, "");
    driver.findElement(By.xpath("(//a[contains(text(),'Edit/Remove')])[5]")).click();
    // Warning: assertTextPresent may require manual changes
    Assert.assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Andy Programmer"));
    driver.findElement(By.xpath("//form[@id='mainForm']/input[3]")).click();
    //assertLogged(TransactionType.APPOINTMENT_REMOVE, 9000000000L, 100L, "");
  }
  
  @Test
  public void testEditAppt() throws Exception {
    driver.get(ADDRESS);
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("9000000000");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("pw");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    assertEquals("iTrust - HCP Home", driver.getTitle());
    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
    driver.findElement(By.linkText("View My Appointments")).click();
    assertLogged(TransactionType.APPOINTMENT_ALL_VIEW, 9000000000L, 0L, "");
    driver.findElement(By.xpath("(//a[contains(text(),'Edit/Remove')])[5]")).click();
    // Warning: assertTextPresent may require manual changes
    Assert.assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Andy Programmer"));
    driver.findElement(By.xpath("//form[@id='mainForm']/table/tbody/tr[2]/td/textarea")).clear();
    driver.findElement(By.xpath("//form[@id='mainForm']/table/tbody/tr[2]/td/textarea")).sendKeys("New comment!");
    driver.findElement(By.id("changeButton")).click();
    //assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Success: Appointment changed"));
    //assertLogged(TransactionType.APPOINTMENT_EDIT, 9000000000L, 2L, "");
  }
 
  
  @After
  public void tearDown() throws Exception {
	super.tearDown();
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }
}
