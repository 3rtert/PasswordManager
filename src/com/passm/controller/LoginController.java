package com.passm.controller;

import java.util.logging.Logger;

import com.passm.model.config.Configuration;
import com.passm.model.crypt.DatabaseEncrypterDecrypter;
import com.passm.view.menu.login.LoginView;
import com.passm.view.menu.main.MainMenuView;

public class LoginController extends ConsoleController<LoginController, LoginView> {
	
	private final static Logger LOGGER = Logger.getLogger(LoginController.class.getName());
	
	public LoginController(LoginView loginView, Configuration configuration) {
		super(null, loginView, configuration);
	}
	
	public boolean isPasswordCorrect(String password) {
		try {
			DatabaseEncrypterDecrypter databaseEncrypterDecrypter = new DatabaseEncrypterDecrypter(password.toCharArray());
			if(databaseEncrypterDecrypter.decrypt()) {
				databaseEncrypterDecrypter.encrypt();
				configuration.setDatabasePassword(password);
				return true;
			}
		} catch (Exception e) {
			LOGGER.warning(e.getMessage());
		} 
		return false;
	}

	public void acceptPassword() {
		view.reset();
		MainMenuView mainMenu = new MainMenuView(view.getConsole());
		MainMenuController mainMenuController = new MainMenuController(mainMenu, this, configuration);
		mainMenuController.run();
	}

	@Override
	public LoginController getInstance() {
		return this;
	}
}
