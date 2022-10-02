package com.passm.view;

import com.passm.view.console.Console;

public abstract class ConsoleView implements View {
	
	private final static String ORNAMENT = "-";

	private final Console console;
	
	protected ConsoleView(Console console) {
		this.console = console;
	}
	
	protected Console getConsole() {
		return console;
	}
	
	@Override
	public void init() {
		reset();
	}
	
	protected void printTitle() {
		int widthInCharacters = console.getWidthInCharacters();
		int withOfLeftOrnament = (widthInCharacters - getTitle().length() - 2) / 2 / ORNAMENT.length();
		int withOfRightOrnament = (getTitle().length() % 2 == 0) ? withOfLeftOrnament : withOfLeftOrnament + 1;
		StringBuilder fullOrnament = new StringBuilder();
		fullOrnament.append(ORNAMENT.repeat(withOfLeftOrnament));
		fullOrnament.append(" ");
		fullOrnament.append(getTitle());
		fullOrnament.append(" ");
		fullOrnament.append(ORNAMENT.repeat(withOfRightOrnament));
		console.print(fullOrnament.toString());
	}
	
	protected int getLeftMarginLength() {
		return (console.getWidthInCharacters() - getTitle().length() - 2) / 2 / ORNAMENT.length();
	}
	
	abstract protected String getTitle();
}
