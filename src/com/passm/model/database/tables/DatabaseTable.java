package com.passm.model.database.tables;

import java.sql.SQLException;
import java.sql.Statement;

public abstract class DatabaseTable {
	
	public  void createTable(Statement statement) throws SQLException {
		statement.execute(getCreateSql());
	}
	
	protected void insertRecord(Statement statement, Object...values) throws SQLException {
		statement.executeUpdate(getInsertSql(values), getColumnNames());
	}
	
	protected String getInsertSql(Object...values) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO " + getTable_name() + " (");
		for(Object value : values) {
			sb.append(value.toString() + ", ");
		}
		sb.deleteCharAt(sb.length());
		sb.append(");");
		return sb.toString();
	}
	
	abstract protected String getTable_name();
	
	abstract protected String[] getColumnNames();
	
	abstract protected String getCreateSql();
	
}
