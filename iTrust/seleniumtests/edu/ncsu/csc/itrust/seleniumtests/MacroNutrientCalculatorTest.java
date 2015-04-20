package edu.ncsu.csc.itrust.seleniumtests;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

/**
 * HtmlUnitDriver does not support Chart.js. So most test cases in this class use Javascript hacks
 * to test expected and actual results without actually drawing the chart. Please note that 
 * macronutrient chart functionality has been thoroughly tested manually and through the FirefoxDriver.
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
	
	/**
	 * Tests adding a valid macronutrient chart entry
	 * @throws Exception
	 */
	@Test
	public void testAddValidEntry() throws Exception {
		login("1", "pw");
	    
		//Click on 'View'
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
	    //Click on 'My Food Diary'
	    driver.findElement(By.linkText("My Food Diary")).click();
	    //Click on 'Macro Calculator
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
	    JavascriptExecutor js = (JavascriptExecutor)driver;
	    //Simulate clicking the Preview Button. Since using driver.findElement.click() does not work, we have to use this javascript hack
	    assertEquals(new Boolean(true), (Boolean)js.executeScript("return validateInput();"));
	}
	
	/**
	 * Tests adding an invalid weight entry
	 * @throws Exception
	 */
	@Test
	public void testAddInvalidWeight1() throws Exception{
		//Login as patient 1
		login("1", "pw");
		//Click on 'View' in the sidebar
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
	    //Click on 'My Food Diary'
	    driver.findElement(By.linkText("My Food Diary")).click();
	    //Click on 'Macro Calculator'
	    driver.findElement(By.id("macroBtn")).click();
	    //Select Male
	    driver.findElement(By.id("male")).click();
	    driver.findElement(By.id("age")).clear();
	    //Add age 30
	    driver.findElement(By.id("age")).sendKeys("30");
	    driver.findElement(By.id("weight")).clear();
	    //Add weight 'seventy' -- invalid
	    driver.findElement(By.id("weight")).sendKeys("seventy");
	    driver.findElement(By.id("height")).clear();
	    driver.findElement(By.id("height")).sendKeys("170");
	    //Click maintain weight
	    driver.findElement(By.id("maintainmode")).click();
	    driver.findElement(By.id("sedentary")).click();
	    
	    
	    //driver.findElement(By.id("preview")).click();
	    //Click 'Save
	    JavascriptExecutor js = (JavascriptExecutor)driver;
	    //Simulate clicking the Preview Button. Since using driver.findElement.click() does not work, we have to use this javascript hack
	    js.executeScript("graphIt();");
	    //driver.findElement(By.id("preview")).click();
	    String errorMsg = (String)js.executeScript("return document.getElementById('errorTextWeight').innerHTML;");
	    
	    assertEquals("Enter value in range 1-790", errorMsg);
	    //Logout
	    logout();
	}
	
	/**
	 * Tests adding another invalid weight entry
	 * @throws Exception
	 */
	@Test
	public void testAddInvalidWeight2() throws Exception{
		//Login as patient 1
		login("1", "pw");
		//Click on 'View' in the sidebar
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
	    //Click on 'My Food Diary'
	    driver.findElement(By.linkText("My Food Diary")).click();
	    //Click on 'Macro Calculator'
	    driver.findElement(By.id("macroBtn")).click();
	    //Select Male
	    driver.findElement(By.id("male")).click();
	    driver.findElement(By.id("age")).clear();
	    //Add age 30
	    driver.findElement(By.id("age")).sendKeys("30");
	    driver.findElement(By.id("weight")).clear();
	    //Add weight '900' -- invalid
	    driver.findElement(By.id("weight")).sendKeys("900");
	    driver.findElement(By.id("height")).clear();
	    //Add height 170
	    driver.findElement(By.id("height")).sendKeys("170");
	    //Click maintain weight
	    driver.findElement(By.id("maintainmode")).click();
	    driver.findElement(By.id("sedentary")).click();
	    
	    
	    //driver.findElement(By.id("preview")).click();
	    //Click 'Save
	    JavascriptExecutor js = (JavascriptExecutor)driver;
	    //Simulate clicking the Preview Button. Since using driver.findElement.click() does not work, we have to use this javascript hack
	    js.executeScript("graphIt();");
	    //driver.findElement(By.id("preview")).click();
	    String errorMsg = (String)js.executeScript("return document.getElementById('errorTextWeight').innerHTML;");
	    
	    assertEquals("Enter value in range 1-790", errorMsg);
	    //Logout
	    logout();
	}
	
	/**
	 * Tests adding an invalid height entry
	 * @throws Exception
	 */
	@Test
	public void testAddInvalidHeight1() throws Exception{
		//Login as patient 1
		login("1", "pw");
		//Click on 'View' in the sidebar
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
	    //Click on 'My Food Diary'
	    driver.findElement(By.linkText("My Food Diary")).click();
	    //Click on 'Macro Calculator'
	    driver.findElement(By.id("macroBtn")).click();
	    //Select Male
	    driver.findElement(By.id("male")).click();
	    driver.findElement(By.id("age")).clear();
	    //Add age 30
	    driver.findElement(By.id("age")).sendKeys("30");
	    driver.findElement(By.id("weight")).clear();
	    //Add weight 70
	    driver.findElement(By.id("weight")).sendKeys("70");
	    driver.findElement(By.id("height")).clear();
	    //Add height 274 -- invalid
	    driver.findElement(By.id("height")).sendKeys("274");
	    //Click maintain weight
	    driver.findElement(By.id("maintainmode")).click();
	    driver.findElement(By.id("sedentary")).click();
	    
	    
	    //driver.findElement(By.id("preview")).click();
	    //Click 'Save
	    JavascriptExecutor js = (JavascriptExecutor)driver;
	    //Simulate clicking the Preview Button. Since using driver.findElement.click() does not work, we have to use this javascript hack
	    js.executeScript("graphIt();");
	    //driver.findElement(By.id("preview")).click();
	    String errorMsg = (String)js.executeScript("return document.getElementById('errorTextHeight').innerHTML;");
	    
	    assertEquals("Enter value in range: 1-273", errorMsg);
	    //Logout
	    logout();
	}
	
	/**
	 * Tests adding another invalid height entry
	 * @throws Exception
	 */
	@Test
	public void testAddInvalidHeight2() throws Exception{
		//Login as patient 1
		login("1", "pw");
		//Click on 'View' in the sidebar
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
	    //Click on 'My Food Diary'
	    driver.findElement(By.linkText("My Food Diary")).click();
	    //Click on 'Macro Calculator'
	    driver.findElement(By.id("macroBtn")).click();
	    //Select Male
	    driver.findElement(By.id("male")).click();
	    driver.findElement(By.id("age")).clear();
	    //Add age 30
	    driver.findElement(By.id("age")).sendKeys("30");
	    driver.findElement(By.id("weight")).clear();
	    //Add weight '70
	    driver.findElement(By.id("weight")).sendKeys("70");
	    driver.findElement(By.id("height")).clear();
	    //Add height 'tall' - invalid
	    driver.findElement(By.id("height")).sendKeys("tall");
	    //Click maintain weight
	    driver.findElement(By.id("maintainmode")).click();
	    driver.findElement(By.id("sedentary")).click();
	    
	    
	    //driver.findElement(By.id("preview")).click();
	    //Click 'Save
	    JavascriptExecutor js = (JavascriptExecutor)driver;
	    //Simulate clicking the Preview Button. Since using driver.findElement.click() does not work, we have to use this javascript hack
	    js.executeScript("graphIt();");
	    //driver.findElement(By.id("preview")).click();
	    String errorMsg = (String)js.executeScript("return document.getElementById('errorTextHeight').innerHTML;");
	    
	    assertEquals("Enter value in range: 1-273", errorMsg);
	    //Logout
	    logout();
	}
}
