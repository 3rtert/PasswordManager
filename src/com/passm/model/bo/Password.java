package com.passm.model.bo;

public class Password {

	private String password;
	private String name;
	private String description;
	
	public Password(String password, String name, String description) {
		this.password = password;
		this.name = name;
		this.description = description;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
}