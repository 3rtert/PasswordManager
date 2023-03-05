package com.passm.model.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.passm.model.database.tables.DatabaseTable;
import com.passm.model.database.tables.PasswordTable;

public class PasswordEntity extends Entity {
	
	static PasswordTable passwordTable = new PasswordTable();
	
	private String password;
	private String login;
	private String name;
	
	private PasswordEntity(int id, String password, String login, String name) {
		super(id);
		this.password = password;
		this.login = login;
		this.name = name;
	}
	
	public static PasswordEntity createEntity(int id, String password, String login, String name) {
		return new PasswordEntity(id, password, login, name);
	}
	
	public static PasswordEntity createEntity(String password, String login, String name) {
		return new PasswordEntity(0, password, login, name);
	}
	
	public static PasswordEntity createEntity(int id) {
		return new PasswordEntity(id, null, null, null);
	}
	
	public static PasswordEntity createEntity() {
		return new PasswordEntity(0, null, null, null);
	}
	
	@Override
	protected Object[] getFields() {
		return new Object[] {
			password,
			login,
			name
		};
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	protected DatabaseTable getDatabaseTable() {
		return passwordTable;
	}

	@Override
	public boolean load(Statement statement) {
		try (ResultSet rs = passwordTable.getObject(statement, getId())) {
			this.password = rs.getString(2);
			this.login = rs.getString(3);
			this.name = rs.getString(4);
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean validate() {
		return login.length() <= 50 
				&& name.length() <= 100;
	}
}
