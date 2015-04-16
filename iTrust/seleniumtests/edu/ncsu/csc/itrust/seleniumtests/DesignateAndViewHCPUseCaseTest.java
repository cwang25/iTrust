package edu.ncsu.csc.itrust.seleniumtests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import edu.ncsu.csc.itrust.enums.TransactionType;

/**
 * Use Case 6
 * Test designated and ViewHCPCase 
 *
 */

public class DesignateAndViewHCPUseCaseTest extends iTrustSeleniumTest {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
		gen.patient_hcp_vists();
	}
	
	
	/**
	 * Test testReportSeenHCPs0
	 * @throws Exception
	 */
	public void testReportSeenHCPs0() throws Exception {

		login("2", "pw");

		//click my providers
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
	    driver.findElement(By.linkText("My Providers")).click();
		assertEquals("iTrust - My Providers", driver.getTitle());
		
		assertTrue(pageContains("HCP Name"));
		assertTrue(pageContains("Gandalf Stormcrow"));
		assertTrue(pageContains("Mary Shelley"));
		assertTrue(pageContains("Lauren Frankenstein"));
		assertTrue(pageContains("Jason Frankenstein"));
		assertTrue(pageContains("Kelly Doctor"));
	}
	
	public void testReportSeenHCPs1() throws Exception {
		login("2", "pw");

		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("My Providers")).click();
		assertEquals("iTrust - My Providers", driver.getTitle());
		
		driver.findElement(By.xpath("//*[@id='hcp_table']/tbody/tr[5]/td[5]/input")).click();

		
		assertLogged(TransactionType.LHCP_VIEW, 2L, 0L, "");
		
		assertTrue(driver.getPageSource().contains("Jason Frankenstein"));
	}
	
	public void testReportSeenHCPs2() throws Exception {
		login("2", "pw");

		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("My Providers")).click();
		
		driver.findElement(By.xpath("//*[@id='searchForm']/div/table/tbody/tr[2]/td[2]/input")).sendKeys("Frank");

		driver.findElement(By.xpath("//*[@id='searchForm']/div/table/tbody/tr[3]/td[2]/input")).sendKeys("pediatrician");
		driver.findElement(By.xpath("//*[@id='searchForm']/div/input")).click();
		assertEquals("iTrust - My Providers", driver.getTitle());
		
		assertTrue(driver.getPageSource().contains("Lauren Frankenstein"));
	}
}
