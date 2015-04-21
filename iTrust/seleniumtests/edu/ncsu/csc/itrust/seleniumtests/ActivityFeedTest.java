package edu.ncsu.csc.itrust.seleniumtests;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.*;

public class ActivityFeedTest extends iTrustSeleniumTest {
	
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
		
	}

	
	/**
	 * Tests the limit for activities on the home page and then for all the activities.
	 * @throws Exception
	 */
	public void testOlderActivities() throws Exception {
		gen.transactionLog6();
		
		//Login
		driver = login("2", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		
		//Get the panels and select the activity panel
		List<WebElement> panels = driver.findElements(By.className("panel-group"));
		WebElement activityPanel = panels.get(panels.size() - 1);
		
		//Get the list items and count how many items there are, should be 20
		List<WebElement> listItems = activityPanel.findElements(By.tagName("li"));
		assertEquals(20, listItems.size());
		
		//Now we check the all the activities
		driver.findElement(By.linkText("Older Activities")).click();
		
		//Get the panels and select the activity panel
		panels = driver.findElements(By.className("panel-group"));
		activityPanel = panels.get(panels.size() - 1);
		
		//Get the list items and count how many items there are, should be 40
		listItems = activityPanel.findElements(By.tagName("li"));
		//Plus three because of the two refresh links and then the one padding <li>
		assertEquals(40 + 3, listItems.size());
	}
	
	/**
	 * Tests the refresh functionality of the activity panel, which refreshes and activity 
	 * log on the homepage and limits it to 20.
	 * @throws Exception
	 */
	public void testUpdateActivityFeed() throws Exception {
		gen.transactionLog6();
		
		//Login
		driver = login("2", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		
		//Click on Older Activities to show all activity
		driver.findElement(By.linkText("Older Activities")).click();
		//Then refresh to show only newest 20
		driver.findElement(By.linkText("Refresh")).click();
		
		//Get the panels and select toe activity panel
		List<WebElement> panels = driver.findElements(By.className("panel-group"));
		WebElement activityPanel = panels.get(panels.size() - 1);
		
		//Get the list items and count how many items there are, should be 20
		List<WebElement> listItems = activityPanel.findElements(By.tagName("li"));
		assertEquals(20, listItems.size());
	}
	
	/**
	 * Tests for valid information in Activity Feed
	 * @throws Exception
	 */
	public void testViewActivityFeed() throws Exception {
		gen.transactionLog5();

		//Login
		driver = login("1", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		
		Date d = new Date();
		d.setTime(d.getTime() - 3*24*60*60*1000);
		new SimpleDateFormat("MM/dd/yyyy");
		
		//Get the panels and select toe activity panel
		List<WebElement> panels = driver.findElements(By.className("panel-group"));
		WebElement activityPanel = panels.get(panels.size() - 1);
		
		//Get the list items and count how many items there are, should be 20
		List<WebElement> listItems = activityPanel.findElements(By.tagName("li"));
		
		assertEquals(8, listItems.size());
		assertTrue(listItems.get(0).getText().contains("You successfully authenticated today at "));
		assertEquals("Kelly Doctor viewed your risk factors yesterday at 1:15PM.", listItems.get(1).getText());
		assertEquals("FirstUAP LastUAP viewed your risk factors yesterday at 1:02PM.", listItems.get(2).getText());
		assertEquals("FirstUAP LastUAP viewed your lab procedure results yesterday at 12:02PM.", listItems.get(3).getText());
		assertEquals("Justin Time created an emergency report for you yesterday at 10:04AM.", listItems.get(4).getText());
		assertEquals("Andy Programmer viewed your prescription report yesterday at 9:43AM.", listItems.get(5).getText());
		assertEquals("Kelly Doctor viewed your prescription report yesterday at 8:15AM.", listItems.get(6).getText());
		assertTrue(listItems.get(7).getText().contains("Kelly Doctor edited your office visit on "));
	}
	
	/**
	 * Tests to see if hidden activities show up on activity page
	 * @throws Exception
	 */
	public void testDLHCPActivityHiddenInFeed1() throws Exception {
		login("9000000008", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("Patient Information")).click();
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		
		driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("21");
	    //waitFor(1);
	    //Click on first MID button
	    clickOnJavascriptElement(By.xpath("//input[@value='21' and @type='button']"));
	    
	    
		
		assertEquals("iTrust - Edit Patient", driver.getTitle());
		
		driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Basic Health Information")).click();
		assertEquals("iTrust - Edit Basic Health Record", driver.getTitle());
		
		logout();
		assertEquals("iTrust - Login", driver.getTitle());
		
		login("9000000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());

		//Click Document Office Visit Link
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div")).click();
	    driver.findElement(By.linkText("Document Office Visit")).click();
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());

		driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("21");
	    //waitFor(1);
	    //Click on first MID button
	    clickOnJavascriptElement(By.xpath("//input[@value='21' and @type='button']"));
	    //Click Yes, Document Office Visit
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		
		//Click the create button
	    driver.findElement(By.id("update")).click();
		assertTrue(pageContains("Information Successfully Updated"));

		logout();
		
		login("21", "pw");

		assertTrue(pageContains("Kelly Doctor"));
		assertTrue(pageContains("created an office visit"));
		assertTrue(pageContains("Curious George"));
		assertTrue(pageContains("viewed your health records history today at"));
		assertTrue(pageContains("Curious George"));
		assertTrue(pageContains("viewed your demographics"));
		
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div")).click();
	    driver.findElement(By.linkText("My Providers")).click();
		
		assertEquals("iTrust - My Providers", driver.getTitle());
		
		driver.findElement(By.id("doctor1")).click();
		assertEquals("iTrust - My Providers", driver.getTitle());
		
		driver.findElement(By.id("doctor0")).click();
		assertEquals("iTrust - My Providers", driver.getTitle());
		
		
		logout();
		assertEquals("iTrust - Login", driver.getTitle());
		
		login("21", "pw");
		assertEquals("iTrust - Patient Home",driver.getTitle());

		assertFalse(pageContains("NumberFormatException"));
		assertFalse(pageContains("created an office visit"));
	}
	
	public void testDLHCPActivityHiddenInFeed2() throws Exception {
		driver = login("23", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		
		WebElement element = driver.findElement(By.xpath("//div[@id='act-accord']//div[2]"));
		assertFalse(element.getText().contains("Beaker Beaker viewed your demographics"));
		assertTrue(element.getText().contains("Beaker Beaker edited your demographics"));
		assertFalse(element.getText().contains("Beaker Beaker added you to the telemedicine monitoring list"));

	}
}