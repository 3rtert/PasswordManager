package com.passm.model.database.tables;

public class PasswordTable extends DatabaseTable {

	private final static String createSql = "CREATE TABLE IF NOT EXISTS PASSWORD "
		+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT, "
		+ " NAME CHAR(50) NOT NULL, "
		+ " DESCRIPTION CHAR(100), "
		+ " PASSWORD_HASH TEXT NOT NULL)";

	@Override
	public void insertRecord(Object... values) {
		// TODO Auto-generated method stub

	}
	
	@Override
	protected String getCreateSql() {
		return createSql;
	}
}
