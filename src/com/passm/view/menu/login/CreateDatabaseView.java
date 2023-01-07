package com.passm.view.menu.login;

import java.util.logging.Logger;

import com.passm.controller.CreateDatabaseController;
import com.passm.view.ConsoleView;
import com.passm.view.console.Console;

public class CreateDatabaseView extends ConsoleView<CreateDatabaseController, CreateDatabaseView>{
	
	private final static Logger LOGGER = Logger.getLogger(CreateDatabaseView.class.getName());

	private final static String TITLE = " Let's start ";
	private final static String CREATE_PASSWORD_MESSAGE = "Create main password. It must be at least 10 characters long";
	private final static String MAIN_PASSWORD_MESSAGE = "New password: ";
	private final static String MAIN_PASSWORD_TO_CONFIRM_MESSAGE = "Password again: ";
	
	public CreateDatabaseView(Console console) {
		super(console);
	}
	
	@Override
	public void init() {
		super.init();
		update();
	}
	
	@Override
	public void update() {
		super.update();
		Console console = getConsole();
		console.ln(false);
		boolean isPasswordCorrect = false;
		boolean arePasswordsTheSame = false;
		String password = "";
		while(!isPasswordCorrect || !arePasswordsTheSame) {
			LOGGER.info("Creating first password");
			console.println(CREATE_PASSWORD_MESSAGE, false);
			console.ln(false);
			console.print(MAIN_PASSWORD_MESSAGE);
			password = console.readLine(true);
			console.ln(false);
			console.print(MAIN_PASSWORD_TO_CONFIRM_MESSAGE);
			String passwordToConfirm = console.readLine(true);
			console.ln(false);
			isPasswordCorrect = password.length() >= 10;
			arePasswordsTheSame = password.equals(passwordToConfirm);
		}
		LOGGER.info("First password created");
		controller.createDatabase(password);
	}

	@Override
	public String getTitle() {
		return TITLE;
	}

	@Override
	public String getName() {
		return CreateDatabaseView.class.getSimpleName();
	}

}
