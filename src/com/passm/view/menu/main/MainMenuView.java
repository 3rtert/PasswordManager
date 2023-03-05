package com.passm.view.menu.main;

import java.util.Arrays;
import java.util.logging.Logger;

import com.passm.controller.MainMenuController;
import com.passm.view.ConsoleView;
import com.passm.view.console.Action;
import com.passm.view.console.Console;
import com.passm.view.console.content.InputListener;
import com.passm.view.menu.selector.NextOptionSelector;
import com.passm.view.menu.selector.OptionSelector;
import com.passm.view.menu.selector.PreviousOptionSelector;
import com.passm.view.menu.selector.Selectable;

public class MainMenuView extends ConsoleView<MainMenuController, MainMenuView> implements Selectable {
	
	private final static Logger LOGGER = Logger.getLogger(MainMenuView.class.getName());

	private final static String TITLE = " Main Menu ";
	private final static String SELECTION = ">> ";
	
	private String[] selectableOptions;

	private int position;

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
		Action selectOption = new OptionSelector(this);
		console.registerAction(InputListener.ENTER, selectOption);
		console.registerAction(InputListener.ARROW_RIGHT_CHAR_CODE, selectOption);
		console.registerAction('d', selectOption);
		LOGGER.info("Initialization finished");
		update();
	}
	
	public void setSelectableOptions(String[] selectableOptions) {
		this.selectableOptions = selectableOptions;
	}
	
	@Override
	public void selectNextOption() {
		if(position == selectableOptions.length) {
			position = 1;
		} else {
			position++;
		}
		update();
	}
	
	@Override
	public void selectPreviousOption() {
		if(position == 1) {
			position = selectableOptions.length;
		} else {
			position--;
		}
		update();
	}
	
	@Override
	public void select() {
		controller.select(position);
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
		for (int i = 0; i < selectableOptions.length; i++) {
			if (i + 1 == position) {
				console.println(marginWithSelection + selectableOptions[i], false);
			} else {
				console.println(margin + selectableOptions[i], false);
			}
		}
		console.refresh();
	}
	
	private int getAverageLengthOfSelectableOptions() {
		return (int) Arrays.asList(selectableOptions).stream().map(option -> option.length()).mapToInt(Integer::intValue).average().getAsDouble();
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
	
	@Override
	protected String getName() {
		return MainMenuView.class.getSimpleName();
	}
}
