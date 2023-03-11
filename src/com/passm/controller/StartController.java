package com.passm.controller;

import com.passm.general.log.LogHandler;
import com.passm.model.config.Configuration;
import com.passm.model.crypt.DatabaseEncrypterDecrypter;
import com.passm.view.StartView;
import com.passm.view.console.Console;
import com.passm.view.console.content.SwingConsole;
import com.passm.view.menu.login.CreateDatabaseView;
import com.passm.view.menu.login.LoginView;

public class StartController extends ConsoleController<StartController, StartView> {
	
	public StartController() {
		super(null, new StartView(null), new Configuration());
	}

	@Override
	public void run() {
		LogHandler.setup();
		Console console = SwingConsole.create("Password Manager");
		LoginView loginView = new LoginView(console);
		LoginController loginController = new LoginController(loginView, configuration);
		if(!DatabaseEncrypterDecrypter.isDatabaseEncypted()) {
			CreateDatabaseView createDatabaseView = new CreateDatabaseView(console);
			CreateDatabaseController createDatabaseController = new CreateDatabaseController(loginController, createDatabaseView, configuration, false);
			createDatabaseController.run();
		} else {
			loginController.run();
		}
	}

	@Override
	public StartController getInstance() {
		return this;
	}
}
