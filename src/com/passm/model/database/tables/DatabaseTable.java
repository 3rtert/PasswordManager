package com.passm.model.database.tables;

import java.sql.SQLException;
import java.sql.Statement;

public abstract class DatabaseTable {
	
	public void createTable(Statement statement) throws SQLException {
		statement.execute(getCreateSql());
	}
	
	abstract public void insertRecord(Object...values);
	
	abstract protected String getCreateSql();
}
