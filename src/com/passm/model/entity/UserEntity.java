package com.passm.model.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.passm.model.database.tables.DatabaseTable;
import com.passm.model.database.tables.UserTable;

public class UserEntity extends Entity {

	UserTable userTable = new UserTable();
	
	private String name;
	private int mainPassword;
	
	public UserEntity() {}
	
	public UserEntity(int id) {
		super(id);
	}
	
	public UserEntity(String name, int mainPassword) {
		this.name = name;
		this.mainPassword = mainPassword;
	}
	
	@Override
	protected Object[] getFields() {
		return new Object[] {
			name,
			mainPassword
		};
	}
	
	public UserTable getUserTable() {
		return userTable;
	}

	public void setUserTable(UserTable userTable) {
		this.userTable = userTable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMainPassword() {
		return mainPassword;
	}

	public void setMainPassword(int mainPassword) {
		this.mainPassword = mainPassword;
	}

	@Override
	public boolean load(Statement statement) {
		try (ResultSet rs = userTable.getObject(statement, getId())) {
			this.name = rs.getString(2);
			this.mainPassword = rs.getInt(3);
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	@Override
	protected DatabaseTable getDatabaseTable() {
		return userTable;
	}

	@Override
	public boolean validate() {
		return name.length() <= 50;
	}

}
