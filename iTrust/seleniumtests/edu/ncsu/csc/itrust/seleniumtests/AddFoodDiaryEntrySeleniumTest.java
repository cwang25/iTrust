package edu.ncsu.csc.itrust.seleniumtests;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AddFoodDiaryEntrySeleniumTest extends iTrustSeleniumTest {

	/*
	 * The URL for iTrust, change as needed
	 */
	/**ADDRESS*/
	public static final String ADDRESS = "http://localhost:8080/iTrust/";
	private WebDriver driver;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
		// turn off htmlunit warnings
	    java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
	    java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);
	}
	
	@Test
	public void testAddValidEntryToEmptyFoodDiary() throws Exception {
		driver = login("501", "pw");
		assertEquals(driver.getTitle(), "iTrust - Patient Home");
		driver.findElement(By.linkText("Add Food Diary")).click();
		assertEquals(driver.getTitle(), "iTrust - Edit Patient");
		driver.findElement(By.name("date")).clear();
		driver.findElement(By.name("date")).sendKeys("02/04/2015");
		driver.findElement(By.name("type")).clear();
		driver.findElement(By.name("type")).sendKeys("Dinner");
		driver.findElement(By.name("name")).clear();
		driver.findElement(By.name("name")).sendKeys("Fruity Pebbles");
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
		driver.findElement(By.name("sugars")).clear();
		driver.findElement(By.name("sugars")).sendKeys("11");
		driver.findElement(By.name("protein")).clear();
		driver.findElement(By.name("protein")).sendKeys("1");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals(driver.getTitle(), "iTrust - View My Food Diary");
		assertTrue(driver.getPageSource().contains("Viewing Log For:"));
		assertTrue(driver.getPageSource().contains("Totals:"));
		assertTrue(driver.getPageSource().contains("02/04/2015"));
		assertTrue(driver.getPageSource().contains("Dinner"));
		assertTrue(driver.getPageSource().contains("Fruity Pebbles"));
		assertTrue(driver.getPageSource().contains("7.0"));
		assertTrue(driver.getPageSource().contains("770.0"));
		assertTrue(driver.getPageSource().contains("7.0"));
		assertTrue(driver.getPageSource().contains("1190.0"));
		assertTrue(driver.getPageSource().contains("168.0"));
		assertTrue(driver.getPageSource().contains("0.0"));
		assertTrue(driver.getPageSource().contains("77.0"));
		assertTrue(driver.getPageSource().contains("7.0"));
	}
	
	@Test
	public void testAddIncompleteEntryToEmptyFoodDiary() throws Exception {
		driver = login("501", "pw");
		assertEquals(driver.getTitle(), "iTrust - Patient Home");
		driver.findElement(By.linkText("Add Food Diary")).click();
		assertEquals(driver.getTitle(), "iTrust - Edit Patient");
		driver.findElement(By.name("date")).clear();
		driver.findElement(By.name("date")).sendKeys("02/04/2015");
		driver.findElement(By.name("type")).clear();
		driver.findElement(By.name("type")).sendKeys("Dinner");
		driver.findElement(By.name("name")).clear();
		driver.findElement(By.name("name")).sendKeys("Fruity Pebbles");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertTrue(driver.getPageSource().contains("NumberFormatException"));
	}
	
	@Test
	public void testAddEntryToEmptyFoodDiaryWithInvalidName() throws Exception {
		driver = login("501", "pw");
		assertEquals(driver.getTitle(), "iTrust - Patient Home");
		driver.findElement(By.linkText("Add Food Diary")).click();
		assertEquals(driver.getTitle(), "iTrust - Edit Patient");
		driver.findElement(By.name("date")).clear();
		driver.findElement(By.name("date")).sendKeys("02/04/2015");
		driver.findElement(By.name("type")).clear();
		driver.findElement(By.name("type")).sendKeys("Dinner");
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
		driver.findElement(By.name("sugars")).clear();
		driver.findElement(By.name("sugars")).sendKeys("11");
		driver.findElement(By.name("protein")).clear();
		driver.findElement(By.name("protein")).sendKeys("1");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertTrue(driver.getPageSource().contains("FormValidationException"));
	}
	
	@Test
	public void testAddEntryToNonEmptyFoodDiaryWithValidName() throws Exception {
		driver = login("502", "pw");
		assertEquals(driver.getTitle(), "iTrust - Patient Home");
		driver.findElement(By.linkText("Add Food Diary")).click();
		assertEquals(driver.getTitle(), "iTrust - Edit Patient");
		driver.findElement(By.name("date")).clear();
		driver.findElement(By.name("date")).sendKeys("11/12/2014");
		driver.findElement(By.name("type")).clear();
		driver.findElement(By.name("type")).sendKeys("Snack");
		driver.findElement(By.name("name")).clear();
		driver.findElement(By.name("name")).sendKeys("Cookie Dough");
		driver.findElement(By.name("servings")).clear();
		driver.findElement(By.name("servings")).sendKeys(".5");
		driver.findElement(By.name("calories")).clear();
		driver.findElement(By.name("calories")).sendKeys("160");
		driver.findElement(By.name("fat")).clear();
		driver.findElement(By.name("fat")).sendKeys("8");
		driver.findElement(By.name("sodium")).clear();
		driver.findElement(By.name("sodium")).sendKeys("45");
		driver.findElement(By.name("carbs")).clear();
		driver.findElement(By.name("carbs")).sendKeys("21");
		driver.findElement(By.name("fiber")).clear();
		driver.findElement(By.name("fiber")).sendKeys("0");
		driver.findElement(By.name("sugars")).clear();
		driver.findElement(By.name("sugars")).sendKeys("16");
		driver.findElement(By.name("protein")).clear();
		driver.findElement(By.name("protein")).sendKeys("2");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals(driver.getTitle(), "iTrust - View My Food Diary");
		assertTrue(driver.getPageSource().contains("Viewing Log For:"));
		assertTrue(driver.getPageSource().contains("Totals:"));
		assertTrue(driver.getPageSource().contains("11/12/2014"));
		assertTrue(driver.getPageSource().contains("Snack"));
		assertTrue(driver.getPageSource().contains("Cookie Dough"));
		assertTrue(driver.getPageSource().contains("0.5"));
		assertTrue(driver.getPageSource().contains("160.0"));
		assertTrue(driver.getPageSource().contains("8.0"));
		assertTrue(driver.getPageSource().contains("45.0"));
		assertTrue(driver.getPageSource().contains("21.0"));
		assertTrue(driver.getPageSource().contains("0.0"));
		assertTrue(driver.getPageSource().contains("16.0"));
		assertTrue(driver.getPageSource().contains("2.0"));
		
		assertEquals(driver.getTitle(), "iTrust - View My Food Diary");
		assertTrue(driver.getPageSource().contains("Viewing Log For:"));
		assertTrue(driver.getPageSource().contains("Totals:"));
		assertTrue(driver.getPageSource().contains("09/30/2012"));
		assertTrue(driver.getPageSource().contains("Breakfast"));
		assertTrue(driver.getPageSource().contains("Hot dog"));
		assertTrue(driver.getPageSource().contains("4.0"));
		assertTrue(driver.getPageSource().contains("80.0"));
		assertTrue(driver.getPageSource().contains("5.0"));
		assertTrue(driver.getPageSource().contains("480.0"));
		assertTrue(driver.getPageSource().contains("2.0"));
		assertTrue(driver.getPageSource().contains("0.0"));
		assertTrue(driver.getPageSource().contains("0.0"));
		assertTrue(driver.getPageSource().contains("5.0"));
		
		assertEquals(driver.getTitle(), "iTrust - View My Food Diary");
		assertTrue(driver.getPageSource().contains("Viewing Log For:"));
		assertTrue(driver.getPageSource().contains("Totals:"));
		assertTrue(driver.getPageSource().contains("09/30/2012"));
		assertTrue(driver.getPageSource().contains("Lunch"));
		assertTrue(driver.getPageSource().contains("Mango Passionfruit Juice"));
		assertTrue(driver.getPageSource().contains("1.2"));
		assertTrue(driver.getPageSource().contains("130.0"));
		assertTrue(driver.getPageSource().contains("0.0"));
		assertTrue(driver.getPageSource().contains("25.0"));
		assertTrue(driver.getPageSource().contains("32.0"));
		assertTrue(driver.getPageSource().contains("0.0"));
		assertTrue(driver.getPageSource().contains("29.0"));
		assertTrue(driver.getPageSource().contains("1.0"));
	}
	
	@Test
	public void testAddEntryToNonEmptyFoodDiaryWithInvalidDate() throws Exception {
		driver = login("502", "pw");
		assertEquals(driver.getTitle(), "iTrust - Patient Home");
		driver.findElement(By.linkText("Add Food Diary")).click();
		assertEquals(driver.getTitle(), "iTrust - Edit Patient");
		driver.findElement(By.name("date")).clear();
		driver.findElement(By.name("date")).sendKeys("2015/37/42");
		driver.findElement(By.name("type")).clear();
		driver.findElement(By.name("type")).sendKeys("Snack");
		driver.findElement(By.name("name")).clear();
		driver.findElement(By.name("name")).sendKeys("Cookie Dough");
		driver.findElement(By.name("servings")).clear();
		driver.findElement(By.name("servings")).sendKeys(".5");
		driver.findElement(By.name("calories")).clear();
		driver.findElement(By.name("calories")).sendKeys("160");
		driver.findElement(By.name("fat")).clear();
		driver.findElement(By.name("fat")).sendKeys("8");
		driver.findElement(By.name("sodium")).clear();
		driver.findElement(By.name("sodium")).sendKeys("45");
		driver.findElement(By.name("carbs")).clear();
		driver.findElement(By.name("carbs")).sendKeys("21");
		driver.findElement(By.name("fiber")).clear();
		driver.findElement(By.name("fiber")).sendKeys("0");
		driver.findElement(By.name("sugars")).clear();
		driver.findElement(By.name("sugars")).sendKeys("16");
		driver.findElement(By.name("protein")).clear();
		driver.findElement(By.name("protein")).sendKeys("2");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		
		assertTrue(driver.getPageSource().contains("FormValidationException"));
		assertEquals(driver.getTitle(), "iTrust - View My Food Diary");
		assertTrue(driver.getPageSource().contains("Viewing Log For:"));
		assertTrue(driver.getPageSource().contains("Totals:"));
		assertTrue(driver.getPageSource().contains("09/30/2012"));
		assertTrue(driver.getPageSource().contains("Breakfast"));
		assertTrue(driver.getPageSource().contains("Hot dog"));
		assertTrue(driver.getPageSource().contains("4.0"));
		assertTrue(driver.getPageSource().contains("80.0"));
		assertTrue(driver.getPageSource().contains("5.0"));
		assertTrue(driver.getPageSource().contains("480.0"));
		assertTrue(driver.getPageSource().contains("2.0"));
		assertTrue(driver.getPageSource().contains("0.0"));
		assertTrue(driver.getPageSource().contains("0.0"));
		assertTrue(driver.getPageSource().contains("5.0"));
		
		assertEquals(driver.getTitle(), "iTrust - View My Food Diary");
		assertTrue(driver.getPageSource().contains("Viewing Log For:"));
		assertTrue(driver.getPageSource().contains("Totals:"));
		assertTrue(driver.getPageSource().contains("09/30/2012"));
		assertTrue(driver.getPageSource().contains("Lunch"));
		assertTrue(driver.getPageSource().contains("Mango Passionfruit Juice"));
		assertTrue(driver.getPageSource().contains("1.2"));
		assertTrue(driver.getPageSource().contains("130.0"));
		assertTrue(driver.getPageSource().contains("0.0"));
		assertTrue(driver.getPageSource().contains("25.0"));
		assertTrue(driver.getPageSource().contains("32.0"));
		assertTrue(driver.getPageSource().contains("0.0"));
		assertTrue(driver.getPageSource().contains("29.0"));
		assertTrue(driver.getPageSource().contains("1.0"));
		
		assertEquals(driver.getTitle(), "iTrust - View My Food Diary");
		assertTrue(driver.getPageSource().contains("Viewing Log For:"));
		assertTrue(driver.getPageSource().contains("Totals:"));
		assertFalse(driver.getPageSource().contains("2015/37/42"));
		assertFalse(driver.getPageSource().contains("Snack"));
		assertFalse(driver.getPageSource().contains("Cookie Dough"));
	}
	
	@Test
	public void testDeleteLastEntry() throws Exception {
		driver = login("700", "pw");
		assertEquals(driver.getTitle(), "iTrust - Patient Home");
		driver.findElement(By.linkText("My Food Diary")).click();
		assertEquals(driver.getTitle(), "iTrust - View My Food Diary");
		driver.findElement(By.linkText("Delete")).click();
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals(driver.getTitle(), "iTrust - View My Food Diary");
		assertTrue(driver.getPageSource().contains("No Data"));	
	}
	
	@Test
	public void testEditValueToNullFoodDiary() throws Exception {
		driver = login("503", "pw");
		assertEquals(driver.getTitle(), "iTrust - Patient Home");
		driver.findElement(By.linkText("My Food Diary")).click();
		driver.findElement(By.linkText("Edit")).click();
		assertEquals(driver.getTitle(), "iTrust - Edit Patient");
		driver.findElement(By.name("servings")).clear();
		driver.findElement(By.name("servings")).sendKeys("");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertTrue(driver.getPageSource().contains("NumberFormatException"));
	}
	
	@Test
	public void testDeleteFoodDiaryEntry() throws Exception {
		driver = login("502", "pw");
		assertEquals(driver.getTitle(), "iTrust - Patient Home");
		driver.findElement(By.linkText("My Food Diary")).click();
		assertEquals(driver.getTitle(), "iTrust - View My Food Diary");
		driver.findElement(By.linkText("Delete")).click();
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals(driver.getTitle(), "iTrust - View My Food Diary");
		assertTrue(driver.getPageSource().contains("Viewing Log For:"));
		assertTrue(driver.getPageSource().contains("Totals:"));
		assertTrue(driver.getPageSource().contains("09/30/2012"));
		assertTrue(driver.getPageSource().contains("Lunch"));
		assertTrue(driver.getPageSource().contains("Mango Passionfruit Juice"));
		assertTrue(driver.getPageSource().contains("1.2"));
		assertTrue(driver.getPageSource().contains("130.0"));
		assertTrue(driver.getPageSource().contains("0.0"));
		assertTrue(driver.getPageSource().contains("25.0"));
		assertTrue(driver.getPageSource().contains("32.0"));
		assertTrue(driver.getPageSource().contains("0.0"));
		assertTrue(driver.getPageSource().contains("29.0"));
		assertTrue(driver.getPageSource().contains("1.0"));
	}
	
	@Test
	public void testEditValidEntryToInvalidFoodDiary() throws Exception {
		driver = login("503", "pw");
		assertEquals(driver.getTitle(), "iTrust - Patient Home");
		driver.findElement(By.linkText("My Food Diary")).click();
		driver.findElement(By.linkText("Edit")).click();
		assertEquals(driver.getTitle(), "iTrust - Edit Patient");
		driver.findElement(By.name("servings")).clear();
		driver.findElement(By.name("servings")).sendKeys("-17");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertTrue(driver.getPageSource().contains("FormValidationException"));
	}
	
	@Test
	public void testEditValidEntryFoodDiary() throws Exception {
		driver = login("700", "pw");
		assertEquals(driver.getTitle(), "iTrust - Patient Home");
		driver.findElement(By.linkText("My Food Diary")).click();
		driver.findElement(By.linkText("Edit")).click();
		assertEquals(driver.getTitle(), "iTrust - Edit Patient");
		driver.findElement(By.name("servings")).clear();
		driver.findElement(By.name("servings")).sendKeys("3");
		driver.findElement(By.name("calories")).clear();
		driver.findElement(By.name("calories")).sendKeys("1327");
		driver.findElement(By.name("fat")).clear();
		driver.findElement(By.name("fat")).sendKeys("62.5");
		driver.findElement(By.name("sodium")).clear();
		driver.findElement(By.name("sodium")).sendKeys("687");
		driver.findElement(By.name("carbs")).clear();
		driver.findElement(By.name("carbs")).sendKeys("176.4");
		driver.findElement(By.name("sugars")).clear();
		driver.findElement(By.name("sugars")).sendKeys("112.4");
		driver.findElement(By.name("protein")).clear();
		driver.findElement(By.name("protein")).sendKeys("15.6");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		
		assertEquals(driver.getTitle(), "iTrust - View My Food Diary");
		assertTrue(driver.getPageSource().contains("Viewing Log For:"));
		assertTrue(driver.getPageSource().contains("Totals:"));
		assertTrue(driver.getPageSource().contains("03/16/2014"));
		assertTrue(driver.getPageSource().contains("Lunch"));
		assertTrue(driver.getPageSource().contains("Chocolate Shake"));
		assertTrue(driver.getPageSource().contains("3.0"));
		assertTrue(driver.getPageSource().contains("1327.0"));
		assertTrue(driver.getPageSource().contains("62.5"));
		assertTrue(driver.getPageSource().contains("687.0"));
		assertTrue(driver.getPageSource().contains("176.4"));
		assertTrue(driver.getPageSource().contains("112.4"));
		assertTrue(driver.getPageSource().contains("15.6"));
	}
}
