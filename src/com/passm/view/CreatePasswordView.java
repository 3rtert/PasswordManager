package com.passm.view;

import com.passm.controller.CreatePasswordController;
import com.passm.view.console.Console;

public class CreatePasswordView extends ConsoleView<CreatePasswordController, CreatePasswordView> {

	private final static String TITLE = " Create new password ";
	
	private final static String LOGIN_MESSAGE = "Login: ";
	private final static String PASSOWRD_MESSAGE = "Password: ";
	private final static String NAME_MESSAGE = "Name (webside/program): ";
	
	public CreatePasswordView(Console console) {
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
		String login = null, password = null, name = null;
		boolean isLoginCorrect = false;
		while(!isLoginCorrect) {
			console.print(LOGIN_MESSAGE);
			login = console.readLine();
			isLoginCorrect = login.length() > 0;
		}
		boolean isPasswordCorrect = false;
		while(!isPasswordCorrect) {
			console.print(PASSOWRD_MESSAGE);
			password = console.readLine();
			isPasswordCorrect = password.length() > 0;
		}
		boolean isNameCorrect = false;
		while(!isNameCorrect) {
			console.print(NAME_MESSAGE);
			name = console.readLine();
			isNameCorrect = name.length() > 0;
		}
		controller.createNewPassword(login, password, name);
	}
	
	@Override
	protected String getTitle() {
		return TITLE;
	}

	@Override
	protected String getName() {
		return CreatePasswordView.class.getSimpleName();
	}

}
