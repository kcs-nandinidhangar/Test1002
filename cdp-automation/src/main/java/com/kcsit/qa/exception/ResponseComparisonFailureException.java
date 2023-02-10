package com.kcsit.qa.exception;

/**
 * The Class ResponseComparisonFailureException.
 */
public class ResponseComparisonFailureException extends RuntimeException {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new response comparison failure exception.
	 *
	 * @param exceptionMessage the exception message
	 */
	public ResponseComparisonFailureException(String exceptionMessage) {
		super(exceptionMessage);
	}
}
