package edu.ncsu.csc.itrust.seleniumteststests;

import org.openqa.selenium.By;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class ViewEmergencyReportTest extends iTrustSeleniumTest {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.icd9cmCodes();
		gen.ndCodes();
		gen.hospitals();
		gen.hcp1();
		gen.hcp2();
		gen.hcp3();
		gen.er4();
		gen.patient9();
		gen.UC32Acceptance();
		gen.clearLoginFailures();
	}
	//9000000006
	public void testGenerateReport() throws Exception {
		
        login("9000000006", "pw");
        assertEquals("iTrust - ER Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000006L, 0L, "");
        driver.findElement(By.cssSelector("div.panel-heading")).click();
        driver.findElement(By.linkText("Emergency Patient Report")).click();
        driver.findElement(By.id("searchBox")).clear();
        driver.findElement(By.id("searchBox")).sendKeys("99");
        driver.findElement(By.xpath("//input[@value='99' and @type='button']")).click();
        assertTrue(pageContains("Darryl Thompson"));
        logout();
        assertTrue(pageContains("patient-centered"));
        login("9900000000", "pw");
        assertEquals("iTrust - HCP Home", driver.getTitle());
        driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[4]/div/h2")).click();
        driver.findElement(By.linkText("Email History")).click();
        assertTrue(pageContains("tarehart@iTrust.org"));
        assertLogged(TransactionType.EMERGENCY_REPORT_VIEW, 9000000006L, 99L, "");



	}
}
