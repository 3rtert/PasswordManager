package com.passm.model.database.tables;

public class UserTable extends DatabaseTable {

	private final static String createSql = "CREATE TABLE IF NOT EXISTS USER "
		+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT, "
		+ " NAME CHAR(50) NOT NULL, "
		+ " MAIN_PASSWORD INTEGER, "
		+ "FOREIGN KEY(MAIN_PASSWORD) REFERENCES PASSWORD ( ID ))";

	@Override
	public void insertRecord(Object... values) {
		// TODO Auto-generated method stub

	}

	@Override
	protected String getCreateSql() {
		return createSql;
	}
}
