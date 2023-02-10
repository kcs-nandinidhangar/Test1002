package com.kcsit.qa.util;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import com.kcsit.qa.dto.TestCase;

public class HTMLRenderUtility {

	private HTMLRenderUtility() {
		//DUMMY
	}
	
	public static String getSummaryDetails(TestExecutionSummary testExecutionSummary) {
		StringBuilder htmlBuilder = new StringBuilder();
		htmlBuilder.append("<table>");
			htmlBuilder.append("<tr>");
				htmlBuilder.append("<td style=\"border: 1px solid white; border-collapse: collapse; background-color: #F7DC6F;\" >Number Of Test Cases</td>");
				htmlBuilder.append("<td style=\"border: 1px solid white; border-collapse: collapse; background-color: #F7DC6F;\" > : </td>");
				htmlBuilder.append("<td style=\"border: 1px solid white; border-collapse: collapse; background-color: #F7DC6F;\" >" + testExecutionSummary.getTestsStartedCount() + "</td>");
			htmlBuilder.append("</tr>");
			htmlBuilder.append("<tr>");
				htmlBuilder.append("<td style=\"border: 1px solid white; border-collapse: collapse; background-color: #F7DC6F;\" >Number Of Automated Test Cases</td>");
				htmlBuilder.append("<td style=\"border: 1px solid white; border-collapse: collapse; background-color: #F7DC6F;\" > : </td>");
				htmlBuilder.append("<td style=\"border: 1px solid white; border-collapse: collapse; background-color: #F7DC6F;\" >" + testExecutionSummary.getTestsStartedCount() + "</td>");
			htmlBuilder.append("</tr>");
			htmlBuilder.append("<tr>");
				htmlBuilder.append("<td style=\"border: 1px solid white; border-collapse: collapse; background-color: #F7DC6F;\" >Successful Test Cases</td>");
				htmlBuilder.append("<td style=\"border: 1px solid white; border-collapse: collapse; background-color: #F7DC6F;\" > : </td>");
				htmlBuilder.append("<td style=\"border: 1px solid white; border-collapse: collapse; background-color: #F7DC6F;\" >" + testExecutionSummary.getTestsSucceededCount() + "</td>");
			htmlBuilder.append("</tr>");
			htmlBuilder.append("<tr>");
				htmlBuilder.append("<td style=\"border: 1px solid white; border-collapse: collapse; background-color: #F7DC6F;\" >Failure Test Cases</td>");
				htmlBuilder.append("<td style=\"border: 1px solid white; border-collapse: collapse; background-color: #F7DC6F;\" > : </td>");
				htmlBuilder.append("<td style=\"border: 1px solid white; border-collapse: collapse; background-color: #F7DC6F;\" >" + testExecutionSummary.getTestsFailedCount() + "</td>");
			htmlBuilder.append("</tr>");
		htmlBuilder.append("</table>");
		return htmlBuilder.toString();
	}
	
	public static String getTestCategoryDetails() {
		StringBuilder htmlBuilder = new StringBuilder();
		Map<String, List<TestCase>> groupTestCase = ExcelReaderUtility.getAllTestCases().stream().collect(Collectors.groupingBy(TestCase::getTestCaseCategory));
		htmlBuilder.append("<table>");
		htmlBuilder.append("<tr>");
			htmlBuilder.append("<th style=\"border: 1px solid white; border-collapse: collapse; background-color: #F7DC6F;\" >Test Case Category</td>");
			htmlBuilder.append("<th style=\"border: 1px solid white; border-collapse: collapse; background-color: #F7DC6F;\" >Total Test Cases</td>");
			htmlBuilder.append("<th style=\"border: 1px solid white; border-collapse: collapse; background-color: #F7DC6F;\" >Non Automated Test Cases</td>");
			htmlBuilder.append("<th style=\"border: 1px solid white; border-collapse: collapse; background-color: #F7DC6F;\" >Automated Test Cases</td>");
			htmlBuilder.append("<th style=\"border: 1px solid white; border-collapse: collapse; background-color: #F7DC6F;\" >Success Test Cases</td>");
			htmlBuilder.append("<th style=\"border: 1px solid white; border-collapse: collapse; background-color: #F7DC6F;\" >Failure Test Cases</td>");
		htmlBuilder.append("</tr>");
		groupTestCase.entrySet().forEach(entrySet -> {
			htmlBuilder.append("<tr>");
			String testCategory = entrySet.getKey();
				htmlBuilder.append("<td style=\"border: 1px solid white; border-collapse: collapse; background-color: #F7DC6F;\" >" + testCategory + "</td>");
			List<TestCase> testCases = entrySet.getValue();
			AtomicInteger testCaseCount = new AtomicInteger(0);
			AtomicInteger successTestCases = new AtomicInteger(0);
			AtomicInteger failureTestCases = new AtomicInteger(0);
			AtomicInteger nonAutomatedTestCases = new AtomicInteger(0);
			testCases.forEach(testCase -> {
				testCaseCount.incrementAndGet();
				if(StringUtils.equalsIgnoreCase("SUCCESSFUL", testCase.getTestCaseResult())) {
					successTestCases.incrementAndGet();
				}else if(StringUtils.equalsIgnoreCase("FAILED", testCase.getTestCaseResult())) {
					failureTestCases.incrementAndGet();
				} else {
					nonAutomatedTestCases.incrementAndGet();
				}
			});
				htmlBuilder.append("<td style=\"border: 1px solid white; border-collapse: collapse; background-color: #F7DC6F;\" >" + testCaseCount.get() + "</td>");
				htmlBuilder.append("<td style=\"border: 1px solid white; border-collapse: collapse; background-color: #F7DC6F;\" >" + nonAutomatedTestCases.get() + "</td>");
				htmlBuilder.append("<td style=\"border: 1px solid white; border-collapse: collapse; background-color: #F7DC6F;\" >" + (testCaseCount.get() - nonAutomatedTestCases.get()) + "</td>");
				htmlBuilder.append("<td style=\"border: 1px solid white; border-collapse: collapse; background-color: #F7DC6F;\" >" + successTestCases.get() + "</td>");
				htmlBuilder.append("<td style=\"border: 1px solid white; border-collapse: collapse; background-color: #F7DC6F;\" >" + failureTestCases.get() + "</td>");
			htmlBuilder.append("</tr>");
			
			
			
		});
		htmlBuilder.append("</table>");
		return htmlBuilder.toString();
	}
	
}
