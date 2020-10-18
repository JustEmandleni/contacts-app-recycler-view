package com.contacts.my.are.these;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

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

    public ArrayList<Model> getData (String orderBy){
        ArrayList<Model> contactList = new ArrayList<>();

        String sqlSelectAllQuery = "SELECT * FROM " + Constants.TABLE_NAME + " ORDER BY " + orderBy; //missing ; ?
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sqlSelectAllQuery, null);

        if(cursor.moveToNext()){
            do {
                Model model = new Model(
                        "" + cursor.getInt(cursor.getColumnIndex(Constants.C_ID)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_IMAGE)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_NAME)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_PHONE)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_COMPANY)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_ADD_TIMESTAMP)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_UPDATE_TIMESTAMP))
                );
                contactList.add(model);
            }
            while (cursor.moveToNext());
        }

        return contactList;
    }
}
