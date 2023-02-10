package com.kcsit.qa.dto;

/**
 * The Class TestInformation.
 */
public class TestInformation {
	/** The test case number. */
	private String testCaseNumber;

	/** The test case category. */
	private String testCaseCategory;

	/**
	 * Instantiates a new test information.
	 */
	public TestInformation() {
		// DUMMY
	}
	
	/**
	 * Instantiates a new test information.
	 *
	 * @param testCaseNumber the test case number
	 * @param testCaseCategory the test case category
	 */
	public TestInformation(String testCaseNumber, String testCaseCategory) {
		super();
		this.testCaseNumber = testCaseNumber;
		this.testCaseCategory = testCaseCategory;
	}

	/**
	 * Gets the test case number.
	 *
	 * @return the test case number
	 */
	public String getTestCaseNumber() {
		return testCaseNumber;
	}

	/**
	 * Sets the test case number.
	 *
	 * @param testCaseNumber the new test case number
	 */
	public void setTestCaseNumber(String testCaseNumber) {
		this.testCaseNumber = testCaseNumber;
	}

	/**
	 * Gets the test case category.
	 *
	 * @return the test case category
	 */
	public String getTestCaseCategory() {
		return testCaseCategory;
	}

	/**
	 * Sets the test case category.
	 *
	 * @param testCaseCategory the new test case category
	 */
	public void setTestCaseCategory(String testCaseCategory) {
		this.testCaseCategory = testCaseCategory;
	}

}
