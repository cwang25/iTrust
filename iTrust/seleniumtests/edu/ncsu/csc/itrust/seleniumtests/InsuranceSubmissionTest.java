package edu.ncsu.csc.itrust.seleniumtests;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class InsuranceSubmissionTest extends iTrustSeleniumTest {

	// MIDs for various users.
	private static final long JOHN_SMITH = 313;
	private static final long MARIA_LOPEZ = 314;
	private static final long JUAN_CARLOS = 315;
	private static final long ALEX_PAUL = 316;

	private static final long MIKE_JONES = 9000000012L;
	private static final long DANIEL_WILLIAMS = 9000000013L;
	private static final long JANE_SMITH = 9000000014L;
	private static final long ROGER_KING = 9000000015L;

	// Passwords
	private static final String PW = "pw";

	private HtmlUnitDriver driver;
	private StringBuffer verificationErrors = new StringBuffer();

	public void setUp() throws Exception {
		gen.clearAllTables();
		gen.hospitals();
		gen.hospitals1();
		gen.hcp0();
		gen.uc51();
		gen.uc60();
		driver = new HtmlUnitDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
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
	
	public void testUAPApproval() throws Exception{
		//Log in as John Smith
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
		
		//Login as UAP Mike Jones
		driver = login("" + MIKE_JONES, PW);
		driver.findElement(By.xpath("//*[@id=\"view-menu\"]/ul/li[2]/a")).click();
		driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/table/tbody/tr[2]/td[1]/a")).click();
		//Approve the claim
		driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/form/table/tbody/tr[15]/td[1]/input[2]")).click();
		assertLogged(TransactionType.UAP_INITIAL_APPROVAL, MIKE_JONES, MIKE_JONES, "");
		driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/ul/li[2]/a")).click();//logout
		
		//Make sure the bill is approved.
		driver = login("" + JOHN_SMITH, PW);
		driver.findElement(By.xpath("//*[@id=\"view-menu\"]/ul/li[12]/a")).click();
		assertEquals("Approved", driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/div/table/tbody/tr[2]/td[4]")).getText());
	}
	
	public void testUAPDenialThenApproval() throws Exception {
		for (int i = 0; i < 2; i++) {
			// Login as Maria Lopez
			driver = login("" + MARIA_LOPEZ, PW);
			driver.setJavascriptEnabled(false);
			driver.findElement(By.xpath("//*[@id=\"view-menu\"]/ul/li[12]/a")).click();
			assertEquals(
					"Kelly Doctor",
					driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/div/table/tbody/tr[2]/td[3]")).getText());
			// Pay the bill
			driver.findElement(By.linkText("02/17/2014")).click();
			// Make an insurance claim.
			assertEquals("$250",driver.findElement(By.xpath("//*[@id=\"genInfo\"]/tbody/tr[3]/td[2]")).getText());
			assertEquals("Le Awesome Hospital",driver.findElement(By.xpath("//*[@id=\"genInfo\"]/tbody/tr[7]/td[2]")).getText());
			assertEquals("Mammogram",driver.findElement(By.xpath("//*[@id=\"genInfo\"]/tbody/tr[8]/td[2]")).getText());
			driver = submitPayment(driver, "Maria Lopez", "4447157D13", "GMX Insurance", "113 Seaboard Ave", "", "Raleigh", "NC", "27604", "919-468-1537");
			driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/ul/li[2]/a")).click();//logout
			
			// Login as UAP Daniel Williams
			driver = login("" + DANIEL_WILLIAMS, PW);
		    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
		    driver.findElement(By.linkText("View Insurance Claims")).click();
			// Deny / approve the claim
		    driver.findElement(By.linkText(new SimpleDateFormat("MM/dd/YYYY").format(new Date()))).click();
			if(i == 0){
				driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/form/table/tbody/tr[15]/td[2]/input")).click();
				assertLogged(TransactionType.UAP_INITIAL_DENIAL, DANIEL_WILLIAMS, DANIEL_WILLIAMS, "");
			}
			else if (i == 1){
				driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/form/table/tbody/tr[15]/td[1]/input[2]")).click();
				assertLogged(TransactionType.UAP_SECOND_APPROVAL, DANIEL_WILLIAMS, DANIEL_WILLIAMS, "");
			}
			
			driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/ul/li[2]/a")).click(); // logout
		}
		
		// Make sure the bill is approved
		driver = login("" + MARIA_LOPEZ, PW);
		driver.findElement(By.xpath("//*[@id=\"view-menu\"]/ul/li[12]/a")).click();
		assertEquals("Approved", driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/div/table/tbody/tr[2]/td[4]")).getText());
	}
	
	public void testTwoUAPDenials() throws Exception {
		for (int i = 0; i < 2; i++) {
			// login as Juan Carlos.
			driver = login("" + JUAN_CARLOS, PW);
			driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
			driver.findElement(By.linkText("My Bills")).click();
			assertTrue(driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/div/table/tbody")).getText().contains("Shelly Vang"));
			// Pay the bill with insurance.
			driver.findElement(By.linkText("02/07/2014")).click();
			assertTrue(driver.findElement(By.xpath("//*[@id=\"genInfo\"]/tbody")).getText().contains("$350"));
			assertTrue(driver.findElement(By.xpath("//*[@id=\"genInfo\"]/tbody")).getText().contains("Facebook Rehab Center"));
			assertTrue(driver.findElement(By.xpath("//*[@id=\"genInfo\"]/tbody")).getText().contains("Ultrasound"));
			submitPayment(driver, "Juan Carlos", "9871932F25", "LZA Insurance", "222 Noname Dr", "", "Raleigh", "NC", "27604", "919-222-6579");
		    driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/ul/li[2]/a")).click();
		    
			// Login as Jane Smith
			driver = login("" + JANE_SMITH, PW);
		    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
		    driver.findElement(By.linkText("View Insurance Claims")).click();
		    driver.findElement(By.linkText(new SimpleDateFormat("MM/dd/YYYY").format(new Date()))).click();
			if (i == 0) {
				driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/form/table/tbody/tr[15]/td[2]/input")).click();
				assertLogged(TransactionType.UAP_INITIAL_DENIAL, JANE_SMITH, JANE_SMITH, "");
			} else if (i == 1) {
				driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/form/table/tbody/tr[15]/td[2]/input")).click();
				assertLogged(TransactionType.UAP_SECOND_DENIAL, JANE_SMITH, JANE_SMITH, "");
			}
			
		    driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/ul/li[2]/a")).click(); // logout
		}
		
		// Login as Juan Carlos
		driver = login("" + JUAN_CARLOS, PW);
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("My Bills")).click();
		// Make sure that it is denied and you cant pay with insurance.
		assertTrue(driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/div/table/tbody")).getText().contains("Denied"));
		driver.findElement(By.linkText("02/07/2014")).click();
		assertFalse(driver.findElement(By.xpath("//*[@id=\"typeTable\"]/tbody")).getText().contains("Insurance"));
		
		// Pay with a credit card.
		driver.findElement(By.id("CC")).click();
	    driver.findElement(By.name("ccNumber")).clear();
	    driver.findElement(By.name("ccNumber")).sendKeys("4539592576502361");
	    driver.findElement(By.name("cvv")).clear();
	    driver.findElement(By.name("cvv")).sendKeys("007");
	    new Select(driver.findElement(By.name("ccType"))).selectByVisibleText("Visa");
	    driver.findElement(By.name("ccHolder")).clear();
	    driver.findElement(By.name("ccHolder")).sendKeys("Juan Carlos");
	    driver.findElement(By.name("billAddress")).clear();
	    driver.findElement(By.name("billAddress")).sendKeys("412 Conifer Dr, Raleigh, NC 27606");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    
	    assertTrue(driver.findElement(By.xpath("//*[@id=\"genInfo\"]/tbody")).getText().contains("Payment Information"));
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("My Bills")).click();
		assertTrue(driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/div/table/tbody")).getText().contains("Submitted"));
	}
	
	public void testUAPDenialThenCC() throws Exception{
		// Login as Alex Paul
		driver = login("" + ALEX_PAUL, PW);
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("My Bills")).click();
		assertTrue(driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/div/table/tbody")).getText().contains("Kelly Doctor"));
		// Pay the bill with insurance.
		driver.findElement(By.linkText("01/25/2014")).click();
		assertTrue(driver.findElement(By.xpath("//*[@id=\"genInfo\"]/tbody")).getText().contains("$250"));
		assertTrue(driver.findElement(By.xpath("//*[@id=\"genInfo\"]/tbody")).getText().contains("Central Hospital"));
		assertTrue(driver.findElement(By.xpath("//*[@id=\"genInfo\"]/tbody")).getText().contains("Colonoscopy"));
		submitPayment(driver, "Alex Paul", "7772510K99", "YYY Insurance", "525 Grumpy Dr", "", "Raleigh", "NC", "21337", "919-555-9876");
	    driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/ul/li[2]/a")).click();
	    
	    // Login as UAP Roger King
	    driver = login("" + ROGER_KING, PW);
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("View Insurance Claims")).click();
	    driver.findElement(By.linkText(new SimpleDateFormat("MM/dd/YYYY").format(new Date()))).click();
	    driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/form/table/tbody/tr[15]/td[2]/input")).click();
	    driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/ul/li[2]/a")).click(); // logout

		// Login as Alex Paul
	    driver = login("" + ALEX_PAUL, PW);
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("My Bills")).click();
		// See the claim is denied, and pay it with a credit card.
		assertTrue(driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/div/table/tbody/tr[2]/td[4]")).getText().contains("Denied"));
		driver.findElement(By.linkText("01/25/2014")).click();
		assertTrue(driver.findElement(By.xpath("//*[@id=\"typeTable\"]/tbody/tr[2]/td[2]")).getText().contains("Insurance"));
		
		// Pay with a credit card.
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
	    
	    assertTrue(driver.findElement(By.xpath("//*[@id=\"genInfo\"]/tbody")).getText().contains("Payment Information"));
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("My Bills")).click();
		assertTrue(driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/div/table/tbody")).getText().contains("Submitted"));
	}
	
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
}
