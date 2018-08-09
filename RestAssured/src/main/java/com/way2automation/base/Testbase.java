package com.way2automation.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.way2automation.util.TestUtil;

public class Testbase {
	
	public static Properties prop;
	public static WebDriver driver;
	public static EventFiringWebDriver eWebdriver;
	
	public Testbase() throws IOException 
	{
		prop = new Properties();
		FileInputStream fi = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\com\\way2automation\\property\\url.properties");
		prop.load(fi);
	}
	
	public static void testInitialization()
	{
		System.setProperty("webdriver,chrome,driver", System.getProperty("user.dir")+"\\chromedriver.exe");
		driver = new ChromeDriver();
		eWebdriver = new EventFiringWebDriver(driver);
		driver = eWebdriver;
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(TestUtil.Page_load, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtil.implictily_wait, TimeUnit.SECONDS);
		driver.get(prop.getProperty("URL"));
	}

}
