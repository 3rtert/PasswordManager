package com.passm.view.menu.main;

import com.passm.view.console.Action;
import com.passm.view.console.Console;

public class NextOptionSelector implements Action {
	
	private final MainMenuView view;
	
	protected NextOptionSelector(MainMenuView view) {
		this.view = view;
	}

	@Override
	public void activate(Console console) {
		view.selectNextOption();
	}
}
