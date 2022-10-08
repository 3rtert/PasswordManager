package com.passm.view;

import com.passm.view.console.Action;
import com.passm.view.console.Console;

public class PreciousViewStarter implements Action {
	
	private View view;
	
	protected PreciousViewStarter(View view) {
		this.view = view;
	}

	@Override
	public void activate(Console console) {
		view.startPreviousView();
	}

}
