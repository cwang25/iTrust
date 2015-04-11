package edu.ncsu.csc.itrust.seleniumtests;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

public class InsuranceNotificationTest extends iTrustSeleniumTest {
	// MIDs for various people.
	private static final long JOHN_SMITH = 313;
	private static final long JUAN_CARLOS = 315;
	private static final long ALEX_PAUL = 316;

	private static final long ROGER_KING = 9000000015L;
	private static final long JANE_SMITH = 9000000014L;
	private static final long MIKE_JONES = 9000000012L;

	// password for users.
	private static final String PW = "pw";

	private StringBuffer verificationErrors = new StringBuffer();

	public void setUp() throws Exception {
		super.setUp();
		gen.hospitals();
		gen.hospitals1();
		gen.hcp0();
		gen.uc51();
		gen.uc60();
	}

	private static HtmlUnitDriver submitPayment(HtmlUnitDriver driver,
			String holder, String id, String provider, String add1,
			String add2, String city, String state, String zip, String phone)
			throws Exception {
	    driver.findElement(By.id("Ins")).click();
	    driver.findElement(By.name("insID")).clear();
	    driver.findElement(By.name("insID")).sendKeys(id);
	    driver.findElement(By.name("insHolder")).clear();
	    driver.findElement(By.name("insHolder")).sendKeys(holder);
	    driver.findElement(By.name("insProvider")).clear();
	    driver.findElement(By.name("insProvider")).sendKeys(provider);
	    driver.findElement(By.name("insAdd1")).clear();
	    driver.findElement(By.name("insAdd1")).sendKeys(add1);
	    driver.findElement(By.name("insAdd2")).clear();
	    driver.findElement(By.name("insAdd2")).sendKeys(add2);
	    driver.findElement(By.name("insCity")).clear();
	    driver.findElement(By.name("insCity")).sendKeys(city);
	    driver.findElement(By.name("insState")).clear();
	    driver.findElement(By.name("insState")).sendKeys(state);
	    driver.findElement(By.name("insZip")).clear();
	    driver.findElement(By.name("insZip")).sendKeys(zip);
	    driver.findElement(By.name("insPhone")).clear();
	    driver.findElement(By.name("insPhone")).sendKeys(phone);
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		return driver;
	}

	public void testCantViewSubmitted() throws Exception {
		// Login as Alex Paul
		driver = login(ALEX_PAUL+"", PW);
		
		driver.setJavascriptEnabled(false);
		driver.findElement(By.xpath("//*[@id=\"view-menu\"]/ul/li[12]/a")).click();
		driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/div/table/tbody/tr[2]/td[1]/a")).click();
		assertTrue(driver.findElement(By.xpath("//*[@id=\"typeTable\"]/tbody")).getText().contains("Insurance"));
		
		//Pay with a credit card.
	    driver.findElement(By.id("CC")).click();
	    driver.findElement(By.name("ccNumber")).clear();
	    driver.findElement(By.name("ccNumber")).sendKeys("343570480641495");
	    driver.findElement(By.name("cvv")).clear();
	    driver.findElement(By.name("cvv")).sendKeys("0123");
	    new Select(driver.findElement(By.name("ccType"))).selectByVisibleText("American Express");
	    driver.findElement(By.name("ccHolder")).clear();
	    driver.findElement(By.name("ccHolder")).sendKeys("Alex Paul");
	    driver.findElement(By.name("billAddress")).clear();
	    driver.findElement(By.name("billAddress")).sendKeys("206 Crest Road, Raleigh, NC 27606");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		
		//Login as UAP Roger King
		driver = login("" + ROGER_KING, PW);
		//Make sure that you cant see the statement.
		assertTrue(driver.findElement(By.xpath("//*[@id=\"notificationArea\"]/div[2]/div")).getText().contains("No Pending Insurance Claims."));
		driver.findElement(By.xpath("//*[@id=\"view-menu\"]/ul/li[2]/a")).click();
		assertTrue(driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]")).getText().contains("No claims to display."));
	}

	public void testClaimNotification() throws Exception {
		// Login as John Smith
		driver = login("" + JOHN_SMITH, PW);
		driver.setJavascriptEnabled(false);
		driver.findElement(By.xpath("//*[@id=\"view-menu\"]/ul/li[12]/a")).click();
		assertTrue(driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/div/table/tbody/tr[2]/td[3]")).getText().contains("Shelly Vang"));
		driver.findElement(By.linkText("01/10/2014")).click();
		// Make an insurance claim.
		assertEquals("$150",driver.findElement(By.xpath("//*[@id=\"genInfo\"]/tbody/tr[3]/td[2]")).getText());
		assertEquals("Health Institute Dr. E",driver.findElement(By.xpath("//*[@id=\"genInfo\"]/tbody/tr[7]/td[2]")).getText());
		assertEquals("General Checkup",driver.findElement(By.xpath("//*[@id=\"genInfo\"]/tbody/tr[8]/td[2]")).getText());
		driver = submitPayment(driver, "John Smith", "1234567A01","ABC Insurance", "365 Broad St", "", "Raleigh", "NC", "27606","919-112-8234");
		driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/ul/li[2]/a")).click();//logout

		// Login as UAP Mike Jones
		driver = login("" + MIKE_JONES, PW);
		// Make sure the notifications are correct.
		assertEquals(
				"/iTrust/image/icons/inboxUnread.png",
				driver.findElement(By.xpath("//*[@id=\"notificationArea\"]/div[2]/div/div[2]/ul/li[2]/a[1]/img")).getAttribute("src"));
		driver.findElement(By.xpath("//*[@id=\"notificationArea\"]/div[2]/div/div[2]/ul/li[2]/a[1]/img")).click();
		assertEquals("iTrust - View Insurance Claims", driver.getTitle());
	}
	
	public void testApprovalNotification() throws Exception{
		// Login as John Smith
		driver = login("" + JOHN_SMITH, PW);
		driver.setJavascriptEnabled(false);
		driver.findElement(By.xpath("//*[@id=\"view-menu\"]/ul/li[12]/a")).click();
		assertTrue(driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/div/table/tbody/tr[2]/td[3]")).getText().contains("Shelly Vang"));
		driver.findElement(By.linkText("01/10/2014")).click();
		// Make an insurance claim.
		assertEquals("$150",driver.findElement(By.xpath("//*[@id=\"genInfo\"]/tbody/tr[3]/td[2]")).getText());
		assertEquals("Health Institute Dr. E",driver.findElement(By.xpath("//*[@id=\"genInfo\"]/tbody/tr[7]/td[2]")).getText());
		assertEquals("General Checkup",driver.findElement(By.xpath("//*[@id=\"genInfo\"]/tbody/tr[8]/td[2]")).getText());
		driver = submitPayment(driver, "John Smith", "1234567A01","ABC Insurance", "365 Broad St", "", "Raleigh", "NC", "27606","919-112-8234");
		driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/ul/li[2]/a")).click();//logout
		
		// Login as UAP Mike Jones
		driver = login("" + MIKE_JONES, PW);
		driver.findElement(By.xpath("//*[@id=\"view-menu\"]/ul/li[2]/a")).click();
		driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/table/tbody/tr[2]/td[1]/a")).click();
		//Approve the claim
		driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/form/table/tbody/tr[15]/td[1]/input[2]")).click();
		driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/ul/li[2]/a")).click();//logout
		
		//Login as John Smith
		driver = login("" + JOHN_SMITH, PW);
		// Check the notifications.
		assertFalse(driver.getPageSource().contains("denied.png"));
		assertTrue(driver.getPageSource().contains("approved.png"));
		assertTrue(driver.findElement(By.xpath("//*[@id=\"notificationArea\"]/div[2]/div/div[2]/ul/li[6]"))
				.getText().contains("1 approved insurance claim."));
		driver.findElement(By.xpath("//*[@id=\"notificationArea\"]/div[2]/div/div[2]/ul/li[6]/a[2]")).click();
		assertEquals("iTrust - View My Bills", driver.getTitle());
		assertEquals("Approved", driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/div/table/tbody/tr[2]/td[4]")).getText());
	}
	
	public void testDenialNotification() throws Exception{
		// Login as John Smith
		driver = login("" + JOHN_SMITH, PW);
		driver.setJavascriptEnabled(false);
		driver.findElement(By.xpath("//*[@id=\"view-menu\"]/ul/li[12]/a")).click();
		assertTrue(driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/div/table/tbody/tr[2]/td[3]")).getText().contains("Shelly Vang"));
		driver.findElement(By.linkText("01/10/2014")).click();
		// Make an insurance claim.
		assertEquals("$150",driver.findElement(By.xpath("//*[@id=\"genInfo\"]/tbody/tr[3]/td[2]")).getText());
		assertEquals("Health Institute Dr. E",driver.findElement(By.xpath("//*[@id=\"genInfo\"]/tbody/tr[7]/td[2]")).getText());
		assertEquals("General Checkup",driver.findElement(By.xpath("//*[@id=\"genInfo\"]/tbody/tr[8]/td[2]")).getText());
		driver = submitPayment(driver, "John Smith", "1234567A01","ABC Insurance", "365 Broad St", "", "Raleigh", "NC", "27606","919-112-8234");
		driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/ul/li[2]/a")).click();//logout
		
		// Login as UAP Mike Jones
		driver = login("" + MIKE_JONES, PW);
		driver.findElement(By.xpath("//*[@id=\"view-menu\"]/ul/li[2]/a")).click();
		driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/table/tbody/tr[2]/td[1]/a")).click();
		//Deny the claim
		driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/form/table/tbody/tr[15]/td[2]/input")).click();
		driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/ul/li[2]/a")).click();//logout
		
		//Login as John Smith
		driver = login("" + JOHN_SMITH, PW);
		//Make sure the icon and the notification are correct.
		assertTrue(driver.getPageSource().contains("denied.png"));
		assertEquals("1 denied insurance claim.", driver.findElement(By.xpath("//*[@id=\"notificationArea\"]/div[2]/div/div[2]/ul/li[5]")).getText());
		driver.findElement(By.xpath("//*[@id=\"notificationArea\"]/div[2]/div/div[2]/ul/li[5]/a[2]")).click();
		assertEquals("iTrust - View My Bills", driver.getTitle());
		assertEquals("Denied", driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/div/table/tbody/tr[2]/td[4]")).getText());
	}
	
	public void testMultiplePatients() throws Exception{
		// Login as John Smith
		driver = login("" + JOHN_SMITH, PW);
		driver.setJavascriptEnabled(false);
		driver.findElement(By.xpath("//*[@id=\"view-menu\"]/ul/li[12]/a")).click();
		assertTrue(driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/div/table/tbody/tr[2]/td[3]")).getText().contains("Shelly Vang"));
		driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/div/table/tbody/tr[2]/td[1]/a")).click();
		// Make an insurance claim.
		assertEquals("$150",driver.findElement(By.xpath("//*[@id=\"genInfo\"]/tbody/tr[3]/td[2]")).getText());
		assertEquals("Health Institute Dr. E",driver.findElement(By.xpath("//*[@id=\"genInfo\"]/tbody/tr[7]/td[2]")).getText());
		assertEquals("General Checkup",driver.findElement(By.xpath("//*[@id=\"genInfo\"]/tbody/tr[8]/td[2]")).getText());
		driver = submitPayment(driver, "John Smith", "1234567A01","ABC Insurance", "365 Broad St", "", "Raleigh", "NC", "27606","919-112-8234");
		driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/ul/li[2]/a")).click();//logout
		
		//Login as Juan Carlos
		driver = login("" + JUAN_CARLOS, PW);
		driver.findElement(By.xpath("//*[@id=\"view-menu\"]/ul/li[12]/a")).click();
		assertTrue(driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/div/table/tbody/tr[2]/td[3]")).getText().contains("Shelly Vang"));
		driver.findElement(By.linkText("02/07/2014")).click();
		assertEquals("$350",driver.findElement(By.xpath("//*[@id=\"genInfo\"]/tbody/tr[3]/td[2]")).getText());
		assertEquals("Facebook Rehab Center",driver.findElement(By.xpath("//*[@id=\"genInfo\"]/tbody/tr[7]/td[2]")).getText());
		assertEquals("Ultrasound",driver.findElement(By.xpath("//*[@id=\"genInfo\"]/tbody/tr[8]/td[2]")).getText());
		//Make an insurance claim
		driver = submitPayment(driver, "Juan Carlos", "9871932F25", "LZA Insurance", "222 Noname Dr", "", "Raleigh", "NC", "27604", "919-222-6579");
		
		//Login as UAP Jane Smith
		driver = login("" + JANE_SMITH, PW);
		//Make sure that there are the proper number of claims and notifications.
		assertEquals("2 Pending Insurance Claims.", driver.findElement(By.xpath("//*[@id=\"notificationArea\"]/div[2]/div/div[2]/ul/li[2]")).getText());
		driver.findElement(By.xpath("//*[@id=\"view-menu\"]/ul/li[2]/a")).click();
		
		int idx = driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/table/tbody")).getText().indexOf(new SimpleDateFormat("MM/dd/YYYY").format(new Date()));
		assertTrue(driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/table/tbody")).getText().substring(0, idx + 11).contains(new SimpleDateFormat("MM/dd/YYYY").format(new Date())));
		assertTrue(driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/table/tbody")).getText().substring(idx + 1).contains(new SimpleDateFormat("MM/dd/YYYY").format(new Date())));
	}
	
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
}
