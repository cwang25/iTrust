package edu.ncsu.csc.itrust.seleniumtests;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.junit.Test;

public class ExpertReviewsTest extends iTrustSeleniumTest{
	  @Before
	  public void setUp() throws Exception {
		super.setUp();
		gen.standardData();
		gen.reviews();
	  }

	  @Test
	  public void testDirectRating() throws Exception {
		
		
	    login("109", "pw");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[7]/div/h2")).click();
	    assertEquals("iTrust - Patient Home", driver.getTitle());
	    driver.findElement(By.linkText("Expert's Reviews")).click();
	    assertEquals("iTrust - Please Select an Expert", driver.getTitle());
	    
	    driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("kelly");
	    clickOnJavascriptElement(By.linkText("View Reviews"));    
	    
	    assertEquals("iTrust - Reviews Page", driver.getTitle());
	    assertEquals("Reviews for Kelly Doctor", driver.findElement(By.cssSelector("h1")).getText());
	    assertTrue(pageContains("Kelly Doctor is horrible!"));
	    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Best doctor at this hospital!"));
	    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("So Bad."));
	    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("I am pretty happy"));
	  }
	  
	  @Test
	  public void testOverallRating() throws Exception {
		login("22", "pw");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[7]/div/h2")).click();
	    assertEquals("iTrust - Patient Home", driver.getTitle());
	    driver.findElement(By.linkText("Expert's Reviews")).click();
	    assertEquals("iTrust - Please Select an Expert", driver.getTitle());
	    driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("gandalf");
	    clickOnJavascriptElement(By.linkText("View Reviews"));
	    assertEquals("iTrust - Reviews Page", driver.getTitle());
	    assertEquals("Reviews for Gandalf Stormcrow", driver.findElement(By.cssSelector("h1")).getText());
	  }
	   
	  @Test
	  public void testInvaildHCP() throws Exception {
		login("109", "pw");
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
		login("2","pw");
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
}
