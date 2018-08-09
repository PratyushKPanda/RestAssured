package com.way2automation.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestUtil {
	
	public static long Page_load = 50;
	public static long implictily_wait = 20;
	public static String testDataFile = System.getProperty("user.dir")+"/src/main/java/com/way2automation/testdata/data.xlsx";
	public static Workbook book;
	public static Sheet sheet;
	public static WebDriver driver;
	
	public static Object [] [] getData(String sheetname)
	{
		FileInputStream fi = null;
		try{
			fi = new FileInputStream(testDataFile);
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		try{
			book = WorkbookFactory.create(fi);
		}catch(InvalidFormatException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
		sheet = book.getSheet(sheetname);
		Object [][] data = new Object [sheet.getLastRowNum()] [sheet.getRow(0).getLastCellNum()];
		for (int i=0; i<sheet.getLastRowNum(); i++)
		{
			for(int j=0; j<sheet.getRow(i).getLastCellNum(); j++)
			{
				data [i][j] = sheet.getRow(i).getCell(j).toString();
			}
		}
		return data;
	}
	public static void screenshots() throws IOException
	{
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(srcFile, new File(System.getProperty("user.dir")+"/Screenshots"+System.currentTimeMillis()));
	}
	public static void explicitlyWait(By by)
	{
		WebDriverWait wait = new WebDriverWait(driver, 35);
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}
	public static void scrollDown(WebElement element)
	{
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}
	

}
