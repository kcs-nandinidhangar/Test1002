package com.kcsit.qa.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kcsit.qa.constant.ApplicationConstant;
import com.kcsit.qa.dto.TestCase;

/**
 * The Class ExcelWriterUtility.
 */
public class ExcelWriterUtility {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelWriterUtility.class);

	/** The excel writer utility. */
	private static ExcelWriterUtility excelWriterUtility = null;

	/** The date formatter. */
	private static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd-MMM-yyyy");

	/** The excel output file path. */
	private String excelOutputFilePath = "";

	/** The workbook. */
	private Workbook workbook = null;

	/** The sheet. */
	private Sheet sheet = null;

	/**
	 * Instantiates a new excel writer utility.
	 */
	private ExcelWriterUtility() {
		if (excelWriterUtility != null) {
			throw new IllegalStateException("Cann't create new instance as class is Singleton.");
		}
		createTestResultOutputFile();
		createWorkbookSheet();

	}

	/**
	 * Gets the single instance of ExcelWriterUtility.
	 *
	 * @return single instance of ExcelWriterUtility
	 */
	public static ExcelWriterUtility getInstance() {
		if (excelWriterUtility == null) {
			synchronized (ExcelWriterUtility.class) {
				if (excelWriterUtility == null) {
					excelWriterUtility = new ExcelWriterUtility();
				}
			}
		}
		return excelWriterUtility;
	}

	/**
	 * Adds the test case.
	 *
	 * @param testCase the test case
	 */
	public void addTestCase(TestCase testCase) {
		int lastRow = sheet.getLastRowNum() + 1;
		Row row = sheet.createRow(lastRow);

		addCell(row, 0, testCase.getTestCaseNumber());
		addCell(row, 1, testCase.getTestCaseCategory());
		addCell(row, 2, testCase.getDescription());
		addCell(row, 3, testCase.getExpectedResult());
		addCell(row, 4, testCase.getTestEnabled());
		addCell(row, 5, testCase.getActualResult());
		addCell(row, 6, "Automation Tool");
		addCell(row, 7, DATE_FORMATTER.format(new Date()));
		addCell(row, 8, testCase.getFindings());
		addCell(row, 9, testCase.getTestCaseResult());
		addCell(row, 10, testCase.getInputData());

		saveRecord();

	}

	/**
	 * Adds the test summary.
	 *
	 * @param totalTestCases the total test cases
	 * @param successTestCases the success test cases
	 * @param failTestCases the fail test cases
	 */
	public void addTestSummary(Long totalTestCases, Long successTestCases, Long failTestCases) {
		Row totalTestCaseRow = sheet.getRow(2);
		Row automatedTestCaseRow = sheet.getRow(3);
		Row successTestCaseRow = sheet.getRow(4);
		Row failTestCaseRow = sheet.getRow(5);

		totalTestCaseRow.createCell(9, CellType.NUMERIC).setCellValue(totalTestCases);
		automatedTestCaseRow.createCell(9, CellType.NUMERIC).setCellValue(totalTestCases);
		successTestCaseRow.createCell(9, CellType.NUMERIC).setCellValue(successTestCases);
		failTestCaseRow.createCell(9, CellType.NUMERIC).setCellValue(failTestCases);

		saveRecord();
	}

	/**
	 * Creates the cell.
	 *
	 * @param row       the row
	 * @param cellIndex the cell index
	 * @param value     the value
	 */
	private void addCell(Row row, int cellIndex, String value) {
		Cell cell = row.createCell(cellIndex);
		cell.setCellValue(value);
		CellStyle cellStyle = this.workbook.createCellStyle();
		cellStyle.setWrapText(true);
		cell.setCellStyle(cellStyle);

	}

	/**
	 * Creates the test result output file.
	 */
	private void createTestResultOutputFile() {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("ddMMMyyHHmm");
			excelOutputFilePath = ApplicationConstant.OUTPUT_FOLDER + ApplicationConstant.EMAIL_CONTENT_OUTPUT_FILE;
			excelOutputFilePath += "_" + formatter.format(new Date()) + ".xlsx";
			FileUtility.copyResource(ApplicationConstant.TESTCASE_RESULT_TEMPLATE, excelOutputFilePath);
		} catch (IOException exception) {
			LOGGER.error("Failed to create test result output file. Reason - {}", exception.getLocalizedMessage(),
					exception);
		}
	}

	/**
	 * Creates the workbook sheet.
	 */
	private void createWorkbookSheet() {
		try {
			workbook = new XSSFWorkbook(new FileInputStream(this.excelOutputFilePath));
			sheet = workbook.getSheetAt(0);
		} catch (IOException exception) {
			LOGGER.error("Failed to create workbook. Reason - {}", exception.getLocalizedMessage(), exception);
		}
	}

	/**
	 * Save record.
	 */
	private void saveRecord() {
		synchronized (ExcelWriterUtility.class) {
			try (FileOutputStream fileOutStream = new FileOutputStream(this.excelOutputFilePath);) {
				this.workbook.write(fileOutStream);
				fileOutStream.close();
			} catch (IOException exception) {
				LOGGER.error("Failed to save record. Reason - {}", exception.getLocalizedMessage(), exception);
			}
		}
	}
}
