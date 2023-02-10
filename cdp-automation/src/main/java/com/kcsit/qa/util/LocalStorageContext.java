package com.kcsit.qa.util;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.kcsit.qa.dto.TestCase;
import com.kcsit.qa.dto.TestInformation;

/**
 * The Class LocalStorageContext.
 */
public class LocalStorageContext {

	/** The test information storage. */
	private static ThreadLocal<TestInformation> testInformationStorage = new ThreadLocal<>();

	/**
	 * Adds the test information.
	 *
	 * @param testCaseNumber the test case number
	 * @param testCategory   the test category
	 */
	public static void addTestInformation(String testCaseNumber, String testCategory) {
		testInformationStorage.set(new TestInformation(testCaseNumber, testCategory));
	}

	/**
	 * Gets the test information.
	 *
	 * @return the test information
	 */
	public static TestInformation getTestInformation() {
		return testInformationStorage.get();
	}

	/**
	 * Removes the test information.
	 */
	public static void removeTestInformation() {
		testInformationStorage.remove();
	}

	/**
	 * Gets the test cases for category.
	 *
	 * @param testCaseCategory the test case category
	 * @return the test cases for category
	 */
	public static List<TestCase> getTestCasesForCategory(String testCaseCategory) {
		return ExcelReaderUtility.getTestCases(testCaseCategory.toString());
	}

	/**
	 * Find test case.
	 *
	 * @param testInformation the test information
	 * @return the test case
	 */
	public static TestCase findTestCase(TestInformation testInformation) {
		TestCase testCase = null;

		if (testInformation != null && StringUtils.isNotBlank(testInformation.getTestCaseCategory())
				&& StringUtils.isNotBlank(testInformation.getTestCaseNumber())) {
			List<TestCase> testList = ExcelReaderUtility.getTestCases(testInformation.getTestCaseCategory());
			if (CollectionUtils.isNotEmpty(testList)) {
				for (TestCase tempTestCase : testList) {
					if (StringUtils.equalsIgnoreCase(tempTestCase.getTestCaseCategory(),
							testInformation.getTestCaseCategory())
							&& StringUtils.equalsIgnoreCase(tempTestCase.getTestCaseNumber(),
									testInformation.getTestCaseNumber())) {
						testCase = tempTestCase;
						break;
					}
				}
			}
		}
		return testCase;
	}

	/**
	 * Adds the actual result.
	 *
	 * @param testInformation the test information
	 * @param actualResult    the actual result
	 */
	public static void addActualResult(TestInformation testInformation, String actualResult) {
		TestCase testCase = findTestCase(testInformation);
		if (testCase != null) {
			testCase.setActualResult(actualResult);
		}
	}
}
