package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.exceptions.DriverException;
import com.qa.opencart.exceptions.FrameworkException;
import com.qa.opencart.utils.Browser;
import com.qa.opencart.utils.Errors;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

	public WebDriver driver;
	public Properties prop;
	public static String highlight;
	public OptionsManager optionsManager;
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	public static final Logger log = Logger.getLogger(DriverFactory.class);

	/**
	 * This method is used to initialize the webdriver on the basis of given browser
	 * name. This method will take care of local and remote execution
	 * 
	 * @param browserName
	 * @return
	 */
	public WebDriver init_driver(Properties prop) {

		String browserName = prop.getProperty("browser").trim();
		System.out.println("browser name is : " + browserName);
		log.info("Running browser name is : " + browserName);

		highlight = prop.getProperty("highlight").trim();
		optionsManager = new OptionsManager(prop);

		if (browserName.equalsIgnoreCase(Browser.CHROME_BROWSER_VAUE)) {
			log.info("running test on chrome browser....");

			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				init_remoteWebDriver(Browser.CHROME_BROWSER_VAUE);
			} else {
				// local execution:
				WebDriverManager.chromedriver().setup();
				tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			}

		} else if (browserName.equalsIgnoreCase(Browser.FIREFOX_BROWSER_VAUE)) {

			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				init_remoteWebDriver(Browser.FIREFOX_BROWSER_VAUE);
			} else {
				WebDriverManager.firefoxdriver().setup();
				tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			}

		} else if (browserName.equalsIgnoreCase(Browser.SAFARI_BROWSER_VAUE)) {
			tlDriver.set(new SafariDriver());
		} else {
			System.out.println(Errors.BROWSER_NOT_FOUND_ERROR_MESSG + browserName);
			throw new DriverException("browser is not correct..." + Errors.BROWSER_NOT_FOUND_ERROR_MESSG + browserName);
		}

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().fullscreen();
		getDriver().get(prop.getProperty("url"));
		log.info(prop.getProperty("url") + " .... url is launched....");

		return getDriver();

	}

	/**
	 * this method is used to run tests on remote - docker machine
	 * 
	 * @param browserName
	 */
	private void init_remoteWebDriver(String browserName) {

		System.out.println("Runnng test cases on remote grid server: " + browserName);

		if (browserName.equalsIgnoreCase("chrome")) {
			try {
				tlDriver.set(
						new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getChromeOptions()));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		} else if (browserName.equalsIgnoreCase("firefox")) {
			try {
				tlDriver.set(
						new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getFirefoxOptions()));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * this will return the thread local copy of the webdriver(driver)
	 * 
	 * @return
	 */
	public static WebDriver getDriver() {
		return tlDriver.get();
	}

	/**
	 * This method is used to initialize the properties on the basis of given
	 * environment: QA/DEV/Stage/PROD
	 * 
	 * @return this returns prop
	 */
	public Properties init_prop() {

		prop = new Properties();
		FileInputStream ip = null;

		// mvn clean install -Denv="qa"
		// mvn clean install
		String envName = System.getProperty("env");
		System.out.println("Running tests on environment: " + envName);
		log.info("Running tests on environment: " + envName);

		if (envName == null) {
			System.out.println("No env is given....hence running it on QA");
			log.info("No env is given....hence running it on QA");
			try {
				ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		else {
			try {
				switch (envName.toLowerCase()) {
				case "qa":
					log.info("running it on QA");
					ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
					break;
				case "stage":
					log.info("running it on Stage");
					ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
					break;
				case "dev":
					ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
					break;
				case "uat":
					ip = new FileInputStream("./src/test/resources/config/uat.config.properties");
					break;
				case "prod":
					ip = new FileInputStream("./src/test/resources/config/config.properties");
					break;

				default:
					System.out.println("please pass the right environment....." + envName);
					log.error("please pass the right environment....." + envName);
					log.warn("env name is not found....");
					log.fatal("env is not found....");
					throw new FrameworkException("environment name is not valid..." + envName);
				// break;
				}
			} catch (Exception e) {

			}

		}

		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;

	}

	/**
	 * take screenshot
	 */
	public static String getScreenshot() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
		File destination = new File(path);
		try {
			FileUtils.copyFile(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;

	}

}
