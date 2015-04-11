package edu.ncsu.csc.itrust.seleniumtests;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

import edu.ncsu.csc.itrust.enums.TransactionType;
 
public class CreateHCPSpecTest extends iTrustSeleniumTest {
 
	@Override
	protected void setUp() throws Exception{
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	
	public void testSpecialtyOnForm() throws Exception {
		WebDriver driver = login("9000000001", "pw");
		
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
		assertEquals("iTrust - Admin Home", driver.getTitle());
		
		driver.findElement(By.linkText("Add HCP")).click();

		assertEquals("iTrust - Add HCP", driver.getTitle());
		
		driver.findElement(By.name("firstName")).sendKeys("Firstname");
		driver.findElement(By.name("lastName")).sendKeys("Lastname");
		driver.findElement(By.name("email")).sendKeys("abcdef@ncsu.edu");
		Select select = new Select(driver.findElement(By.name("specialty")));
		select.selectByValue("pediatrician");
		driver.findElement(By.name("email")).submit();
		
		String newMID = driver.findElement(By.className("fTable")).findElements(By.cssSelector("td")).get(1).getText();
		
		assertLogged(TransactionType.LHCP_CREATE, 9000000001L, Long.parseLong(newMID), "");
	}
}