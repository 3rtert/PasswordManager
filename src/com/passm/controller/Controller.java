package com.passm.controller;

import java.util.logging.Logger;

import com.passm.view.View;
import com.passm.view.console.Console;

abstract public class Controller {
	
	private final static Logger LOGGER = Logger.getLogger(Controller.class.getName());

	Controller previousController;
	
	public Controller(Controller previousController) {
		this.previousController = previousController;
	}
	
	public void startPreviousView(Console console, View<?> view) {
		if(previousController == null) {
			LOGGER.info("Application stoped by ESCAPE");
			console.stop();
		}
		LOGGER.info("Go to previous view by ESCAPE");
		view.reset();
		previousController.run();
	}
	
	abstract public void run();
}
