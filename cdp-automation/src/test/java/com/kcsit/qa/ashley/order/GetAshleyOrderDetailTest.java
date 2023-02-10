package com.kcsit.qa.ashley.order;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.kcsit.qa.authentication.AuthenticationTest;
import com.kcsit.qa.constant.ApplicationConstant;
import com.kcsit.qa.constant.TestCaseCategory;
import com.kcsit.qa.dto.TestCase;
import com.kcsit.qa.util.LocalStorageContext;
import com.kcsit.qa.util.TestUtility;

import io.restassured.http.ContentType;
import io.restassured.response.ResponseBody;

/**
 * The Class GetAshleyOrderDetailTest.
 */
public class GetAshleyOrderDetailTest {

	/** The test case category. */
	private static TestCaseCategory searchOrderByPhoneNoAndOrderNo = TestCaseCategory.SEARCH_ORDER_BY_PHONE_AND_ORDER_NO;

	/**
	 * Test get order detail by phone number and order number.
	 *
	 * @param testCaseNumber the test case number
	 * @param description    the description
	 * @param exceptedResult the excepted result
	 * @param inputData      the input data
	 */
	@ParameterizedTest(name = "#{index} - Test : {2}")
	@MethodSource("testGetOrderDetailByPhoneNumberAndOrderNumberCategory")
	void testGetOrderDetailByPhoneNumberAndOrderNumber(String testCaseNumber, String description, String exceptedResult,
			String inputData) {

		LocalStorageContext.addTestInformation(testCaseNumber, searchOrderByPhoneNoAndOrderNo.toString());

		/* Test Case Modification started from here */
		/* CARA API Request Start */
		String CARA_AUTH_TOKEN = AuthenticationTest.performCaraAuthentication();
		ResponseBody<?> caraResponseBody = given()
				.trustStore(ApplicationConstant.trustStoreFile, ApplicationConstant.trustStorePassword)
				.header("Authorization", CARA_AUTH_TOKEN).contentType(ContentType.JSON).when()
				.get("https://test.cara.ashleyretail.com/self-help/askashley?" + inputData).getBody();
		/* CARA API Request End */

		/* CDP API Request Start */
		String CDP_AUTH_TOKEN = AuthenticationTest.performCdpAuthentication();
		ResponseBody<?> cdpResponseBody = given()
				.trustStore(ApplicationConstant.trustStoreFile, ApplicationConstant.trustStorePassword)
				.header("Authorization", CDP_AUTH_TOKEN).contentType(ContentType.JSON).when()
				.get("https://cdp.stage.ashleyretail.com/askashley/self-help/askashley?" + inputData).getBody();
		/* CDP API Request End */
		/* Test Case Modification end from here */
		
		TestUtility.updateActualAndExpectedResult(testCaseNumber, searchOrderByPhoneNoAndOrderNo.toString(),
				caraResponseBody, cdpResponseBody);
		TestUtility.compareExpectedWithActual(caraResponseBody, cdpResponseBody);
	}

	/**
	 * Test get order detail by phone number and order number category.
	 *
	 * @return the stream
	 */
	private static Stream<Arguments> testGetOrderDetailByPhoneNumberAndOrderNumberCategory() {
		List<TestCase> testCases = LocalStorageContext
				.getTestCasesForCategory(searchOrderByPhoneNoAndOrderNo.toString());
		List<Arguments> arguments = new ArrayList<>();
		for (TestCase testCase : testCases) {
			arguments.add(Arguments.of(testCase.getTestCaseNumber(), testCase.getDescription(),
					testCase.getExpectedResult(), testCase.getInputData()));
		}
		return arguments.stream();
	}
}
