/**
 * 
 */
package edu.ncsu.csc.itrust.seleniumtests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

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
		//Login as patient 1
		login("1", "pw");
	    
		//Click on 'View'
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
	    //Click on 'My Food Diary'
	    driver.findElement(By.linkText("My Food Diary")).click();
	    //Click on 'Macro Calculator'
	    driver.findElement(By.xpath("(//button[@type='button'])[3]")).click();
	    
	    
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
	    
	    driver.findElement(By.xpath("(//button[@type='button'])[3]")).click();
	    
	    assertTrue(pageContains("Total Calories: 2048"));
	    assertTrue(pageContains("Protein: 109 gms"));
	    assertTrue(pageContains("Fat: 56 gms"));
	    assertTrue(pageContains("Carbs: 274 gms"));
	}
}
