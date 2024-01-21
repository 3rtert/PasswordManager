package com.passm.view.confirmation;

import java.util.logging.Logger;

import com.passm.controller.confirmation.ConfirmationController;
import com.passm.view.ConsoleView;
import com.passm.view.console.Action;
import com.passm.view.console.Console;
import com.passm.view.console.content.InputListener;
import com.passm.view.menu.selector.NextOptionSelector;
import com.passm.view.menu.selector.OptionSelector;
import com.passm.view.menu.selector.PreviousOptionSelector;
import com.passm.view.menu.selector.Selectable;

public class ConfirmationView extends ConsoleView<ConfirmationController, ConfirmationView> implements Selectable {

	private final static Logger LOGGER = Logger.getLogger(ConfirmationView.class.getName());
	
	private final static String SELECTION = ">> ";
	private final static String YES_LABEL = "[Yes]";
	private final static String NO_LABEL = "[No]";
	
	private final String message;
	private boolean optionPositiveSelected;
	
	public ConfirmationView(Console console, String message) {
		super(console);
		this.message = message;
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
	
	@Override
	public void update() {
		super.update();
		printOptions();
	}
	
	@Override
	public void reset() {
		super.reset();
		optionPositiveSelected = true;
	}
	
	private void printOptions() {
		Console console = getConsole();
		int leftMarginLength = getLeftMarginLength() - YES_LABEL.length() / 2 + getTitle().length() / 2;
		String margin = " ".repeat(leftMarginLength);
		String marginWithSelection = " ".repeat(leftMarginLength - SELECTION.length()) + SELECTION;
		console.ln(false);
		console.ln(false);
		if(optionPositiveSelected) {
			console.println(marginWithSelection + YES_LABEL, false);
			console.println(margin + NO_LABEL, false);
		} else {
			console.println(margin + YES_LABEL, false);
			console.println(marginWithSelection + NO_LABEL, false);
		}
		console.refresh();
	}

	@Override
	protected String getTitle() {
		return message;
	}

	@Override
	protected String getName() {
		return ConfirmationController.class.getSimpleName();
	}

	@Override
	public void selectNextOption() {
		optionPositiveSelected = !optionPositiveSelected;
		update();
	}

	@Override
	public void selectPreviousOption() {
		optionPositiveSelected = !optionPositiveSelected;
		update();
	}

	@Override
	public void select() {
		controller.returnResponse(optionPositiveSelected);
	}

}
