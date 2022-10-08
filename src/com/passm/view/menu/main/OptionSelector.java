package com.passm.view.menu.main;

import com.passm.view.console.Action;
import com.passm.view.console.Console;

public class OptionSelector implements Action {

	private final MainMenuView view;
	
	public OptionSelector(MainMenuView view) {
		this.view = view;
	}

	@Override
	public void activate(Console console) {
		view.select();
	}

}
