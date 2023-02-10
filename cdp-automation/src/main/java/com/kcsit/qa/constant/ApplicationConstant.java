/*
 * 
 */
package com.kcsit.qa.constant;

import java.io.File;

// TODO: Auto-generated Javadoc
/**
 * The Class ApplicationConstant.
 */
public class ApplicationConstant {

	/**
	 * Instantiates a new application constant.
	 */
	private ApplicationConstant() {
		// DUMMY
	}

	/** The Constant TESTCASE_FILE_NAME. */
	public static final String TESTCASE_FILE_NAME = "./input/KCS_CDP_TestCases.xlsx";

	/** The Constant OUTPUT_FOLDER. */
	public static final String OUTPUT_FOLDER = "./output/";

	/** The Constant TESTCASE_RESULT_TEMPLATE. */
	public static final String TESTCASE_RESULT_TEMPLATE = "/KCS_CDP_TestCasesResult_Template.xlsx";

	/** The Constant TESTCASE_RESULT_FILENAME. */
	public static final String TESTCASE_RESULT_FILENAME = "KCS_CDP_TestCasesResult";

	/** The Constant EMAIL_SENT_WINDOW_LOGO_IMAGE. */
	public static final String EMAIL_SENT_WINDOW_LOGO_IMAGE = "./input/sendEmail.jpg";

	/** The Constant EMAIL_CONTENT_FILE. */
	public static final String EMAIL_TEMPLATE_FILE = "/html_template.htm";
	
	/** The Constant EMAIL_CONTENT_OUTPUT_FILE. */
	public static final String EMAIL_CONTENT_OUTPUT_FILE = "KCS_CDP_HTMLEmail";

	/** The Constant CERTIFICATE_FILE_LOCATION. */
	public static final String CERTIFICATE_FILE_LOCATION = "./certificate/trustore.jks";

	/** The trust store file. */
	public static File trustStoreFile = null;

	/** The Constant trustStorePassword. */
	public static final String trustStorePassword = "lavanya";

	/** The Constant TO_MAIL_DEFAULTLIST. */
	public static final String TO_MAIL_DEFAULTLIST = "vaishali.patil@kcsitglobal.com";

	/** The Constant CC_MAIL_DEFAULTLIST. */
	public static final String CC_MAIL_DEFAULTLIST = "";
	
	/** The Constant MAIL_SUBJECT. */
	public static final String MAIL_SUBJECT = "Automation Test report - CDP";

	/** The Constant EMAIl_USERNAME. */
	public static final String EMAIl_USERNAME = "patilvaishub@gmail.com";

	/** The Constant EMAIL_PASSWORD. */
	public static final String EMAIL_PASSWORD = "ihpytatgjhiizhea";

	/** The Constant SMTP_MAIL_HOST. */
	public static final String SMTP_MAIL_HOST = "smtp.gmail.com";

	/** The Constant SMTP_MAIL_SERVER_PORT. */
	public static final String SMTP_MAIL_SERVER_PORT = "465";

	/** The Constant SMTP_MAIL_AUTH. */
	public static final String SMTP_MAIL_AUTH = "true";

	/** The Constant SMTP_MAIL_SOCKET_SERVER_PORT. */
	public static final String SMTP_MAIL_SOCKET_SERVER_PORT = "465";

}
