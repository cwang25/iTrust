package edu.ncsu.csc.itrust.seleniumtests;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.enums.TransactionType;

/*
 * This is the Selenium equivalent of DependentsTest.java
 */
public class DependentsSTest extends iTrustSeleniumTest{
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.standardData();
	}
	
	/**
	 * Test adding a new patient as a dependent
	 * @throws Exception
	 *
	 */
	public void testAddDependentPatient() throws Exception {
		// Login
		login("9000000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[6]/div/h2")).click();
	    driver.findElement(By.linkText("Patient")).click();
	    driver.findElement(By.name("firstName")).clear();
	    driver.findElement(By.name("firstName")).sendKeys("Bob");
	    driver.findElement(By.name("lastName")).clear();
	    driver.findElement(By.name("lastName")).sendKeys("Marley");
	    driver.findElement(By.name("email")).clear();
	    driver.findElement(By.name("email")).sendKeys("bmarley@test.com");
	    driver.findElement(By.id("isDependent")).click();
	    driver.findElement(By.id("repId")).clear();
	    driver.findElement(By.id("repId")).sendKeys("102");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertTrue(driver.getPageSource().contains("successfully added"));
		long mid = Long.parseLong(driver.findElement(By.xpath("//*[@id='iTrustContent']/div[1]/table/tbody/tr[2]/td[2]")).getText());
		assertLogged(TransactionType.HCP_CREATED_DEPENDENT_PATIENT, 9000000000L, mid, "");
	}
	
	/**
	 * Tests adding a dependent / representative relationship to existing patients
	 * @throws Exception
	 */
	public void testEditDependentRepresentative() throws Exception {
		//Log in as Kelly Doctor
		login("9000000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		//Navigate to the edit personal representatives page
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("Representatives")).click();

		//Search for Fulton Gray (MID 103)
		driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("103");
	    //waitFor(1);
	    clickOnJavascriptElement(By.xpath("//input[@value='103' and @type='button']"));
		assertTrue(driver.getTitle().equals("iTrust - Manage Representatives"));
		
		//Add Caldwell Hudson as a representative
		driver.setJavascriptEnabled(false);
		driver.findElement(By.name("UID_repID")).sendKeys("102");
		driver.findElement(By.name("action")).submit();
		driver.setJavascriptEnabled(true);
		assertTrue(driver.getPageSource().contains("Caldwell Hudson"));
	}

	/**
	 * Tests that a dependent cannot login
	 * @throws Exception
	 */
	public void testDependentLogin() throws Exception {
		//Disables CSS warning and error logs.
		//Thanks to http://stackoverflow.com/questions/3600557/turning-htmlunit-warnings-off
		LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF); 

		//Load UC58 data
		gen.uc58();
		driver.get(ADDRESS);
		driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("580");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

		//If user is not on login page, then user logged in successfully.
		assertTrue(driver.getTitle().equals("iTrust - Login"));
	}
	
	/**
	 * Tests that a list of a dependent's representatives is displayed to them
	 * @throws Exception
	 */
	public void testListRepresentatives() throws Exception {
		//Load UC58 data
		gen.uc58();
		
		//Log in as Kelly Doctor
		login("9000000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		//Navigate to the edit personal representatives page
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("Representatives")).click();
		//Search for Morgan Freeman (MID 581)
		driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("581");
	    //waitFor(1);
	    clickOnJavascriptElement(By.xpath("//input[@value='581' and @type='button']"));
	    
		assertTrue(driver.getTitle().equals("iTrust - Manage Representatives"));


		assertEquals(driver.findElement(By.xpath(".//*[@id='iTrustContent']/form/table/tbody/tr[3]/td[1]")).getText(), "Bob Marley");
		
		
	}
	
	/**
	 * Tests to make sure representatives can't be dependents themselves
	 * @throws Exception
	 */
	public void testRepresentativeNotDependent() throws Exception {
		//Load UC58 data
		gen.uc58();
		

		//Log in as Kelly Doctor
		login("9000000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		//Navigate to the edit personal representatives page
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("Representatives")).click();
		
		//Search for Bob Marley (MID 580)
		driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("580");
	    //waitFor(1);
	    clickOnJavascriptElement(By.xpath("//input[@value='580' and @type='button']"));
		assertTrue(driver.getTitle().equals("iTrust - Manage Representatives"));
		assertTrue(driver.getPageSource().contains("Bob Marley is a dependent."));
		assertTrue(driver.getPageSource().contains("Dependent users cannot represent others."));

	}
	
	public void testRequestRecordsForDependent() throws Exception {
		//Load uc59 data
		gen.uc59();


		//Log in as Bob Ross (MID 750)
		login("750", "pw");
		assertLogged(TransactionType.HOME_VIEW, 750L, 0L, "");

		//Navigate to records for Billy Ross
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("Request Records Release")).click();

		//Submit request for dependent Billy Ross	    
		Select oSelection = new Select(driver.findElement(By.name("selectedPatient")));
        oSelection.selectByVisibleText("Billy Ross");
		driver.findElement(By.id("submitDep")).click();
        assertTrue(driver.getPageSource().contains("Billy Ross's Requested Records Release History"));
        
        //Submit new request for Billy Ross
		driver.findElement(By.id("submitRequest")).submit();
		assertTrue(driver.getTitle().equals("iTrust - Records Release Request"));

		//Fill in medical records release form
		oSelection = new Select(driver.findElement(By.name("releaseHospital")));
        oSelection.selectByVisibleText("Health Institute Dr. E");
		driver.findElement(By.name("recFirstName")).sendKeys("Benedict");
		driver.findElement(By.name("recLastName")).sendKeys("Cucumberpatch");
		driver.findElement(By.name("recPhone")).sendKeys("555-666-7777");
		driver.findElement(By.name("recEmail")).sendKeys("a@b.com");
		driver.findElement(By.name("recHospitalName")).sendKeys("Rex Hospital");
		driver.findElement(By.name("recHospitalAddress1")).sendKeys("123 Broad St.");
		driver.findElement(By.name("recHospitalAddress2")).sendKeys(" ");
		driver.findElement(By.name("recHospitalCity")).sendKeys("Cary");
		driver.findElement(By.name("recHospitalState")).sendKeys("NC");
		driver.findElement(By.name("recHospitalZip")).sendKeys("27164");
		driver.findElement(By.name("releaseJustification")).sendKeys("Moving");
		driver.findElement(By.name("verifyForm")).click();
		driver.findElement(By.name("digitalSig")).sendKeys("Bob Ross");
		driver.findElement(By.id("submit")).click();
		
        assertTrue(driver.getPageSource().contains("Request successfully sent"));
        
		//Check to see that the dependent's record is added
        driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
		driver.findElement(By.linkText("Request Records Release")).click();
		
		//Submit request for dependent Billy Ross
		oSelection = new Select(driver.findElement(By.name("selectedPatient")));
        oSelection.selectByVisibleText("Billy Ross");
		driver.findElement(By.id("submitDep")).click();
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String sdate = sdf.format(date);
        assertTrue(driver.getPageSource().contains(sdate));
		
		
	}
	
	public void testRequestRecordsWithDependentSignature() throws Exception {
		//Load uc59 data
		gen.uc59();


		login("750", "pw");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("Request Records Release")).click();
	    new Select(driver.findElement(By.name("selectedPatient"))).selectByVisibleText("Billy Ross");
	    driver.findElement(By.id("submitDep")).click();
	    driver.findElement(By.id("submitReq")).click();
	    new Select(driver.findElement(By.name("releaseHospital"))).selectByVisibleText("Health Institute Dr. E");
	    driver.findElement(By.id("recFirstName")).clear();
	    driver.findElement(By.id("recFirstName")).sendKeys("Benedict");
	    driver.findElement(By.id("recLastName")).clear();
	    driver.findElement(By.id("recLastName")).sendKeys("Cucumberpatch");
	    driver.findElement(By.id("recPhone")).clear();
	    driver.findElement(By.id("recPhone")).sendKeys("555-666-7777");
	    driver.findElement(By.id("recEmail")).clear();
	    driver.findElement(By.id("recEmail")).sendKeys("a@b.com");
	    driver.findElement(By.id("recHospitalName")).clear();
	    driver.findElement(By.id("recHospitalName")).sendKeys("Rex Hospital");
	    driver.findElement(By.id("recHospitalAddress1")).clear();
	    driver.findElement(By.id("recHospitalAddress1")).sendKeys("123 Broad St.");
	    driver.findElement(By.id("recHospitalAddress2")).clear();
	    driver.findElement(By.id("recHospitalAddress2")).sendKeys("");
	    driver.findElement(By.id("recHospitalCity")).clear();
	    driver.findElement(By.id("recHospitalCity")).sendKeys("Cary");
	    driver.findElement(By.id("recHospitalState")).clear();
	    driver.findElement(By.id("recHospitalState")).sendKeys("NC");
	    driver.findElement(By.id("recHospitalZip")).clear();
	    driver.findElement(By.id("recHospitalZip")).sendKeys("27164");
	    driver.findElement(By.id("releaseJustification")).clear();
	    driver.findElement(By.id("releaseJustification")).sendKeys("Moving");
	    driver.findElement(By.id("verifyForm")).click();
	    driver.findElement(By.id("digitalSig")).clear();
	    driver.findElement(By.id("digitalSig")).sendKeys("illy Ross");
	    driver.findElement(By.id("submit")).click();

		assertTrue(driver.getTitle().equals("iTrust - Records Release Request"));
        System.out.println(driver.getPageSource());
		assertTrue(driver.getPageSource().contains("Error"));
        
        
	}
	
	public void testRequestRecordsForNotRepresentedDependent() throws Exception {
		//Load uc59 data
		gen.uc59();
		
		//Log in as Kelly Doctor 
		login("9000000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("Representatives")).click();
		
		//Search for Bob Ross (MID 750)
		driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("750");
	    //waitFor(1);
	    this.clickOnJavascriptElement(By.xpath("//input[@value='750' and @type='button']"));
		assertTrue(driver.getTitle().equals("iTrust - Manage Representatives"));
		
		
		driver.findElement(By.linkText("Remove")).click();
		logout();

		//Log in as Bob Ross 
		login("750", "pw");
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("Request Records Release")).click();
		
		try{
			//Submit request for dependent Billy Ross	    
			Select oSelection = new Select(driver.findElement(By.name("selectedPatient")));
	        oSelection.selectByVisibleText("Billy Ross");
			driver.findElement(By.id("submitDep")).click();

		} catch (Exception e) {

			assertTrue(driver.getTitle().equals("iTrust - Records Release Request History"));
		}
	}
	
	public void testViewRequestedRecordsForDependent() throws Exception {
		gen.uc59();
		
		//Log in as Bob Ross (MID 750)
		login("750", "pw");
		assertLogged(TransactionType.HOME_VIEW, 750L, 0L, "");		

		//Request records
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("Request Records Release")).click();
		//Submit request for dependent Billy Ross	    
		Select oSelection = new Select(driver.findElement(By.name("selectedPatient")));
        oSelection.selectByVisibleText("Billy Ross");
		driver.findElement(By.id("submitDep")).click();
        assertTrue(driver.getPageSource().contains("Billy Ross's Requested Records Release History"));
        

		//View medical records release form
		driver.findElement(By.xpath("//*[@id='requestHistory']/tbody/tr[2]/td[4]/a")).click();
		//So the Httpunit test version of this is actually wrong
		//after this point because they ended up clicking
		//"View My Records" link rather than "View" link.
		assertTrue(driver.getTitle().equals("iTrust - Records Release Request"));
		assertTrue(driver.getPageSource().contains("Approved"));
		assertTrue(driver.getPageSource().contains("Patient name:"));
		assertTrue(driver.getPageSource().contains("Billy Ross"));
	}	
	public void testApproveRecordsRequestForDependent() throws Exception {
		gen.uc59();

		//Log in as Kelly Doctor 
		login("9000000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		//Request records
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("Records Release Requests")).click();
		

		//Approve the most recent record
		driver.findElement(By.linkText("View")).click();
		assertTrue(driver.getTitle().equals("iTrust - Records Release Request"));
		driver.findElement(By.id("Approve")).submit();

		//View medical records release form
		assertTrue(driver.getTitle().equals("iTrust - Records Release Request"));
		assertTrue(driver.getPageSource().contains("Approved"));
	}
	

}
