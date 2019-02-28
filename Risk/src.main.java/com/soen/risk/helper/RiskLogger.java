package com.soen.risk.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class RiskLogger {
	private final Logger logger = Logger.getLogger(RiskLogger.class
			.getName());
	private FileHandler fh = null;

	public RiskLogger() {

		SimpleDateFormat format = new SimpleDateFormat("M-d_HHmmss");
		try {
			fh = new FileHandler("G:/temp/test/MyLogFile_"
					+ format.format(Calendar.getInstance().getTime()) + ".log");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// fh.setFormatter(new SimpleFormatter());
		fh.setFormatter(new Formatter() {
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
		logger.addHandler(fh);

	}

	public void doLogging(String log) {
		logger.info(""+log);
	}
}
