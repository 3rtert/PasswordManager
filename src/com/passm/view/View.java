package com.passm.view;

import com.passm.controller.Controller;

public interface View<C extends Controller<C, V> ,V extends View<C, V>> {
	
	void init();
	void reset();
	void update();
	void setController(C controller);
}
