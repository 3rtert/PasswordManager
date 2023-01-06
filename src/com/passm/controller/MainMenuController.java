package com.passm.controller;

import java.util.HashMap;
import java.util.Map;

import com.passm.view.menu.main.MainMenuView;

public class MainMenuController extends Controller {
	
	private final static String[] SELECTABLE_OPTIONS = { "List of passwords", "Add new password", "Change password", "Remove password", "Update main password", "Settings" };
	private final Map<String, Controller> SELECTABLE_OPTINS_TO_CONTROLLERS;

	MainMenuView mainMenuView;
	
	public MainMenuController(MainMenuView mainMenuView, Controller prevoiusController) {
		super(prevoiusController);
		this.mainMenuView = mainMenuView;
		this.SELECTABLE_OPTINS_TO_CONTROLLERS = new HashMap<>();
	}
	
	@Override
	public void run() {
		mainMenuView.setSelectableOptions(SELECTABLE_OPTIONS);
		mainMenuView.setController(this);
		mainMenuView.init();
	}
	
	public void select(int position) {
		Controller nextController = SELECTABLE_OPTINS_TO_CONTROLLERS.get(SELECTABLE_OPTIONS[position-1]);
		if(nextController != null) {
			mainMenuView.reset();
			nextController.run();
		}
	}
}
