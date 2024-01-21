package com.passm.view;

import java.util.logging.Logger;

import com.passm.controller.Controller;
import com.passm.view.console.Action;
import com.passm.view.console.Console;
import com.passm.view.console.content.InputListener;

public abstract class ConsoleView<C extends Controller<C, V>, V extends View<C,V>> implements View<C,V> {
	
	private final static Logger LOGGER = Logger.getLogger(ConsoleView.class.getName());
	
	private final static String ORNAMENT = "-";

	private final Console console;
	protected C controller;
	
	protected ConsoleView(Console console) {
		this.console = console;
		
	}
	
	public Console getConsole() {
		return console;
	}
	
	@Override
	public void setController(C controller) {
		this.controller = controller;
	}
	
	@Override
	public void init() {
		LOGGER.info("Start initialization of " + getName());
		reset();
		Action preciousViewStarter = new PreciousViewStarter(this, controller);
		console.registerAction(InputListener.ARROW_LEFT_CHAR_CODE, preciousViewStarter);
		console.registerAction(InputListener.ESCAPE, preciousViewStarter);
		console.registerAction('a', preciousViewStarter);
	}
	
	@Override
	public void reset() {
		LOGGER.info("Start reset of " + getName());
		console.clear();
		console.clearActions();
		console.diableListening();
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
		String fullOrnament = ORNAMENT.repeat(withOfLeftOrnament) +
				getTitle() +
				ORNAMENT.repeat(withOfRightOrnament);
		console.print(fullOrnament, false);
	}
	
	protected int getLeftMarginLength() {
		return (console.getWidthInCharacters() - getTitle().length()) / 2 / ORNAMENT.length();
	}
	
	abstract protected String getTitle();
	
	abstract protected String getName();
}
