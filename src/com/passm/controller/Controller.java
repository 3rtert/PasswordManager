package com.passm.controller;

import com.passm.model.config.Configuration;
import com.passm.view.View;
import com.passm.view.console.Console;

public interface Controller<C extends Controller<C, V>, V extends View<C, V>> {
	
	public void startPreviousView(Console console, View<?, ?> view);
	public void setView(V view);
	public void run();
	public C getInstance();
	public void setConfig(Configuration configuration);	
}
