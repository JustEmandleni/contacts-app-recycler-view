package com.contacts.my.are.these;

public class Constants {
    public static final String DATABASE_NAME = "MYPETJOURNAL_DB";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "PET_EVENT_TABLE";

    public static final String C_ID = "ID";
    public static final String C_DATE = "DATE";
    public static final String C_ENTRY_TYPE = "ENTRY_TYPE";
    public static final String C_DESCRIPTION = "DESCRIPTION";
    public static final String C_IMAGE = "IMAGE";
    public static final String C_ADD_TIMESTAMP = "ADD_TIMESTAMP";
    public static final String C_UPDATE_TIMESTAMP = "UPDATE_TIMESTAMP";

    public static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("
            + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + C_DATE + " TEXT,"
            + C_ENTRY_TYPE + " TEXT,"
            + C_DESCRIPTION + " TEXT,"
            + C_IMAGE + " TEXT,"
            + C_ADD_TIMESTAMP + " TEXT,"
            + C_UPDATE_TIMESTAMP + " TEXT"
            + ");";

}
