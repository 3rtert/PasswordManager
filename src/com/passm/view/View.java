package com.passm.view;

import com.passm.controller.Controller;

public interface View<T extends Controller> {
	
	public void init();
	public void reset();
	public void update();
	public void setController(T Controller);
}
