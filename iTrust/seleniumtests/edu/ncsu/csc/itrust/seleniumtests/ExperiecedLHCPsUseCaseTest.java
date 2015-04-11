package edu.ncsu.csc.itrust.seleniumtests;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import edu.ncsu.csc.itrust.enums.TransactionType;

public class ExperiecedLHCPsUseCaseTest extends iTrustSeleniumTest{
  private WebDriver driver;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
	super.setUp();
    driver = new HtmlUnitDriver();
    gen.patient_hcp_vists();
    gen.hcp_diagnosis_data();
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
  }

  @Test
  public void testViewDiagnoses() throws Exception {
    driver.get(ADDRESS + "/iTrust/auth/forwardUser.jsp");
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("1");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("pw");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
    driver.findElement(By.linkText("My Diagnoses")).click();
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Echovirus(79.10)"));
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Acute Lycanthropy(250.00)"));
  }

  @Test
  public void testViewDiagnosesisEchoVirus() throws Exception {
    driver.get(ADDRESS + "/iTrust/auth/forwardUser.jsp");
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("1");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("pw");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
    driver.findElement(By.linkText("My Diagnoses")).click();
    assertEquals("iTrust - My Diagnoses", driver.getTitle());
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Echovirus(79.10)"));
    driver.findElement(By.linkText("Echovirus(79.10)")).click();
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("HCPs having experience with diagnosis 79.10"));
	assertLogged(TransactionType.EXPERIENCED_LHCP_FIND, 1L, 0L, "");	
  }
  
  @Test
  public void testViewHCPDetails() throws Exception {
    driver.get(ADDRESS + "/iTrust/auth/forwardUser.jsp");
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("1");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("pw");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
    driver.findElement(By.linkText("My Diagnoses")).click();
    assertEquals("iTrust - My Diagnoses", driver.getTitle());
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Echovirus(79.10)"));
    driver.findElement(By.linkText("Echovirus(79.10)")).click();
    driver.findElement(By.linkText("Jason Frankenstein")).click();
    assertEquals("iTrust - View Personnel Details", driver.getTitle());
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Jason Frankenstein"));
	assertLogged(TransactionType.PERSONNEL_VIEW, 1L, 9000000004L, "");
  }
 
 
}
