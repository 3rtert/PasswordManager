package com.passm.model.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.passm.model.database.tables.DatabaseTable;
import com.passm.model.database.tables.PasswordTable;

public class PasswordEntity extends Entity {
	
	PasswordTable passwordTable = new PasswordTable();
	
	private String password;
	private String name;
	private String description;
	
	public PasswordEntity() {}
	
	public PasswordEntity(int id) {
		super(id);
	}
	
	public PasswordEntity(String password, String name, String description) {
		this.password = password;
		this.name = name;
		this.description = description;
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
