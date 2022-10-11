package com.passm.view;

import java.util.logging.Logger;

import com.passm.view.console.Action;
import com.passm.view.console.Console;
import com.passm.view.console.content.InputListener;

public abstract class ConsoleView implements View {
	
	private final static Logger LOGGER = Logger.getLogger(ConsoleView.class.getName());
	
	private final static String ORNAMENT = "-";

	private final Console console;
	private View previusView;
	
	protected ConsoleView(Console console) {
		this.console = console;
		
	}
	
	protected ConsoleView(Console console, View previusView) {
		this.console = console;
		this.previusView = previusView;
	}
	
	protected Console getConsole() {
		return console;
	}
	
	@Override
	public void init() {
		LOGGER.info("Start initialization of " + getName());
		reset();
		Action preciousViewStarter = new PreciousViewStarter(this);
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
	
	@Override
	public void startPreviousView() {
		if(previusView == null) {
			LOGGER.info("Application stoped by ESCAPE");
			console.stop();
		}
		LOGGER.info("Go to previous view by ESCAPE");
		reset();
		previusView.init();
	}
	
	abstract protected String getTitle();
	
	abstract protected String getName();
}
