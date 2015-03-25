package edu.ncsu.csc.itrust.selenium;

import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptExecutor;

import edu.ncsu.csc.itrust.http.iTrustHTTPTest;

public class DesignatedNutritionistTest extends iTrustHTTPTest{
	private HtmlUnitDriver driver;
	private String baseUrl;
	

	@Override
	public void setUp() throws Exception {
		super.setUp();
		driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_3_6);
		driver.setJavascriptEnabled(true);
		baseUrl = "http://localhost:8080/iTrust/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		gen.standardData();
	}
	
	/*Test ID: NotDesignatedNutritionistViewPatientFoodDiary
	 * UC: UC74
	 */
	@Test
	public void testNotDesignatedNutritionistViewPatientFoodDiary() throws Exception {
		driver.get(baseUrl);
		driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9900000012");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Patient Food Diary")).click();
	    driver.findElement(By.id("searchBox")).sendKeys("100");
	    Thread.sleep(2000);
	    //id('searchTarget')/x:table/x:tbody/x:tr[2]
	    //driver.findElement(By.xpath("//input[@value='100']")).sendKeys(Keys.TAB);
	    //System.out.println(driver.findElement(By.xpath("//input[@value='100' and @type='button']")));
	    driver.findElement(By.xpath("//input[@value='100' and @type='button']")).click();
	    assertTrue((driver.getPageSource()).contains("2. Patient has to set you as his/her Designated Nutritionist in order to view it."));
	    Thread.sleep(2000);
	    //((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.xpath("//input[@value='100']")));
	    //System.out.println(driver.getPageSource());
	    System.out.println(driver.getCurrentUrl());
	    System.out.println(driver.getTitle());
	    // id('iTrustContent')/x:center/x:span/x:a[8]
	    driver.findElement(By.linkText("Switch Patient")).click();
	    driver.findElement(By.id("searchBox")).sendKeys("500");
	    Thread.sleep(2000);
	    driver.findElement(By.xpath("//input[@value='500' and @type='button']")).click();
	    driver.findElement(By.linkText("Food Diary")).click();
	    assertTrue((driver.getPageSource()).contains("Oreos"));
	    assertTrue((driver.getPageSource()).contains("Cheese and Bean Dip"));
//	    driver.findElement(By.className("container-fluid")).click();
//	    System.out.println(driver.findElement(By.id("logoutBtn")));
	    //((JavascriptExecutor)driver).executeScript("arguments[0].style.visibility = 'visible';", driver.findElement(By.id("logoutBtn")));
	    driver.setJavascriptEnabled(false);
	    driver.findElement(By.id("logoutBtn")).click();
	    driver.findElement(By.linkText("Transaction Log")).click();
	}

}
