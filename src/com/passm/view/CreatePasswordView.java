package com.passm.view;

import com.passm.controller.CreatePasswordController;
import com.passm.view.console.Console;

public class CreatePasswordView extends ConsoleView<CreatePasswordController, CreatePasswordView> {

	private final static String TITLE = " Create new password ";
	
	private final static String LOGIN_MESSAGE = "Login: ";
	private final static String PASSOWRD_MESSAGE = "Password: ";
	private final static String PASSWORD_CONFIRMATION_MESSAGE = "Password (again): ";
	private final static String NAME_MESSAGE = "Name (webside/program): ";
	private final static String EMPTY_FIELD_WARNING = "This field cannot be empty";
	private final static String DIFFERENT_PASSWORDS_WARNING = "Passwords must be the same";
	
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
		String login = receiveLogin(console);
		String password = receivePassword(console);
		String name = receiveName(console);
		controller.createNewPassword(login, password, name);
	}
	
	private String receiveLogin(Console console) {
		String login = null;
		boolean isLoginCorrect = false;
		while(!isLoginCorrect) {
			console.print(LOGIN_MESSAGE);
			login = console.readLine();
			isLoginCorrect = login.length() > 0;
			if(!isLoginCorrect) {
				console.println(EMPTY_FIELD_WARNING);
			}
		}
		return login;
	}
	
	private String receivePassword(Console console) {
		String password = null, passwordConfirmation = null;
		boolean isPasswordCorrect = false;
		while(!isPasswordCorrect) {
			console.print(PASSOWRD_MESSAGE);
			password = console.readLine(true);
			console.ln();
			if(password.length() > 0) {
				console.print(PASSWORD_CONFIRMATION_MESSAGE);
				passwordConfirmation = console.readLine(true);
				console.ln();
				isPasswordCorrect = password.equals(passwordConfirmation);
				if(!isPasswordCorrect) {
					console.println(DIFFERENT_PASSWORDS_WARNING);
				}
			} else {
				console.println(EMPTY_FIELD_WARNING);
			}
		}
		return password;
	}
	
	private String receiveName(Console console) {
		String name = null;
		boolean isNameCorrect = false;
		while(!isNameCorrect) {
			console.print(NAME_MESSAGE);
			name = console.readLine();
			isNameCorrect = name.length() > 0;
			if(!isNameCorrect) {
				console.println(EMPTY_FIELD_WARNING);
			}
		}
		return name;
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
