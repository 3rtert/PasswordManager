package com.passm.view.menu.main;

import com.passm.view.ConsoleView;
import com.passm.view.console.Action;
import com.passm.view.console.Console;
import com.passm.view.console.content.InputListener;

public class MainMenuView extends ConsoleView {

	private final static String TITLE = "Main Menu";
	private final static String SELECTION = ">> ";

	private final static String[] SELECTABLE_OPTINS = { "Option 1", "Option 2", "Exit" };

	private int position = 1;

	public MainMenuView(Console console) {
		super(console);
	}
	
	@Override
	public void init() {
		super.init();
		Console console = getConsole();
		Action previousOptionSelector = new PreviousOptionSelector(this);
		Action nextOptionSelector = new NextOptionSelector(this);
		console.registerAction('w', previousOptionSelector);
		console.registerAction('s', nextOptionSelector);
		console.registerAction(InputListener.ARROW_UP_CHAR_CODE, previousOptionSelector);
		console.registerAction(InputListener.ARROW_DOWN_CHAR_CODE, nextOptionSelector);
	}
	
	public void selectNextOption() {
		if(position == SELECTABLE_OPTINS.length) {
			position = 1;
		} else {
			position++;
		}
		update();
	}
	
	public void selectPreviousOption() {
		if(position == 1) {
			position = SELECTABLE_OPTINS.length;
		} else {
			position--;
		}
		update();
	}

	@Override
	public void reset() {
		position = 1;
		update();
	}

	private void printMenu() {
		printTitle();
		Console console = getConsole();
		int leftMarginLength = getLeftMarginLength() + 1;
		String margin = " ".repeat(leftMarginLength);
		String marginWithSelection = " ".repeat(leftMarginLength - SELECTION.length()) + SELECTION;
		console.ln();
		console.ln();
		for (int i = 0; i < SELECTABLE_OPTINS.length; i++) {
			if (i + 1 == position) {
				console.println(marginWithSelection + SELECTABLE_OPTINS[i]);
			} else {
				console.println(margin + SELECTABLE_OPTINS[i]);
			}
		}
	}

	@Override
	public void update() {
		getConsole().clear();
		printMenu();
	}

	@Override
	protected String getTitle() {
		return TITLE;
	}
}
