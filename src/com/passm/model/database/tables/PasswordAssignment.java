package com.passm.model.database.tables;

public class PasswordAssignment extends DatabaseTable {
	
	private final static String createSql = "CREATE TABLE IF NOT EXISTS PASSWORD_ASSIGNMENT "
			+ "(ID INT PRIMARY KEY AUTOINCREMENT, "
			+ " USER_ID INTEGER, "
			+ " PASSWORD_ID INTE, "
			+ " FOREIGN KEY(USER_ID) REFERENCES USER ( ID )"
			+ " FOREIGN KEY(PASSWORD_ID) REFERENCES PASSWORD ( ID ))";

	@Override
	public void insertRecord(Object... values) {
		// TODO Auto-generated method stub

	}

	@Override
	protected String getCreateSql() {
		return createSql;
	}
}
