package com.passm.controller;

import com.passm.general.log.LogHandler;
import com.passm.model.crypt.DatabaseEncrypterDecrypter;
import com.passm.view.console.Console;
import com.passm.view.console.content.SwingConsole;
import com.passm.view.menu.login.CreateDatabaseView;
import com.passm.view.menu.login.LoginView;

public class StartController extends Controller {

	public StartController() {
		super(null);
	}

	@Override
	public void run() {
		LogHandler.setup();
		Console console = SwingConsole.create("Password Manager");
		if(!DatabaseEncrypterDecrypter.isDatabaseEncypted()) {
			CreateDatabaseView createDatabaseView = new CreateDatabaseView(console);
			CreateDatabaseController createDatabaseController = new CreateDatabaseController(createDatabaseView);
			createDatabaseController.run();
		}
		LoginView loginView = new LoginView(console);
		LoginController loginController = new LoginController(loginView);
		loginController.run();
	}

}
