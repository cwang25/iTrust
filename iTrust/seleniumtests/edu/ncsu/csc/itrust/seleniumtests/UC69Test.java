package edu.ncsu.csc.itrust.seleniumtests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;


public class UC69Test extends iTrustSeleniumTest{
	
	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
		gen.patient30();
		gen.patient32();
		gen.patient33();
		gen.patient34();
		gen.hcp11();
		gen.hcp0();
	}
	
	public void testViewEmptyFoodDiary() throws Exception{
		WebDriver wd = this.login("30", "pw");
		wd.findElement(By.linkText("Food Diary")).click();
		
		WebElement foodDiary = wd.findElement(By.id("food_diary"));
		
	}
	
	public void testViewFoodDiary() throws Exception{
		WebDriver wd = this.login("33", "pw");
		wd.findElement(By.linkText("Food Diary")).click();
		
		WebElement foodDiary = wd.findElement(By.id("food_diary"));
		
		WebElement label = foodDiary.findElement(By.name("label"));
		assertEquals("Atkins Diet", label.getText());
		
		List<WebElement> meals = foodDiary.findElements(By.id("meal"));
		assertEquals("Breakfast", meals.get(0).getText());
		assertEquals("Lunch", meals.get(1).getText());
		
		List<WebElement> foodName = foodDiary.findElements(By.id("food_name"));
		assertEquals("Hot Dog", foodName.get(0).getText());
		assertEquals("Mango Passionfriut Juice", foodName.get(1).getText());
		
		List<WebElement> servings = foodDiary.findElements(By.id("servings"));
		assertEquals("4.0", servings.get(0).getText());
		assertEquals("1.2", servings.get(1).getText());
		
		List<WebElement> calories = foodDiary.findElements(By.id("calories"));
		assertEquals("80.0", calories.get(0).getText());
		assertEquals("130.0", calories.get(1).getText());
		
		List<WebElement> fat = foodDiary.findElements(By.id("fat"));
		assertEquals("5.0", fat.get(0).getText());
		assertEquals("0.0", fat.get(1).getText());
		
		List<WebElement> sodium = foodDiary.findElements(By.id("sodium"));
		assertEquals("480.0", sodium.get(0).getText());
		assertEquals("25.0", sodium.get(1).getText());
		
		List<WebElement> carbs = foodDiary.findElements(By.id("carbs"));
		assertEquals("2.0", carbs.get(0).getText());
		assertEquals("32.0", carbs.get(1).getText());
		
		List<WebElement> sugar = foodDiary.findElements(By.id("sugar"));
		assertEquals("0.0", sugar.get(0).getText());
		assertEquals("0.0", sugar.get(1).getText());
		
		List<WebElement> fiber = foodDiary.findElements(By.id("fiber"));
		assertEquals("2.0", fiber.get(0).getText());
		assertEquals("29.0", fiber.get(1).getText());
		
		List<WebElement> protein = foodDiary.findElements(By.id("protein"));
		assertEquals("5.0", protein.get(0).getText());
		assertEquals("1.0", protein.get(1).getText());
		
	}
	
	public void testViewFoodDiaryInRange() throws Exception{
		WebDriver wd = this.login("32", "pw");
		wd.findElement(By.linkText("Food Diary")).click();
		
		WebElement foodDiary = wd.findElement(By.id("food_diary"));
		
		List<WebElement> labels = foodDiary.findElements(By.name("label"));
		assertEquals("Munchies", labels.get(0).getText());
		assertEquals("Unhealthy", labels.get(1).getText());
		
		List<WebElement> meals = foodDiary.findElements(By.id("meal"));
		assertEquals("Snack", meals.get(0).getText());
		assertEquals("Breakfast", meals.get(1).getText());
		
		List<WebElement> foodName = foodDiary.findElements(By.id("food_name"));
		assertEquals("Oreos", foodName.get(0).getText());
		assertEquals("Cheese and Bean Dip", foodName.get(1).getText());
		
		List<WebElement> servings = foodDiary.findElements(By.id("servings"));
		assertEquals("53.0", servings.get(0).getText());
		assertEquals("0.75", servings.get(1).getText());
		
		List<WebElement> calories = foodDiary.findElements(By.id("calories"));
		assertEquals("140.0", calories.get(0).getText());
		assertEquals("45.0", calories.get(1).getText());
		
		List<WebElement> fat = foodDiary.findElements(By.id("fat"));
		assertEquals("5.0", fat.get(0).getText());
		assertEquals("2.0", fat.get(1).getText());
		
		List<WebElement> sodium = foodDiary.findElements(By.id("sodium"));
		assertEquals("90.0", sodium.get(0).getText());
		assertEquals("230.0", sodium.get(1).getText());
		
		List<WebElement> carbs = foodDiary.findElements(By.id("carbs"));
		assertEquals("21.0", carbs.get(0).getText());
		assertEquals("5.0", carbs.get(1).getText());
		
		List<WebElement> sugar = foodDiary.findElements(By.id("sugar"));
		assertEquals("1.0", sugar.get(0).getText());
		assertEquals("2.0", sugar.get(1).getText());
		
		List<WebElement> fiber = foodDiary.findElements(By.id("fiber"));
		assertEquals("13.0", fiber.get(0).getText());
		assertEquals("0.0", fiber.get(1).getText());
		
		List<WebElement> protein = foodDiary.findElements(By.id("protein"));
		assertEquals("0.0", protein.get(0).getText());
		assertEquals("2.0", protein.get(1).getText());
		
		//select date range
		//startdate
		WebElement startDateSelector = wd.findElement(By.name("startDate"));
		
		//enter in the date
		startDateSelector.clear();
		startDateSelector.sendKeys("04/12/2014");
		
		//startdate
		WebElement endDateSelector = wd.findElement(By.name("endDate"));
		
		//enter in the date
		endDateSelector.clear();
		endDateSelector.sendKeys("04/14/2014");
		
		wd.findElement(By.name("filterDiary")).click();
		//recheck
		
		foodDiary = wd.findElement(By.id("food_diary"));
		
		labels = foodDiary.findElements(By.name("label"));
		assertEquals("Munchies", labels.get(0).getText());
		
		
		meals = foodDiary.findElements(By.id("meal"));
		assertEquals("Snack", meals.get(0).getText());

		
		foodName = foodDiary.findElements(By.id("food_name"));
		assertEquals("Oreos", foodName.get(0).getText());

		
		servings = foodDiary.findElements(By.id("servings"));
		assertEquals("53.0", servings.get(0).getText());

		
		calories = foodDiary.findElements(By.id("calories"));
		assertEquals("140.0", calories.get(0).getText());

		
		fat = foodDiary.findElements(By.id("fat"));
		assertEquals("5.0", fat.get(0).getText());

		
		sodium = foodDiary.findElements(By.id("sodium"));
		assertEquals("90.0", sodium.get(0).getText());

		
		carbs = foodDiary.findElements(By.id("carbs"));
		assertEquals("21.0", carbs.get(0).getText());

		
		sugar = foodDiary.findElements(By.id("sugar"));
		assertEquals("1.0", sugar.get(0).getText());

		
		fiber = foodDiary.findElements(By.id("fiber"));
		assertEquals("13.0", fiber.get(0).getText());

		
		protein = foodDiary.findElements(By.id("protein"));
		assertEquals("0.0", protein.get(0).getText());
		
	}
	
}
