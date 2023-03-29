package com.example.deliverable1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class InstructorDatabase extends SQLiteOpenHelper {
    private static final String TAG = "InstructorDatabase.db";
    public static final String TABLE_NAME = "InstructorDatabase";

    public InstructorDatabase(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table if not exists instructors(username TEXT primary key, password TEXT not null)");

        ContentValues instructorAccount= new ContentValues();

        instructorAccount.put("username", "Jared");
        instructorAccount.put("password", "geff");

        db.insert("instructors", null, instructorAccount);

        ContentValues instructorAccount2= new ContentValues();

        instructorAccount2.put("username", "Sasha");
        instructorAccount2.put("password", "kelm");


        db.insert("instructors", null, instructorAccount2);
    }

    public void onUpgrade(SQLiteDatabase db, int i, int j) {
        db.execSQL("drop Table if exists instructors");
    }

    public Boolean checkAdmin(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from instructors where username = ? and password = ?", new String[] {username, password});
        return cursor.getCount() > 0;
    }


}
