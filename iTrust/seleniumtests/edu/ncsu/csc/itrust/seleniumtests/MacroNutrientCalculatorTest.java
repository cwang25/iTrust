/**
 * 
 */
package edu.ncsu.csc.itrust.seleniumtests;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import net.sourceforge.htmlunit.corejs.javascript.Function;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.google.api.client.auth.oauth2.BrowserClientRequestUrl;


/**
 * @author nishant
 *
 */
public class MacroNutrientCalculatorTest extends iTrustSeleniumTest{
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		super.setUp();
		gen.standardData();
	}
	
	
	@Test
	public void test() throws Exception {
		login("1", "pw");
	    
		//Click on 'View'
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
	    //Click on 'My Food Diary'
	    Thread.sleep(1000);
	    driver.findElement(By.linkText("My Food Diary")).click();
	    //Click on 'Macro Calculator'
	    Thread.sleep(1000);
	    driver.findElement(By.id("macroBtn")).click();
	    
	    
	    //Select 'male'
	    driver.findElement(By.id("male")).click();
	    //Clear 'age' field
	    driver.findElement(By.id("age")).clear();
	    //Enter age as 30
	    driver.findElement(By.id("age")).sendKeys("30");
	    //Clear weight field and enter 60 for weight
	    driver.findElement(By.id("weight")).clear();
	    driver.findElement(By.id("weight")).sendKeys("60");
	    //Clear height field and enter 170 for height
	    driver.findElement(By.id("height")).clear();
	    driver.findElement(By.id("height")).sendKeys("170");
	    //Select goal as 'Maintain weight'
	    driver.findElement(By.id("maintainmode")).click();
	    //Select Activity Level as 'Lightly active'
	    driver.findElement(By.id("light")).click();
	    //Click on Preview
	    driver.findElement(By.id("preview")).click();
	    //Click on Save
	    driver.findElement(By.id("saveMacroForm")).click();
	    
	    Thread.sleep(5000);
	    driver.findElement(By.id("macroBtn")).click();
	    //driver.findElement(By.id("submitForm")).click();
	    //Click on 'Macro Calculator'
	}
}
