package com.passm.general;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

import com.passm.general.log.ConsoleHandler;
import com.passm.general.log.LogFormatter;

public class LogHandler {

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
			Handler fileHandler = new FileHandler("log/%g_log.log", 1024 * 1024 * 5, 10);
			fileHandler.setFormatter(new LogFormatter(true));
			rootLogger.addHandler(fileHandler);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
	}	
}


