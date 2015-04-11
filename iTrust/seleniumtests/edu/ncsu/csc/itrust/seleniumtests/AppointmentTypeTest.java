package edu.ncsu.csc.itrust.seleniumtests;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class AppointmentTypeTest extends iTrustSeleniumTest{

	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	
	public void testAddAppointmentType() throws Exception {
		WebDriver driver = login("9000000001", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
		
		WebElement element;
		List<WebElement> elements;
		
		//go to the Edit Appointment types page
		element = driver.findElement(By.linkText("Edit Appointment Types"));
		element.click();
		assertEquals("iTrust - Maintain Appointment Types", driver.getTitle());
		assertLogged(TransactionType.APPOINTMENT_TYPE_VIEW, 9000000001L, 0L, "");
		
		//fill and submit form
		element = driver.findElement(By.name("name"));
		element.sendKeys("Immunization");
		element = driver.findElement(By.name("duration"));
		element.sendKeys("30");
		element.submit();
		assertLogged(TransactionType.APPOINTMENT_TYPE_ADD, 9000000001L, 0L, "");
		
		//make sure added type is in table
		elements = driver.findElements(By.xpath("//*[@id='mainForm']/table[2]/tbody/tr[9]/td"));
		assertTrue(elements.get(0).getText().contains("Immunization"));
		assertTrue(elements.get(1).getText().contains("30"));
		assertLogged(TransactionType.APPOINTMENT_TYPE_VIEW, 9000000001L, 0L, "");
	}
	
	public void testEditAppointmentTypeDuration() throws Exception {
		WebDriver driver = login("9000000001", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
		
		WebElement element;
		List<WebElement> elements;
		
		//go to the Edit Appointment types page
		element = driver.findElement(By.linkText("Edit Appointment Types"));
		element.click();
		assertEquals("iTrust - Maintain Appointment Types", driver.getTitle());
		assertLogged(TransactionType.APPOINTMENT_TYPE_VIEW, 9000000001L, 0L, "");
		
		//fill and submit form
		element = driver.findElement(By.name("name"));
		element.sendKeys("Physical");
		element = driver.findElement(By.name("duration"));
		element.sendKeys("45");
		element = driver.findElement(By.xpath("//*[@id='update']"));
		element.click();
		
		assertLogged(TransactionType.APPOINTMENT_TYPE_EDIT, 9000000001L, 0L, "");
		
		//make sure edited type is in table
		assertTrue(driver.getPageSource().contains("Success: Physical - Duration: 45 updated"));
		elements = driver.findElements(By.xpath("//*[@id='mainForm']/table[2]/tbody/tr[5]/td"));
		assertTrue(elements.get(0).getText().contains("Physical"));
		assertTrue(elements.get(1).getText().contains("45"));
		assertLogged(TransactionType.APPOINTMENT_TYPE_VIEW, 9000000001L, 0L, "");
	}
	
	public void testEditAppointmentTypeDurationStringInput() throws Exception {
		WebDriver driver = login("9000000001", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
		
		WebElement element;
		
		//go to the Edit Appointment types page
		element = driver.findElement(By.linkText("Edit Appointment Types"));
		element.click();
		assertEquals("iTrust - Maintain Appointment Types", driver.getTitle());
		assertLogged(TransactionType.APPOINTMENT_TYPE_VIEW, 9000000001L, 0L, "");
		
		//fill and submit form
		element = driver.findElement(By.name("name"));
		element.sendKeys("Physical");
		element = driver.findElement(By.name("duration"));
		element.sendKeys("foo");
		element = driver.findElement(By.xpath("//*[@id='update']"));
		element.click();
		
		assertNotLogged(TransactionType.APPOINTMENT_TYPE_EDIT, 9000000001L, 0L, "");
		
		//make sure added type is in table
		assertTrue(driver.getPageSource().contains("Error: Physical - Duration: must be an integer value."));
		assertTrue(driver.getCurrentUrl().contains("/auth/admin/editApptType.jsp"));
		assertLogged(TransactionType.APPOINTMENT_TYPE_VIEW, 9000000001L, 0L, "");
	}
	
	public void testScheduleAppointment() throws Exception {
		WebDriver driver = login("9000000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		WebElement element;
		
		//go to the Schedule Appointment page
		element = driver.findElement(By.linkText("Schedule Appointment"));
		element.click();
		
		//use the old search to go to the patients page
		element = driver.findElement(By.name("UID_PATIENTID"));
		element.sendKeys("1");
		element = driver.findElement(By.id("mainForm"));
		element.submit();
		
		element = driver.findElement(By.id("mainForm"));
		int year = Calendar.getInstance().get(Calendar.YEAR) + 1;
		String scheduledDate = "11/11/" + year;
		
		//fill out form and submit
		Select select = new Select (driver.findElement(By.name("apptType")));
		select.selectByValue("General Checkup");
		driver.findElement(By.name("schedDate")).clear();
		driver.findElement(By.name("schedDate")).sendKeys(scheduledDate);
		select = new Select (driver.findElement(By.name("time1")));
		select.selectByValue("01");
		select = new Select (driver.findElement(By.name("time2")));
		select.selectByValue("00");
		select = new Select (driver.findElement(By.name("time3")));
		select.selectByValue("PM");
		driver.findElement(By.name("comment")).sendKeys("This is the next checkup for your blood pressure medication.");
		driver.findElement(By.name("schedDate")).submit();
		assertLogged(TransactionType.APPOINTMENT_ADD, 9000000000L, 1L, "");
		
		//check to confirm appointment was added
		assertEquals("iTrust - Schedule an Appointment",driver.getTitle());
		driver.findElement(By.linkText("View My Appointments")).click();
		assertEquals(scheduledDate + " 01:00 PM",driver.findElement(By.xpath("//*[@id='iTrustContent']/div/table/tbody/tr[13]/td[3]")).getText());
		assertLogged(TransactionType.APPOINTMENT_ALL_VIEW, 9000000000L, 0L, "");
		
	}
	
	public void testPatientViewUpcomingAppointments() throws Exception {
		gen.clearAppointments();
		gen.appointmentCase1();
		
		WebDriver driver = login("2", "pw");
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		
		WebElement element;
		
		//go to the View my appointments page
		element = driver.findElement(By.linkText("View My Appointments"));
		element.click();
		
		// Create timestamp
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Timestamp time = new Timestamp(new Date().getTime());
		
		//Check Table
		//Row 1
		assertTrue(driver.findElement(By.xpath("//*[@id='iTrustContent']/div/table/tbody/tr[2]/td[1]")).getText().contains("Kelly"));
		assertEquals(driver.findElement(By.xpath("//*[@id='iTrustContent']/div/table/tbody/tr[2]/td[2]")).getText(),"General Checkup");
		Timestamp time1 = new Timestamp(time.getTime()+(14*24*60*60*1000));
		String dt1 = dateFormat.format(new Date(time1.getTime()));
		assertTrue(driver.findElement(By.xpath("//*[@id='iTrustContent']/div/table/tbody/tr[2]/td[3]")).getText().contains(dt1+" 10:30 AM"));
		assertEquals(driver.findElement(By.xpath("//*[@id='iTrustContent']/div/table/tbody/tr[2]/td[4]")).getText(),"45 minutes");
		assertTrue(driver.findElement(By.xpath("//*[@id='iTrustContent']/div/table/tbody/tr[2]/td[5]")).getText().contains("Read"));
		
	}
	
	public void testHcpViewUpcomingAppointments() throws Exception {
		// Create DB for this test case
		gen.clearAppointments();
		gen.appointmentCase2();
		
		WebDriver driver = login("9000000000", "pw");

		WebElement element;
		//go to the View my appointments page
		element = driver.findElement(By.linkText("View My Appointments"));
		element.click();

		//Check Table
		//Row 1
		assertTrue(driver.findElement(By.xpath("//*[@id='iTrustContent']/div/table/tbody/tr[2]/td[1]")).getText().contains("Random Person"));
		assertTrue(driver.findElement(By.xpath("//*[@id='iTrustContent']/div/table/tbody/tr[2]/td[2]")).getText().contains("Consultation"));
		assertEquals(driver.findElement(By.xpath("//*[@id='iTrustContent']/div/table/tbody/tr[2]/td[4]")).getText(),"30 minutes");
		assertTrue(driver.findElement(By.xpath("//*[@id='iTrustContent']/div/table/tbody/tr[2]/td[5]")).getText().contains("No"));
		
		
	}
	
	public void testAddAppointmentTypeLengthZero() throws Exception {
		WebDriver driver = login("9000000001", "pw");
		WebElement element;
		
		//go to the Edit Appointment types page
		element = driver.findElement(By.linkText("Edit Appointment Types"));
		element.click();
		assertEquals("iTrust - Maintain Appointment Types", driver.getTitle());
		assertLogged(TransactionType.APPOINTMENT_TYPE_VIEW, 9000000001L, 0L, "");
		
		//fill and submit form
		element = driver.findElement(By.name("name"));
		element.sendKeys("Immunization");
		element = driver.findElement(By.name("duration"));
		element.sendKeys("0");
		element.submit();
		
		assertTrue(driver.getPageSource().contains("This form has not been validated correctly"));
	}
}
