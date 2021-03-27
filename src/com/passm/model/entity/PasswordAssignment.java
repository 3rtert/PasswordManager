package com.passm.model.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.passm.model.database.tables.DatabaseTable;
import com.passm.model.database.tables.PasswordAssignmentTable;

public class PasswordAssignment extends Entity {
	
	PasswordAssignmentTable passwordAssignmentTable = new PasswordAssignmentTable();
	
	private int id;
	private int userId;
	private int passwordId;
	
	@Override
	protected Object[] getFields() {
		return new Object[] {
				userId,
				passwordId
			};
	}

	@Override
	public boolean load(Statement statement) {
		try (ResultSet rs = passwordAssignmentTable.getObject(statement, id)) {
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
	protected int getId() {
		return id;
	}

	@Override
	public boolean validate() {
		return true;
	}

}
