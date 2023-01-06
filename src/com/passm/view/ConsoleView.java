package com.passm.view;

import java.util.logging.Logger;

import com.passm.controller.Controller;
import com.passm.view.console.Action;
import com.passm.view.console.Console;
import com.passm.view.console.content.InputListener;

public abstract class ConsoleView<T extends Controller> implements View<T> {
	
	private final static Logger LOGGER = Logger.getLogger(ConsoleView.class.getName());
	
	private final static String ORNAMENT = "-";

	private final Console console;
	
	protected T controller;
	
	protected ConsoleView(Console console) {
		this.console = console;
		
	}
	
	@Override
	public void setController(T controller) {
		this.controller = controller;
	}
	
	public Console getConsole() {
		return console;
	}
	
	@Override
	public void init() {
		LOGGER.info("Start initialization of " + getName());
		reset();
		Action preciousViewStarter = new PreciousViewStarter(this, controller);
		console.registerAction(InputListener.ESCAPE, preciousViewStarter);
	}
	
	@Override
	public void reset() {
		LOGGER.info("Start reset of " + getName());
		console.clear();
		console.clearActions();
	}
	
	@Override
	public void update() {
		LOGGER.info("Start update of " + getName());
		console.clear();
		printTitle();
	}
	
	protected void printTitle() {
		int widthInCharacters = console.getWidthInCharacters();
		int withOfLeftOrnament = (widthInCharacters - getTitle().length()) / 2 / ORNAMENT.length();
		int withOfRightOrnament = (getTitle().length() % 2 == 0) ? withOfLeftOrnament : withOfLeftOrnament + 1;
		StringBuilder fullOrnament = new StringBuilder();
		fullOrnament.append(ORNAMENT.repeat(withOfLeftOrnament));
		fullOrnament.append(getTitle());
		fullOrnament.append(ORNAMENT.repeat(withOfRightOrnament));
		console.print(fullOrnament.toString(), false);
	}
	
	protected int getLeftMarginLength() {
		return (console.getWidthInCharacters() - getTitle().length()) / 2 / ORNAMENT.length();
	}
	
	abstract protected String getTitle();
	
	abstract protected String getName();
}
