package com.passm.controller;

import java.util.HashMap;
import java.util.Map;

import com.passm.model.config.Configuration;
import com.passm.view.CreatePasswordView;
import com.passm.view.PasswordsListView;
import com.passm.view.RemovePasswordView;
import com.passm.view.menu.login.CreateDatabaseView;
import com.passm.view.menu.main.MainMenuView;

public class MainMenuController extends ConsoleController<MainMenuController, MainMenuView> {
	
	private final static String[] SELECTABLE_OPTIONS = { "List of passwords", "Add new password", "Change password", "Remove password", "Update main password", "Settings" };
	private final Map<String, ConsoleController<?, ?>> SELECTABLE_OPTIONS_TO_CONTROLLERS;
	
	public MainMenuController(MainMenuView mainMenuView, ConsoleController<?, ?> previousController, Configuration configuration) {
		super(previousController, mainMenuView, configuration);
		this.SELECTABLE_OPTIONS_TO_CONTROLLERS = new HashMap<>();
		SELECTABLE_OPTIONS_TO_CONTROLLERS.put(SELECTABLE_OPTIONS[0], new PasswordsListController(this, new PasswordsListView(view.getConsole()), configuration));
		SELECTABLE_OPTIONS_TO_CONTROLLERS.put(SELECTABLE_OPTIONS[1], new CreatePasswordController(this, new CreatePasswordView(view.getConsole()), configuration));
		SELECTABLE_OPTIONS_TO_CONTROLLERS.put(SELECTABLE_OPTIONS[3], new RemovePasswordController(this, new RemovePasswordView(view.getConsole()), configuration));
		SELECTABLE_OPTIONS_TO_CONTROLLERS.put(SELECTABLE_OPTIONS[4], new CreateDatabaseController(this, new CreateDatabaseView(view.getConsole()), configuration, true));
	}
	
	@Override
	public void run() {
		view.setSelectableOptions(SELECTABLE_OPTIONS);
		super.run();
	}
	
	public void select(int position) {
		ConsoleController<?, ?> nextController = SELECTABLE_OPTIONS_TO_CONTROLLERS.get(SELECTABLE_OPTIONS[position-1]);
		if(nextController != null) {
			view.reset();
			nextController.run();
		}
	}

	@Override
	public MainMenuController getInstance() {
		return this;
	}
}
