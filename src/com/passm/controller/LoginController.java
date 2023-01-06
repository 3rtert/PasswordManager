package com.passm.controller;

import java.util.logging.Logger;

import com.passm.model.crypt.DatabaseEncrypterDecrypter;
import com.passm.view.menu.login.LoginView;
import com.passm.view.menu.main.MainMenuView;

public class LoginController extends Controller {
	
	private final static Logger LOGGER = Logger.getLogger(LoginController.class.getName());
	
	LoginView loginView;
	
	public LoginController(LoginView loginView) {
		super(null);
		this.loginView = loginView;
	}
	
	@Override
	public void run() {
		loginView.setController(this);
		loginView.init();
	}
	
	public boolean isPasswordCorrect(String password) {
		try {
			DatabaseEncrypterDecrypter databaseEncrypterDecrypter = new DatabaseEncrypterDecrypter(password.toCharArray());
			if(databaseEncrypterDecrypter.decrypt()) {
				databaseEncrypterDecrypter.encrypt();
				return true;
			}
		} catch (Exception e) {
			LOGGER.warning(e.getMessage());
		} 
		return false;
	}

	public void acceptPassword() {
		loginView.reset();
		MainMenuView mainMenu = new MainMenuView(loginView.getConsole());
		MainMenuController mainMenuController = new MainMenuController(mainMenu, this);
		mainMenuController.run();
	}
}
