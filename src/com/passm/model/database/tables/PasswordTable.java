package com.passm.model.database.tables;

public class PasswordTable extends DatabaseTable {

	private final static String TABLE_NAME = "PASSWORD";
	
	private final static String ID = "ID";
	
	private final static String NAME = "NAME";
	
	private final static String DESCRIPTION = "DESCRIPTION";
	
	private final static String PASSWORD_HASH = "PASSWORD_HASH";
	
	private final static String[] COLUMN_NAMES = {
			PASSWORD_HASH,
			NAME,
			DESCRIPTION
	};
	
	private final static String createSql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
			+ " ( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ NAME + " CHAR(50) NOT NULL, "
			+ DESCRIPTION + " CHAR(100), "
			+ PASSWORD_HASH + " TEXT NOT NULL)";

	@Override
	protected String[] getColumnNames() {
		return COLUMN_NAMES;
	}
	
	@Override
	public String getTable_name() {
		return getTableName();
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

	public static String getDescription() {
		return DESCRIPTION;
	}

	public static String getPasswordHash() {
		return PASSWORD_HASH;
	}

	@Override
	protected String getCreateSql() {
		return createSql;
	}
}
