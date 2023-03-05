package com.passm.view;

import java.util.List;
import java.util.logging.Logger;

import com.passm.controller.PasswordsListController;
import com.passm.view.console.Action;
import com.passm.view.console.Console;
import com.passm.view.console.content.InputListener;
import com.passm.view.menu.UnselectorThread;
import com.passm.view.menu.selector.NextOptionSelector;
import com.passm.view.menu.selector.OptionSelector;
import com.passm.view.menu.selector.PreviousOptionSelector;
import com.passm.view.menu.selector.Selectable;

public class PasswordsListView extends ConsoleView<PasswordsListController, PasswordsListView> implements Selectable {

	private final static Logger LOGGER = Logger.getLogger(PasswordsListView.class.getName());
	
	private final static String TITLE = " List of passwords ";
	private final static String SELECTION = ">> ";
	private final static String SUBSELECTION = " * ";
	private final static int LOGIN_OPTION = 1;
	private final static int PASSWORD_OPTION = 2;
	private final static int DEFAULT_OPTIN = LOGIN_OPTION;
	private final static String LOGIN_LABEL = "Login: ";
	private final static String PASSWORD_LABEL = "Password: ";
	private final static String PASSWORD_HIDED_LABEL = "hided";
	
	private List<String> passwordNames;
	
	private int position;
	private boolean passwordSelected;
	private int subPosition;
	
	UnselectorThread unselectorThread;
	
	public PasswordsListView(Console console) {
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
		LOGGER.info("Initialization finished");
		update();
	}
	
	@Override
	public void selectNextOption() {
		if(passwordSelected) {
			if(subPosition == PASSWORD_OPTION) {
				subPosition = LOGIN_OPTION;
			} else {
				subPosition = PASSWORD_OPTION;
			}
			updateClipboard();
		} else {
			if(position == passwordNames.size()) {
				position = 1;
			} else {
				position++;
			}
		}
		update();
	}
	
	@Override
	public void selectPreviousOption() {
		if(passwordSelected) {
			if(subPosition == LOGIN_OPTION) {
				subPosition = PASSWORD_OPTION;
			} else {
				subPosition = LOGIN_OPTION;
			}
			updateClipboard();
		} else {
			if(position == 1) {
				position = passwordNames.size();
			} else {
				position--;
			}
		}
		update();
	}
	
	@Override
	public void select() {
		if(!passwordSelected) {
			
		}
		passwordSelected = !passwordSelected;
		subPosition = DEFAULT_OPTIN;
		updateClipboard();
		update();
	}
	
	private void updateClipboard() {
		String currentValue = "";
		if(passwordSelected) {
			if(subPosition == LOGIN_OPTION) {
				currentValue = controller.getLogin(position - 1);
			} else if(subPosition == PASSWORD_OPTION) {
				currentValue = controller.getUnhiddenPassword(position - 1);
			}
			new ClipboardUpdater().update(currentValue);
			if(unselectorThread != null && unselectorThread.isAlive()) {
				unselectorThread.setWaitUntil(System.currentTimeMillis() + controller.getClipboardClearingDelay());
			} else {
				unselectorThread = new UnselectorThread(System.currentTimeMillis() + controller.getClipboardClearingDelay(), currentValue, this);
				unselectorThread.start();
			}
		}
	}

	@Override
	public void reset() {
		super.reset();
		position = 1;
		passwordSelected = false;
		subPosition = 1;
	}
	
	private void printList() {
		Console console = getConsole();
		int leftMarginLength = getLeftMarginLength() - getAverageLengthOfPasswordNames() / 2 + getTitle().length() / 2 - SUBSELECTION.length();
		String margin = " ".repeat(leftMarginLength);
		String marginWithSelection = " ".repeat(leftMarginLength - SELECTION.length()) + SELECTION;
		console.ln(false);
		console.ln(false);
		for (int i = 0; i < passwordNames.size(); i++) {
			if (i + 1 == position) {
				console.println(marginWithSelection + passwordNames.get(i), false);
				if(passwordSelected) {
					printSubList(console, leftMarginLength, i);
				}
			} else {
				console.println(margin + passwordNames.get(i), false);
			}
		}
		console.refresh();
	}
	
	private int getAverageLengthOfPasswordNames() {
		return (int) passwordNames.stream().map(password -> password.length()).mapToInt(Integer::intValue).average().getAsDouble();
	}
	
	private void printSubList(Console console, int leftMarginLength, int numberOfPassword) {
		String submargin = " ".repeat(leftMarginLength + SUBSELECTION.length());
		String submarginWithSelection = " ".repeat(leftMarginLength) + SUBSELECTION;
		if(subPosition == LOGIN_OPTION) {
			printLogin(console, submarginWithSelection, numberOfPassword);
			printPassword(console, submargin, numberOfPassword);
		} else if(subPosition == PASSWORD_OPTION) {
			printLogin(console, submargin, numberOfPassword);
			printPassword(console, submarginWithSelection, numberOfPassword);
		}
	}
	
	private void printLogin(Console console, String margin, int numberOfPassword) {
		console.println(margin + LOGIN_LABEL + controller.getLogin(numberOfPassword), false);
	}
	
	private void printPassword(Console console, String margin, int numberOfPassword) {
		String password = controller.getPassword(numberOfPassword);
		console.println(margin + PASSWORD_LABEL + (password.isEmpty() ? PASSWORD_HIDED_LABEL : password), false);
	}

	@Override
	public void update() {
		super.update();
		printList();
	}

	@Override
	protected String getTitle() {
		return TITLE;
	}

	@Override
	protected String getName() {
		return PasswordsListView.class.getSimpleName();
	}

}
