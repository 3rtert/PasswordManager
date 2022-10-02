package com.passm.general;

import com.passm.view.console.Action;
import com.passm.view.console.Console;

public class TestAction implements Action {
	
	private char button;
	
	public TestAction(char button) {
		this.button = button;
	}

	@Override
	public void activate(Console console) {
		System.out.println(Thread.currentThread().getStackTrace().length);
		System.out.println("Action activated by: " + button);
		console.clearActions();
		console.registerAction('b', new TestAction('b'));
	}

}
