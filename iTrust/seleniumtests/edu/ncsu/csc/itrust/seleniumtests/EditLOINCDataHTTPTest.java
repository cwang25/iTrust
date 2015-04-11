package edu.ncsu.csc.itrust.seleniumtests;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

public class EditLOINCDataHTTPTest extends iTrustSeleniumTest{
  private WebDriver driver;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
	super.setUp();
    driver = new HtmlUnitDriver();
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
  }

  @Test
  public void testAddLOINCFile() throws Exception {
	driver.get(ADDRESS + "/iTrust/auth/forwardUser.jsp");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("pw");
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("9000000001");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
    driver.findElement(By.linkText("Edit LOINC Codes")).click();
    driver.findElement(By.name("import")).click();
    driver.get(ADDRESS + "/iTrust/auth/admin/uploadLOINC.jsp");
    assertEquals("iTrust - Upload LOINC Codes", driver.getTitle());
    driver.findElement(By.name("loincFile")).clear();
    driver.findElement(By.name("loincFile")).sendKeys("testing-files/sample_loinc/sampleLoinc.txt");
    new Select(driver.findElement(By.name("ignoreDupData"))).selectByVisibleText("Ignore Duplicates");
    driver.findElement(By.id("sendFile")).click();
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Upload Successful"));
    driver.findElement(By.linkText("Return to LOINC Codes List")).click();
    assertFalse(driver.findElement(By.cssSelector("BODY")).getText().contains("THIS ONE IS DIFFERENT"));
  }
  
  @Test
  public void testAddLOINCFileNoIgnore() throws Exception {
	driver.get(ADDRESS + "/iTrust/auth/forwardUser.jsp");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("pw");
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("9000000001");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
    driver.findElement(By.linkText("Edit LOINC Codes")).click();
    driver.findElement(By.name("import")).click();
    driver.get(ADDRESS + "/iTrust/auth/admin/uploadLOINC.jsp");
    assertEquals("iTrust - Upload LOINC Codes", driver.getTitle());
    driver.findElement(By.name("loincFile")).clear();
    driver.findElement(By.name("loincFile")).sendKeys("testing-files/sample_loinc/sampleLoinc.txt");
    new Select(driver.findElement(By.name("ignoreDupData"))).selectByVisibleText("Replace Duplicates");
    driver.findElement(By.id("sendFile")).click();
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Upload Successful"));
    driver.findElement(By.linkText("Return to LOINC Codes List")).click();
    // Warning: assertTextNotPresent may require manual changes
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("THIS ONE IS DIFFERENT"));
  }
  
  @Test
  public void testUploadLOINCFileInvalidLines() throws Exception {
	driver.get(ADDRESS + "/iTrust/auth/forwardUser.jsp");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("pw");
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("9000000001");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
    driver.findElement(By.linkText("Edit LOINC Codes")).click();
    driver.findElement(By.name("import")).click();
    driver.get(ADDRESS + "/iTrust/auth/admin/uploadLOINC.jsp");
    assertEquals("iTrust - Upload LOINC Codes", driver.getTitle());
    driver.findElement(By.name("loincFile")).clear();
    driver.findElement(By.name("loincFile")).sendKeys("testing-files/sample_loinc/invalidLine.txt");
    new Select(driver.findElement(By.name("ignoreDupData"))).selectByVisibleText("Replace Duplicates");
    driver.findElement(By.id("sendFile")).click();
    assertTrue(driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[2]/td/div")).getText().contains("ERROR, LINE 2: \"10054-5\" \"I skip rest of fields\" This form has not been validated correctly. The following field are not properly filled in: [You must have a Lab Procedure Code, Component and Kind Of Property]"));
    assertTrue(driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[2]/td/div")).getText().contains("Successfully added 2 lines of new LOINC data. Updated 0 lines of existing LOINC data."));
  }
  
  @Test
  public void testUploadBadLOINCFile() throws Exception {
	driver.get(ADDRESS + "/iTrust/auth/forwardUser.jsp");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("pw");
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("9000000001");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
    driver.findElement(By.linkText("Edit LOINC Codes")).click();
    driver.findElement(By.name("import")).click();
    driver.get(ADDRESS + "/iTrust/auth/admin/uploadLOINC.jsp");
    assertEquals("iTrust - Upload LOINC Codes", driver.getTitle());
    driver.findElement(By.name("loincFile")).clear();
    driver.findElement(By.name("loincFile")).sendKeys("testing-files/sample_loinc/badLoincFile.txt");
    new Select(driver.findElement(By.name("ignoreDupData"))).selectByVisibleText("Ignore Duplicates");
    driver.findElement(By.id("sendFile")).click();
    assertTrue(driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[2]/td/div")).getText().contains("IGNORED LINE 1: This file contains no LOINC data and should fail the LOINC file verification process."));
    assertTrue(driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[2]/td/div")).getText().contains("File invalid. No LOINC data added."));
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
