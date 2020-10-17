package com.contacts.my.are.these;

public class Constants {
    public static final String DATABASE_NAME = "PERSON_INFO_DB";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "PERSON_INFO_TABLE";

    public static final String C_ID = "ID";
    public static final String C_NAME = "NAME";
    public static final String C_PHONE = "PHONE";
    public static final String C_COMPANY = "COMPANY";
    public static final String C_IMAGE = "IMAGE";
    public static final String C_ADD_TIMESTAMP = "ADD_TIMESTAMP";
    public static final String C_UPDATE_TIMESTAMP = "UPDATE_TIMESTAMP";

    public static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("
            + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + C_NAME + " TEXT,"
            + C_PHONE + " TEXT,"
            + C_COMPANY + " TEXT,"
            + C_IMAGE + " TEXT,"
            + C_ADD_TIMESTAMP + " TEXT,"
            + C_UPDATE_TIMESTAMP + " TEXT"
            + ");";

}
