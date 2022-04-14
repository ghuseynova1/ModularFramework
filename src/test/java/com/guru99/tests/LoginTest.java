package com.guru99.tests;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import com.aventstack.extentreports.Status;
import com.guru99.pages.HomePage;

public class LoginTest extends BaseTest{
	
	@Parameters({"userName","userPassword"})
	@Test
	public void verifyUserLoginWithCorrectCredentials (String userName, String password) {
		reportUtils.createATestCase("Verify User Login With Correct Credentials");
		reportUtils.addTestLog(Status.INFO, "Performing Login");
		
		loginPage.loginToApplication(userName, password);
		
		String expectedTitle = "Guru99 Bank Manager Home Page";
		String acttualTitle = cmnDriver.getTitleOfThePage();
		
		reportUtils.addTestLog(Status.INFO, "Comparing expected and actual title");
		Assert.assertEquals(acttualTitle, expectedTitle);
	}
	
	
	
}
