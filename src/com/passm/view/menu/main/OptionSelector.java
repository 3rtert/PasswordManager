package com.passm.view.menu.main;

import com.passm.view.console.Action;
import com.passm.view.console.Console;

public class OptionSelector implements Action {

	private final MainMenuView mainMenuView;
	
	public OptionSelector(MainMenuView mainMenuView) {
		this.mainMenuView = mainMenuView;
	}

	@Override
	public void activate(Console console) {
		mainMenuView.select();
	}

}
