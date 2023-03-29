package com.example.deliverable1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MemberDatabase extends SQLiteOpenHelper {
    private static final String TAG = "MemberDatabase.db";
    public static final String TABLE_NAME = "MemberDatabase";

    public MemberDatabase(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists members(memberID INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT not null)");

        ContentValues memberAccount= new ContentValues();

        memberAccount.put("username", "James");
        memberAccount.put("password", "Smith");

        db.insert("members", null, memberAccount);

        ContentValues memberAccount2 = new ContentValues();

        memberAccount2.put("username", "Joan");
        memberAccount2.put("password", "Myth");

        db.insert("members", null, memberAccount2);

        ContentValues memberAccount3 = new ContentValues();

        memberAccount3.put("username", "Jack");
        memberAccount3.put("password", "Jack");

        db.insert("members", null, memberAccount3);
    }

    public void onUpgrade(SQLiteDatabase db, int i, int j) {
        db.execSQL("drop Table if exists members");

        db.execSQL("drop table if exists enrollment");
    }

    public Boolean checkMember(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from members where username = ? and password = ?", new String[] {username, password});
        return cursor.getCount() > 0;
    }
}
