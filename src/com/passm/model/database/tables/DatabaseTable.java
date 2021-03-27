package com.passm.model.database.tables;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DatabaseTable {
	
	private final static String ID_FIELD_NAME = "ID";
	
	public void createTable(Statement statement) throws SQLException {
		statement.execute(getCreateSql());
	}
	
	public int insert(Statement statement, Object...values) throws SQLException {
		return statement.executeUpdate(getInsertSql(getColumnNames(), values));
	}
	
	protected String getInsertSql(String[] columns, Object...values) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO " + getTable_name() + " (");
		for(String column : columns) {
			sb.append(" ");
			sb.append(column);
			sb.append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(" ) VALUES (");
		for(Object value : values) {
			sb.append(" '");
			sb.append(value);
			sb.append("',");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(" );");
		return sb.toString();
	}
	
	public int update(Statement statement, int id, Object...values) throws SQLException {
		return statement.executeUpdate(getUpdateSql(values, id));
	}
	
	protected String getUpdateSql(Object[] values, int id) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ");
		sb.append(getTable_name());
		sb.append(" SET");
		String[] columns = getColumnNames();
		for(int i = 0 ; i < values.length; i++) {
			sb.append(" ");
			sb.append(columns[i]);
			sb.append(" = '");
			sb.append(values[i]);
			sb.append("',");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(" WHERE ");
		sb.append(ID_FIELD_NAME);
		sb.append(" = ");
		sb.append(id);
		sb.append(";");
		return sb.toString();
	}
	
	public void delete(Statement statement, int id) throws SQLException {
		statement.executeUpdate(getDeleteSql(id));
	}

	protected String getDeleteSql(int id) {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM ");
		sb.append(getTable_name());
		sb.append(" WHERE ");
		sb.append(ID_FIELD_NAME);
		sb.append(" = ");
		sb.append(id);
		sb.append(";");
		return sb.toString();
	}

	public boolean exist(Statement statement, int id) throws SQLException {
		return getObject(statement, id).next();
	}
	
	public ResultSet getObject(Statement statement, int id) throws SQLException {
		String sql = "SELECT * FROM " + getTable_name() + " o WHERE " + ID_FIELD_NAME + " = " + Integer.toString(id);
		return statement.executeQuery(sql);
	}
	
	abstract protected String getTable_name();
	
	abstract protected String[] getColumnNames();
	
	abstract protected String getCreateSql();
}
