package edu.ncsu.csc.itrust.seleniumtests;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class PatientFoodDiaryTest extends iTrustSeleniumTest{
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	
	/*
	 * Authenticate Patient: Derek Morgan
	 * MID: 684
	 * Password: pw
	 * Choose Food Diary.
	 * In the entry boxes below fill the following items
	 * Change Field:
	 * 2/4/2015 for the date, selects Dinner, enters Fruity Pebbles for food, 
	 * enters 7 servings, 110 cal, 1g of fat, 170mg of sodium, 24g of carbs, 
	 * 0g of fiber, 11g of sugars, and 1g of protein
	 * Confirm and approve the selection
	 */
	public void testScenario1Derek1() throws Exception {
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("684");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    
	    driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Add Food Diary")).click();
	    driver.findElement(By.name("entryDate")).clear();
	    driver.findElement(By.name("entryDate")).sendKeys("02/04/");
	    driver.findElement(By.name("entryDate")).clear();
	    driver.findElement(By.name("entryDate")).sendKeys("02/04/2015");
	    driver.findElement(By.name("meal")).clear();
	    driver.findElement(By.name("meal")).sendKeys("Dinner");
	    driver.findElement(By.name("food")).clear();
	    driver.findElement(By.name("food")).sendKeys("Fruity Pebbles");
	    driver.findElement(By.name("servings")).clear();
	    driver.findElement(By.name("servings")).sendKeys("7");
	    driver.findElement(By.name("cals")).clear();
	    driver.findElement(By.name("cals")).sendKeys("110");
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
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    try {
	      assertEquals("\"Food Diary Successfully Added.\"", driver.findElement(By.cssSelector("span.font_success")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
	    driver.findElement(By.linkText("View Food Diary")).click();
	    try {
	      assertEquals("Dinner", driver.findElement(By.xpath("//table[@id='foodDiaryTable']/tbody/tr[3]/td[2]")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	    try {
	      assertEquals("Fruity Pebbles", driver.findElement(By.xpath("//table[@id='foodDiaryTable']/tbody/tr[3]/td[3]")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	}

	
	/*
	 * Authenticate Patient: Jennifer Jareau 
	 * MID: 685
	 * Password: pw
	 * Choose Food Diary.
	 * In the entry boxes below fill the following items
	 * Change Field:
	 * 11/12/2014 for the date, selects Snack, enters Cookie Dough Ice Cream for food, 
	 * enters .5 servings, 160 calories, 8g of fat, 45mg of sodium, 21g of carbs, 
	 * 0g of fiber, 16g of sugars, and 2g of protein
	 * Confirm and approve the selection
	 */
	public void testScenarioJennifer2() throws Exception {
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("685");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    
	    driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Add Food Diary")).click();
	    driver.findElement(By.name("entryDate")).clear();
	    driver.findElement(By.name("entryDate")).sendKeys("11/12/2014");
	    driver.findElement(By.name("meal")).clear();
	    driver.findElement(By.name("meal")).sendKeys("Snack");
	    driver.findElement(By.name("food")).clear();
	    driver.findElement(By.name("food")).sendKeys("Cookie Dough Icecream");
	    driver.findElement(By.name("servings")).clear();
	    driver.findElement(By.name("servings")).sendKeys(".5");
	    driver.findElement(By.name("cals")).clear();
	    driver.findElement(By.name("cals")).sendKeys("160");
	    driver.findElement(By.name("fat")).clear();
	    driver.findElement(By.name("fat")).sendKeys("8");
	    driver.findElement(By.name("sodium")).clear();
	    driver.findElement(By.name("sodium")).sendKeys("45");
	    driver.findElement(By.name("carbs")).clear();
	    driver.findElement(By.name("carbs")).sendKeys("21");
	    driver.findElement(By.name("fiber")).clear();
	    driver.findElement(By.name("fiber")).sendKeys("0");
	    driver.findElement(By.name("sugar")).clear();
	    driver.findElement(By.name("sugar")).sendKeys("16");
	    driver.findElement(By.name("protein")).clear();
	    driver.findElement(By.name("protein")).sendKeys("2");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    try {
	      assertEquals("\"Food Diary Successfully Added.\"", driver.findElement(By.cssSelector("span.font_success")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
	    driver.findElement(By.linkText("View Food Diary")).click();
	    try {
	      assertEquals("Hotdog", driver.findElement(By.xpath("//table[@id='foodDiaryTable']/tbody/tr[4]/td[3]")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	    try {
	      assertEquals("Breakfast", driver.findElement(By.xpath("//table[@id='foodDiaryTable']/tbody/tr[4]/td[2]")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	}
	
	/*
	 * Authenticate HCP: Spencer Reid 9900000025
	 * Password: pw
	 * Choose Food Diary.
	 * Check food diary of MID 2
	 * Check and see if all fields exist
	 */
	public void testScenarioSReid() throws Exception {
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9900000025");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    
	    driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("View Food Diary")).click();
	    driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("686");
	    driver.findElement(By.xpath("//input[@value='686']")).click();
	    
	    driver.findElement(By.id("iTrustContent"));
	    
	    driver.findElement(By.id("foodDiaryTable"));
	    
	    //<itrust:patientNav thisTitle="Patient Food Diary" />
	    
	    try {
	      assertEquals("Oreos", driver.findElement(By.xpath("//table[@id='foodDiaryTable']/tbody/tr[4]/td[3]")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	    try {
	      assertEquals("Cheese and Bean Dip", driver.findElement(By.xpath("//table[@id='foodDiaryTable']/tbody/tr[3]/td[3]")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	    try {
	      assertEquals("0.75", driver.findElement(By.xpath("//table[@id='foodDiaryTable']/tbody/tr[3]/td[4]")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	    try {
	      assertEquals("53.0", driver.findElement(By.xpath("//table[@id='foodDiaryTable']/tbody/tr[4]/td[4]")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	    try {
	      assertEquals("05/21/2013", driver.findElement(By.xpath("//table[@id='foodDiaryTable']/tbody/tr[3]/td")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	    try {
	      assertEquals("04/13/2012", driver.findElement(By.xpath("//table[@id='foodDiaryTable']/tbody/tr[4]/td")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	}
	
	////Test Below are the Five Additional Test
	/*
	 * Authenticate Patient: Jennifer Jareau 
	 * MID: 685
	 * Password: pw
	 * Choose Food Diary.
	 * In the entry boxes below fill the following items
	 * Change Field:
	 * 11/12/2016 for the date, selects Snack, enters Cookie Dough Ice Cream for food, 
	 * enters .5 servings, 160 calories, 8g of fat, 45mg of sodium, 21g of carbs, 
	 * 0g of fiber, 16g of sugars, and 2g of protein
	 * Confirm and approve the selection
	 * 
	 * Date is in future and invalid
	 * 
	 */
	public void testAddFDBadDate() throws Exception {
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("685");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    
	    driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Add Food Diary")).click();
	    driver.findElement(By.name("entryDate")).clear();
	    driver.findElement(By.name("entryDate")).sendKeys("11/12/2020");
	    driver.findElement(By.name("meal")).clear();
	    driver.findElement(By.name("meal")).sendKeys("Snack");
	    driver.findElement(By.name("food")).clear();
	    driver.findElement(By.name("food")).sendKeys("Cookie Dough Icecream");
	    driver.findElement(By.name("servings")).clear();
	    driver.findElement(By.name("servings")).sendKeys(".5");
	    driver.findElement(By.name("cals")).clear();
	    driver.findElement(By.name("cals")).sendKeys("160");
	    driver.findElement(By.name("fat")).clear();
	    driver.findElement(By.name("fat")).sendKeys("8");
	    driver.findElement(By.name("sodium")).clear();
	    driver.findElement(By.name("sodium")).sendKeys("45");
	    driver.findElement(By.name("carbs")).clear();
	    driver.findElement(By.name("carbs")).sendKeys("21");
	    driver.findElement(By.name("fiber")).clear();
	    driver.findElement(By.name("fiber")).sendKeys("0");
	    driver.findElement(By.name("sugar")).clear();
	    driver.findElement(By.name("sugar")).sendKeys("16");
	    driver.findElement(By.name("protein")).clear();
	    driver.findElement(By.name("protein")).sendKeys("2");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    try {
	      assertEquals("This form has not been validated correctly. The following field are not properly filled in: [Consumption Date: Restricted to Current or Past Dates]\n Consumption Date Meal Food Name Servings Calories Per Servings Grams Fat Per Serving mg Sodium Per Serving Grams Carbs Per Serving Grams Fiber Per Serving Grams Sugar Per Serving Grams Protein Per Serving", driver.findElement(By.cssSelector("span.font_failure")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	}
	
	/*
	 * Authenticate Patient: Jennifer Jareau 
	 * MID: 685
	 * Password: pw
	 * Choose Food Diary.
	 * In the entry boxes below fill the following items
	 * Change Field:
	 * 30/1/2015 for the date, selects Snack, enters Cookie Dough Ice Cream for food, 
	 * enters .5 servings, 160 calories, 8g of fat, 45mg of sodium, 21g of carbs, 
	 * 0g of fiber, 16g of sugars, and 2g of protein
	 * Confirm and approve the selection
	 * 
	 * Date in in DD MM YYYY and is invalid
	 *
	 */
	public void testAddFDInvalidDateFormat() throws Exception {
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("685");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    
	    driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Add Food Diary")).click();
	    driver.findElement(By.name("entryDate")).clear();
	    driver.findElement(By.name("entryDate")).sendKeys("30/1/2015");
	    driver.findElement(By.name("meal")).clear();
	    driver.findElement(By.name("meal")).sendKeys("Snack");
	    driver.findElement(By.name("food")).clear();
	    driver.findElement(By.name("food")).sendKeys("Cookie Dough Icecream");
	    driver.findElement(By.name("servings")).clear();
	    driver.findElement(By.name("servings")).sendKeys(".5");
	    driver.findElement(By.name("cals")).clear();
	    driver.findElement(By.name("cals")).sendKeys("160");
	    driver.findElement(By.name("fat")).clear();
	    driver.findElement(By.name("fat")).sendKeys("8");
	    driver.findElement(By.name("sodium")).clear();
	    driver.findElement(By.name("sodium")).sendKeys("45");
	    driver.findElement(By.name("carbs")).clear();
	    driver.findElement(By.name("carbs")).sendKeys("21");
	    driver.findElement(By.name("fiber")).clear();
	    driver.findElement(By.name("fiber")).sendKeys("0");
	    driver.findElement(By.name("sugar")).clear();
	    driver.findElement(By.name("sugar")).sendKeys("16");
	    driver.findElement(By.name("protein")).clear();
	    driver.findElement(By.name("protein")).sendKeys("2");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    try {
	      assertEquals("This form has not been validated correctly. The following field are not properly filled in: [Enter Consumption Date in MM/dd/yyyy]\n Consumption Date Meal Food Name Servings Calories Per Servings Grams Fat Per Serving mg Sodium Per Serving Grams Carbs Per Serving Grams Fiber Per Serving Grams Sugar Per Serving Grams Protein Per Serving", driver.findElement(By.cssSelector("span.font_failure")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	}
	
	/*
	 * Authenticate Patient: Derek Morgan
	 * MID: 684
	 * Password: pw
	 * Choose Food Diary.
	 * In the entry boxes below fill the following items
	 * Change Field:
	 * 2/4/2015 for the date, selects Dinner, enters Fruity Pebbles for food, 
	 * enters -7 servings, 110 cal, 1g of fat, 170mg of sodium, 24g of carbs, 
	 * 0g of fiber, 11g of sugars, and 1g of protein
	 * Confirm and approve the selection
	 * 
	 * Invalid amount of servings check if error message appears
	 * 
	 */
	public void testInvalidServings() throws Exception {
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("684");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    
	    driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Add Food Diary")).click();
	    driver.findElement(By.name("entryDate")).clear();
	    driver.findElement(By.name("entryDate")).sendKeys("02/04/2015");
	    driver.findElement(By.name("meal")).clear();
	    driver.findElement(By.name("meal")).sendKeys("D");
	    driver.findElement(By.name("meal")).clear();
	    driver.findElement(By.name("meal")).sendKeys("Dinner");
	    driver.findElement(By.name("food")).clear();
	    driver.findElement(By.name("food")).sendKeys("Fruity Pebbles");
	    driver.findElement(By.name("servings")).clear();
	    driver.findElement(By.name("servings")).sendKeys("-7");
	    driver.findElement(By.name("cals")).clear();
	    driver.findElement(By.name("cals")).sendKeys("110");
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
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    try {
	      assertEquals("This form has not been validated correctly. The following field are not properly filled in: [Servings: Must be Greater than 0]\n Consumption Date Meal Food Name Servings Calories Per Servings Grams Fat Per Serving mg Sodium Per Serving Grams Carbs Per Serving Grams Fiber Per Serving Grams Sugar Per Serving Grams Protein Per Serving", driver.findElement(By.cssSelector("span.font_failure")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	}
	
	/*
	 * Authenticate Patient: Derek Morgan
	 * MID: 684
	 * Password: pw
	 * Choose Food Diary.
	 * In the entry boxes below fill the following items
	 * Change Field:
	 * 2/4/2015 for the date, selects Dinner, enters Fruity Pebbles for food, 
	 * enters 7 servings, 110 cal, 1g of fat, 170mg of sodium, 24g of carbs, 
	 * 0g of fiber, 11g of sugars, and 1g of protein
	 * Confirm and approve the selection
	 * 
	 * Invalid amount of protein check if error message appears
	 * 
	 */
	public void testInvalidProteins() throws Exception {
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("684");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    
	    driver.findElement(By.cssSelector("div.panel-heading")).click();
	    driver.findElement(By.linkText("Add Food Diary")).click();
	    driver.findElement(By.name("entryDate")).clear();
	    driver.findElement(By.name("entryDate")).sendKeys("02/04/2015");
	    driver.findElement(By.name("meal")).clear();
	    driver.findElement(By.name("meal")).sendKeys("Dinner");
	    driver.findElement(By.name("food")).clear();
	    driver.findElement(By.name("food")).sendKeys("Fruity Pebbles");
	    driver.findElement(By.name("servings")).clear();
	    driver.findElement(By.name("servings")).sendKeys(".5");
	    driver.findElement(By.name("cals")).clear();
	    driver.findElement(By.name("cals")).sendKeys("1");
	    driver.findElement(By.name("cals")).clear();
	    driver.findElement(By.name("cals")).sendKeys("110");
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
	    driver.findElement(By.name("protein")).sendKeys("-9000");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    try {
	      assertEquals("This form has not been validated correctly. The following field are not properly filled in: [Grams Protein Per Servings: Must be 0 or Greater]\n Consumption Date Meal Food Name Servings Calories Per Servings Grams Fat Per Serving mg Sodium Per Serving Grams Carbs Per Serving Grams Fiber Per Serving Grams Sugar Per Serving Grams Protein Per Serving", driver.findElement(By.cssSelector("span.font_failure")).getText());
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	}
	
	/*
	 * Authenticate HCP: Spencer Reid 
	 * Password: pw
	 * Choose Food Diary.
	 * Find the entries to MID 684
	 * Nothing has been entered yet so nothing should appear
	 * A No Entries message should be on the page
	 */
	public void testEmptyFoodDiary() throws Exception {
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9900000025");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    
	    driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("View Food Diary")).click();
	    driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("684");
	    driver.findElement(By.xpath("//input[@value='684']")).click();
	}

}
