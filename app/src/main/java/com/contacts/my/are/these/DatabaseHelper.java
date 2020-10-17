package com.contacts.my.are.these;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(@Nullable Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Constants.TABLE_NAME);
        onCreate(db);
    }

    public long insertInfo (String name, String mobilePhone, String company, String image, String addTimestamp, String updateTimestamp){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.C_NAME, name);
        contentValues.put(Constants.C_PHONE, mobilePhone);
        contentValues.put(Constants.C_COMPANY, company);
        contentValues.put(Constants.C_IMAGE, image);
        contentValues.put(Constants.C_ADD_TIMESTAMP, addTimestamp);
        contentValues.put(Constants.C_UPDATE_TIMESTAMP, updateTimestamp);
        long id = database.insert(Constants.TABLE_NAME, null, contentValues);
        database.close();
        return id;
    }
}
