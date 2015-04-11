package edu.ncsu.csc.itrust.seleniumtests;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;
import org.junit.Test;

public class ExpertReviewsTest {
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
	  public void testDirectRating() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080/iTrust/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseUrl + "auth/forwardUser.jsp");
		
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("109");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[7]/div/h2")).click();
	    assertEquals("iTrust - Patient Home", driver.getTitle());
	    driver.findElement(By.linkText("Expert's Reviews")).click();
	    assertEquals("iTrust - Please Select an Expert", driver.getTitle());
	    
	   // driver.findElement(By.id("searchBox")).clear();
	   // driver.findElement(By.id("searchBox")).sendKeys("kelly");
	   // driver.findElement(By.name("UID_PATIENTID")).sendKeys("kelly"); //still a problem
	   // driver.findElement(By.linkText("View Reviews")).click();
	   // driver.findElement(By.name("UID_PATIENTID")).submit();
	    driver.get(baseUrl + "auth/patient/reviewsPage.jsp?expertID=9000000000");
	    
	    assertEquals("iTrust - Reviews Page", driver.getTitle());
	    assertEquals("Reviews for Kelly Doctor", driver.findElement(By.cssSelector("h1")).getText());
	  }
	  
	  @Test
	  public void testOverallRating() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080/iTrust/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseUrl + "auth/forwardUser.jsp");
			
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("22");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[7]/div/h2")).click();
	    assertEquals("iTrust - Patient Home", driver.getTitle());
	    driver.findElement(By.linkText("Expert's Reviews")).click();
	    assertEquals("iTrust - Please Select an Expert", driver.getTitle());
	    //driver.findElement(By.id("searchBox")).clear();
	    //driver.findElement(By.id("searchBox")).sendKeys("gan"); //not need with work around 
	    //driver.findElement(By.linkText("View Reviews")).click();
	    driver.get(baseUrl + "auth/patient/reviewsPage.jsp?expertID=9000000003");
	    assertEquals("iTrust - Reviews Page", driver.getTitle());
	    assertEquals("Reviews for Gandalf Stormcrow", driver.findElement(By.cssSelector("h1")).getText());
	  }
	   
	  @Test
	  public void testInvaildHCP() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080/iTrust/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseUrl + "auth/forwardUser.jsp");
			
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("109");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
	    assertEquals("iTrust - Patient Home", driver.getTitle());
	    driver.findElement(By.linkText("Find an Expert")).click();
	    assertEquals("iTrust - Find an Expert", driver.getTitle());
	    new Select(driver.findElement(By.name("specialty"))).selectByVisibleText("Pediatrician");
	    driver.findElement(By.name("zipCode")).clear();
	    driver.findElement(By.name("zipCode")).sendKeys("27607");
	    new Select(driver.findElement(By.name("range"))).selectByVisibleText("All");
	    driver.findElement(By.name("findExpert")).click();
	    // Warning: assertTextPresent may require manual changes
	    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*Specialty: Pediatrician[\\s\\S]*$"));
	    // Warning: assertTextPresent may require manual changes
	    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*Physician Name:[\\s\\S]*$"));
	    driver.findElement(By.cssSelector("span.font1")).click();
	    assertThat("Add a Review", is(not(driver.findElement(By.cssSelector("body > div.container-fluid")).getText())));
	  }
	  
	  @Test
	  public void testVaildHCP() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080/iTrust/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseUrl + "auth/forwardUser.jsp");
			
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("2");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
	    assertEquals("iTrust - Patient Home", driver.getTitle());
	    driver.findElement(By.linkText("Find an Expert")).click();
	    assertEquals("iTrust - Find an Expert", driver.getTitle());
	    new Select(driver.findElement(By.name("specialty"))).selectByVisibleText("Surgeon");
	    driver.findElement(By.name("zipCode")).clear();
	    driver.findElement(By.name("zipCode")).sendKeys("27607");
	    new Select(driver.findElement(By.name("range"))).selectByVisibleText("250 Miles");
	    driver.findElement(By.name("findExpert")).click();
	    // Warning: assertTextPresent may require manual changes
	    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*Specialty: Surgeon[\\s\\S]*$"));
	    // Warning: assertTextPresent may require manual changes
	    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*Physician Name:[\\s\\S]*$"));
	    //driver.findElement(By.cssSelector("span.font1")).click();
	    driver.findElement(By.linkText("View")).click();
	    assertEquals("iTrust - Reviews Page", driver.getTitle());
	    driver.findElement(By.linkText("Add a Review")).click();
	    driver.findElement(By.name("title")).clear();
	    driver.findElement(By.name("title")).sendKeys("Too Boared");
	    new Select(driver.findElement(By.name("rating"))).selectByVisibleText("2");
	    driver.findElement(By.name("description")).clear();
	    driver.findElement(By.name("description")).sendKeys("They seemed nice, but they asked how I was then started snoring.");
	    driver.findElement(By.name("addReview")).click();
	    assertEquals("iTrust - Reviews Page", driver.getTitle());
	    // Warning: assertTextPresent may require manual changes
	    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*Too Boared[\\s\\S]*$"));
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
