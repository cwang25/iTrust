package edu.ncsu.csc.itrust.seleniumtests;

import java.net.ConnectException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.gargoylesoftware.htmlunit.BrowserVersion;

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
 * Please use the driver in this class instead of creating another instance in every test case.
 * @author Chi-Han
 *
 */
abstract public class iTrustSeleniumTest extends TestCase{
	/**ADDRESS**/
	public static final String ADDRESS = "http://localhost:8080/iTrust/";
	/**Gen**/
	protected TestDataGenerator gen = new TestDataGenerator();
	/**htmlunitdriver**/
	protected HtmlUnitDriver driver;
	private boolean acceptNextAlert = true;
	protected StringBuffer verificationErrors = new StringBuffer();
	
	/**
	 * This is the value that is used for failure times tolerance for
	 * Javascript elements, assertLogged...etc for those functions that
	 * Selenium test framework will occur some delay and result it false failures at the begining.
	 * So use this tolerance to give it a couple trials before report it as failed.
	 */
	static int failureTolerance = 5;
	/**
	 * Generic setUp
	 * This will setup the driver for the browser version and the wait time.
	 * 
	 */
	@Override
	protected void setUp() throws Exception {
		driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_24);
		driver.setJavascriptEnabled(true);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		gen.clearAllTables();

	}
	/**
	 * Login helper function.
	 * @param username
	 * @param password
	 * @return 
	 * @throws Exception
	 */
	protected HtmlUnitDriver login(String username, String password) throws Exception{
		try {
			driver.get(ADDRESS);
			driver.findElement(By.id("j_username")).clear();
		    driver.findElement(By.id("j_username")).sendKeys(username);
		    driver.findElement(By.id("j_password")).clear();
		    driver.findElement(By.id("j_password")).sendKeys(password);
		    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
			if (driver.getTitle().equals("iTrust - Login")) {
				throw new IllegalArgumentException("Error logging in, user not in database?");
			}
			assertLogged(TransactionType.LOGIN_SUCCESS, Long.parseLong(username), Long.parseLong(username), "");
			return driver;
		} catch (Exception e) {
			throw new ConnectException("Tomcat must be running to run Selenium tests.");
		}
	}
	/**
	 * Logout helper method.
	 */
	protected void logout(){
		driver.setJavascriptEnabled(false);
	    driver.findElement(By.id("logoutBtn")).click();
	    driver.setJavascriptEnabled(true);
	}
	/**
	 * Helper method for checking if the current page source (html) contains the given string texts.
	 * @param str String texts to check.
	 * @return true if contains, otherwise false.
	 */
	protected boolean pageContains(String str){
		return driver.getPageSource().contains(str);
	}
	/**
	 * Helper method for checking if the current page source (html) contains the given string texts.
	 * Other team use this name.
	 * @param str String texts to check.
	 * @return true if contains, otherwise false.
	 */
	protected boolean assertTextPresent(String str, HtmlUnitDriver drive){
		assertTrue(pageContains(str));
		return true;
	}
	/**
	 * assertLogged
	 * Selenium might occurs some delay issues so we use while loop to give the database have some chances to get it right.
	 * AssertLogged will re-check a couple times if it fails, and will ultimately fail if the result fails a specific times in a row.
	 * @param code code
	 * @param loggedInMID loggedInMID
	 * @param secondaryMID secondaryMID
	 * @param addedInfo addedInfo
	 * @throws DBException
	 * @throws InterruptedException 
	 */
	protected static void assertLogged(TransactionType code, long loggedInMID,
			long secondaryMID, String addedInfo) throws DBException, InterruptedException {
		int attempt = 0;
		while (attempt < failureTolerance) {
			List<TransactionBean> transList = TestDAOFactory.getTestInstance()
					.getTransactionDAO().getAllTransactions();
			for (TransactionBean t : transList) {
				if ((t.getTransactionType() == code)
						&& (t.getLoggedInMID() == loggedInMID)
						&& (t.getSecondaryMID() == secondaryMID)) {
					assertTrue(t.getTransactionType() == code);
					if (!t.getAddedInfo().trim().contains(addedInfo.trim())) {
						fail("Additional Information is not logged correctly.");
					}
					return;
				}
			}
			attempt++;
			Thread.sleep(1000);
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
		gen.clearAllTables();
		gen.standardData();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
		
	}
	/**
	 * Check if the element is present.
	 * @param by
	 * @return
	 */
	protected boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	/**
	 * Check if there is alert dialog.
	 * @return true alert dialog, false no alert.
	 */
	protected boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}
	/**
	 * Get alert message and close it.
	 * @return message
	 */
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
	/**
	 * When you need to delay the process to wait for the interface.
	 * Such as waiting javascript to generate the interface, or other things that need time to load.
	 * 
	 * @param seconds
	 * @throws InterruptedException
	 */
	public void waitFor(int seconds) throws InterruptedException{
		Thread.sleep(seconds*1000);
	}
	
	/**
	 * Method to click on element that associate with javascript.
	 * This helepr will help you delay the process a little bit to allow javascript to finish up loading before further instructions come.
	 * If there is no delay, then the upcoming new elements or new interface will not be showed when the further process comes.
	 * No delay may result in exception, or element not visible errors.
	 * Also this method will give a couple trials to attempt to click on the javascript element before reporting an error.
	 * @param w WebElement to be clicked
	 * @return True success, False Fail
	 * @throws InterruptedException
	 */
	public boolean clickOnJavascriptElement(By by) throws InterruptedException{
		boolean result = false;
		int attempt = 0;
		while(attempt < failureTolerance){
			try{
				driver.findElement(by).click();
				result = true;
				break;
			}catch(StaleElementReferenceException e){
				
			}
			attempt++;
		}
		return result;

		//Thread.sleep(500);
	}
	/**
	 * Method to return the element instead of clicking it. Exactly like clickOnJavascriptElement(By by)
	 * @param by
	 * @return
	 * @throws InterruptedException
	 */
	public WebElement getJavascriptElement(By by) throws InterruptedException{
		boolean result = false;
		int attempt = 0;
		WebElement theElement = null;
		String text = "";
		while(attempt < failureTolerance){
			try{
				text = driver.findElement(by).getText();
				theElement = driver.findElement(by);
				result = true;
				break;
			}catch(StaleElementReferenceException e){
				
			}
			attempt++;
		}
		return theElement;

		//Thread.sleep(500);
	}
	
	/**
	 * This is helepr for some elements that having trouble to be clicked when javascript is enable.
	 * For my experience, iTrst Logout buttons has this problem that if javascript is always on, then the logout buttons is not clickable.
	 * Selenium keeps returning element is not visible errors until I set javascript enable to false.
	 * So, this method will help us to temporary disable the javascript and click the element then re-enable it.
	 * @param w WebElement to be clicked
	 */
	public void clickOnNonJavascriptElement(WebElement w){
		driver.setJavascriptEnabled(false);
		w.click();
		driver.setJavascriptEnabled(true);
	}
	/**
	 * Integrated from Selenium-Test-15
	 * @param elementName
	 * @param value
	 * @param driver
	 */
	public void selectComboValue(final String elementName, final String value, final WebDriver driver) {
	    final Select selectBox = new Select(driver.findElement(By.name(elementName)));
	    selectBox.selectByValue(value);
	}
	
	
	/**
	 * @deprecated THIS IS FOR INTEGRATING seleniumtests-7 test cases, they use it toooo many times, can't fix it in this short time. We will not suggest to use this method, the Webdriver itself can interact with the search patient Perfectly with no problem.
	 * Enters the given patient MID into the "Select a Patient" search box and clicks the button for that patient.
	 * This method will fail the calling test if the current page is not the search page or if a patient could not be found.
	 * @param patientId the MID of the patient to look for.
	 */
	@Deprecated
	public void selectPatientFromSearch(String patientId) throws Exception {
		if (!driver.isJavascriptEnabled()) {
			throw new IllegalArgumentException("Javascript is not enabled");
		}
		if (driver.getTitle().equals("iTrust - Please Select a Patient")) {
			WebElement searchBox = driver.findElement(By.id("searchBox"));
			searchBox.clear();
			new Actions(driver).click(searchBox).sendKeys(patientId).sendKeys(Keys.TAB).perform();
			List<WebElement> possibleButtons = driver.findElements(By.cssSelector("input[value='" + patientId + "'][type='button']"));
			if (possibleButtons.size() < 1) {
				fail("Unable to find patient with MID: " + patientId);
			} else {
				driver.findElements(By.cssSelector("input[value='" + patientId + "'][type='button']")).get(0).click();
			}
		} else {
			fail("Attempted to search for a patient but was not on the 'Select a Patient' page!");
		}
	}
	
	/**
	 * Handles the common task of asserting that some text is somewhere in the body of the HTML document.
	 * @param expectedText
	 */
	public void assertTextInBody(String expectedText) {
		assertTrue(driver.findElement(By.tagName("body")).getText().contains(expectedText));
	}

	/**
	 * Finds and clicks the specified navbar heading, expanding it and allowing links to be found and clicked.
	 * @param heading The heading to expand.
	 */
	public void expandNavHeading(NavigationHeading heading) {
		driver.findElementByXPath("//*[@anim-target = '" + heading.toString() + "']").click();
	}

	/**
	 * @deprecated From other team code, not clear about the logic of it. Added for test cases integration.
	 * @param name
	 * @param rowNum
	 * @return
	 */
	public String getTableRow(String name, int rowNum){
		WebElement table = driver.findElement(By.id(name));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		WebElement row = rows.get(rowNum);
		return row.getText();
	}
	/**
	 * @deprecated From other team code, not clear about the logic of it. Added for test cases integration.
	 * @param name
	 * @param rowNum
	 * @param colNum
	 * @return
	 */
	public String getTableCell(String name, int rowNum, int colNum){
		WebElement table = driver.findElement(By.id(name));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		WebElement row = rows.get(rowNum);
		List<WebElement> cells = row.findElements(By.tagName("td"));

		return cells.get(colNum).getText();
	}
	/**
	 * @deprecated From other team code, not clear about the logic of it. Added for test cases integration.
	 * @param name
	 * @param rowNum
	 * @param linkText
	 */
	public void clickTableButton(String name, int rowNum, String linkText){
		WebElement table = driver.findElement(By.id(name));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		WebElement row = rows.get(rowNum);
		row.findElement(By.partialLinkText(linkText)).click();
	}
	/**
	 * @deprecated From other team code, not clear about the logic of it. Added for test cases integration.
	 * @param name
	 * @return
	 */
	public int tableRows(String name){
		WebElement table = driver.findElement(By.id(name));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		return rows.size();
	}
	/**
	 * @deprecated From other team code, not clear about the logic of it. Added for test cases integration.
	 * @param name
	 * @return
	 */
	public int tableColumns(String name){
		WebElement table = driver.findElement(By.id(name));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		List<WebElement> cells = rows.get(1).findElements(By.tagName("td"));
		return cells.size();
	}
	/**
	 * @deprecated From other team code, not clear about the logic of it. Added for test cases integration. 
	 *
	 */
	public enum NavigationHeading {
		Info("#info-menu"),
		Appointment("#appt-menu"),
		OfficeVisits("#ov-menu"),
		Messaging("#msg-menu"),
		Telemedicine("#tele-menu"),
		Add("#add-menu"),
		PersonalInfo("#pi-menu"),
		Obstetrics("#ob-menu"),
		Nutrition("#nutrition-menu"),
		Other("#other-menu");

		private String identifier;

		private NavigationHeading(String identifier) {
			this.identifier = identifier;
		}

		@Override
		public String toString() {
			return this.identifier;
		}
	}
}
