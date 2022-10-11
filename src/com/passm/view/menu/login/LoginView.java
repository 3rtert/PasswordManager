package com.passm.view.menu.login;

import java.util.logging.Logger;

import com.passm.view.ConsoleView;
import com.passm.view.console.Console;
import com.passm.view.menu.main.MainMenuView;

public class LoginView extends ConsoleView {
	
	private final static Logger LOGGER = Logger.getLogger(LoginView.class.getName());
	
	private final static String TITLE = " Login ";
	private final static String MAIN_PASSWORD_MESSAGE = "Password: ";

	public LoginView(Console console) {
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
		while(!isPasswordCorrect) {
			console.print(MAIN_PASSWORD_MESSAGE);
			String password = console.readLine(true);
			if(isPasswordCorrect(password)) {
				LOGGER.info("Password correct");
				isPasswordCorrect = true;
				MainMenuView mainMenu = new MainMenuView(console, this);
				reset();
				mainMenu.init();
			} else {
				LOGGER.info("Password incorrect");
				console.ln();
			}
		}
	}

	private boolean isPasswordCorrect(String password) {
		return "pass".equals(password);
	}

	@Override
	protected String getTitle() {
		return TITLE;
	}

	@Override
	protected String getName() {
		return LoginView.class.getName();
	}
}
