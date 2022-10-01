package com.passm.general;

import com.passm.view.console.Action;

public class TestAction implements Action {

	@Override
	public void activate() {
		System.out.println("Action activated");
	}

}
