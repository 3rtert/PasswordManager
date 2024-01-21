package com.passm.model.database.tables;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DatabaseTable {
	
	private final static String ID_FIELD_NAME = "ID";
	private final static String LAST_INSERT_ROW_ID_SQL = "SELECT last_insert_rowid();";
	
	public void createTable(Statement statement) throws SQLException {
		statement.execute(getCreateSql());
	}
	
	public int insert(Statement statement, Object...values) throws SQLException {
		statement.executeUpdate(getInsertSql(getColumnNames(), values));
		try(ResultSet resultSet = statement.executeQuery(LAST_INSERT_ROW_ID_SQL)){
			if(resultSet.next()) {
				return resultSet.getInt(1);
			}
			return 0;
		}
	}
	
	protected String getInsertSql(String[] columns, Object...values) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ").append(getTable_name()).append(" (");
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
		sb.append(" ); ");
		sb.append(LAST_INSERT_ROW_ID_SQL);
		return sb.toString();
	}
	
	public void update(Statement statement, int id, Object...values) throws SQLException {
		statement.executeUpdate(getUpdateSql(values, id));
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
		try(ResultSet resultSet = getObject(statement, id)) {
			return resultSet.next();
		}
	}
	
	public ResultSet getObject(Statement statement, int id) throws SQLException {
		String sql = "SELECT * FROM " + getTable_name() + " o WHERE " + ID_FIELD_NAME + " = " + id;
		return statement.executeQuery(sql);
	}
	
	abstract protected String getTable_name();
	
	abstract protected String[] getColumnNames();
	
	abstract protected String getCreateSql();
}
