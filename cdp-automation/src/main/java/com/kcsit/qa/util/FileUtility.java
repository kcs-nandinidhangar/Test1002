package com.kcsit.qa.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.junit.platform.launcher.listeners.TestExecutionSummary;

import com.kcsit.qa.constant.ApplicationConstant;

// TODO: Auto-generated Javadoc
/**
 * The Class FileUtility.
 */
public class FileUtility {

	/**
	 * Instantiates a new file utility.
	 */
	private FileUtility() {
		// DUMMY
	}

	/**
	 * Copy resource.
	 *
	 * @param resourceName    the resource name
	 * @param destinationPath the destination path
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void copyResource(String resourceName, String destinationPath) throws IOException {
		InputStream stream = null;
		OutputStream resStreamOut = null;
		try {
			stream = FileUtility.class.getResourceAsStream(resourceName);
			if (stream == null) {
				throw new IOException("Cannot find resource \"" + resourceName + "\"");
			}

			int readBytes;
			byte[] buffer = new byte[4096];
			resStreamOut = new FileOutputStream(destinationPath);
			while ((readBytes = stream.read(buffer)) > 0) {
				resStreamOut.write(buffer, 0, readBytes);
			}
		} catch (IOException exception) {
			exception.printStackTrace();
			throw exception;
		} finally {
			stream.close();
			resStreamOut.close();
		}
	}

	/**
	 * Gets the file as IO stream.
	 *
	 * @param fileName the file name
	 * @return the file as IO stream
	 */
	public static InputStream getFileAsIOStream(final String fileName) {
		InputStream ioStream = FileUtility.class.getClassLoader().getResourceAsStream(fileName);
		if (ioStream == null) {
			throw new IllegalArgumentException(fileName + " is not found");
		}
		return ioStream;
	}

	/**
	 * Gets the file content.
	 *
	 * @param is the is
	 * @return the file content
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static String getFileContent(InputStream is) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		try (InputStreamReader isr = new InputStreamReader(is); BufferedReader br = new BufferedReader(isr);) {
			String line;
			while ((line = br.readLine()) != null) {
				stringBuilder.append(line);
			}
			is.close();
		}
		return stringBuilder.toString();
	}

	/**
	 * Creates the email content.
	 *
	 * @param summary the summary
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void createEmailContent(TestExecutionSummary summary) throws IOException {
		String destinationPath = ApplicationConstant.OUTPUT_FOLDER + ApplicationConstant.EMAIL_CONTENT_OUTPUT_FILE
				+ ".htm";
		copyResource(ApplicationConstant.EMAIL_TEMPLATE_FILE, destinationPath);
		File destinationFile = new File(destinationPath);
		if (destinationFile.exists()) {
			String fileContent = readFileAsString(destinationFile);
			String finalEmailContent = fileContent.replace("${EMAIL_SUMMARY_CONTENT}", HTMLRenderUtility.getSummaryDetails(summary));
			finalEmailContent = finalEmailContent.replace("${EMAIL_TEST_CATEGORY_CONTENT}", HTMLRenderUtility.getTestCategoryDetails());
			writeFile(destinationFile, finalEmailContent, true);
		}
	}

	/**
	 * Write file.
	 *
	 * @param destinationFile   the destination file
	 * @param finalEmailContent the final email content
	 * @param createNew         the create new
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static void writeFile(File destinationFile, String finalEmailContent, boolean createNew)
			throws IOException {
		if (createNew & destinationFile.delete()) {
			destinationFile.createNewFile();
			FileWriter fileWriter = new FileWriter(destinationFile);
			fileWriter.write(finalEmailContent);
			fileWriter.close();
		} else {
			FileWriter fileWriter = new FileWriter(destinationFile, false);
			fileWriter.append(finalEmailContent);
			fileWriter.close();
		}
	}

	/**
	 * Read file as string.
	 *
	 * @param destinationFile the destination file
	 * @return the string
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException           Signals that an I/O exception has occurred.
	 */
	private static String readFileAsString(File destinationFile) throws FileNotFoundException, IOException {
		StringBuilder fileData = new StringBuilder();
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(destinationFile));) {
			while (true) {
				String temp = bufferedReader.readLine();
				if (temp == null) {
					break;
				}
				fileData.append(temp).append("\n");
			}
		}
		return fileData.toString();
	}
}
