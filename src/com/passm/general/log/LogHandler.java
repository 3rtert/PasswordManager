package com.passm.general.log;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

public class LogHandler {

	private final static String CURRENT_DIRECTORY_PROPERTY = "user.dir";
	private final static String LOG_DIRECTORY_NAME = "/logs";
	private final static String LOG_FILE_NAME_PATTERN = "/%g_log.log";
	private final static int MB = 1024 * 1024;
	private final static int LOG_FILE_SIZE_LIMIT = 5 * MB;
	private final static int LOG_FILES_LIMIT = 100;

	static public void setup() {		
		Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        if (handlers[0] instanceof StreamHandler) {
            rootLogger.removeHandler(handlers[0]);
        }
        rootLogger.setLevel(Level.INFO);
        StreamHandler streamHandler = new ConsoleHandler();
        rootLogger.addHandler(streamHandler);
        try {
			String currentDirectory = System.getProperty(CURRENT_DIRECTORY_PROPERTY);
			File logDirectory = new File(currentDirectory + LOG_DIRECTORY_NAME);
			if(!logDirectory.exists()){
				logDirectory.mkdirs();
			}
			Handler fileHandler = new FileHandler(logDirectory+LOG_FILE_NAME_PATTERN, LOG_FILE_SIZE_LIMIT, LOG_FILES_LIMIT);
			fileHandler.setFormatter(new LogFormatter(true));
			rootLogger.addHandler(fileHandler);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
	}	
}


