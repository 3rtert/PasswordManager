package com.passm.model;

import java.util.List;

public class User {

	private String name;
	private List<Password> passwords;
	private Password mainPassword;

	public User(String name, List<Password> passwords, Password mainPassword) {
		this.name = name;
		this.passwords = passwords;
		this.mainPassword = mainPassword;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Password> getPasswords() {
		return passwords;
	}
	
	public void setPasswords(List<Password> passwords) {
		this.passwords = passwords;
	}
	
	public Password getMainPassword() {
		return mainPassword;
	}
	
	public void setMainPassword(Password mainPassword) {
		this.mainPassword = mainPassword;
	}
}
