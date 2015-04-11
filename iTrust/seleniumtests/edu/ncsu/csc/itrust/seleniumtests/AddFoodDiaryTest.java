package edu.ncsu.csc.itrust.seleniumtests;

import org.junit.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

public class AddFoodDiaryTest extends iTrustSeleniumTest {
	private WebDriver driver;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
		driver = new HtmlUnitDriver();
	}

	/**
	 * Acceptance test for HW3P1
	 * Login as Derek Morgan (91, pw)
	 */
	public void testAddFoodDiaryEntryWithEmptyDiary() throws Exception {
		driver.get("http://localhost:8080/iTrust/auth/forwardUser.jsp");
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("91");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.findElement(By.cssSelector("div.panel-heading")).click();
		driver.findElement(By.linkText("Food Diary Entry")).click();
		driver.findElement(By.name("date")).clear();
		driver.findElement(By.name("date")).sendKeys("02/04/2015");
		new Select(driver.findElement(By.name("typeOfMeal"))).selectByVisibleText("Dinner");
		driver.findElement(By.name("mealName")).clear();
		driver.findElement(By.name("mealName")).sendKeys("Fruity Pebbles");
		driver.findElement(By.name("numServings")).clear();
		driver.findElement(By.name("numServings")).sendKeys("7");
		driver.findElement(By.name("numCalories")).clear();
		driver.findElement(By.name("numCalories")).sendKeys("110");
		driver.findElement(By.name("gramsFat")).clear();
		driver.findElement(By.name("gramsFat")).sendKeys("1");
		driver.findElement(By.name("mgSodium")).clear();
		driver.findElement(By.name("mgSodium")).sendKeys("170");
		driver.findElement(By.name("gramsCarbs")).clear();
		driver.findElement(By.name("gramsCarbs")).sendKeys("24");
		driver.findElement(By.name("gramsSugar")).clear();
		driver.findElement(By.name("gramsSugar")).sendKeys("0");
		driver.findElement(By.name("gramsFiber")).clear();
		driver.findElement(By.name("gramsFiber")).sendKeys("11");
		driver.findElement(By.name("gramsProtein")).clear();
		driver.findElement(By.name("gramsProtein")).sendKeys("1");
		driver.findElement(By.name("action")).click();

		assertEquals(driver.getTitle(), "iTrust - View My Food Diary");
		assertTrue(driver.getPageSource().contains("Fruity Pebbles"));


	}
	/**
	 * Login as Jennifer Jaraeu (90, pw)
	 * @throws Exception
	 */
	public void testAddFoodDiaryEntryWithNonEmptyDiary() throws Exception {
		driver.get("http://localhost:8080/iTrust/auth/forwardUser.jsp");
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("90");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.findElement(By.cssSelector("div.panel-heading")).click();
		driver.findElement(By.linkText("Food Diary Entry")).click();
		driver.findElement(By.name("date")).clear();
		driver.findElement(By.name("date")).sendKeys("11/12/2014");
		new Select(driver.findElement(By.name("typeOfMeal"))).selectByVisibleText("Snack");
		driver.findElement(By.name("mealName")).clear();
		driver.findElement(By.name("mealName")).sendKeys("Cookie Dough Ice Cream");
		driver.findElement(By.name("numServings")).clear();
		driver.findElement(By.name("numServings")).sendKeys(".5");
		driver.findElement(By.name("numCalories")).clear();
		driver.findElement(By.name("numCalories")).sendKeys("160");
		driver.findElement(By.name("gramsFat")).clear();
		driver.findElement(By.name("gramsFat")).sendKeys("8");
		driver.findElement(By.name("mgSodium")).clear();
		driver.findElement(By.name("mgSodium")).sendKeys("45");
		driver.findElement(By.name("gramsCarbs")).clear();
		driver.findElement(By.name("gramsCarbs")).sendKeys("21");
		driver.findElement(By.name("gramsSugar")).clear();
		driver.findElement(By.name("gramsSugar")).sendKeys("0");
		driver.findElement(By.name("gramsFiber")).clear();
		driver.findElement(By.name("gramsFiber")).sendKeys("16");
		driver.findElement(By.name("gramsProtein")).clear();
		driver.findElement(By.name("gramsProtein")).sendKeys("2");
		driver.findElement(By.name("action")).click();

		assertEquals(driver.getTitle(), "iTrust - View My Food Diary");
		assertTrue(driver.getPageSource().contains("Cookie Dough Ice Cream"));
		assertTrue(driver.getPageSource().contains("9/30/2012"));


	}
	/**
	 * Login as 91, pw
	 * @throws Exception
	 */
	public void testFutureDateInFoodDiaryEntry() throws Exception {
		driver.get("http://localhost:8080/iTrust/auth/forwardUser.jsp");
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("91");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("Food Diary Entry")).click();
		driver.findElement(By.name("date")).clear();
		driver.findElement(By.name("date")).sendKeys("02/04/3015");
		new Select(driver.findElement(By.name("typeOfMeal"))).selectByVisibleText("Dinner");
		driver.findElement(By.name("mealName")).clear();
		driver.findElement(By.name("mealName")).sendKeys("Fruity Pebbles");
		driver.findElement(By.name("numServings")).clear();
		driver.findElement(By.name("numServings")).sendKeys("7");
		driver.findElement(By.name("numCalories")).clear();
		driver.findElement(By.name("numCalories")).sendKeys("110");
		driver.findElement(By.name("gramsFat")).clear();
		driver.findElement(By.name("gramsFat")).sendKeys("1");
		driver.findElement(By.name("mgSodium")).clear();
		driver.findElement(By.name("mgSodium")).sendKeys("170");
		driver.findElement(By.name("gramsCarbs")).clear();
		driver.findElement(By.name("gramsCarbs")).sendKeys("24");
		driver.findElement(By.name("gramsSugar")).clear();
		driver.findElement(By.name("gramsSugar")).sendKeys("0");
		driver.findElement(By.name("gramsFiber")).clear();
		driver.findElement(By.name("gramsFiber")).sendKeys("11");
		driver.findElement(By.name("gramsProtein")).clear();
		driver.findElement(By.name("gramsProtein")).sendKeys("1");
		driver.findElement(By.name("action")).click();

		assertEquals(driver.getTitle(), "iTrust - Add Food Diary Entry");
		assertTrue(driver.getPageSource().contains("Date can not be from the future!"));
	}


	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
}
