package com.passm.view;

import com.passm.controller.Controller;
import com.passm.view.console.Action;
import com.passm.view.console.Console;

public class PreciousViewStarter implements Action {
	
	private Controller<?,?> controller;
	private View<?,?> view;
	
	protected PreciousViewStarter(View<?,?> view, Controller<?,?> controller) {
		this.view = view;
		this.controller = controller;
	}

	@Override
	public void activate(Console console) {
		controller.startPreviousView(console, view);
	}

}
