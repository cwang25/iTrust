package edu.ncsu.csc.itrust.seleniumtests;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import edu.ncsu.csc.itrust.enums.TransactionType;

public class EditDemographicsTest extends iTrustSeleniumTest{
  private WebDriver driver;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
	super.setUp();
    driver = new HtmlUnitDriver();
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
  }

  @Test
  public void testEditDemographics2() throws Exception {
    driver.get(ADDRESS + "/iTrust/auth/forwardUser.jsp");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("uappass1");
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("8000000009");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    assertEquals("iTrust - UAP Home", driver.getTitle());
    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
    driver.findElement(By.linkText("Edit Patient")).click();
    driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
    driver.findElement(By.xpath("//input[@value='2']")).submit();
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    // Warning: assertTextPresent may require manual changes
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*Patient Information[\\s\\S]*$"));
    driver.findElement(By.name("streetAddress1")).clear();
    driver.findElement(By.name("streetAddress1")).sendKeys("<script>alert('HACK!');</script>");
    driver.findElement(By.name("action")).click();
    // Warning: assertTextPresent may require manual changes
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*Street Address 1: Up to 30 alphanumeric characters, and \\.[\\s\\S]*$"));
    assertLogged(TransactionType.DEMOGRAPHICS_EDIT, 8000000009L, 2L, "");
  }
  
  @Test
  public void testEditDemographics3() throws Exception {
    driver.get(ADDRESS + "/iTrust/auth/forwardUser.jsp");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("uappass1");
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("8000000009");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    assertEquals("iTrust - UAP Home", driver.getTitle());
    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
    driver.findElement(By.linkText("Edit Patient")).click();
    driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
    driver.findElement(By.xpath("//input[@value='2']")).submit();
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    // Warning: assertTextPresent may require manual changes
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*Patient Information[\\s\\S]*$"));
    driver.findElement(By.name("streetAddress1")).clear();
    driver.findElement(By.name("streetAddress1")).sendKeys("100 New Address");
    driver.findElement(By.name("city")).clear();
    driver.findElement(By.name("city")).sendKeys("New Bern");
    driver.findElement(By.name("zip")).clear();
    driver.findElement(By.name("zip")).sendKeys("28562");
    driver.findElement(By.name("action")).click();
    // Warning: assertTextPresent may require manual changes
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*Information Successfully Updated[\\s\\S]*$"));
    assertLogged(TransactionType.DEMOGRAPHICS_EDIT, 8000000009L, 2L, "");
  }
  
  @Test
  public void testEditDemographics5() throws Exception {
    driver.get(ADDRESS + "/iTrust/auth/forwardUser.jsp");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("uappass1");
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("8000000009");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    assertEquals("iTrust - UAP Home", driver.getTitle());
    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
    driver.findElement(By.linkText("Edit Patient")).click();
    driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
    driver.findElement(By.xpath("//input[@value='2']")).submit();
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    // Warning: assertTextPresent may require manual changes
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*Patient Information[\\s\\S]*$"));
    driver.findElement(By.name("streetAddress1")).clear();
    driver.findElement(By.name("streetAddress1")).sendKeys("");
    driver.findElement(By.name("action")).click();
    // Warning: assertTextPresent may require manual changes
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*Information Successfully Updated[\\s\\S]*$"));
    assertLogged(TransactionType.DEMOGRAPHICS_EDIT, 8000000009L, 2L, "");
  }

  @Test
  public void testEditDemographics6() throws Exception {
    driver.get(ADDRESS + "/iTrust/auth/forwardUser.jsp");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("uappass1");
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("8000000009");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    assertEquals("iTrust - UAP Home", driver.getTitle());
    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
    driver.findElement(By.linkText("Edit Patient")).click();
    driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
    driver.findElement(By.xpath("//input[@value='2']")).submit();
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    // Warning: assertTextPresent may require manual changes
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*Patient Information[\\s\\S]*$"));
    driver.findElement(By.name("phone")).clear();
    driver.findElement(By.name("phone")).sendKeys("xxx-xxx-xxxx");
    driver.findElement(By.name("dateOfDeathStr")).clear();
    driver.findElement(By.name("dateOfDeathStr")).sendKeys("");
    driver.findElement(By.name("action")).click();
    // Warning: assertTextPresent may require manual changes
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*This form has not been validated correctly\\.[\\s\\S]*$"));
    assertLogged(TransactionType.DEMOGRAPHICS_EDIT, 8000000009L, 2L, "");
  }
  
  @Test
  public void testEditDemographics7() throws Exception {
	driver.get(ADDRESS + "/iTrust/auth/forwardUser.jsp");
	driver.findElement(By.id("j_password")).clear();
	driver.findElement(By.id("j_password")).sendKeys("pw");
	driver.findElement(By.id("j_username")).clear();
	driver.findElement(By.id("j_username")).sendKeys("2");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    assertEquals("iTrust - Patient Home", driver.getTitle());
    driver.findElement(By.cssSelector("h2.panel-title")).click();
    driver.findElement(By.linkText("My Demographics")).click();
    try{
    	driver.findElement(By.linkText("Sync With Google Account")).click();
    	assertTrue(false);
    } catch(NoSuchElementException e){
    	assertTrue(true);
    }
  }
  
  @Test
  public void testEditUpdatesImmediately() throws Exception {
    driver.get(ADDRESS + "/iTrust/auth/forwardUser.jsp");
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("1");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("pw");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.findElement(By.cssSelector("h2.panel-title")).click();
    driver.findElement(By.linkText("My Demographics")).click();
    driver.findElement(By.name("firstName")).clear();
    driver.findElement(By.name("firstName")).sendKeys("Randomer");
    driver.findElement(By.name("action")).click();
    // Warning: assertTextPresent may require manual changes
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*Information Successfully Updated[\\s\\S]*$"));
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }
}