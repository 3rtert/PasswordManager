package com.passm.view;

import com.passm.controller.Controller;

public interface View<C extends Controller<C, V> ,V extends View<C, V>> {
	
	public void init();
	public void reset();
	public void update();
	public void setController(C controller);
}
