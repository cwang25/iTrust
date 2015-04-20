package edu.ncsu.csc.itrust.seleniumtests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.Select;

public class PersonalHealthRecordsUseCaseTest extends iTrustSeleniumTest{

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	
	  public void testEditPatient() throws Exception {
		    driver.get(ADDRESS);
		    driver.findElement(By.id("j_username")).clear();
		    driver.findElement(By.id("j_username")).sendKeys("9000000000");
		    driver.findElement(By.id("j_password")).clear();
		    driver.findElement(By.id("j_password")).sendKeys("pw");
		    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		    
		    driver.findElement(By.cssSelector("div.panel-heading")).click();
		    driver.findElement(By.linkText("PHR Information")).click();
		    
		    driver.findElement(By.id("searchBox")).clear();
		    driver.findElement(By.id("searchBox")).sendKeys("2");
		    try {
		      assertEquals("Andy", driver.findElement(By.xpath("//div[@id='searchTarget']/table/tbody/tr[2]/td[2]")).getText());
		    } catch (Error e) {
		      verificationErrors.append(e.toString());
		    }
	  }
	  
	  public void testInvalidPatientDates() throws Exception {
			login("9000000000", "pw");
			driver.findElement(By.cssSelector("div.panel-heading")).click();
			driver.findElement(By.linkText("Patient Information")).click();
			driver.findElement(By.id("searchBox")).clear();
			driver.findElement(By.id("searchBox")).sendKeys("2");
			clickOnJavascriptElement(By.xpath("//input[@value='2' and @type='button']"));
			driver.findElement(By.name("dateOfDeathStr")).clear();
			driver.findElement(By.name("dateOfDeathStr")).sendKeys("01/03/2050");
			driver.findElement(By.name("action")).click();
			assertTrue(pageContains("This form has not been validated correctly. The following field are not properly filled in: [Death date cannot be in the future!]"));
	  }
	  
	  public void testInvalidPatientBirthDates() throws Exception {
		  driver.get(ADDRESS);
		    try {
		        assertEquals("", driver.findElement(By.id("j_username")).getAttribute("value"));
		      } catch (Error e) {
		        verificationErrors.append(e.toString());
		      }
		    driver.findElement(By.id("j_username")).clear();
		    driver.findElement(By.id("j_username")).sendKeys("9000000000");
		    try {
		        assertEquals("", driver.findElement(By.id("j_password")).getAttribute("value"));
		      } catch (Error e) {
		        verificationErrors.append(e.toString());
		      }
		    driver.findElement(By.id("j_password")).clear();
		    driver.findElement(By.id("j_password")).sendKeys("pw");
		    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		    
		    driver.findElement(By.cssSelector("h2.panel-title")).click();
		    driver.findElement(By.linkText("Patient Information")).click();
		    driver.findElement(By.id("searchBox")).clear();
		    driver.findElement(By.id("searchBox")).sendKeys("2");
		    clickOnJavascriptElement(By.xpath("//input[@value='2' and @type='button']"));
		    
		    driver.findElement(By.name("dateOfBirthStr")).clear();
		    driver.findElement(By.name("dateOfBirthStr")).sendKeys("");
		    driver.findElement(By.name("dateOfBirthStr")).clear();
		    driver.findElement(By.name("dateOfBirthStr")).sendKeys("01/03/2050");
		    driver.findElement(By.name("action")).click();
		    try {
		      assertEquals("This form has not been validated correctly. The following field are not properly filled in: [Death date cannot be before birth date!, Birth date cannot be in the future!]", driver.findElement(By.cssSelector("span.iTrustError")).getText());
		    } catch (Error e) {
		      verificationErrors.append(e.toString());
		    }
	  }
	  
	  public void testRepresent() throws Exception {
		  login("9000000000", "pw");
		    
		    driver.findElement(By.cssSelector("h2.panel-title")).click();
		    driver.findElement(By.linkText("PHR Information")).click();
		    driver.findElement(By.id("searchBox")).clear();
		    driver.findElement(By.id("searchBox")).sendKeys("2");
		    clickOnJavascriptElement(By.xpath("//input[@value='2' and @type='button']"));
		  
		  driver.findElement(By.linkText("Baby Programmer")).click();
		    try {
		      assertEquals("Andy Programmer", driver.findElement(By.linkText("Andy Programmer")).getText());
		    } catch (Error e) {
		      verificationErrors.append(e.toString());
		    }
		    try {
		      assertEquals("Diabetes with ketoacidosis", driver.findElement(By.xpath("//div[@id='iTrustContent']/table/tbody/tr[3]/td[9]")).getText());
		    } catch (Error e) {
		      verificationErrors.append(e.toString());
		    }
		    try {
		      assertEquals("Grandparent", driver.findElement(By.xpath("//div[@id='iTrustContent']/table/tbody/tr[7]/td[2]")).getText());
		    } catch (Error e) {
		      verificationErrors.append(e.toString());
		    }
		    driver.findElement(By.linkText("Random Person")).click();
		    try {
		      assertEquals("nobody@gmail.com", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/div/table/tbody/tr[5]/td[2]")).getText());
		    } catch (Error e) {
		      verificationErrors.append(e.toString());
		    }
	  }
	  
	  public void testAllergy() throws Exception {
		  driver.get(ADDRESS);
		    try {
		        assertEquals("", driver.findElement(By.id("j_username")).getAttribute("value"));
		      } catch (Error e) {
		        verificationErrors.append(e.toString());
		      }
		    driver.findElement(By.id("j_username")).clear();
		    driver.findElement(By.id("j_username")).sendKeys("9000000000");
		    try {
		        assertEquals("", driver.findElement(By.id("j_password")).getAttribute("value"));
		      } catch (Error e) {
		        verificationErrors.append(e.toString());
		      }
		    driver.findElement(By.id("j_password")).clear();
		    driver.findElement(By.id("j_password")).sendKeys("pw");
		    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		    
		    driver.findElement(By.cssSelector("h2.panel-title")).click();
		    driver.findElement(By.linkText("PHR Information")).click();
		    driver.findElement(By.id("searchBox")).clear();
		    driver.findElement(By.id("searchBox")).sendKeys("2");
		    clickOnJavascriptElement(By.xpath("//input[@value='2' and @type='button']"));
		    
		    JavascriptExecutor js = (JavascriptExecutor)driver;
		    js.executeScript("var descField = document.getElementById('description'); document.getElementById('addAllergyForm').appendChild(descField);");
		    js.executeScript("var btn = document.getElementById('addA'); document.getElementById('addAllergyForm').appendChild(btn);");
		    driver.findElement(By.id("description")).clear();
		    driver.findElement(By.id("description")).sendKeys("081096");
		    js.executeScript("document.getElementById('addA').click()");
		    
//		    driver.findElement(By.id("description")).clear();
//		    driver.findElement(By.id("description")).sendKeys("081096");
//		    driver.findElement(By.name("addA")).click();
//		    try {
//		      assertEquals("Allergy Added", driver.findElement(By.cssSelector("span.iTrustError")).getText());
//		    } catch (Error e) {
//		      verificationErrors.append(e.toString());
//		    }
		    assertTrue(pageContains("Allergy Added"));
	  }
	  
	  public void testAllergy2() throws Exception {
		  driver.get(ADDRESS);
		    try {
		        assertEquals("", driver.findElement(By.id("j_username")).getAttribute("value"));
		      } catch (Error e) {
		        verificationErrors.append(e.toString());
		      }
		    driver.findElement(By.id("j_username")).clear();
		    driver.findElement(By.id("j_username")).sendKeys("9000000000");
		    try {
		        assertEquals("", driver.findElement(By.id("j_password")).getAttribute("value"));
		      } catch (Error e) {
		        verificationErrors.append(e.toString());
		      }
		    driver.findElement(By.id("j_password")).clear();
		    driver.findElement(By.id("j_password")).sendKeys("pw");
		    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		    
		    driver.findElement(By.cssSelector("h2.panel-title")).click();
		    driver.findElement(By.linkText("PHR Information")).click();
		    driver.findElement(By.id("searchBox")).clear();
		    driver.findElement(By.id("searchBox")).sendKeys("2");
		    clickOnJavascriptElement(By.xpath("//input[@value='2' and @type='button']"));
		    
		    JavascriptExecutor js = (JavascriptExecutor)driver;
		    js.executeScript("var descField = document.getElementById('description'); document.getElementById('addAllergyForm').appendChild(descField);");
		    js.executeScript("var btn = document.getElementById('addA'); document.getElementById('addAllergyForm').appendChild(btn);");
		    driver.findElement(By.id("description")).clear();
		    driver.findElement(By.id("description")).sendKeys("Penicillin");
		    js.executeScript("document.getElementById('addA').click()");
		    //driver.findElement(By.name("addA")).click();
		  //assertEquals("Allergy 664662530 - Penicillin has already been added for Andy Programmer.", driver.findElement(By.cssSelector("span.iTrustError")).getText());
	    	assertTrue(pageContains("Allergy 664662530 - Penicillin has already been added for Andy Programmer."));
	  }
	  
	  public void testEditSmokingStatus() throws Exception {
		  driver.get(ADDRESS);
		    try {
		        assertEquals("", driver.findElement(By.id("j_username")).getAttribute("value"));
		      } catch (Error e) {
		        verificationErrors.append(e.toString());
		      }
		    driver.findElement(By.id("j_username")).clear();
		    driver.findElement(By.id("j_username")).sendKeys("9000000000");
		    try {
		        assertEquals("", driver.findElement(By.id("j_password")).getAttribute("value"));
		      } catch (Error e) {
		        verificationErrors.append(e.toString());
		      }
		    driver.findElement(By.id("j_password")).clear();
		    driver.findElement(By.id("j_password")).sendKeys("pw");
		    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		    
		    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
		    driver.findElement(By.linkText("Document Office Visit")).click();
		    driver.findElement(By.id("searchBox")).clear();
		    driver.findElement(By.id("searchBox")).sendKeys("2");
		    clickOnJavascriptElement(By.xpath("//input[@value='2' and @type='button']"));
		    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		    driver.findElement(By.id("update")).click();
		    try {
		      assertEquals("Information Successfully Updated", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
		    } catch (Error e) {
		      verificationErrors.append(e.toString());
		    }
		    driver.findElement(By.name("height")).clear();
		    driver.findElement(By.name("height")).sendKeys("56.0");
		    driver.findElement(By.name("weight")).clear();
		    driver.findElement(By.name("weight")).sendKeys("111.0");
		    new Select(driver.findElement(By.id("isSmoker"))).selectByVisibleText("2 - Current some day smoker");
		    new Select(driver.findElement(By.id("householdSmokingStatus"))).selectByVisibleText("1 - non-smoking household");
		    driver.findElement(By.name("bloodPressureN")).clear();
		    driver.findElement(By.name("bloodPressureN")).sendKeys("999");
		    driver.findElement(By.name("bloodPressureD")).clear();
		    driver.findElement(By.name("bloodPressureD")).sendKeys("000");
		    driver.findElement(By.name("cholesterolHDL")).clear();
		    driver.findElement(By.name("cholesterolHDL")).sendKeys("50");
		    driver.findElement(By.name("cholesterolLDL")).clear();
		    driver.findElement(By.name("cholesterolLDL")).sendKeys("200");
		    driver.findElement(By.name("cholesterolTri")).clear();
		    driver.findElement(By.name("cholesterolTri")).sendKeys("200");
		    driver.findElement(By.id("addHR")).click();
		    try {
		      assertEquals("Health information successfully updated.", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
		    } catch (Error e) {
		      verificationErrors.append(e.toString());
		    }
		    new Select(driver.findElement(By.id("isSmoker"))).selectByVisibleText("1 - Current every day smoker");
		    new Select(driver.findElement(By.id("householdSmokingStatus"))).selectByVisibleText("1 - non-smoking household");
		    driver.findElement(By.id("addHR")).click();
		    try {
		      assertEquals("Health information successfully updated.", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
		    } catch (Error e) {
		      verificationErrors.append(e.toString());
		    }
		    
	  }
	  
	  public void testAddAdditionalDemographics1() throws Exception {
		  driver.get(ADDRESS);
		    try {
		        assertEquals("", driver.findElement(By.id("j_username")).getAttribute("value"));
		      } catch (Error e) {
		        verificationErrors.append(e.toString());
		      }
		    driver.findElement(By.id("j_username")).clear();
		    driver.findElement(By.id("j_username")).sendKeys("9000000000");
		    try {
		        assertEquals("", driver.findElement(By.id("j_password")).getAttribute("value"));
		      } catch (Error e) {
		        verificationErrors.append(e.toString());
		      }
		    driver.findElement(By.id("j_password")).clear();
		    driver.findElement(By.id("j_password")).sendKeys("pw");
		    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		  
		  driver.findElement(By.cssSelector("div.panel-heading")).click();
		    driver.findElement(By.linkText("Patient Information")).click();
		    driver.findElement(By.id("searchBox")).clear();
		    driver.findElement(By.id("searchBox")).sendKeys("2");
		    clickOnJavascriptElement(By.xpath("//input[@value='2' and @type='button']"));
		    
		    driver.findElement(By.name("religion")).clear();
		    driver.findElement(By.name("religion")).sendKeys("Jedi");
		    driver.findElement(By.name("action")).click();
		    try {
		      assertEquals("Information Successfully Updated", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
		    } catch (Error e) {
		      verificationErrors.append(e.toString());
		    }
		    driver.findElement(By.cssSelector("h2.panel-title")).click();
		    driver.findElement(By.linkText("PHR Information")).click();
		    try {
		      assertEquals("Jedi", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/div/table/tbody/tr[7]/td[2]")).getText());
		    } catch (Error e) {
		      verificationErrors.append(e.toString());
		    }
	  }
	  
	  public void testAddAdditionalDemographics2() throws Exception {
		  driver.get(ADDRESS);
		    try {
		        assertEquals("", driver.findElement(By.id("j_username")).getAttribute("value"));
		      } catch (Error e) {
		        verificationErrors.append(e.toString());
		      }
		    driver.findElement(By.id("j_username")).clear();
		    driver.findElement(By.id("j_username")).sendKeys("9000000000");
		    try {
		        assertEquals("", driver.findElement(By.id("j_password")).getAttribute("value"));
		      } catch (Error e) {
		        verificationErrors.append(e.toString());
		      }
		    driver.findElement(By.id("j_password")).clear();
		    driver.findElement(By.id("j_password")).sendKeys("pw");
		    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		  
		  driver.findElement(By.cssSelector("div.panel-heading")).click();
		    driver.findElement(By.linkText("Patient Information")).click();
		    driver.findElement(By.id("searchBox")).clear();
		    driver.findElement(By.id("searchBox")).sendKeys("2");
		    clickOnJavascriptElement(By.xpath("//input[@value='2' and @type='button']"));		    
		    driver.findElement(By.name("spiritualPractices")).clear();
		    driver.findElement(By.name("spiritualPractices")).sendKeys("Sleeps in class");
		    driver.findElement(By.name("action")).click();
		    try {
		      assertEquals("Information Successfully Updated", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
		    } catch (Error e) {
		      verificationErrors.append(e.toString());
		    }
		    driver.findElement(By.cssSelector("h2.panel-title")).click();
		    driver.findElement(By.linkText("PHR Information")).click();
		    try {
		      assertEquals("Sleeps in class", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/div/table/tbody/tr[9]/td[2]")).getText());
		    } catch (Error e) {
		      verificationErrors.append(e.toString());
		    }
	  }
	  
	  public void testAddAdditionalDemographics3() throws Exception {
		  driver.get(ADDRESS);
		    try {
		        assertEquals("", driver.findElement(By.id("j_username")).getAttribute("value"));
		      } catch (Error e) {
		        verificationErrors.append(e.toString());
		      }
		    driver.findElement(By.id("j_username")).clear();
		    driver.findElement(By.id("j_username")).sendKeys("9000000000");
		    try {
		        assertEquals("", driver.findElement(By.id("j_password")).getAttribute("value"));
		      } catch (Error e) {
		        verificationErrors.append(e.toString());
		      }
		    driver.findElement(By.id("j_password")).clear();
		    driver.findElement(By.id("j_password")).sendKeys("pw");
		    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		  
		  driver.findElement(By.cssSelector("div.panel-heading")).click();
		    driver.findElement(By.linkText("Patient Information")).click();
		    driver.findElement(By.id("searchBox")).clear();
		    driver.findElement(By.id("searchBox")).sendKeys("2");
		    clickOnJavascriptElement(By.xpath("//input[@value='2' and @type='button']"));
		    
		    driver.findElement(By.name("alternateName")).clear();
		    driver.findElement(By.name("alternateName")).sendKeys("Randy");
		    driver.findElement(By.name("action")).click();
		    try {
		      assertEquals("Information Successfully Updated", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
		    } catch (Error e) {
		      verificationErrors.append(e.toString());
		    }
		    driver.findElement(By.cssSelector("div.panel-heading")).click();
		    driver.findElement(By.linkText("PHR Information")).click();
		    try {
		      assertEquals("Randy", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/div/table/tbody/tr[10]/td[2]")).getText());
		    } catch (Error e) {
		      verificationErrors.append(e.toString());
		    }
	  }
	  
	  public void testAddDupAllergy() throws Exception {
		  driver.get(ADDRESS);
		    try {
		        assertEquals("", driver.findElement(By.id("j_username")).getAttribute("value"));
		      } catch (Error e) {
		        verificationErrors.append(e.toString());
		      }
		    driver.findElement(By.id("j_username")).clear();
		    driver.findElement(By.id("j_username")).sendKeys("9000000000");
		    try {
		        assertEquals("", driver.findElement(By.id("j_password")).getAttribute("value"));
		      } catch (Error e) {
		        verificationErrors.append(e.toString());
		      }
		    driver.findElement(By.id("j_password")).clear();
		    driver.findElement(By.id("j_password")).sendKeys("pw");
		    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		  
		  driver.findElement(By.cssSelector("div.panel-heading")).click();
		    driver.findElement(By.linkText("PHR Information")).click();
		    driver.findElement(By.id("searchBox")).clear();
		    driver.findElement(By.id("searchBox")).sendKeys("25");
		    clickOnJavascriptElement(By.xpath("//input[@value='25' and @type='button']"));
		    
		    JavascriptExecutor js = (JavascriptExecutor)driver;
		    js.executeScript("var descField = document.getElementById('description'); document.getElementById('addAllergyForm').appendChild(descField);");
		    js.executeScript("var btn = document.getElementById('addA'); document.getElementById('addAllergyForm').appendChild(btn);");
		    driver.findElement(By.id("description")).clear();
		    driver.findElement(By.id("description")).sendKeys("Penicillin");
		    js.executeScript("document.getElementById('addA').click()");
		    
//		    driver.findElement(By.id("description")).clear();
//		    driver.findElement(By.id("description")).sendKeys("Penicillin");
//		    driver.findElement(By.name("addA")).click();
		    try {
		      assertEquals("Allergy Added", driver.findElement(By.cssSelector("span.iTrustError")).getText());
		    } catch (Error e) {
		      verificationErrors.append(e.toString());
		    }
		    js.executeScript("var descField = document.getElementById('description'); document.getElementById('addAllergyForm').appendChild(descField);");
		    js.executeScript("var btn = document.getElementById('addA'); document.getElementById('addAllergyForm').appendChild(btn);");
		    driver.findElement(By.id("description")).clear();
		    driver.findElement(By.id("description")).sendKeys("Penicillin");
		    js.executeScript("document.getElementById('addA').click()");
//		    driver.findElement(By.id("description")).clear();
//		    driver.findElement(By.id("description")).sendKeys("Penicillin");
//		    driver.findElement(By.name("addA")).click();
		    try {
		      assertEquals("Allergy 664662530 - Penicillin has already been added for Trend Setter.", driver.findElement(By.cssSelector("span.iTrustError")).getText());
		    } catch (Error e) {
		      verificationErrors.append(e.toString());
		    }
	  }
	  
	  public void testAddAllergyPrevRX() throws Exception {
		  driver.get(ADDRESS);
		    try {
		        assertEquals("", driver.findElement(By.id("j_username")).getAttribute("value"));
		      } catch (Error e) {
		        verificationErrors.append(e.toString());
		      }
		    driver.findElement(By.id("j_username")).clear();
		    driver.findElement(By.id("j_username")).sendKeys("9000000000");
		    try {
		        assertEquals("", driver.findElement(By.id("j_password")).getAttribute("value"));
		      } catch (Error e) {
		        verificationErrors.append(e.toString());
		      }
		    driver.findElement(By.id("j_password")).clear();
		    driver.findElement(By.id("j_password")).sendKeys("pw");
		    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		    
		    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
		    driver.findElement(By.linkText("Document Office Visit")).click();
		    driver.findElement(By.id("searchBox")).clear();
		    driver.findElement(By.id("searchBox")).sendKeys("25");
		    clickOnJavascriptElement(By.xpath("//input[@value='25' and @type='button']"));
		    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		    driver.findElement(By.name("visitDate")).clear();
		    driver.findElement(By.name("visitDate")).sendKeys("01/01/2012");
		    driver.findElement(By.name("notes")).clear();
		    driver.findElement(By.name("notes")).sendKeys("just some more notes");
		    driver.findElement(By.id("update")).click();
		    try {
		      assertEquals("Information Successfully Updated", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
		    } catch (Error e) {
		      verificationErrors.append(e.toString());
		    }
		    new Select(driver.findElement(By.id("medID"))).selectByVisibleText("664662530 - Penicillin");
		    driver.findElement(By.id("dosage")).clear();
		    driver.findElement(By.id("dosage")).sendKeys("60");
		    driver.findElement(By.id("startDate")).click();
		    driver.findElement(By.id("startDate")).click();
		    driver.findElement(By.id("startDate")).click();
		    driver.findElement(By.id("startDate")).clear();
		    driver.findElement(By.id("startDate")).sendKeys("01/01/2012");
		    driver.findElement(By.xpath("//button[@onclick='updateDateField(\"startDate\");']")).click();
		    driver.findElement(By.id("endDate")).click();
		    driver.findElement(By.id("endDate")).click();
		    driver.findElement(By.id("endDate")).click();
		    driver.findElement(By.id("endDate")).click();
		    driver.findElement(By.id("endDate")).clear();
		    driver.findElement(By.id("endDate")).sendKeys("01/31/2012");
		    driver.findElement(By.id("instructions")).clear();
		    driver.findElement(By.id("instructions")).sendKeys("");
		    driver.findElement(By.id("instructions")).clear();
		    driver.findElement(By.id("instructions")).sendKeys("Take three times daily with food.");
		    driver.findElement(By.id("addprescription")).click();
		    try {
		      assertEquals("Prescription information successfully updated.", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
		    } catch (Error e) {
		      verificationErrors.append(e.toString());
		    }
		    driver.findElement(By.cssSelector("h2.panel-title")).click();
		    driver.findElement(By.linkText("PHR Information")).click();
		    
		    JavascriptExecutor js = (JavascriptExecutor)driver;
		    js.executeScript("var descField = document.getElementById('description'); document.getElementById('addAllergyForm').appendChild(descField);");
		    js.executeScript("var btn = document.getElementById('addA'); document.getElementById('addAllergyForm').appendChild(btn);");
		    driver.findElement(By.id("description")).clear();
		    driver.findElement(By.id("description")).sendKeys("Penicillin");
		    js.executeScript("document.getElementById('addA').click()");
		    
//		    driver.findElement(By.id("description")).clear();
//		    driver.findElement(By.id("description")).sendKeys("Penicillin");
//		    driver.findElement(By.name("addA")).click();
		    try {
		      assertEquals("Allergy Added", driver.findElement(By.cssSelector("span.iTrustError")).getText());
		    } catch (Error e) {
		      verificationErrors.append(e.toString());
		    }
	  }
	  
	  public void testAddAllergyFutRX() throws Exception {
		 login("9000000000", "pw");
		    
		    driver.findElement(By.cssSelector("h2.panel-title")).click();
		    driver.findElement(By.linkText("All Patients")).click();
		    driver.findElement(By.linkText("Anakin Skywalker")).click();
		    
		    JavascriptExecutor js = (JavascriptExecutor)driver;
		    js.executeScript("var descField = document.getElementById('description'); document.getElementById('addAllergyForm').appendChild(descField);");
		    js.executeScript("var btn = document.getElementById('addA'); document.getElementById('addAllergyForm').appendChild(btn);");
		    driver.findElement(By.id("description")).clear();
		    driver.findElement(By.id("description")).sendKeys("Midichlominene");
		    js.executeScript("document.getElementById('addA').click()");
		    
//		    driver.findElement(By.id("description")).clear();
//		    driver.findElement(By.id("description")).sendKeys("Midichlominene");
//		    driver.findElement(By.name("addA")).click();
		    try {
		      assertEquals("Medication 483012382 - Midichlominene is currently prescribed to Anakin Skywalker.", driver.findElement(By.cssSelector("span.iTrustError")).getText());
		    } catch (Error e) {
		      verificationErrors.append(e.toString());
		    }
	  }
	  
	  public void testAddAllergyExistRX() throws Exception {
		  driver.get(ADDRESS);
		    try {
		        assertEquals("", driver.findElement(By.id("j_username")).getAttribute("value"));
		      } catch (Error e) {
		        verificationErrors.append(e.toString());
		      }
		    driver.findElement(By.id("j_username")).clear();
		    driver.findElement(By.id("j_username")).sendKeys("9000000000");
		    try {
		        assertEquals("", driver.findElement(By.id("j_password")).getAttribute("value"));
		      } catch (Error e) {
		        verificationErrors.append(e.toString());
		      }
		    driver.findElement(By.id("j_password")).clear();
		    driver.findElement(By.id("j_password")).sendKeys("pw");
		    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		    
		    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
		    driver.findElement(By.linkText("Document Office Visit")).click();
		    driver.findElement(By.id("searchBox")).clear();
		    driver.findElement(By.id("searchBox")).sendKeys("25");
		    clickOnJavascriptElement(By.xpath("//input[@value='25' and @type='button']"));
		    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		    driver.findElement(By.name("visitDate")).clear();
		    driver.findElement(By.name("visitDate")).sendKeys("02/21/2012");
		    driver.findElement(By.name("visitDate")).clear();
		    driver.findElement(By.name("visitDate")).sendKeys("02/01/2012");
		    driver.findElement(By.name("notes")).clear();
		    driver.findElement(By.name("notes")).sendKeys("just some notes");
		    driver.findElement(By.id("update")).click();
		    try {
		      assertEquals("Information Successfully Updated", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
		    } catch (Error e) {
		      verificationErrors.append(e.toString());
		    }
		    new Select(driver.findElement(By.id("medID"))).selectByVisibleText("00882219 - Lantus");
		    driver.findElement(By.id("dosage")).clear();
		    driver.findElement(By.id("dosage")).sendKeys("100");
		    driver.findElement(By.id("startDate")).click();
		    driver.findElement(By.id("startDate")).click();
		    driver.findElement(By.id("startDate")).clear();
		    driver.findElement(By.id("startDate")).sendKeys("02/01/2012");
		    driver.findElement(By.id("endDate")).click();
		    driver.findElement(By.id("endDate")).clear();
		    driver.findElement(By.id("endDate")).sendKeys("04/01/2020");
		    driver.findElement(By.id("instructions")).clear();
		    driver.findElement(By.id("instructions")).sendKeys("Take once daily");
		    driver.findElement(By.id("addprescription")).click();
		    try {
		      assertEquals("Prescription information successfully updated.", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
		    } catch (Error e) {
		      verificationErrors.append(e.toString());
		    }
		    driver.findElement(By.cssSelector("h2.panel-title")).click();
		    driver.findElement(By.linkText("PHR Information")).click();
		    
		    JavascriptExecutor js = (JavascriptExecutor)driver;
		    js.executeScript("var descField = document.getElementById('description'); document.getElementById('addAllergyForm').appendChild(descField);");
		    js.executeScript("var btn = document.getElementById('addA'); document.getElementById('addAllergyForm').appendChild(btn);");
		    driver.findElement(By.id("description")).clear();
		    driver.findElement(By.id("description")).sendKeys("Lantus");
		    js.executeScript("document.getElementById('addA').click()");
		    
//		    driver.findElement(By.id("description")).clear();
//		    driver.findElement(By.id("description")).sendKeys("Lantus");
//		    driver.findElement(By.name("addA")).click();
		    try {
		      assertEquals("Medication 00882219 - Lantus is currently prescribed to Trend Setter.", driver.findElement(By.cssSelector("span.iTrustError")).getText());
		    } catch (Error e) {
		      verificationErrors.append(e.toString());
		    }
	  }
}
