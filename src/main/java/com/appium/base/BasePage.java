package com.appium.base;

import static com.appium.constants.FrameworkConstants.EXPLICIT_WAIT;
import static com.appium.constants.FrameworkConstants.WAIT;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appium.manager.DriverManager;
import com.appium.reports.ExtentLogger;
import com.appium.reports.ExtentManager;
import com.appium.utils.ScreenshotUtils;
import com.appium.utils.TestUtils;
import com.appium.utils.VerificationUtils;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.google.common.util.concurrent.Uninterruptibles;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class BasePage {
//	private AppiumDriver<?> driver;
//	TestUtils utils = new TestUtils();

	public BasePage() {
//		this.driver = DriverManager.getDriver();
//		PageFactory.initElements(new AppiumFieldDecorator(this.driver), this);
		
		PageFactory.initElements(new AppiumFieldDecorator(DriverManager.getDriver()), this);
	}

	public void waitForVisibility(MobileElement mobileElement) {
		WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), EXPLICIT_WAIT);
		wait.until(ExpectedConditions.visibilityOf(mobileElement));
	}

	public void click(MobileElement mobileElement) {
		waitForVisibility(mobileElement);
		TestUtils.log().info(mobileElement.getText() + " is clicked");
		ExtentLogger.info("<b>" + mobileElement.getText() + "</b> is clicked");
		mobileElement.click();
	}

	public void click(MobileElement mobileElement, String elementName) {
		waitForVisibility(mobileElement);
		TestUtils.log().info(elementName + " is clicked");
		ExtentLogger.info("<b>" + elementName + "</b> is clicked");
		mobileElement.click();
	}

	public void sendKeys(MobileElement mobileElement, String txt) {
		waitForVisibility(mobileElement);
		TestUtils.log().info("Filling " + txt + " in " + mobileElement.getText());
		ExtentLogger.info("Filling <b>" + txt + "</b> in <b>" + mobileElement.getText() + "</b>");
		mobileElement.sendKeys(txt);
	}

	public void sendKeys(MobileElement mobileElement, String txt, String elementName) {
		waitForVisibility(mobileElement);
		TestUtils.log().info("Filling " + txt + " in " + elementName);
		ExtentLogger.info("Filling <b>" + txt + "</b> in <b>" + elementName + "</b>");
		mobileElement.sendKeys(txt);
	}

	public String getAttribute(MobileElement mobileElement, String attribute) {
		waitForVisibility(mobileElement);
		TestUtils.log().info("Attribute: " + attribute + " value for " + mobileElement.getText() + " is - "
				+ mobileElement.getAttribute(attribute));
		/*
		 * ExtentLogger.info("Attribute: <b>" + attribute + "</b> value for <b>" +
		 * mobileElement.getText() + "</b> is - <b>" +
		 * mobileElement.getAttribute(attribute) + "</b>");
		 */return mobileElement.getAttribute(attribute);
	}

	
	protected void captureScreenshot() {
		ExtentManager.getExtentTest().info("Capturing Screenshot",
				MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotUtils.getBase64Image()).build());
	}

	protected void waitForSomeTime() {
		Uninterruptibles.sleepUninterruptibly(WAIT, TimeUnit.SECONDS);
	}

	protected void waitForGivenTime(long time) {
		Uninterruptibles.sleepUninterruptibly(time, TimeUnit.SECONDS);
	}

	protected void webElementPresent(MobileElement mobileElement, String elementName) {
		VerificationUtils.validate(isDisplayed(mobileElement), true, elementName + " presence");
	}

	protected void webElementAbsent(MobileElement mobileElement, String elementName) {
		VerificationUtils.validate(isDisplayed(mobileElement), false, elementName + " absence");
	}

	private boolean isDisplayed(MobileElement element) {
		try {
			waitForVisibility(element);
			return element.isDisplayed();
		} catch (NoSuchElementException | TimeoutException exception) {
			ExtentLogger.fail("Element is not present");
			return false;
		}
	}
	
}
