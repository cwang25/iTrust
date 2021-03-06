package edu.ncsu.csc.itrust.seleniumtests;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.Select;

public class FoodDiaryLabelTest extends iTrustSeleniumTest {
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		gen.standardData();
		gen.uc73();
	}
	
	@Test
	public void testAddLabel() throws Exception {
		login("500", "pw");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
	    driver.findElement(By.linkText("My Food Diary")).click();
	    driver.findElement(By.xpath("(//button[@type='button'])[9]")).click();
	    driver.findElement(By.id("newLabelName")).clear();
	    driver.findElement(By.id("newLabelName")).sendKeys("testlabel");
	    driver.findElement(By.id("mockSaveNewLabelBtn")).click();
	    assertTrue(pageContains("Label has been added."));
	}
	
	@Test
	public void testSetLabel() throws Exception {
		login("500", "pw");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
	    driver.findElement(By.linkText("My Food Diary")).click();
	    driver.findElement(By.xpath("(//button[@type='button'])[9]")).click();
	    driver.findElement(By.id("newLabelName")).clear();
	    driver.findElement(By.id("newLabelName")).sendKeys("test");
	    driver.findElement(By.id("mockSaveNewLabelBtn")).click();
	    assertTrue(pageContains("Label has been added."));
	    new Select(driver.findElement(By.xpath("(//select[@value=''])[2]"))).selectByVisibleText("test");
	    driver.findElement(By.xpath("//table[@id='foodDiaryTable']/tbody/tr[7]/td[13]/button")).click();
	    assertTrue(pageContains("Label has been set."));
	}
	
	@Test
	public void testRemoveAttachedLabel() throws Exception {
		login("500", "pw");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
	    driver.findElement(By.linkText("My Food Diary")).click();
	    driver.findElement(By.xpath("(//button[@type='button'])[9]")).click();
	    driver.findElement(By.id("newLabelName")).clear();
	    driver.findElement(By.id("newLabelName")).sendKeys("test");
	    driver.findElement(By.id("mockSaveNewLabelBtn")).click();
	    assertTrue(pageContains("Label has been added."));
	    new Select(driver.findElement(By.xpath("//select[@value='']"))).selectByVisibleText("test");
	    driver.findElement(By.cssSelector("button.changeLabelBtn")).click();
	    assertTrue(pageContains("Label has been set."));
	    new Select(driver.findElement(By.xpath("//select[@value='']"))).selectByVisibleText("none");
	    driver.findElement(By.cssSelector("button.changeLabelBtn")).click();
	    assertTrue(pageContains("Label has been set."));
	}
	
	@Test
	public void testFilterByLabel() throws Exception {
		login("500", "pw");
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
	    driver.findElement(By.linkText("My Food Diary")).click();
	    driver.findElement(By.xpath("(//button[@type='button'])[9]")).click();
	    driver.findElement(By.id("newLabelName")).clear();
	    driver.findElement(By.id("newLabelName")).sendKeys("test");
	    driver.findElement(By.id("mockSaveNewLabelBtn")).click();
	    assertTrue(pageContains("Label has been added."));
	    new Select(driver.findElement(By.xpath("//select[@value='']"))).selectByVisibleText("test");
	    driver.findElement(By.cssSelector("button.changeLabelBtn")).click();
	    assertTrue(pageContains("Label has been set."));
	    new Select(driver.findElement(By.id("labelFilter"))).selectByVisibleText("test");
	    assertFalse(driver.findElementsByXPath("//*[contains(text(), '5/21/2013')]").get(0).isDisplayed());
	}
	
	@Test
	public void testRemoveExistingFoodDiaryLabel() throws Exception {
		login("731", "pw");
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div")).click();
	    driver.findElement(By.linkText("My Food Diary")).click();
		JavascriptExecutor js = (JavascriptExecutor)driver;
		//Work around for this function.
		//Since selenium outdated javascript engine doesn't not support inserting jQuery functions in javascript which 
		//is supported in most popular browser.
		// 6 is the rowID of the label which is going to be removed.
		js.executeScript("document.getElementById('removeLabelRowID').value='6';");
		js.executeScript("document.getElementById('removeLabel').submit();");
		
	    assertTrue(pageContains("Label has been removed"));
	    //oldlabel shoud be totally gone on the page.
	    assertFalse(pageContains("oldlabel"));
	}
}
