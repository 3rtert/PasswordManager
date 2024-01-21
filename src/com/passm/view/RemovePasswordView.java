package com.passm.view;

import java.util.List;
import java.util.logging.Logger;

import com.passm.controller.RemovePasswordController;
import com.passm.view.console.Action;
import com.passm.view.console.Console;
import com.passm.view.console.content.InputListener;
import com.passm.view.menu.selector.NextOptionSelector;
import com.passm.view.menu.selector.OptionSelector;
import com.passm.view.menu.selector.PreviousOptionSelector;
import com.passm.view.menu.selector.Selectable;

public class RemovePasswordView extends ConsoleView<RemovePasswordController, RemovePasswordView> implements Selectable {

	private final static Logger LOGGER = Logger.getLogger(RemovePasswordView.class.getName());
	
	private final static String TITLE = " Remove password ";
	private final static String SELECTION = ">> ";
	
	private List<String> passwordNames;
	private int position;
	
	public RemovePasswordView(Console console) {
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
		passwordNames = controller.getPasswordsNameList();
		update();
	}
	
	@Override
	public void reset() {
		super.reset();
		position = 1;
	}
	
	@Override
	public void update() {
		super.update();
		printList();
	}

	private void printList() {
		Console console = getConsole();
		int leftMarginLength = getLeftMarginLength() - getAverageLengthOfPasswordNames() / 2 + getTitle().length() / 2;
		String margin = " ".repeat(leftMarginLength);
		String marginWithSelection = " ".repeat(leftMarginLength - SELECTION.length()) + SELECTION;
		console.ln(false);
		console.ln(false);
		for (int i = 0; i < passwordNames.size(); i++) {
			if (i + 1 == position) {
				console.println(marginWithSelection + passwordNames.get(i), false);
			} else {
				console.println(margin + passwordNames.get(i), false);
			}
		}
		console.refresh();
	}

	private int getAverageLengthOfPasswordNames() {
		if(passwordNames.isEmpty())
			return 0;
		return (int) passwordNames.stream().map(String::length).mapToInt(Integer::intValue).average().getAsDouble();
	}
	
	@Override
	protected String getTitle() {
		return TITLE;
	}

	@Override
	protected String getName() {
		return RemovePasswordView.class.getSimpleName();
	}

	@Override
	public void selectNextOption() {
		if(position == passwordNames.size()) {
			position = 1;
		} else {
			position++;
		}
		update();
	}

	@Override
	public void selectPreviousOption() {
		if(position == 1) {
			position = passwordNames.size();
		} else {
			position--;
		}
		update();
	}

	@Override
	public void select() {
		int passwordIndex = position-1;
		LOGGER.info("Id of password selected to removal: " + passwordIndex);
		controller.askConfirmation(passwordNames.get(passwordIndex), passwordIndex);
	}

}
