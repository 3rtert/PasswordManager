package com.passm.model.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.passm.model.database.tables.DatabaseTable;
import com.passm.model.database.tables.PasswordTable;

public class PasswordEntity extends Entity {
	
	static PasswordTable passwordTable = new PasswordTable();
	
	private String password;
	private String name;
	private String description;
	
	private PasswordEntity(int id, String password, String name, String description) {
		super(id);
		this.password = password;
		this.name = name;
		this.description = description;
	}
	
	public static PasswordEntity createEntity(int id, String password, String name, String description) {
		return new PasswordEntity(id, password, name, description);
	}
	
	public static PasswordEntity createEntity(String password, String name, String description) {
		return new PasswordEntity(0, password, name, description);
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
			name,
			description
		};
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
	
	protected DatabaseTable getDatabaseTable() {
		return passwordTable;
	}

	@Override
	public boolean load(Statement statement) {
		try (ResultSet rs = passwordTable.getObject(statement, getId())) {
			this.password = rs.getString(2);
			this.name = rs.getString(3);
			this.description = rs.getString(4);
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean validate() {
		return name.length() <= 50 
				&& description.length() <= 100;
	}
}
