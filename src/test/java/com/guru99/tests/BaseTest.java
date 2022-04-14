package com.guru99.tests;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.internal.TestResult;

import com.aventstack.extentreports.Status;
import com.guru99.pages.LoginPage;

import commonLibs.implementation.CommonDriver;
import commonLibs.utils.ConfigUtils;
import commonLibs.utils.ReportUtils;
import commonLibs.utils.ScreenShotUtils;

public class BaseTest {
	
	CommonDriver cmnDriver;
	String url;
	WebDriver driver;
	
	LoginPage loginPage;
	
	String currentWorkingDirectory;
	String configFileName;
	Properties configProperty;
	
	String reportFileName;
	ReportUtils reportUtils;
	
	ScreenShotUtils screenshot;
	
	@BeforeSuite
	public void preSteup()  throws Exception {
		currentWorkingDirectory = System.getProperty("user.dir");
		
		configFileName = currentWorkingDirectory + "/config/config.properties";
		reportFileName = currentWorkingDirectory + "/reports/guru99TestReport.html";
		
		configProperty = ConfigUtils.readProperties(configFileName);
		
		reportUtils = new ReportUtils(reportFileName);
	}
	
	@BeforeClass
	public void setup() throws Exception {
		url = configProperty.getProperty("baseUrl");
		String browserType = configProperty.getProperty("browserType");
		cmnDriver = new CommonDriver(browserType);
		
		driver = cmnDriver.getDriver();
		
		loginPage = new LoginPage(driver);
		
		screenshot = new ScreenShotUtils(driver);
		cmnDriver.navigateToUrl(url);
	}
	
	@AfterMethod
	public void postTestAction(ITestResult result) throws Exception {
		String testCaseName = result.getName();
		long executionTime = System.currentTimeMillis();
		String screenshotFileName = currentWorkingDirectory +
				"/screenshots/"
				+ testCaseName + executionTime + ".jpeg";
		if (result.getStatus() == ITestResult.FAILURE) {
			reportUtils.addTestLog(Status.FAIL, "One or more steps failed");
			screenshot.captureAndSaveScreenshot(screenshotFileName);
			
			reportUtils.atttachScreenshotToReport(screenshotFileName);
		}
	}
	
	@AfterClass
	public void tearDown() {
		cmnDriver.closeAllBrowser();
	}
	
	@AfterSuite
	public void postTearDown() {
		reportUtils.flushReport();
	}
}
