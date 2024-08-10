package com.passm.view.console;

import java.awt.Image;
import java.util.List;

public interface Console {

	void setIconImage(Image image);
	void setName(String name);
	void print(String text);
	void println(String text, boolean withRefresh);
	void println(String text);
	void print(String text, boolean newLine);
	void print(String text, boolean newLine, boolean withRefresh);
	void ln();
	void ln(boolean withRefresh);
	void refresh();
	void clear(boolean withRefresh);
	void clear();
	String readLine();
	String readLine(boolean hide);
	char read();
	void registerAction(char activator, Action action);
	void clearActions();
	void clearActions(char activator);
	void enableAction(char activator);
	void disableAction(char activator);
	void enableAllActions();
	void disableAllActions();
	List<Action> getActions();
	void setCaseSensitive(boolean caseSensitive);
	void setSize(int width, int height);
	int getHeight();
	int getWidth();
	int getHeightInCharacters();
	int getWidthInCharacters();
	void setSizeInCharacters(int width, int height);
	void setLocation(int x, int y);
	void setFontSize(int fontSize);
	void stop();
	void disableListening();

}
