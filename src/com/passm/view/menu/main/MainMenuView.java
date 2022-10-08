package com.passm.view.menu.main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.passm.view.ConsoleView;
import com.passm.view.View;
import com.passm.view.console.Action;
import com.passm.view.console.Console;
import com.passm.view.console.content.InputListener;

public class MainMenuView extends ConsoleView {

	private final static String TITLE = " Main Menu ";
	private final static String SELECTION = ">> ";

	private final static String[] SELECTABLE_OPTINS = { "List of passwords", "Add new password", "Change password", "Remove password", "Update main password", "Settings" };
	private final Map<String, View> SELECTABLE_OPTINS_TO_VIEWS;

	private int position = 1;

	public MainMenuView(Console console, View previousView) {
		super(console, previousView);
		SELECTABLE_OPTINS_TO_VIEWS = new HashMap<>();
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
		Action selectOption = new OptionSelector(this);
		console.registerAction(InputListener.ENTER, selectOption);
		update();
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
	
	public void select() {
		View nextView = SELECTABLE_OPTINS_TO_VIEWS.get(SELECTABLE_OPTINS[position-1]);
		if(nextView != null) {
			reset();
			nextView.init();
		}
	}

	@Override
	public void reset() {
		super.reset();
		position = 1;
	}

	private void printMenu() {
		Console console = getConsole();
		int leftMarginLength = getLeftMarginLength() - getAverageLengthOfSelectableOptions() / 2 + getTitle().length() / 2;
		String margin = " ".repeat(leftMarginLength);
		String marginWithSelection = " ".repeat(leftMarginLength - SELECTION.length()) + SELECTION;
		console.ln(false);
		console.ln(false);
		for (int i = 0; i < SELECTABLE_OPTINS.length; i++) {
			if (i + 1 == position) {
				console.println(marginWithSelection + SELECTABLE_OPTINS[i], false);
			} else {
				console.println(margin + SELECTABLE_OPTINS[i], false);
			}
		}
		console.refresh();
	}
	
	private int getAverageLengthOfSelectableOptions() {
		return (int) Arrays.asList(SELECTABLE_OPTINS).stream().map(option -> option.length()).mapToInt(Integer::intValue).average().getAsDouble();
	}

	@Override
	public void update() {
		super.update();
		printMenu();
	}

	@Override
	protected String getTitle() {
		return TITLE;
	}
}
