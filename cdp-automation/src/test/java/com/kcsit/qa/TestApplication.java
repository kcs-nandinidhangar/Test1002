package com.kcsit.qa;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kcsit.qa.constant.ApplicationConstant;
import com.kcsit.qa.listener.TestReportListener;
import com.kcsit.qa.util.EmailSenderUtility;
import com.kcsit.qa.util.ExcelWriterUtility;
import com.kcsit.qa.util.FileUtility;

/**
 * The Class TestApplication.
 */
public class TestApplication {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(TestApplication.class);

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws IOException {
		ApplicationConstant.trustStoreFile = new File(ApplicationConstant.CERTIFICATE_FILE_LOCATION);
		if (ApplicationConstant.trustStoreFile.exists()) {
			LOGGER.info("Truststore file found.....using password as lavanya to access it...");
		}

		LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
				.selectors(selectPackage("com.kcsit.qa.ashley.order"),
						selectClass("com.kcsit.qa.ashley.order.GetCTIBySalesOrder"),
						selectClass("com.kcsit.qa.ashley.order.GetCTIDetails"),
						selectClass("com.kcsit.qa.ashley.order.GetCTIGetDeliveryDetails"),
						selectClass("com.kcsit.qa.ashley.order.SalesOrderSummary"),
						selectClass("com.kcsit.qa.ashley.order.PurchaseHistoryReport")
						/*selectClass("com.kcsit.qa.ashley.order.GetGlobalSalesOrderHeaderDetails"),
						selectClass("com.kcsit.qa.ashley.order.GetSalesOrder"),
						selectClass("com.kcsit.qa.ashley.order.GetSalesOrderHeader"),

						selectClass("com.kcsit.qa.ashley.order.GetSalesOrderLineItem")*/)
				.build();

		Launcher launcher = LauncherFactory.create();
		SummaryGeneratingListener listener = new SummaryGeneratingListener();
		TestReportListener testReportListener = new TestReportListener();
		launcher.registerTestExecutionListeners(listener, testReportListener);
		launcher.execute(request);

		TestExecutionSummary summary = listener.getSummary();
		printReport(summary);

		int input = JOptionPane.showConfirmDialog(null, "Do you want to send email report?",
				"Email Sender Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
		if (input == JOptionPane.YES_OPTION) {
			EmailSenderUtility.createWindow();
		}
	}

	/**
	 * Prints the report.
	 *
	 * @param summary the summary
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static void printReport(TestExecutionSummary summary) throws IOException {
		ExcelWriterUtility.getInstance().addTestSummary(summary.getTestsStartedCount(),
				summary.getTestsSucceededCount(), summary.getTestsFailedCount());

		FileUtility.createEmailContent(summary);

		System.out.println("\n-------- TEST EXECUTION SUMMARY ---------");
		System.out.println("Total Tests     : " + summary.getTestsStartedCount());
		System.out.println("Tests succeeded : " + summary.getTestsSucceededCount());
		System.out.println("Tests failed    : " + summary.getTestsFailedCount());
		System.out.println("------------------------------------------");
	}

}
