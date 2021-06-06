package com.passm.model.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.passm.model.database.tables.DatabaseTable;
import com.passm.model.database.tables.UserTable;

public class UserEntity extends Entity {

	static UserTable userTable = new UserTable();
	
	private String name;
	private int mainPassword;
	
	private UserEntity(int id, String name, int mainPassword) {
		super(id);
		this.name = name;
		this.mainPassword = mainPassword;
	}
	
	public static UserEntity createEntity(int id, String name, int mainPassword) {
		return new UserEntity(id, name, mainPassword);
	}
	
	public static UserEntity createEntity(String name, int mainPassword) {
		return new UserEntity(0, name, mainPassword);
	}
	
	public static UserEntity createEntity(int id) {
		return new UserEntity(id, null, 0);
	}
	
	public static UserEntity createEntity() {
		return new UserEntity(0, null, 0);
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
