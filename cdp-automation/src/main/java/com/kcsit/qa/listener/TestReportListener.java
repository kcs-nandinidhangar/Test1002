package com.kcsit.qa.listener;

import java.util.Optional;

import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.TestSource;
import org.junit.platform.engine.support.descriptor.MethodSource;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kcsit.qa.dto.TestCase;
import com.kcsit.qa.util.ExcelWriterUtility;
import com.kcsit.qa.util.LocalStorageContext;

/**
 * The listener interface for receiving testReport events. The class that is
 * interested in processing a testReport event implements this interface, and
 * the object created with that class is registered with a component using the
 * component's <code>addTestReportListener<code> method. When the testReport
 * event occurs, that object's appropriate method is invoked.
 *
 * @see TestReportEvent
 */
public class TestReportListener implements TestExecutionListener {
	
	/** The Constant LOGGER. */
	public static final Logger LOGGER = LoggerFactory.getLogger(TestReportListener.class);

	/**
	 * Execution finished.
	 *
	 * @param testIdentifier      the test identifier
	 * @param testExecutionResult the test execution result
	 */
	@Override
	public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
		Optional<TestSource> testSourceOptional = testIdentifier.getSource();
		if (testSourceOptional.isPresent() && testSourceOptional.get() instanceof MethodSource) {
			TestCase testCase = LocalStorageContext.findTestCase(LocalStorageContext.getTestInformation());
			if (testCase != null) {
				testCase.setTestCaseResult(testExecutionResult.getStatus().toString());
				Optional<Throwable> throwableOptional = testExecutionResult.getThrowable();
				if(throwableOptional.isPresent()) {
					Throwable throwable = throwableOptional.get();
					testCase.setFindings(throwable.getLocalizedMessage());
					LOGGER.error("Found Issue: ", throwable);
				}
				ExcelWriterUtility.getInstance().addTestCase(testCase);
			}
		}

		TestExecutionListener.super.executionFinished(testIdentifier, testExecutionResult);
		LocalStorageContext.removeTestInformation();
	}

	/**
	 * Execution started.
	 *
	 * @param testIdentifier the test identifier
	 */
	@Override
	public void executionStarted(TestIdentifier testIdentifier) {
		TestExecutionListener.super.executionStarted(testIdentifier);
	}

}
