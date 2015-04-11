package edu.ncsu.csc.itrust.seleniumtests;

import java.util.List;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;


public class UC68Test extends iTrustSeleniumTest{
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.patient30();
		gen.patient32();
		gen.patient33();
		gen.patient34();
		gen.hcp11();
		gen.hcp0();
	}
	
	public void testBasicAddFoodToEmptyDiary() throws Exception{
		
		HtmlUnitDriver wd = this.login("30", "pw");
		wd.findElement(By.linkText("Food Diary")).click();
		wd.findElement(By.linkText("Add Entry")).click();
		
		WebElement date = wd.findElement(By.name("date"));
		date.sendKeys("02/04/15");
		
		Select meal = new Select( wd.findElement(By.name("meal")));
		meal.selectByValue("Dinner");
		
		WebElement name = wd.findElement(By.name("food_name"));
		name.sendKeys("Fruity Pebbles");
		
		WebElement servings = wd.findElement(By.name("servings"));
		servings.sendKeys("7");
		
		WebElement calories = wd.findElement(By.name("calories"));
		calories.sendKeys("110");
		
		WebElement fat = wd.findElement(By.name("fat"));
		fat.sendKeys("1");
		
		WebElement sodium = wd.findElement(By.name("sodium"));
		sodium.sendKeys("170");
		
		WebElement carbs = wd.findElement(By.name("carbs"));
		carbs.sendKeys("24");
		
		WebElement sugar = wd.findElement(By.name("sugar"));
		sugar.sendKeys("11");
		
		WebElement fiber = wd.findElement(By.name("fiber"));
		fiber.sendKeys("0");
		
		WebElement protein = wd.findElement(By.name("protein"));
		protein.sendKeys("1");
		
		wd.findElement(By.name("addAction")).click();
		assertEquals("iTrust - My Food Diary", wd.getTitle());
				
		List<WebElement> meals = wd.findElements(By.id("meal"));
		assertEquals("Dinner", meals.get(0).getText());
		
		List<WebElement> foodName = wd.findElements(By.id("food_name"));
		assertEquals("Fruity Pebbles", foodName.get(0).getText());
		
		
	}
	public void testAddEntryToNonEmptyFoodDiary() throws Exception{
		WebDriver wd = this.login("33", "pw");
		wd.findElement(By.linkText("Food Diary")).click();
		wd.findElement(By.linkText("Add Entry")).click();
		
		WebElement date = wd.findElement(By.name("date"));
		date.sendKeys("11/12/14");
		
		Select meal = new Select( wd.findElement(By.name("meal")));
		meal.selectByValue("Snack");
		
		WebElement name = wd.findElement(By.name("food_name"));
		name.sendKeys("Ice Cream");
		
		WebElement servings = wd.findElement(By.name("servings"));
		servings.sendKeys("5");
		
		WebElement calories = wd.findElement(By.name("calories"));
		calories.sendKeys("160");
		
		WebElement fat = wd.findElement(By.name("fat"));
		fat.sendKeys("8");
		
		WebElement sodium = wd.findElement(By.name("sodium"));
		sodium.sendKeys("45");
		
		WebElement carbs = wd.findElement(By.name("carbs"));
		carbs.sendKeys("21");
		
		WebElement sugar = wd.findElement(By.name("sugar"));
		sugar.sendKeys("16");
		
		WebElement fiber = wd.findElement(By.name("fiber"));
		fiber.sendKeys("0");
		
		WebElement protein = wd.findElement(By.name("protein"));
		protein.sendKeys("2");
		
		wd.findElement(By.name("addAction")).click();
		assertEquals("iTrust - My Food Diary", wd.getTitle());			
		List<WebElement> meals = wd.findElements(By.id("meal"));
		assertEquals("Snack", meals.get(0).getText());
		assertEquals("Breakfast", meals.get(1).getText());
		List<WebElement> foodName = wd.findElements(By.id("food_name"));
		assertEquals("Ice Cream", foodName.get(0).getText());
	}
	public void testAddToDiaryWithBadDate() throws Exception{
		WebDriver wd = this.login("30", "pw");
		wd.findElement(By.linkText("Food Diary")).click();
		wd.findElement(By.linkText("Add Entry")).click();
		
		WebElement date = wd.findElement(By.name("date"));
		date.sendKeys("2014/4/2");
		
		Select meal = new Select( wd.findElement(By.name("meal")));
		meal.selectByValue("Dinner");
		
		WebElement name = wd.findElement(By.name("food_name"));
		name.sendKeys("Fruity Pebbles");
		
		WebElement servings = wd.findElement(By.name("servings"));
		servings.sendKeys("7");
		
		WebElement calories = wd.findElement(By.name("calories"));
		calories.sendKeys("110");
		
		WebElement fat = wd.findElement(By.name("fat"));
		fat.sendKeys("1");
		
		WebElement sodium = wd.findElement(By.name("sodium"));
		sodium.sendKeys("170");
		
		WebElement carbs = wd.findElement(By.name("carbs"));
		carbs.sendKeys("24");
		
		WebElement sugar = wd.findElement(By.name("sugar"));
		sugar.sendKeys("11");
		
		WebElement fiber = wd.findElement(By.name("fiber"));
		fiber.sendKeys("0");
		
		WebElement protein = wd.findElement(By.name("protein"));
		protein.sendKeys("1");
		
		wd.findElement(By.name("addAction")).click();
		assertTrue(wd.getPageSource().contains("Date: date must be in the format MM/DD/YY"));
		
		
		
	}
	public void testAddToDiaryWithZeroServings() throws Exception{
		WebDriver wd = this.login("30", "pw");
		wd.findElement(By.linkText("Food Diary")).click();
		wd.findElement(By.linkText("Add Entry")).click();
		
		WebElement date = wd.findElement(By.name("date"));
		date.sendKeys("11/12/14");
		
		Select meal = new Select( wd.findElement(By.name("meal")));
		meal.selectByValue("Dinner");
		
		WebElement name = wd.findElement(By.name("food_name"));
		name.sendKeys("Fruity Pebbles");
		
		WebElement servings = wd.findElement(By.name("servings"));
		servings.sendKeys("0");
		
		WebElement calories = wd.findElement(By.name("calories"));
		calories.sendKeys("110");
		
		WebElement fat = wd.findElement(By.name("fat"));
		fat.sendKeys("1");
		
		WebElement sodium = wd.findElement(By.name("sodium"));
		sodium.sendKeys("170");
		
		WebElement carbs = wd.findElement(By.name("carbs"));
		carbs.sendKeys("24");
		
		WebElement sugar = wd.findElement(By.name("sugar"));
		sugar.sendKeys("11");
		
		WebElement fiber = wd.findElement(By.name("fiber"));
		fiber.sendKeys("0");
		
		WebElement protein = wd.findElement(By.name("protein"));
		protein.sendKeys("1");
		
		wd.findElement(By.name("addAction")).click();
		assertTrue(wd.getPageSource().contains("Servings: Must be a positive real number"));
		
	}	
	public void testAddToDiaryWithNegativeServings() throws Exception{
		WebDriver wd = this.login("30", "pw");
		wd.findElement(By.linkText("Food Diary")).click();
		wd.findElement(By.linkText("Add Entry")).click();
		
		WebElement date = wd.findElement(By.name("date"));
		date.sendKeys("11/12/14");
		
		Select meal = new Select( wd.findElement(By.name("meal")));
		meal.selectByValue("Dinner");
		
		WebElement name = wd.findElement(By.name("food_name"));
		name.sendKeys("Fruity Pebbles");
		
		WebElement servings = wd.findElement(By.name("servings"));
		servings.sendKeys("-1");
		
		WebElement calories = wd.findElement(By.name("calories"));
		calories.sendKeys("110");
		
		WebElement fat = wd.findElement(By.name("fat"));
		fat.sendKeys("1");
		
		WebElement sodium = wd.findElement(By.name("sodium"));
		sodium.sendKeys("170");
		
		WebElement carbs = wd.findElement(By.name("carbs"));
		carbs.sendKeys("24");
		
		WebElement sugar = wd.findElement(By.name("sugar"));
		sugar.sendKeys("11");
		
		WebElement fiber = wd.findElement(By.name("fiber"));
		fiber.sendKeys("0");
		
		WebElement protein = wd.findElement(By.name("protein"));
		protein.sendKeys("1");
		
		wd.findElement(By.name("addAction")).click();
		assertTrue(wd.getPageSource().contains("Servings: Must be a positive real number"));
		
	}	
	public void testViewFoodDiaryDailytotals() throws Exception{
		WebDriver wd = this.login("33", "pw");
		wd.findElement(By.linkText("Food Diary")).click();
		assertEquals("iTrust - My Food Diary", wd.getTitle());

		WebElement servings = wd.findElement(By.id("totalServings"));
		assertEquals( "5.20", servings.getText());
		
		WebElement calories = wd.findElement(By.id("totalCalories"));
		assertEquals( "210.00", calories.getText());
		
		WebElement fat = wd.findElement(By.id("totalFat"));
		assertEquals("5.00", fat.getText());
		
		WebElement sodium = wd.findElement(By.id("totalSodium"));
		assertEquals("505.00", sodium.getText());
		
		WebElement carbs = wd.findElement(By.id("totalCarbs"));
		assertEquals("34.00",carbs.getText());
		
		WebElement sugar = wd.findElement(By.id("totalSugar"));
		assertEquals("0.00", sugar.getText());
		
		WebElement fiber = wd.findElement(By.id("totalFiber"));
		assertEquals("31.00", fiber.getText());	
		
		WebElement protein = wd.findElement(By.id("totalProtein"));
		assertEquals("6.00", protein.getText());
	}
	
	public void testAddToDiaryWithBadName() throws Exception {
		WebDriver wd = this.login("30", "pw");
		wd.findElement(By.linkText("Food Diary")).click();
		wd.findElement(By.linkText("Add Entry")).click();
		
		WebElement date = wd.findElement(By.name("date"));
		date.sendKeys("11/12/14");
		
		Select meal = new Select( wd.findElement(By.name("meal")));
		meal.selectByValue("Dinner");
		
		WebElement name = wd.findElement(By.name("food_name"));
		name.sendKeys("Fruity? Pebbles?");
		
		WebElement servings = wd.findElement(By.name("servings"));
		servings.sendKeys("7");
		
		WebElement calories = wd.findElement(By.name("calories"));
		calories.sendKeys("110");
		
		WebElement fat = wd.findElement(By.name("fat"));
		fat.sendKeys("1");
		
		WebElement sodium = wd.findElement(By.name("sodium"));
		sodium.sendKeys("170");
		
		WebElement carbs = wd.findElement(By.name("carbs"));
		carbs.sendKeys("24");
		
		WebElement sugar = wd.findElement(By.name("sugar"));
		sugar.sendKeys("11");
		
		WebElement fiber = wd.findElement(By.name("fiber"));
		fiber.sendKeys("0");
		
		WebElement protein = wd.findElement(By.name("protein"));
		protein.sendKeys("1");
		
		wd.findElement(By.name("addAction")).click();
		assertTrue(wd.getPageSource().contains("Food Name: Between 1 and 30 alpha characters and space"));
	}

	public void testAddToDiaryWithNegativeCalories() throws Exception {
		WebDriver wd = this.login("30", "pw");
		wd.findElement(By.linkText("Food Diary")).click();
		wd.findElement(By.linkText("Add Entry")).click();
		
		WebElement date = wd.findElement(By.name("date"));
		date.sendKeys("11/12/14");
		
		Select meal = new Select( wd.findElement(By.name("meal")));
		meal.selectByValue("Dinner");
		
		WebElement name = wd.findElement(By.name("food_name"));
		name.sendKeys("Fruity Pebbles");
		
		WebElement servings = wd.findElement(By.name("servings"));
		servings.sendKeys("7");
		
		WebElement calories = wd.findElement(By.name("calories"));
		calories.sendKeys("-1");
		
		WebElement fat = wd.findElement(By.name("fat"));
		fat.sendKeys("1");
		
		WebElement sodium = wd.findElement(By.name("sodium"));
		sodium.sendKeys("170");
		
		WebElement carbs = wd.findElement(By.name("carbs"));
		carbs.sendKeys("24");
		
		WebElement sugar = wd.findElement(By.name("sugar"));
		sugar.sendKeys("11");
		
		WebElement fiber = wd.findElement(By.name("fiber"));
		fiber.sendKeys("0");
		
		WebElement protein = wd.findElement(By.name("protein"));
		protein.sendKeys("1");
		
		wd.findElement(By.name("addAction")).click();
		assertTrue(wd.getPageSource().contains("Calories: Must be 0 or positive real number"));
	}
	
	public void testAddToDiaryWithNegativeFat() throws Exception {
		WebDriver wd = this.login("30", "pw");
		wd.findElement(By.linkText("Food Diary")).click();
		wd.findElement(By.linkText("Add Entry")).click();
		
		WebElement date = wd.findElement(By.name("date"));
		date.sendKeys("11/12/14");
		
		Select meal = new Select( wd.findElement(By.name("meal")));
		meal.selectByValue("Dinner");
		
		WebElement name = wd.findElement(By.name("food_name"));
		name.sendKeys("Fruity Pebbles");
		
		WebElement servings = wd.findElement(By.name("servings"));
		servings.sendKeys("7");
		
		WebElement calories = wd.findElement(By.name("calories"));
		calories.sendKeys("110");
		
		WebElement fat = wd.findElement(By.name("fat"));
		fat.sendKeys("-1");
		
		WebElement sodium = wd.findElement(By.name("sodium"));
		sodium.sendKeys("170");
		
		WebElement carbs = wd.findElement(By.name("carbs"));
		carbs.sendKeys("24");
		
		WebElement sugar = wd.findElement(By.name("sugar"));
		sugar.sendKeys("11");
		
		WebElement fiber = wd.findElement(By.name("fiber"));
		fiber.sendKeys("0");
		
		WebElement protein = wd.findElement(By.name("protein"));
		protein.sendKeys("1");
		
		wd.findElement(By.name("addAction")).click();
		assertTrue(wd.getPageSource().contains("Fat: Must be 0 or positive real number"));
	}
	
	public void testAddToDiaryWithNegativeSodium() throws Exception {
		WebDriver wd = this.login("30", "pw");
		wd.findElement(By.linkText("Food Diary")).click();
		wd.findElement(By.linkText("Add Entry")).click();
		
		WebElement date = wd.findElement(By.name("date"));
		date.sendKeys("11/12/14");
		
		Select meal = new Select( wd.findElement(By.name("meal")));
		meal.selectByValue("Dinner");
		
		WebElement name = wd.findElement(By.name("food_name"));
		name.sendKeys("Fruity Pebbles");
		
		WebElement servings = wd.findElement(By.name("servings"));
		servings.sendKeys("7");
		
		WebElement calories = wd.findElement(By.name("calories"));
		calories.sendKeys("110");
		
		WebElement fat = wd.findElement(By.name("fat"));
		fat.sendKeys("1");
		
		WebElement sodium = wd.findElement(By.name("sodium"));
		sodium.sendKeys("-170");
		
		WebElement carbs = wd.findElement(By.name("carbs"));
		carbs.sendKeys("24");
		
		WebElement sugar = wd.findElement(By.name("sugar"));
		sugar.sendKeys("11");
		
		WebElement fiber = wd.findElement(By.name("fiber"));
		fiber.sendKeys("0");
		
		WebElement protein = wd.findElement(By.name("protein"));
		protein.sendKeys("1");
		
		wd.findElement(By.name("addAction")).click();
		assertTrue(wd.getPageSource().contains("Sodium: Must be 0 or positive real number"));
	}
	
	
	public void testAddToDiaryWithNegativeCarbs() throws Exception {
		WebDriver wd = this.login("30", "pw");
		wd.findElement(By.linkText("Food Diary")).click();
		wd.findElement(By.linkText("Add Entry")).click();
		
		WebElement date = wd.findElement(By.name("date"));
		date.sendKeys("11/12/14");
		
		Select meal = new Select( wd.findElement(By.name("meal")));
		meal.selectByValue("Dinner");
		
		WebElement name = wd.findElement(By.name("food_name"));
		name.sendKeys("Fruity Pebbles");
		
		WebElement servings = wd.findElement(By.name("servings"));
		servings.sendKeys("7");
		
		WebElement calories = wd.findElement(By.name("calories"));
		calories.sendKeys("110");
		
		WebElement fat = wd.findElement(By.name("fat"));
		fat.sendKeys("1");
		
		WebElement sodium = wd.findElement(By.name("sodium"));
		sodium.sendKeys("170");
		
		WebElement carbs = wd.findElement(By.name("carbs"));
		carbs.sendKeys("-24");
		
		WebElement sugar = wd.findElement(By.name("sugar"));
		sugar.sendKeys("11");
		
		WebElement fiber = wd.findElement(By.name("fiber"));
		fiber.sendKeys("0");
		
		WebElement protein = wd.findElement(By.name("protein"));
		protein.sendKeys("1");
		
		wd.findElement(By.name("addAction")).click();
		assertTrue(wd.getPageSource().contains("Carbs: Must be 0 or positive real number"));
	}
	
	public void testAddToDiaryWithNegativeSugar() throws Exception {
		WebDriver wd = this.login("30", "pw");
		wd.findElement(By.linkText("Food Diary")).click();
		wd.findElement(By.linkText("Add Entry")).click();
		
		WebElement date = wd.findElement(By.name("date"));
		date.sendKeys("11/12/14");
		
		Select meal = new Select( wd.findElement(By.name("meal")));
		meal.selectByValue("Dinner");
		
		WebElement name = wd.findElement(By.name("food_name"));
		name.sendKeys("Fruity Pebbles");
		
		WebElement servings = wd.findElement(By.name("servings"));
		servings.sendKeys("7");
		
		WebElement calories = wd.findElement(By.name("calories"));
		calories.sendKeys("110");
		
		WebElement fat = wd.findElement(By.name("fat"));
		fat.sendKeys("1");
		
		WebElement sodium = wd.findElement(By.name("sodium"));
		sodium.sendKeys("170");
		
		WebElement carbs = wd.findElement(By.name("carbs"));
		carbs.sendKeys("24");
		
		WebElement sugar = wd.findElement(By.name("sugar"));
		sugar.sendKeys("-11");
		
		WebElement fiber = wd.findElement(By.name("fiber"));
		fiber.sendKeys("0");
		
		WebElement protein = wd.findElement(By.name("protein"));
		protein.sendKeys("1");
		
		wd.findElement(By.name("addAction")).click();
		assertTrue(wd.getPageSource().contains("Sugar: Must be 0 or positive real number"));
	}
	
	public void testAddToDiaryWithNegativeFiber() throws Exception {
		WebDriver wd = this.login("30", "pw");
		wd.findElement(By.linkText("Food Diary")).click();
		wd.findElement(By.linkText("Add Entry")).click();
		
		WebElement date = wd.findElement(By.name("date"));
		date.sendKeys("11/12/14");
		
		Select meal = new Select( wd.findElement(By.name("meal")));
		meal.selectByValue("Dinner");
		
		WebElement name = wd.findElement(By.name("food_name"));
		name.sendKeys("Fruity Pebbles");
		
		WebElement servings = wd.findElement(By.name("servings"));
		servings.sendKeys("7");
		
		WebElement calories = wd.findElement(By.name("calories"));
		calories.sendKeys("110");
		
		WebElement fat = wd.findElement(By.name("fat"));
		fat.sendKeys("1");
		
		WebElement sodium = wd.findElement(By.name("sodium"));
		sodium.sendKeys("170");
		
		WebElement carbs = wd.findElement(By.name("carbs"));
		carbs.sendKeys("24");
		
		WebElement sugar = wd.findElement(By.name("sugar"));
		sugar.sendKeys("11");
		
		WebElement fiber = wd.findElement(By.name("fiber"));
		fiber.sendKeys("-1");
		
		WebElement protein = wd.findElement(By.name("protein"));
		protein.sendKeys("1");
		
		wd.findElement(By.name("addAction")).click();
		assertTrue(wd.getPageSource().contains("Fiber: Must be 0 or positive real number"));
	}
	
	public void testAddToDiaryWithNegativeProtein() throws Exception {
		WebDriver wd = this.login("30", "pw");
		wd.findElement(By.linkText("Food Diary")).click();
		wd.findElement(By.linkText("Add Entry")).click();
		
		WebElement date = wd.findElement(By.name("date"));
		date.sendKeys("11/12/14");
		
		Select meal = new Select( wd.findElement(By.name("meal")));
		meal.selectByValue("Dinner");
		
		WebElement name = wd.findElement(By.name("food_name"));
		name.sendKeys("Fruity Pebbles");
		
		WebElement servings = wd.findElement(By.name("servings"));
		servings.sendKeys("7");
		
		WebElement calories = wd.findElement(By.name("calories"));
		calories.sendKeys("110");
		
		WebElement fat = wd.findElement(By.name("fat"));
		fat.sendKeys("1");
		
		WebElement sodium = wd.findElement(By.name("sodium"));
		sodium.sendKeys("170");
		
		WebElement carbs = wd.findElement(By.name("carbs"));
		carbs.sendKeys("24");
		
		WebElement sugar = wd.findElement(By.name("sugar"));
		sugar.sendKeys("11");
		
		WebElement fiber = wd.findElement(By.name("fiber"));
		fiber.sendKeys("0");
		
		WebElement protein = wd.findElement(By.name("protein"));
		protein.sendKeys("-1");
		
		wd.findElement(By.name("addAction")).click();
		assertTrue(wd.getPageSource().contains("Protein: Must be 0 or positive real number"));
	}
}
