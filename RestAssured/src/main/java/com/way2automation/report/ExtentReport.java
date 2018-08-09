package com.way2automation.report;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.LogRecord;

import org.testng.ISuite;
import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExtentReport implements IReporter 
{
	private ExtentReports report;
	public void generateReport(List<ISuite> suites, String outputpath) 
	{
		report = new ExtentReports(outputpath + File.separator + "Report.html", true);
		for(ISuite suite : suites)
		{
			Map<String, ISuiteResult> result = suite.getResults();
			for(ISuiteResult rs : result.values())
			{
				ITestContext context = rs.getTestContext();
				buildTestNode(context.getPassedTests(), LogStatus.PASS);
				buildTestNode(context.getFailedTests(), LogStatus.FAIL);
				buildTestNode(context.getSkippedTests(), LogStatus.SKIP);
			}
		}
		report.flush();
		report.close();
	}
	private void buildTestNode(IResultMap tests, LogStatus status)
	{
		ExtentTest test;
		if(tests.size()>0)
		{
			for(ITestResult result : tests.getAllResults())
			{
				test = report.startTest(result.getMethod().getMethodName());
				test.setStartedTime(getTime(result.getStartMillis()));
				test.setEndedTime(getTime(result.getEndMillis()));
				
				for(String group : result.getMethod().getGroups())
				{
					test.assignCategory(group);
					if(result.getThrowable() != null)
					{
						test.log(status, result.getThrowable());
					}
					else
					{
						test.log(status, "Test" + status.toString());
					}
				}
				report.endTest(test);
			}
		}
	}
	private Date getTime(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
}
