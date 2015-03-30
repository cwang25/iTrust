package edu.ncsu.csc.itrust.selenium;

import org.openqa.selenium.By;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class ViewMyReportRequestsTest extends iTrustSeleniumTest {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.hcp0();
		gen.patient2();
	}
	
	public void testViewMyReportRequests() throws Exception{
		
		login("9000000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
	    driver.findElement(By.cssSelector("div.panel-heading")).click();
	    driver.findElement(By.linkText("My Report Requests")).click();
	    driver.findElement(By.linkText("Add a new Report Request")).click();
	    driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("2");
	    //Thread.sleep(500);
	    driver.findElement(By.xpath("//input[@value='2' and @type='button']")).click();
		TableElement table = new TableElement( driver.findElement(By.id("requestTable")));
		assertLogged(TransactionType.COMPREHENSIVE_REPORT_ADD, 9000000000L, 2L, "");
		assertTrue(table.getTableCell(2, 4).getText().contains("Requested"));
	    driver.findElement(By.linkText("View")).click();
	    assertEquals("iTrust - Comprehensive Patient Report", driver.getTitle());
	    driver.findElement(By.cssSelector("div.panel-heading")).click();
	    driver.findElement(By.linkText("My Report Requests")).click();
		table = new TableElement( driver.findElement(By.id("requestTable")));
		assertTrue(table.getTableCell(2, 4).getText().contains("Viewed"));
	    driver.findElement(By.linkText("View")).click();
	    assertEquals("iTrust - Comprehensive Patient Report", driver.getTitle());
		assertLogged(TransactionType.COMPREHENSIVE_REPORT_VIEW, 9000000000L, 2L, "");


	}

}
