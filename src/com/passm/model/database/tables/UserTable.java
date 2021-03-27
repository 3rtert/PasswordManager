package com.passm.model.database.tables;

public class UserTable extends DatabaseTable {

	private final static String TABLE_NAME = "USER";
	
	private final static String ID = "ID";
	
	private final static String NAME = "NAME";
	
	private final static String MAIN_PASSWORD = "MAIN_PASSWORD";
	
	private final static String[] COLUMN_NAMES = {
			NAME,
			MAIN_PASSWORD
	};
	
	private final static String createSql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
			+ "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+  NAME + " CHAR(50) NOT NULL, "
			+  MAIN_PASSWORD + " INTEGER, "
			+ "FOREIGN KEY(" + MAIN_PASSWORD + ") REFERENCES " + PasswordTable.getTableName() + " (" + PasswordTable.getId() + "))";

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

	public static String getName() {
		return NAME;
	}

	public static String getMainPassword() {
		return MAIN_PASSWORD;
	}

	@Override
	protected String getCreateSql() {
		return createSql;
	}
}
