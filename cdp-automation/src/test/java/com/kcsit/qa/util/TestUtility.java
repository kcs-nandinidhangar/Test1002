package com.kcsit.qa.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kcsit.qa.dto.TestCase;
import com.kcsit.qa.dto.TestInformation;
import com.kcsit.qa.exception.ResponseFailureException;

import io.restassured.response.ResponseBody;

/**
 * The Class TestUtility.
 */
public class TestUtility {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(TestUtility.class);

	/**
	 * Instantiates a new test utility.
	 */
	private TestUtility() {
		// DUMMY
	}

	/**
	 * Gets the string from list.
	 *
	 * @param list the list
	 * @return the string from list
	 */
	public static String getStringFromList(List<String> list) {
		StringBuilder stringBuilder = new StringBuilder();
		int count = 1;
		for (String resultLine : list) {
			stringBuilder.append("Finding - " + count++ + " : ").append(resultLine).append("\n");
		}
		return stringBuilder.toString();
	}

	/**
	 * Update actual and expected result.
	 *
	 * @param testCaseNumber the test case number
	 * @param testCaseCategory the test case category
	 * @param expectedResponseBody the expected response body
	 * @param actualResponseBody the actual response body
	 */
	public static void updateActualAndExpectedResult(String testCaseNumber, String testCaseCategory,
			ResponseBody<?> expectedResponseBody, ResponseBody<?> actualResponseBody) {
		TestCase testCase = LocalStorageContext.findTestCase(new TestInformation(testCaseNumber, testCaseCategory));
		if (testCase != null) {
			testCase.setExpectedResult(expectedResponseBody.asString());
			testCase.setActualResult(actualResponseBody.asString());
		}
	}

	/**
	 * Update actual and expected result.
	 *
	 * @param testCaseNumber the test case number
	 * @param testCaseCategory the test case category
	 * @param responseBody the response body
	 */
	public static void updateActualResult(String testCaseNumber, String testCaseCategory, ResponseBody<?> responseBody) {
		TestCase testCase = LocalStorageContext.findTestCase(new TestInformation(testCaseNumber, testCaseCategory));
		if (testCase != null) {
			testCase.setActualResult(responseBody.asString());
		}
	}
	
	/**
	 * Compare expected with actual.
	 *
	 * @param caraResponseBody the cara response body
	 * @param cdpResponseBody the cdp response body
	 */
	public static void compareExpectedWithActual(ResponseBody<?> caraResponseBody, ResponseBody<?> cdpResponseBody) {
		String findings = "";
		try {
			findings = TestUtility.getStringFromList(
					JSONComparisonUtility.compareJson(caraResponseBody.asString(), cdpResponseBody.asString()));
		} catch (JsonProcessingException exception) {
			LOGGER.error("Failed to parse response JSON. Reason - {}", exception.getLocalizedMessage(), exception);
			throw new ResponseFailureException(
					"Failed to parse response JSON. Reason - " + exception.getLocalizedMessage());
		}

		if (StringUtils.isNotBlank(findings)) {
			throw new ResponseFailureException(findings);
		}
	}
}
