package com.passm.controller;

import java.util.logging.Logger;

import com.passm.model.config.Configuration;
import com.passm.view.View;
import com.passm.view.console.Console;

abstract public class ConsoleController<C extends Controller<C, V>, V extends View<C, V>> implements Controller<C, V> {
	
	private final static Logger LOGGER = Logger.getLogger(ConsoleController.class.getName());

	protected ConsoleController<?,?> previousController;
	protected V view;
	protected Configuration configuration;
	
	public ConsoleController(ConsoleController<?,?> previousController, V view, Configuration configuration) {
		this.previousController = previousController;
		setConfig(configuration);
		setView(view);
	}
	
	@Override
	public void setView(V view) {
		this.view = view;
	}
	
	@Override
	public void startPreviousView(Console console, View<?,?> view) {
		if(previousController == null) {
			LOGGER.info("Application stopped by ESCAPE");
			console.stop();
		}
		LOGGER.info("Go to previous view by ESCAPE");
		view.reset();
		previousController.run();
	}
	
	@Override
	public void setConfig(Configuration configuration) {
		this.configuration = configuration;
	}
	
	@Override
	public void run() {
		view.setController(getInstance());
		view.init();
	}
	
	@Override
	abstract public C getInstance();
}
