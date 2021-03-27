package com.passm.model.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.passm.model.database.tables.DatabaseTable;
import com.passm.model.database.tables.PasswordAssignmentTable;

public class PasswordAssignment extends Entity {
	
	PasswordAssignmentTable passwordAssignmentTable = new PasswordAssignmentTable();
	
	private int userId;
	private int passwordId;

	public PasswordAssignment() {}
	
	public PasswordAssignment(int id) {
		super(id);
	}
	
	public PasswordAssignment(int userId, int passwordId) {
		this.userId = userId;
		this.passwordId = passwordId;
	}
	
	@Override
	protected Object[] getFields() {
		return new Object[] {
				userId,
				passwordId
			};
	}

	public PasswordAssignmentTable getPasswordAssignmentTable() {
		return passwordAssignmentTable;
	}

	public void setPasswordAssignmentTable(PasswordAssignmentTable passwordAssignmentTable) {
		this.passwordAssignmentTable = passwordAssignmentTable;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getPasswordId() {
		return passwordId;
	}

	public void setPasswordId(int passwordId) {
		this.passwordId = passwordId;
	}
	
	@Override
	public boolean load(Statement statement) {
		try (ResultSet rs = passwordAssignmentTable.getObject(statement, getId())) {
			this.userId = rs.getInt(2);
			this.passwordId = rs.getInt(3);
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	@Override
	protected DatabaseTable getDatabaseTable() {
		return passwordAssignmentTable;
	}

	@Override
	public boolean validate() {
		return true;
	}
}
