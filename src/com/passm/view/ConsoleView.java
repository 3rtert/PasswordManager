package com.passm.view;

import com.passm.view.console.Action;
import com.passm.view.console.Console;
import com.passm.view.console.content.InputListener;

public abstract class ConsoleView implements View {
	
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
		reset();
		Action preciousViewStarter = new PreciousViewStarter(this);
		console.registerAction(InputListener.ESCAPE, preciousViewStarter);
	}
	
	@Override
	public void reset() {
		console.clear();
		console.clearActions();
	}
	
	@Override
	public void update() {
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
	
	@Override
	public void startPreviousView() {
		if(previusView == null) {
			console.stop();
		}
		reset();
		previusView.init();
	}
}
