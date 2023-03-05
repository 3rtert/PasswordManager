package com.passm.view.menu.selector;

import com.passm.view.console.Action;
import com.passm.view.console.Console;

public class PreviousOptionSelector implements Action {

	private final Selectable selectable;
	
	public PreviousOptionSelector(Selectable selectable) {
		this.selectable = selectable;
	}

	@Override
	public void activate(Console console) {
		selectable.selectPreviousOption();
	}
}
