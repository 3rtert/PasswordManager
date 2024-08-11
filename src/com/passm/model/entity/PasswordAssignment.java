package com.passm.model.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.passm.model.database.tables.DatabaseTable;
import com.passm.model.database.tables.PasswordAssignmentTable;

public class PasswordAssignment extends Entity {
	
	static PasswordAssignmentTable passwordAssignmentTable = new PasswordAssignmentTable();
	
	private int userId;
	private int passwordId;
	
	private PasswordAssignment(int id, int userId, int passwordId) {
		super(id);
		this.userId = userId;
		this.passwordId = passwordId;
	}
	
	public static PasswordAssignment createEntity(int id, int userId, int passwordId) {
		return new PasswordAssignment(id, userId, passwordId);
	}
	
	public static PasswordAssignment createEntity(int userId, int passwordId) {
		return new PasswordAssignment(0, userId, passwordId);
	}
	
	public static PasswordAssignment createEntity(int id) {
		return new PasswordAssignment(id, 0, 0);
	}
	
	public static PasswordAssignment createEntity() {
		return new PasswordAssignment(0, 0, 0);
	}
	
	@Override
	protected Object[] getFields() {
		return new Object[] {
				userId,
				passwordId
			};
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
			this.userId = rs.getInt(PasswordAssignmentTable.getUserId());
			this.passwordId = rs.getInt(PasswordAssignmentTable.getPasswordId());
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
	
	public static List<PasswordAssignment> getAssignmentsByPasswordId(Statement statement, int id) {
		return getAssignmentsByForeignId(statement, id, PasswordAssignmentTable.getPasswordId());
	}
	
	public static List<PasswordAssignment> getAssignmentsByUserId(Statement statement, int id) {
		return getAssignmentsByForeignId(statement, id, PasswordAssignmentTable.getUserId());
	}
	
	private static List<PasswordAssignment> getAssignmentsByForeignId(Statement statement, int id, String foreignIdName) {
		String sql = "SELECT * FROM " + passwordAssignmentTable.getTable_name() + " o WHERE " + foreignIdName + " = " + id;
		List<PasswordAssignment> passwordAssignments = new ArrayList<>();
		try (ResultSet rs = statement.executeQuery(sql)) {
			while(rs.next()) {
				passwordAssignments.add(PasswordAssignment.createEntity(rs.getInt(1), rs.getInt(2), rs.getInt(3)));
			}
		} catch (SQLException e) {
			return null;
		}
		return passwordAssignments;
	}
}
