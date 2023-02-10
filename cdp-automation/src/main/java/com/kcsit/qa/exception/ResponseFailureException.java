package com.kcsit.qa.exception;

/**
 * The Class ResponseFailureException.
 */
public class ResponseFailureException extends RuntimeException {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new response failure exception.
	 *
	 * @param exceptionMessage the exception message
	 */
	public ResponseFailureException(String exceptionMessage){
		super(exceptionMessage);
	}
}
