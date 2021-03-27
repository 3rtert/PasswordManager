package com.passm.model.database.tables;

public class PasswordAssignment extends DatabaseTable {
	
	private final static String TABLE_NAME = "PASSWORD_ASSIGNMENT";
	
	private final static String ID = "ID";
	
	private final static String USER_ID = "USER_ID";
	
	private final static String PASSWORD_ID = "PASSWORD_ID";
	
	private final static String[] COLUMN_NAMES = {
			USER_ID,
			PASSWORD_ID
	};
	
	private final static String createSql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
			+ " ( " + ID + " INT PRIMARY KEY AUTOINCREMENT, "
			+ USER_ID + " INTEGER, "
			+ PASSWORD_ID + " INTEGER, "
			+ " FOREIGN KEY(" + USER_ID + ") REFERENCES " + UserTable.getTableName() + " (" + UserTable.getId() + ")"
			+ " FOREIGN KEY(" + PASSWORD_ID + ") REFERENCES " + PasswordTable.getTableName() + " (" + PasswordTable.getId() + "))";
	
	private final static String insertSql = "INSERT INTO " + TABLE_NAME + "(" + USER_ID + ", " + PASSWORD_ID + ") "
			+ "VALUES (";

	@Override
	protected String[] getColumnNames() {
		return COLUMN_NAMES;
	}

	@Override
	public String getTable_name() {
		return getTable_name();
	}
	
	public static String getTableName() {
		return TABLE_NAME;
	}


	public static String getId() {
		return ID;
	}

	public static String getUserId() {
		return USER_ID;
	}

	public static String getPasswordId() {
		return PASSWORD_ID;
	}

	@Override
	protected String getCreateSql() {
		return createSql;
	}
}
