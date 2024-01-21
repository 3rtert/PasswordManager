package com.passm.controller;

import com.passm.model.config.Configuration;
import com.passm.view.View;
import com.passm.view.console.Console;

public interface Controller<C extends Controller<C, V>, V extends View<C, V>> {
	
	void startPreviousView(Console console, View<?, ?> view);
	void setView(V view);
	void run();
	C getInstance();
	void setConfig(Configuration configuration);
}
