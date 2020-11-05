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

    public void insertInfo (String date, String entryType, String description, String image, String addTimestamp, String updateTimestamp){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.C_DATE, date);
        contentValues.put(Constants.C_ENTRY_TYPE, entryType);
        contentValues.put(Constants.C_DESCRIPTION, description);
        contentValues.put(Constants.C_IMAGE, image);
        contentValues.put(Constants.C_ADD_TIMESTAMP, addTimestamp);
        contentValues.put(Constants.C_UPDATE_TIMESTAMP, updateTimestamp);
        //long id =
        database.insert(Constants.TABLE_NAME, null, contentValues);
        database.close();
        //return id;
    }

    public void updateInfo (String id, String date, String entryType, String description, String image, String addTimestamp, String updateTimestamp){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.C_DATE, date);
        contentValues.put(Constants.C_ENTRY_TYPE, entryType);
        contentValues.put(Constants.C_DESCRIPTION, description);
        contentValues.put(Constants.C_IMAGE, image);
        contentValues.put(Constants.C_ADD_TIMESTAMP, addTimestamp);
        contentValues.put(Constants.C_UPDATE_TIMESTAMP, updateTimestamp);

        //long id = database.insert(Constants.TABLE_NAME, null, contentValues);
        database.update(Constants.TABLE_NAME, contentValues, Constants.C_ID+ " = ?", new String[]{id});
        database.close();
    }

    public ArrayList<Model> getData (String orderBy){
        ArrayList<Model> petEventList = new ArrayList<>();

        String sqlSelectAllQuery = "SELECT * FROM " + Constants.TABLE_NAME + " ORDER BY " + orderBy; //am i missing a closing ';' ?
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sqlSelectAllQuery, null);

        if(cursor.moveToNext()){
            do {
                Model model = new Model(
                        "" + cursor.getInt(cursor.getColumnIndex(Constants.C_ID)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_IMAGE)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_DATE)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_ENTRY_TYPE)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_DESCRIPTION)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_ADD_TIMESTAMP)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_UPDATE_TIMESTAMP))
                );
                petEventList.add(model);
            }
            while (cursor.moveToNext());
        }

        return petEventList;
    }
}
