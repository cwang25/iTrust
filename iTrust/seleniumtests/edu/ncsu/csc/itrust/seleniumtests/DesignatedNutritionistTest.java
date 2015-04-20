package edu.ncsu.csc.itrust.seleniumtests;

import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;

public class DesignatedNutritionistTest extends iTrustSeleniumTest {
	private String baseUrl;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		baseUrl = "http://localhost:8080/iTrust/";
		gen.standardData();
	}

	/*
	 * Test ID: NotDesignatedNutritionistViewPatientFoodDiary UC: UC74
	 */
	@Test
	public void testNotDesignatedNutritionistViewPatientFoodDiary()	throws Exception {
		driver.get(baseUrl);
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("9900000012");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("Patient Food Diary")).click();
		driver.findElement(By.id("searchBox")).sendKeys("100");
		this.clickOnJavascriptElement(By.xpath("//input[@value='100' and @type='button']"));
		assertTrue((driver.getPageSource()).contains("2. Patient has to set you as his/her Designated Nutritionist in order to view it."));
		driver.setJavascriptEnabled(false);
		driver.findElement(By.id("logoutBtn")).click();
		driver.findElement(By.linkText("Transaction Log")).click();
	}
	
	@Test
	public void testDesignatedNutritionistViewPatientFoodDiary() throws Exception {
		driver.get(baseUrl);
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("9900000012");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("Patient Food Diary")).click();
		driver.findElement(By.id("searchBox")).sendKeys("500");
		//waitFor(2);
		this.clickOnJavascriptElement(By.xpath("//input[@value='500' and @type='button']"));
		assertTrue((driver.getPageSource()).contains("Oreos"));
		assertTrue((driver.getPageSource()).contains("Cheese and Bean Dip"));

		//waitFor(2);
		driver.findElement(By.linkText("Switch Patient")).click();
		driver.findElement(By.id("searchBox")).sendKeys("500");
		//waitFor(2);
		this.clickOnJavascriptElement(By.xpath("//input[@value='500' and @type='button']"));
		driver.findElement(By.linkText("Food Diary")).click();
		assertTrue((driver.getPageSource()).contains("Oreos"));
		assertTrue((driver.getPageSource()).contains("Cheese and Bean Dip"));
		driver.setJavascriptEnabled(false);
		driver.findElement(By.id("logoutBtn")).click();
		driver.findElement(By.linkText("Transaction Log")).click();
	}

	/*
	 * Test ID: makeDesignatedNutritionist UC: UC74
	 */
	@Test
	public void testMakeDesignatedNutritionist() throws Exception {
		driver.get(baseUrl);
		driver.findElement(By.id("j_username")).click();
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("9900000012");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Patient Food Diary")).click();
		driver.findElement(By.id("searchBox")).clear();
		driver.findElement(By.id("searchBox")).sendKeys("100");
		super.clickOnJavascriptElement(By.xpath("//input[@value='100' and @type='button']"));
		//driver.findElement().click();
		assertTrue((driver.getPageSource()).contains("2. Patient has to set you as his/her Designated Nutritionist in order to view it."));
		driver.setJavascriptEnabled(false);
		driver.findElement(By.id("logoutBtn")).click();
		driver.setJavascriptEnabled(true);
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("100");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("My Providers")).click();
		driver.findElement(By.id("doctor1")).click();
		//waitFor(2);
		driver.setJavascriptEnabled(false);
		driver.findElement(By.id("logoutBtn")).click();
		driver.setJavascriptEnabled(true);
		driver.get(baseUrl);
		driver.findElement(By.linkText("Nutritionist")).click();
		driver.findElement(By.cssSelector("div.panel-heading")).click();
		driver.findElement(By.linkText("Patient Food Diary")).click();
		driver.findElement(By.id("searchBox")).clear();
		driver.findElement(By.id("searchBox")).sendKeys("100");
		//waitFor(2);
		
		clickOnJavascriptElement(By.xpath("//input[@value='100' and @type='button']"));
		
		assertTrue((driver.getPageSource()).contains("The patient has no Food diary"));
	}

}
