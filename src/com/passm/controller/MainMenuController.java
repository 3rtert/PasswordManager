package com.passm.controller;

import java.util.HashMap;
import java.util.Map;

import com.passm.model.config.Configuration;
import com.passm.view.CreatePasswordView;
import com.passm.view.PasswordsListView;
import com.passm.view.menu.main.MainMenuView;

public class MainMenuController extends ConsoleController<MainMenuController, MainMenuView> {
	
	private final static String[] SELECTABLE_OPTIONS = { "List of passwords", "Add new password", "Change password", "Remove password", "Update main password", "Settings" };
	private final Map<String, ConsoleController<?, ?>> SELECTABLE_OPTINS_TO_CONTROLLERS;
	
	public MainMenuController(MainMenuView mainMenuView, ConsoleController<?, ?> prevoiusController, Configuration configuration) {
		super(prevoiusController, mainMenuView, configuration);
		this.SELECTABLE_OPTINS_TO_CONTROLLERS = new HashMap<>();
		SELECTABLE_OPTINS_TO_CONTROLLERS.put(SELECTABLE_OPTIONS[0], new PasswordsListController(this, new PasswordsListView(view.getConsole()), configuration));
		SELECTABLE_OPTINS_TO_CONTROLLERS.put(SELECTABLE_OPTIONS[1], new CreatePasswordController(this, new CreatePasswordView(view.getConsole()), configuration));
	}
	
	@Override
	public void run() {
		view.setSelectableOptions(SELECTABLE_OPTIONS);
		super.run();
	}
	
	public void select(int position) {
		ConsoleController<?, ?> nextController = SELECTABLE_OPTINS_TO_CONTROLLERS.get(SELECTABLE_OPTIONS[position-1]);
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
