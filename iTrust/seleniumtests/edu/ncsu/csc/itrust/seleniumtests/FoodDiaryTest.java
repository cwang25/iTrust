package edu.ncsu.csc.itrust.seleniumtests;

import static org.junit.Assert.*;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;
import org.junit.Test;

import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.http.iTrustHTTPTest;

public class FoodDiaryTest extends iTrustSeleniumTest {
	//private WebDriver driver = new HtmlUnitDriver();
	private String baseUrl;
	//private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Override
	public void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	/**
	 * UC 71 Test
	 * Patient Derek Morgan (mid: 97)
	 * Testing date selections for empty food diary
	 * @throws Exception
	 */
	@Test
	  public void testPatientCategorizeEmptyFoodDiary() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080/iTrust/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseUrl + "auth/forwardUser.jsp");
	
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("97"); //Derek Morgan
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    try {
	      assertEquals("iTrust - Patient Home", driver.getTitle());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	      fail();
	    }
	    assertLogged(TransactionType.HOME_VIEW, 97L, 0L, "");
	    //driver.findElement(By.cssSelector("h2.panel-title")).click();
	    //driver.findElement(By.linkText("Nutrition")).click();
	    driver.findElement(By.linkText("Food Diary")).click();
	    assertEquals("iTrust - My Food Diary", driver.getTitle());
	    try {
	      assertEquals("http://localhost:8080/iTrust/auth/patient/viewFoodDiary.jsp", driver.getCurrentUrl());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	      fail();
	    }  
	    driver.findElement(By.id("selectEarlyDate")).clear();
	    driver.findElement(By.id("selectEarlyDate")).sendKeys("2015-02-23");
	    driver.findElement(By.id("selectLateDate")).clear();
	    driver.findElement(By.id("selectLateDate")).sendKeys("2015-02-23");
	    driver.findElement(By.id("refreshDate")).click();
	    assertTrue(driver.getPageSource().contains("No food diary available."));
	    
	  }
	
	/**
	 * UC 71 Test
	 * Patient Aaron Hotchner (mid: 96)
	 * Testing single date selection for nonempty food diary.
	 * @throws Exception
	 */
	@Test
	  public void testPatientCategorizeFoodDiarySingleDate() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080/iTrust/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseUrl + "auth/forwardUser.jsp");
	
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("96"); //Aaron Hotchner
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    try {
	      assertEquals("iTrust - Patient Home", driver.getTitle());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	      fail();
	    }
	    assertLogged(TransactionType.HOME_VIEW, 96L, 0L, "");
	   // driver.findElement(By.cssSelector("h2.panel-title")).click();
	   // driver.findElement(By.linkText("Nutrition")).click();
	    driver.findElement(By.linkText("Food Diary")).click();
	    assertEquals("iTrust - My Food Diary", driver.getTitle());
	    try {
	      assertEquals("http://localhost:8080/iTrust/auth/patient/viewFoodDiary.jsp", driver.getCurrentUrl());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	      fail();
	    }
	    driver.findElement(By.id("selectEarlyDate")).clear();
	    driver.findElement(By.id("selectEarlyDate")).sendKeys("2014-04-13");
	    driver.findElement(By.id("selectLateDate")).clear();
	    driver.findElement(By.id("selectLateDate")).sendKeys("2014-04-13");
	    driver.findElement(By.id("refreshDate")).click();
	    try {
	      assertTrue(driver.getPageSource().contains("Oreo"));
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	      fail();
	    }
	    
	  }
	
	/**
	 * UC 71 Test
	 * Patient Aaron Hotchner (mid: 96)
	 * Testing future single date selection for nonempty food diary.
	 * @throws Exception
	 */
	@Test
	  public void testPatientCategorizeFoodDiaryFutureSingleDate() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080/iTrust/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseUrl + "auth/forwardUser.jsp");
	
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("96"); //Aaron Hotchner
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    try {
	      assertEquals("iTrust - Patient Home", driver.getTitle());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	      fail();
	    }
	    assertLogged(TransactionType.HOME_VIEW, 96L, 0L, "");
	    //driver.findElement(By.cssSelector("h2.panel-title")).click();
	   // driver.findElement(By.linkText("Nutrition")).click();
	    driver.findElement(By.linkText("Food Diary")).click();
	    assertEquals("iTrust - My Food Diary", driver.getTitle());
	    try {
	      assertEquals("http://localhost:8080/iTrust/auth/patient/viewFoodDiary.jsp", driver.getCurrentUrl());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	      fail();
	    }
	    
	    //Will Fail Due to the error message is changing 
	    driver.findElement(By.id("selectEarlyDate")).clear();
	    driver.findElement(By.id("selectEarlyDate")).sendKeys("2019-04-13");
	    driver.findElement(By.id("selectLateDate")).clear();
	    driver.findElement(By.id("selectLateDate")).sendKeys("2019-04-13");
	    driver.findElement(By.id("refreshDate")).click();
	    try {
	      assertTrue(driver.getPageSource().contains("Dates selected cannot be future date."));
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	      fail();
	    }
	    
	  }
	
	/**
	 * UC 71 Test
	 * Patient Aaron Hotchner (mid: 96)
	 * Testing single date selection for nonempty food diary.
	 * @throws Exception
	 */
	@Test
	  public void testPatientCategorizeFoodDiarySingleDateNoEntries() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080/iTrust/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseUrl + "auth/forwardUser.jsp");
	
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("96"); //Aaron Hotchner
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    try {
	      assertEquals("iTrust - Patient Home", driver.getTitle());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	      fail();
	    }
	    assertLogged(TransactionType.HOME_VIEW, 96L, 0L, "");
	    //driver.findElement(By.cssSelector("h2.panel-title")).click();
	   // driver.findElement(By.linkText("Nutrition")).click();
	    driver.findElement(By.linkText("Food Diary")).click();
	    assertEquals("iTrust - My Food Diary", driver.getTitle());
	    try {
	      assertEquals("http://localhost:8080/iTrust/auth/patient/viewFoodDiary.jsp", driver.getCurrentUrl());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	      fail();
	    }
	    driver.findElement(By.id("selectEarlyDate")).clear();
	    driver.findElement(By.id("selectEarlyDate")).sendKeys("2015-01-12");
	    driver.findElement(By.id("selectLateDate")).clear();
	    driver.findElement(By.id("selectLateDate")).sendKeys("2015-02-12");
	    driver.findElement(By.id("refreshDate")).click();
	    try {
	      assertTrue(driver.getPageSource().contains("No food diary available."));
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	      fail();
	    }
	    
	  }
	
	/**
	 * UC 71 Test
	 * Patient Aaron Hotchner (mid: 96)
	 * Testing nonempty food diary, categorize with null dates 
	 * @throws Exception
	 */
	@Test
	  public void testPatientCategorizeFoodDiaryNoDateEntered() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080/iTrust/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseUrl + "auth/forwardUser.jsp");
	
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("96"); //Aaron Hotchner
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    try {
	      assertEquals("iTrust - Patient Home", driver.getTitle());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	      fail();
	    }
	    assertLogged(TransactionType.HOME_VIEW, 96L, 0L, "");
	    //driver.findElement(By.cssSelector("h2.panel-title")).click();
	   // driver.findElement(By.linkText("Nutrition")).click();
	    driver.findElement(By.linkText("Food Diary")).click();
	    assertEquals("iTrust - My Food Diary", driver.getTitle());
	    try {
	      assertEquals("http://localhost:8080/iTrust/auth/patient/viewFoodDiary.jsp", driver.getCurrentUrl());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	      fail();
	    }   
	    driver.findElement(By.id("selectEarlyDate")).clear();
	    driver.findElement(By.id("selectEarlyDate")).sendKeys(" ");
	    driver.findElement(By.id("selectLateDate")).clear();
	    driver.findElement(By.id("selectLateDate")).sendKeys(" ");
	    driver.findElement(By.id("refreshDate")).click();
	    try {
	      assertTrue(driver.getPageSource().contains("Invalid information: Please enter a date to filter by!"));
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	      fail();
	    }
	    
	  }
	
	/**
	 * UC 71 Test
	 * Nutritionist Spencer Reid (mid: 9900000011)
	 * Testing nonempty food diary, categorize by range of dates
	 * @throws Exception
	 */
	@Test
	  public void testNutritionistCategorizeFoodDiaryRangeOfDates() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080/iTrust/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseUrl + "auth/forwardUser.jsp");
	
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9900000011");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    try {
	      assertEquals("iTrust - HCP Home", driver.getTitle());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	      fail();
	    }
	    assertLogged(TransactionType.HOME_VIEW, 9900000011L, 0L, "");
	    //driver.findElement(By.cssSelector("h2.panel-title")).click();
	   // driver.findElement(By.linkText("Nutrition")).click();
	    driver.findElement(By.linkText("Food Diary")).click();
	    driver.findElement(By.name("UID_PATIENTID")).sendKeys("96");
	    driver.findElement(By.xpath("//input[@value='96']")).submit();  
	    try {
	      assertEquals("http://localhost:8080/iTrust/auth/hcp-uap/viewFoodDiary.jsp", driver.getCurrentUrl());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	      fail();
	    }
	    assertEquals("iTrust - Food Diary", driver.getTitle());
	    driver.findElement(By.id("selectEarlyDate")).clear();
	    driver.findElement(By.id("selectEarlyDate")).sendKeys("2013-05-21");
	    driver.findElement(By.id("selectLateDate")).clear();
	    driver.findElement(By.id("selectLateDate")).sendKeys("2014-04-13");
	    driver.findElement(By.id("refreshDate")).click();
	    try {
	      assertTrue(driver.getPageSource().contains("Oreos"));
	      assertTrue(driver.getPageSource().contains("Cheese and Bean Dip"));
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	      fail();
	    }
	    
	  }
	
	/**
	 * UC 71 Test
	 * Nutritionist Spencer Reid (mid: 9900000011)
	 * Testing nonempty food diary, categorize by range of dates
	 * @throws Exception
	 */
	@Test
	  public void testNutritionistCategorizeFoodDiaryInvalideFutureRangeOfDates() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080/iTrust/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseUrl + "auth/forwardUser.jsp");
	
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9900000011");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    try {
	      assertEquals("iTrust - HCP Home", driver.getTitle());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	      fail();
	    }
	    assertLogged(TransactionType.HOME_VIEW, 9900000011L, 0L, "");
	    //driver.findElement(By.cssSelector("h2.panel-title")).click();
	   // driver.findElement(By.linkText("Nutrition")).click();
	    driver.findElement(By.linkText("Food Diary")).click();
	    driver.findElement(By.name("UID_PATIENTID")).sendKeys("96");
	    driver.findElement(By.xpath("//input[@value='96']")).submit();
	    try {
	      assertEquals("http://localhost:8080/iTrust/auth/hcp-uap/viewFoodDiary.jsp", driver.getCurrentUrl());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	      fail();
	    }
	    assertEquals("iTrust - Food Diary", driver.getTitle());
	    driver.findElement(By.id("selectEarlyDate")).clear();
	    driver.findElement(By.id("selectEarlyDate")).sendKeys("2014-04-13");
	    driver.findElement(By.id("selectLateDate")).clear();
	    driver.findElement(By.id("selectLateDate")).sendKeys("2013-05-21");
	    driver.findElement(By.id("refreshDate")).click();
	    try {
	      assertTrue(driver.getPageSource().contains("Start date cannot begin after end date!"));
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	      fail();
	    }
	    
	  }
	
	/**
	 * UC 72 Test
	 * Aaron Hotchner
	 * the passing case
	 * @throws Exception
	 */
	@Test
	public void testpatientCalculateMacronutrients() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080/iTrust/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseUrl + "auth/forwardUser.jsp");
	
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("96"); //Aaron Hotchner
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("iTrust - Patient Home", driver.getTitle());
	    assertLogged(TransactionType.HOME_VIEW, 96L, 0L, "");
	    driver.findElement(By.linkText("Calculate Macronutrients")).click();
	    assertEquals("iTrust - Macronutrients Calculator", driver.getTitle());
	    new Select(driver.findElement(By.name("activityLevel"))).selectByVisibleText("Sedentary");
	    driver.findElement(By.id("submitButton")).click();
	    assertTrue(driver.getPageSource().contains("2388"));
	    assertTrue(driver.getPageSource().contains("179.1g"));
	    assertTrue(driver.getPageSource().contains("298.5g"));
	    assertTrue(driver.getPageSource().contains("53.07g"));
	}
	/**
	 * UC 72 Test
	 * Aaron Hotchner
	 * Invalid age (0) 
	 * @throws Exception
	 */
	@Test
	public void testpatientCalculateMacronutrientsInvalidAge() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080/iTrust/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseUrl + "auth/forwardUser.jsp");
	
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("96"); //Aaron Hotchner
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("iTrust - Patient Home", driver.getTitle());
	    assertLogged(TransactionType.HOME_VIEW, 96L, 0L, "");
	    driver.findElement(By.linkText("Calculate Macronutrients")).click();
	    assertEquals("iTrust - Macronutrients Calculator", driver.getTitle());
	    driver.findElement(By.id("age")).clear();
	    driver.findElement(By.id("age")).sendKeys("0");
	    new Select(driver.findElement(By.name("activityLevel"))).selectByVisibleText("Sedentary");
	    driver.findElement(By.id("submitButton")).click();
	    assertTrue(driver.getPageSource().contains("Please enter a valid, nonnegative or zero number for age."));
	}
	
	/**
	 * UC 72 Test
	 * Invalid height (negative)
	 * @throws Exception
	 */
	@Test
	public void testpatientCalculateMacronutrientsNegativeHeight() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080/iTrust/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseUrl + "auth/forwardUser.jsp");
	
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("96"); //Aaron Hotchner
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("iTrust - Patient Home", driver.getTitle());
	    assertLogged(TransactionType.HOME_VIEW, 96L, 0L, "");
	    driver.findElement(By.linkText("Calculate Macronutrients")).click();
	    assertEquals("iTrust - Macronutrients Calculator", driver.getTitle());
	    driver.findElement(By.id("height")).clear();
	    driver.findElement(By.id("height")).sendKeys("-100");
	    new Select(driver.findElement(By.name("activityLevel"))).selectByVisibleText("Sedentary");
	    driver.findElement(By.id("submitButton")).click();
	    assertTrue(driver.getPageSource().contains("Please enter a valid, nonnegative or zero number for height."));
	}
	/**
	 * UC 72 Test
	 * 
	 * @throws Exception
	 */
	@Test
	public void testpatientgrahicfailnoentry() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080/iTrust/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseUrl + "auth/forwardUser.jsp");
	
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("96"); //Aaron Hotchner
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("iTrust - Patient Home", driver.getTitle());
	    assertLogged(TransactionType.HOME_VIEW, 96L, 0L, "");
	    driver.findElement(By.linkText("View Charts")).click();
	    assertEquals("iTrust - View Calorie Graph", driver.getTitle());
	    driver.findElement(By.id("selectDate")).clear();
	    driver.findElement(By.id("selectDate")).sendKeys("2015-02-04");
	    //new Select(driver.findElement(By.name("activityLevel"))).selectByVisibleText("Sedentary");
	    driver.findElement(By.id("refreshDate")).click();
	    assertTrue(driver.getPageSource().contains("There are no food diary entries for this date."));
	}
	/**
	 * UC 72 Test
	 * 
	 * @throws Exception
	 */
	@Test
	public void testpatientgrahicfailnocal() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080/iTrust/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseUrl + "auth/forwardUser.jsp");
	
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("97");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("iTrust - Patient Home", driver.getTitle());
	    assertLogged(TransactionType.HOME_VIEW, 97L, 0L, "");
	    //goes to the graph and get the error because the date chosen there are no food diary 
	    driver.findElement(By.linkText("View Charts")).click();
	    assertEquals("iTrust - View Calorie Graph", driver.getTitle());
	    driver.findElement(By.id("selectDate")).clear();
	    driver.findElement(By.id("selectDate")).sendKeys("2012-09-30");
	    //new Select(driver.findElement(By.name("activityLevel"))).selectByVisibleText("Sedentary");
	    driver.findElement(By.id("refreshDate")).click();
	    assertTrue(driver.getPageSource().contains("You must have calculated your macronutrients first."));
	}
	
	/**
	 * UC 72 Test
	 * Aaron Hotchner
	 * 
	 * @throws Exception
	 */
	@Test
	public void testpatientgrahicpass() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080/iTrust/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseUrl + "auth/forwardUser.jsp");
		//log in
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("96"); //Aaron Hotchner
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("iTrust - Patient Home", driver.getTitle());
	    assertLogged(TransactionType.HOME_VIEW, 96L, 0L, "");
	    // cals the Macronutrients for the graph 
	    driver.findElement(By.linkText("Calculate Macronutrients")).click();
	    assertEquals("iTrust - Macronutrients Calculator", driver.getTitle());
	    new Select(driver.findElement(By.name("activityLevel"))).selectByVisibleText("Sedentary");
	    driver.findElement(By.id("submitButton")).click();
	    assertTrue(driver.getPageSource().contains("2388"));
	    assertTrue(driver.getPageSource().contains("179.1g"));
	    assertTrue(driver.getPageSource().contains("298.5g"));
	    assertTrue(driver.getPageSource().contains("53.07g"));
	  //goes to the graph and check that that there is no error in making it 
	    driver.findElement(By.linkText("View Charts")).click();
	    assertEquals("iTrust - View Calorie Graph", driver.getTitle());
	    driver.findElement(By.id("selectDate")).clear();
	    driver.findElement(By.id("selectDate")).sendKeys("2015-02-04");
	    assertFalse(driver.getPageSource().contains("You must have calculated your macronutrients first."));
	    assertFalse(driver.getPageSource().contains("There are no food diary entries for this date."));
	    
	}
	
	@Test
	public void testpatientgrahicpass2entrys() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080/iTrust/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseUrl + "auth/forwardUser.jsp");
		//log in 
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("96"); //Aaron Hotchner
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("iTrust - Patient Home", driver.getTitle());
	    assertLogged(TransactionType.HOME_VIEW, 96L, 0L, "");
	    //Adds a new food diary entry so there can be 2 on the same day 
	    driver.findElement(By.linkText("Food Diary")).click();
	    assertEquals("iTrust - My Food Diary", driver.getTitle());
	    driver.get(baseUrl + "auth/patient/addFoodDiary.jsp?add=true"); 
	    System.out.println(driver.getTitle());
	    assertEquals("iTrust - Add Food Diary", driver.getTitle());
	    driver.findElement(By.name("mealDate")).clear();
	    driver.findElement(By.name("mealDate")).sendKeys("2015-02-04");
	    new Select(driver.findElement(By.name("mealList"))).selectByVisibleText("Dinner");
	    driver.findElement(By.name("foodName")).clear();
	    driver.findElement(By.name("foodName")).sendKeys("Fruity Pebbles");
	    driver.findElement(By.name("servings")).clear();
	    driver.findElement(By.name("servings")).sendKeys("7");
	    driver.findElement(By.name("calories")).clear();
	    driver.findElement(By.name("calories")).sendKeys("110");
	    driver.findElement(By.name("fat")).clear();
	    driver.findElement(By.name("fat")).sendKeys("1");
	    driver.findElement(By.name("sodium")).clear();
	    driver.findElement(By.name("sodium")).sendKeys("170");
	    driver.findElement(By.name("carbs")).clear();
	    driver.findElement(By.name("carbs")).sendKeys("24");
	    driver.findElement(By.name("fiber")).clear();
	    driver.findElement(By.name("fiber")).sendKeys("0");
	    driver.findElement(By.name("sugar")).clear();
	    driver.findElement(By.name("sugar")).sendKeys("11");
	    driver.findElement(By.name("protein")).clear();
	    driver.findElement(By.name("protein")).sendKeys("1");
	    driver.findElement(By.id("addDiary")).click();
	    
	    //cals the Macronutrients and checks the values
	    driver.findElement(By.linkText("Calculate Macronutrients")).click();
	    assertEquals("iTrust - Macronutrients Calculator", driver.getTitle());
	    new Select(driver.findElement(By.name("activityLevel"))).selectByVisibleText("Sedentary");
	    driver.findElement(By.id("submitButton")).click();
	    assertTrue(driver.getPageSource().contains("2388"));
	    assertTrue(driver.getPageSource().contains("179.1g"));
	    assertTrue(driver.getPageSource().contains("298.5g"));
	    assertTrue(driver.getPageSource().contains("53.07g"));
	    //goes to the graph and check that that there is no error in making it 
	    driver.findElement(By.linkText("View Charts")).click();
	    assertEquals("iTrust - View Calorie Graph", driver.getTitle());
	    driver.findElement(By.id("selectDate")).clear();
	    driver.findElement(By.id("selectDate")).sendKeys("2015-02-04");
	    assertFalse(driver.getPageSource().contains("You must have calculated your macronutrients first."));
	    assertFalse(driver.getPageSource().contains("There are no food diary entries for this date."));
	    
	}
	

}
