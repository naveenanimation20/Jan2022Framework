package com.qa.opencart.factory;

import java.util.Properties;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class OptionsManager {
	
	private Properties prop;
	private ChromeOptions co;	
	private FirefoxOptions fo;

	
	public OptionsManager(Properties prop) {
		this.prop = prop;
	}
	
	public ChromeOptions getChromeOptions() {
		co = new ChromeOptions();
		if(Boolean.parseBoolean(prop.getProperty("headless"))) co.addArguments("--headless");
		if(Boolean.parseBoolean(prop.getProperty("incognito"))) co.addArguments("--incognito");
		
			if(Boolean.parseBoolean(prop.getProperty("remote"))) {
				//co.setPlatformName("linux");
				co.setCapability("enableVNC", true);
				co.setBrowserVersion(prop.getProperty("browserversion"));
			}
		
		return co;
	}
	
	public FirefoxOptions getFirefoxOptions() {
		fo = new FirefoxOptions();
		if(Boolean.parseBoolean(prop.getProperty("headless"))) fo.addArguments("--headless");
		if(Boolean.parseBoolean(prop.getProperty("incognito"))) fo.addArguments("--incognito");
		
		if(Boolean.parseBoolean(prop.getProperty("remote"))) {
			fo.setPlatformName("linux");
			fo.setCapability("enableVNC", true);
			fo.setBrowserVersion(prop.getProperty("browserversion"));		
			}
		return fo;
	}

}
