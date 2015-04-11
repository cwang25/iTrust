package edu.ncsu.csc.itrust.seleniumtests;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.gargoylesoftware.htmlunit.ConfirmHandler;
import com.gargoylesoftware.htmlunit.Page;

public class UC70Test extends iTrustSeleniumTest {
	
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
	
	public void testFoodDiaryBasicEdit() throws Exception {
		HtmlUnitDriver wd = this.login("33", "pw");
		wd.findElement(By.linkText("Food Diary")).click();
		
		wd.findElement(By.name("editAction_0")).click();
		
		WebElement edit = wd.findElement(By.id("editFood"));
		WebElement servings = edit.findElement(By.name("servings"));
		servings.clear();
		servings.sendKeys("3");
		
		WebElement calories = edit.findElement(By.name("calories"));
		calories.clear();
		calories.sendKeys("3");
		
		WebElement fat = edit.findElement(By.name("fat"));
		fat.clear();
		fat.sendKeys("3");
		
		WebElement sodium = edit.findElement(By.name("sodium"));
		sodium.clear();
		sodium.sendKeys("3");
		
		WebElement carbs = edit.findElement(By.name("carbs"));
		carbs.clear();
		carbs.sendKeys("3");
		
		WebElement protein = edit.findElement(By.name("protein"));
		protein.clear();
		protein.sendKeys("3");
		
		wd.findElement(By.name("submitAction")).click();
		
		wd.findElement(By.linkText("Food Diary")).click();		

		assertEquals("iTrust - My Food Diary", wd.getTitle());

		WebElement foodDiary = wd.findElement(By.id("food_diary"));

		WebElement servingsCheck = foodDiary.findElement(By.id("servings"));
	    assertEquals("3.0", servingsCheck.getText());
		WebElement carbsCheck = foodDiary.findElement(By.id("carbs"));
		assertEquals("3.0", carbsCheck.getText());
		WebElement sodiumCheck = foodDiary.findElement(By.id("sodium"));
		assertEquals("3.0", sodiumCheck.getText());
		WebElement fatCheck = foodDiary.findElement(By.id("fat"));
		assertEquals("3.0", fatCheck.getText());
		


		
		
	}
	
	public void testInvalidEditFoodName() throws Exception{
		HtmlUnitDriver wd = this.login("33", "pw");
		wd.findElement(By.linkText("Food Diary")).click();

		
		wd.findElement(By.name("editAction_0")).click();
		
		WebElement edit = wd.findElement(By.id("editFood"));
		
		WebElement name = edit.findElement(By.name("food_name"));
		name.clear();
		name.sendKeys("peanutButter 2.0");
		
		wd.findElement(By.name("submitAction")).click();
		
		wd.findElement(By.linkText("Food Diary")).click();	
		
		WebElement foodDiary = wd.findElement(By.id("food_diary"));

		WebElement nameCheck = foodDiary.findElement(By.id("food_name"));
		assertEquals("Hot Dog", nameCheck.getText());
		
	}
	
	public void testFoodDiaryInvalidServingsBoundary() throws Exception{
		HtmlUnitDriver wd = this.login("33", "pw");
		wd.findElement(By.linkText("Food Diary")).click();

		
		wd.findElement(By.name("editAction_0")).click();
		
		WebElement edit = wd.findElement(By.id("editFood"));
		
		WebElement servings = edit.findElement(By.name("servings"));
		servings.clear();
		servings.sendKeys("-2");
		
		wd.findElement(By.name("submitAction")).click();
		
		wd.findElement(By.linkText("Food Diary")).click();	
		
		WebElement foodDiary = wd.findElement(By.id("food_diary"));

		WebElement servingsCheck = foodDiary.findElement(By.id("servings"));
		assertEquals("4.0",servingsCheck.getText());
	}
	
	public void testFoodDiaryBasicDelete() throws Exception{
		HtmlUnitDriver wd = this.login("33", "pw");
		
		wd.findElement(By.linkText("Food Diary")).click();
		assertEquals("iTrust - My Food Diary", wd.getTitle());
		wd.setJavascriptEnabled(true);
	    wd.findElement(By.id("deleteButton_1")).click();
		//click initialize
		List<WebElement> input = wd.findElements(By.tagName("input"));//TODO: don't need list?
		input.get(0).click();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wd.findElement(By.linkText("Food Diary")).click();	

		WebElement foodDiary = wd.findElement(By.id("food_diary"));
		
		WebElement servingsCheck = foodDiary.findElement(By.id("servings"));
		assertEquals("4.0",servingsCheck.getText());
		
	}
	
	public void testFoodDiaryInvalidEdit() throws Exception {
		HtmlUnitDriver wd = this.login("33", "pw");
		wd.findElement(By.linkText("Food Diary")).click();
		
		wd.findElement(By.name("editAction_0")).click();
		
		WebElement edit = wd.findElement(By.id("editFood"));
		WebElement servings = edit.findElement(By.name("servings"));
		servings.clear();
		servings.sendKeys("3");
		
		WebElement calories = edit.findElement(By.name("calories"));
		calories.clear();
		calories.sendKeys("-1");
		wd.findElement(By.name("submitAction")).click();
		wd.findElement(By.linkText("Food Diary")).click();	
		WebElement foodDiary = wd.findElement(By.id("food_diary"));
		
		WebElement calCheck = foodDiary.findElement(By.id("calories"));
	    assertEquals("80.0", calCheck.getText());
	}

}
