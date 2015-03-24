package edu.ncsu.csc.itrust.selenium;

import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;

import edu.ncsu.csc.itrust.http.iTrustHTTPTest;

public class DesignatedNutritionistTest extends iTrustHTTPTest{
	private HtmlUnitDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Override
	public void setUp() throws Exception {
		super.setUp();
		driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_3_6);
		driver.setJavascriptEnabled(false);
		baseUrl = "http://localhost:8080/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		gen.standardData();
	}

	@Test
	public void testSelenium() throws Exception {
		driver.get(baseUrl + "/iTrust");
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("500");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("My Food Diary")).click();
		driver.findElement(By.id("logoutBtn")).click();
	}

	@After
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
	}

}
