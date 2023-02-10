package com.kcsit.qa.constant;

/**
 * The Enum TestCaseCategory.
 */
public enum TestCaseCategory {

	/** The get customer. */
	GET_CUSTOMER("GET_CUSTOMER"),

	/** The product. */
	SEARCH_ORDER_BY_PHONE_AND_ORDER_NO("SEARCH_ORDER_BY_PHONE_AND_ORDER_NO"),
	SEARCH_ORDER_BY_PHONE_AND_ORDER_NO_VOIP("SEARCH_ORDER_BY_PHONE_AND_ORDER_NO_VOIP"),
	SEARCH_ORDER_BY_PHONE_AND_ORDER_NO_ASHCOMM("SEARCH_ORDER_BY_PHONE_AND_ORDER_NO_ASHCOMM"),
	GLOBAL_SALES_ORDER_HEADER_SEARCH("GLOBAL_SALES_ORDER_HEADER_SEARCH"),
	SALES_ORDER_HEADER_SEARCH("SALES_ORDER_HEADER_SEARCH"),
	SALES_ORDER_SEARCH("SALES_ORDER_SEARCH"),
	SALES_ORDER_LINE_SEARCH("SALES_ORDER_LINE_SEARCH"),
	CTI_DETAILS("CTI_DATA_DETAILS"),
	CTI_GET_DELIVERY_DETAILS("CTI_GET_DELIVERY_DATA_DETAILS"),
	CTI_SET_DELIVERY_DETAILS("CTI_SET_DELIVERY_DATA_DETAILS"),
	CTI_BY_SO_DETAILS("CTI_BY_SO_DATA_DETAILS"),
	DT_ROUTING_SEARCH("DT_ROUTING_API_SEARCH"),
	DT_ROUTING_HISTORY_SEARCH("DT_ROUTING_HISTORY_SEARCH"),
	DT_DELIVERY_CONFIRMATION_SEARCH("DT_DELIVERY_CONFIRMATION_SEARCH"),
	VOIP_CONFIRMATION_GET_SEARCH("VOIP_CONFIRMATION_GET_SEARCH"),
	SALES_ORDER_SUMMARY_SEARCH("SALES_ORDER_SUMMARY_SEARCH"),
	PURCHASE_HISTORY_REPORTS_DETAILS("PURCHASE_HISTORY_REPORTS_DETAILS");
	
	

	/** The test case category. */
	private final String testCaseCategory;

	/**
	 * Instantiates a new test case category.
	 *
	 * @param testCaseCategory the test case category
	 */
	private TestCaseCategory(String testCaseCategory) {
		this.testCaseCategory = testCaseCategory;
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
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return this.getTestCaseCategory();
	}
}
