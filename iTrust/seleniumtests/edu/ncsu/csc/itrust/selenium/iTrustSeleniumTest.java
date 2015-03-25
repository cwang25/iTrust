package edu.ncsu.csc.itrust.selenium;

import java.net.ConnectException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;

import edu.ncsu.csc.itrust.beans.TransactionBean;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
import junit.framework.TestCase;
/**
 * The base test case for Selenium Test.
 * 
 * Note:
 * Please use the driver in this class instead of creating another instance in every single test class.
 * @author Chi-Han
 *
 */
public class iTrustSeleniumTest extends TestCase{
	/**ADDRESS**/
	public static final String ADDRESS = "http://localhost:8080/iTrust/";
	/**Gen**/
	protected TestDataGenerator gen = new TestDataGenerator();
	/**htmlunitdriver**/
	protected HtmlUnitDriver driver;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Override
	protected void setUp() throws Exception {
		driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_3_6);
		driver.setJavascriptEnabled(true);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		gen.clearAllTables();
	}
	
	protected void login(String username, String password) throws Exception{
		try {
			driver.get(ADDRESS);
			driver.findElement(By.id("j_username")).clear();
		    driver.findElement(By.id("j_username")).sendKeys("9900000012");
		    driver.findElement(By.id("j_password")).clear();
		    driver.findElement(By.id("j_password")).sendKeys("pw");
		    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
			if (driver.getTitle().equals("iTrust Login")) {
				throw new IllegalArgumentException("Error logging in, user not in database?");
			}
			assertLogged(TransactionType.LOGIN_SUCCESS, Long.parseLong(username), Long.parseLong(username), "");
		} catch (Exception e) {
			throw new ConnectException("Tomcat must be running to run Selenium tests.");
		}
	}
	
	protected void logout(){
		driver.setJavascriptEnabled(false);
	    driver.findElement(By.id("logoutBtn")).click();
	    driver.setJavascriptEnabled(true);
	}
	
	protected boolean contain(String str){
		return driver.getPageSource().contains(str);
	}
	/**
	 * assertLogged
	 * @param code code
	 * @param loggedInMID loggedInMID
	 * @param secondaryMID secondaryMID
	 * @param addedInfo addedInfo
	 * @throws DBException
	 */
	protected static void assertLogged(TransactionType code, long loggedInMID,
			long secondaryMID, String addedInfo)
			throws DBException {
		List<TransactionBean> transList = TestDAOFactory.getTestInstance().getTransactionDAO().getAllTransactions();
		for (TransactionBean t : transList)
		{	
			if( (t.getTransactionType() == code) &&
				(t.getLoggedInMID() == loggedInMID) &&
				(t.getSecondaryMID() == secondaryMID))
				{
					assertTrue(t.getTransactionType() == code);
					if(!t.getAddedInfo().trim().contains(addedInfo.trim()))
					{
						fail("Additional Information is not logged correctly.");
					}
					return;
				}
		}
		fail("Event not logged as specified.");
	}

	/**
	 * assertNotLogged
	 * @param code code
	 * @param loggedInMID loggedInMID
	 * @param secondaryMID secondaryMID
	 * @param addedInfo addedInfo
	 * @throws DBException
	 */
	protected static void assertNotLogged(TransactionType code, long loggedInMID,
			long secondaryMID, String addedInfo)
			throws DBException {
		List<TransactionBean> transList = TestDAOFactory.getTestInstance().getTransactionDAO().getAllTransactions();
		for (TransactionBean t : transList)
		{	
			if( (t.getTransactionType() == code) &&
				(t.getLoggedInMID() == loggedInMID) &&
				(t.getSecondaryMID() == secondaryMID) &&
				(t.getAddedInfo().trim().contains(addedInfo)) )
				{
					fail("Event was logged, but should NOT have been logged");
					return;
				}
		}
	}
	

	@After
	protected void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	protected boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	protected boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	protected String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
	
	public void waitFor(int seconds) throws InterruptedException{
		Thread.sleep(seconds*1000);
	}
	
	public void clickOnJavascriptElement(WebElement w){
		
	}

}
