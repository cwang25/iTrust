package edu.ncsu.csc.itrust.seleniumtests;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.junit.Test;

import edu.ncsu.csc.itrust.enums.TransactionType;

//import com.sun.org.apache.bcel.internal.generic.Select;

public class FindExpertTest extends iTrustSeleniumTest {
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
		//WebDriver driver = new HtmlUnitDriver();
		//baseUrl = "http://localhost:8080/";
		//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	@Test
	public void testEditAndFindExpert() throws Exception {
		driver.setJavascriptEnabled(true);
		login("9000000001", "pw");
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		assertEquals("iTrust - Admin Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("Edit Personnel")).click();
		driver.findElement(By.name("FIRST_NAME")).clear();
		driver.findElement(By.name("FIRST_NAME")).sendKeys("Kelly");
		driver.findElement(By.name("LAST_NAME")).clear();
		driver.findElement(By.name("LAST_NAME")).sendKeys("Doctor");
		driver.findElement(By.xpath("//input[@value='User Search']")).click();
		//driver.findElement(By.xpath("(//input[@value='9000000000' and @type='submit'])")).click();
		clickOn9000000000(driver);
		assertEquals("iTrust - Edit Personnel", driver.getTitle());
		driver.findElement(By.name("phone")).clear();
		driver.findElement(By.name("phone")).sendKeys("919-100-1000");
		driver.findElement(By.name("action")).click();
		assertEquals("Information Successfully Updated", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
		assertLogged(TransactionType.LHCP_EDIT, 9000000001L, 9000000000L, "");
		//here is the log out
		logout();
		assertEquals("iTrust - Login", driver.getTitle());
		login("1","pw");
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		assertEquals("iTrust - Patient Home", driver.getTitle());
		driver.findElement(By.linkText("Find an Expert")).click();
		assertEquals("iTrust - Find an Expert", driver.getTitle());
		new Select(driver.findElement(By.name("specialty"))).selectByVisibleText("Surgeon");
		driver.findElement(By.name("findExpert")).click();
		// Warning: assertTextPresent may require manual changes
		assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*Kelly Doctor[\\s\\S]*$"));
		driver.setJavascriptEnabled(false);
	}

	private void clickOn9000000000(WebDriver dr){
		((HtmlUnitDriver)dr).setJavascriptEnabled(true);
		JavascriptExecutor js = (JavascriptExecutor)dr;
		js.executeScript("document.getElementsByTagName('form')[2].submit();");
	}
}
