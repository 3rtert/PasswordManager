package com.passm.general.log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter {

	private final static String SEPARATOR = "|";
	private final static String MESSAGE_SEPARATOR = " >> ";
	private final static String NEW_LINE = "\n";
	private final static String DOT = ".";
	private final static String DOT_REGEX = "\\.";
	private final static String DATE_FORMAT_STRING = "hh:mm:ss:SSS";
	private final static String DATE_FORMAT_STRING_FOR_FILE = "yyyy-MM-dd hh:mm:ss:SSS";
	private final static DateFormat DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT_STRING);
	private final static DateFormat DATE_FORMAT_FOR_FILE = new SimpleDateFormat(DATE_FORMAT_STRING_FOR_FILE);
	
	private final boolean forFile;
	
	public LogFormatter(boolean forFile) {
		this.forFile = forFile;
	}

	@Override
	public String format(LogRecord record) {
		StringBuilder sb = new StringBuilder();
		sb.append((forFile ? DATE_FORMAT_FOR_FILE : DATE_FORMAT).format(new Date(record.getMillis())));
		sb.append(SEPARATOR);
		sb.append(record.getLevel());
		sb.append(SEPARATOR);
		sb.append(forFile ? record.getLoggerName() : getClassName(record.getLoggerName()));
		sb.append(DOT);
		sb.append(record.getSourceMethodName());
		sb.append(MESSAGE_SEPARATOR);
		sb.append(formatMessage(record).strip());
		sb.append(NEW_LINE);
		return sb.toString();
	}
	
	private String getClassName(String fullName) {
		String[] splitedName = fullName.split(DOT_REGEX);
		return splitedName[splitedName.length-1];
	}
}
