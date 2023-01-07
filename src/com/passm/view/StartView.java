package com.passm.view;

import com.passm.controller.StartController;
import com.passm.view.console.Console;

public class StartView extends ConsoleView<StartController, StartView> {

	public StartView(Console console) {
		super(console);
	}
	
	@Override
	public void init() {}
	
	@Override
	public void reset() {}
	
	@Override
	public void update() {}

	@Override
	protected String getTitle() {
		return null;
	}

	@Override
	protected String getName() {
		return null;
	}

}
