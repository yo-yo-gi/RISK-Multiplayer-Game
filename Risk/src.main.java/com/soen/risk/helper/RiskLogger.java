/*
 * 
 */
package com.soen.risk.helper;

import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

// TODO: Auto-generated Javadoc
/**
 * <h2>Risk Logger Class</h2>
 * The Class RiskLogger for creating unique log files for every running instance.
 * 
 * @author Chirag Vora
 * @version 1.0
 */

public class RiskLogger {

	/** The logger. */
	private final Logger logger = Logger.getLogger(RiskLogger.class.getName());

	/** The fileHandler. */
	private FileHandler fileHandler = null;

	/**
	 * Instantiates a new risk logger.
	 */

	public RiskLogger() {

		String logFilePath=Paths.get(System.getProperty("user.dir") + "/src.main.resources/logs/RiskLog").toAbsolutePath().toString();
		SimpleDateFormat format = new SimpleDateFormat("M-d_HHmmss");
		try {
			fileHandler = new FileHandler(logFilePath+ format.format(Calendar.getInstance().getTime()) + ".log");
		} catch (Exception e) {
			e.printStackTrace();
		}

		fileHandler.setFormatter(new Formatter() {
			@Override
			public String format(LogRecord record) {
				SimpleDateFormat logTime = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
				Calendar cal = new GregorianCalendar();
				cal.setTimeInMillis(record.getMillis());
				return record.getLevel()
						+ logTime.format(cal.getTime())
						+ " || "
						+ record.getSourceClassName().substring(
								record.getSourceClassName().lastIndexOf(".")+1,
								record.getSourceClassName().length())
						+ "."
						+ record.getSourceMethodName()
						+ "() : "
						+ record.getMessage() + "\n";
			}
		});
		logger.addHandler(fileHandler);
		logger.setUseParentHandlers(false);

	}

	/**
	 * Do logging.
	 *
	 * @param log the log
	 */

	public void doLogging(String log) {
		logger.info(""+log);
	}
}
