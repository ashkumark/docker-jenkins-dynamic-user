package com.m2at.utils;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.logging.Level;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.m2at.constants.Environment;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverFactory {

	WebDriver driver;
	ChromeOptions options;
	FirefoxOptions foptions;
	
	String hostName;
	String browserType;

	public ChromeOptions getChromeOptions() {
		options = new ChromeOptions();

		options.addArguments("--no-sandbox"); // Bypass OS security model
		options.addArguments("--remote-debugging-port=9222");
		options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
		options.addArguments("start-maximized"); // open Browser in maximized mode
		options.addArguments("disable-infobars"); // disabling infobars
		options.addArguments("--disable-extensions"); // disabling extensions
		options.addArguments("--disable-gpu"); // applicable to windows os only

		options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
		options.setExperimentalOption("useAutomationExtension", false);

		return options;
	}

	public ChromeOptions getChromeHeadlessOptions() {
		options = new ChromeOptions();

		options.addArguments("--no-sandbox"); // Bypass OS security model
		options.addArguments("--remote-debugging-port=9222");
		options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
		options.addArguments("start-maximized"); // open Browser in maximized mode
		options.addArguments("disable-infobars"); // disabling infobars
		options.addArguments("--disable-extensions"); // disabling extensions
		options.addArguments("--disable-gpu"); // applicable to windows os only

		options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
		options.setExperimentalOption("useAutomationExtension", false);

		options.addArguments("--headless");

		return options;
	}
	
	public void setFirefoxLogOff() {
		System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "firefoxLog");
		java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);

	}
	
	public FirefoxOptions getFirefoxOptions() {
		setFirefoxLogOff();
		
		foptions = new FirefoxOptions();
		return foptions;
	}

	public FirefoxOptions getFirefoxHeadlessOptions() {
		setFirefoxLogOff();
		
		foptions = new FirefoxOptions();
		foptions.setHeadless(true);

		return foptions;
	}

	public ChromeDriver getChromeDriver() {
		return new ChromeDriver(getChromeOptions());
	}

	public ChromeDriver getChromeHeadlessDriver() {
		return new ChromeDriver(getChromeHeadlessOptions());
	}
	
	public FirefoxDriver getFirefoxDriver() {
		return new FirefoxDriver(getFirefoxOptions());
	}

	public FirefoxDriver getFirefoxHeadlessDriver() {
		return new FirefoxDriver(getFirefoxHeadlessOptions());
	}

	public RemoteWebDriver getChromeRemoteDriver() throws MalformedURLException {
		return new RemoteWebDriver(new URL("http://" + hostName + ":4444/wd/hub"), getChromeOptions());
	}

	public RemoteWebDriver getChromeRemoteHeadlessDriver() throws MalformedURLException {
		return new RemoteWebDriver(new URL("http://" + hostName + ":4444/wd/hub"), getChromeHeadlessOptions());
	}
	
	public RemoteWebDriver getFirefoxRemoteDriver() throws MalformedURLException {
		return new RemoteWebDriver(new URL("http://" + hostName + ":4444/wd/hub"), getFirefoxOptions());
	}

	public RemoteWebDriver getFirefoxRemoteHeadlessDriver() throws MalformedURLException {
		return new RemoteWebDriver(new URL("http://" + hostName + ":4444/wd/hub"), getFirefoxHeadlessOptions());
	}

	public WebDriver initialiseWebDriver() throws MalformedURLException {
		hostName = System.getProperty("HUB_HOST");
		browserType = System.getProperty("BROWSER");

		if (hostName == null) { // LOCAL
			System.out.println("host name parameter not set, defaulting to 'local'");

			if (browserType == null) {
				System.out.println("browserType parameter not set, defaulting to 'chrome'");
				driver = WebDriverManager.chromedriver().create();
			}

			if (browserType != null) {
				System.out.println("browserType parameter not set, defaulting to - " + Environment.BROWSER.getValue());

				switch (Environment.BROWSER.getValue()) {
				case "chrome":
					//WebDriverManager.chromedriver().setup();
					//driver = getChromeDriver();
					driver = WebDriverManager.chromedriver().create();
					break;
				case "firefox":
					WebDriverManager.firefoxdriver().setup();
					driver = getFirefoxDriver();
					break;
				default:
					break;
				}
				
			}
		}

		if (hostName != null && !hostName.isEmpty()  ) { // REMOTE
			
			if (browserType == null) {
				System.out.println("browserType parameter not set, defaulting to 'chrome'");
				driver = getChromeRemoteDriver();
			}
			
			switch (browserType) {
			case "chrome":
				driver = getChromeRemoteDriver();
				break;
			case "chromeheadless":
				driver = getChromeRemoteHeadlessDriver();
				break;
			case "firefox":
				driver = getFirefoxRemoteDriver();
				break;
			case "firefoxheadless":
				driver = getFirefoxRemoteHeadlessDriver();
				break;
			default:
				System.out.println("Driver not initialised");
				throw new RuntimeException("Unsupported browser");
			}
		}

		return driver;
	}

	/*
	 * public WebDriver initialiseWebDriver() throws MalformedURLException {
	 * 
	 * setDriver(browserType);
	 * 
	 * return driver; }
	 */
}
