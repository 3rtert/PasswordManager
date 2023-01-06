package com.passm.view.menu.login;

import java.util.logging.Logger;

import com.passm.controller.LoginController;
import com.passm.view.ConsoleView;
import com.passm.view.console.Console;

public class LoginView extends ConsoleView<LoginController> {
	
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
			if(controller.isPasswordCorrect(password)) {
				LOGGER.info("Password correct");
				isPasswordCorrect = true;
				controller.acceptPassword();
			} else {
				LOGGER.info("Password incorrect");
				console.ln();
			}
		}
	}

	@Override
	protected String getTitle() {
		return TITLE;
	}

	@Override
	protected String getName() {
		return LoginView.class.getSimpleName();
	}
}
