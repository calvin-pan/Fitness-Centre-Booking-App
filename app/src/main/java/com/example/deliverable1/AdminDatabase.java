package com.example.deliverable1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminDatabase extends SQLiteOpenHelper {
    private static final String TAG = "AdminDatabase.db";
    public static final String TABLE_NAME = "AdminDatabase";

    public AdminDatabase(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists admins(username TEXT primary key, password TEXT not null)");

        ContentValues adminAccount= new ContentValues();

        adminAccount.put("username", "admin");
        adminAccount.put("password", "admin123");

        db.insert("admins", null, adminAccount);
    }

    public void onUpgrade(SQLiteDatabase db, int i, int j) {
        db.execSQL("drop Table if exists admins");
    }

    public Boolean checkAdmin(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from admins where username = ? and password = ?", new String[] {username, password});
        return cursor.getCount() > 0;
    }


}
