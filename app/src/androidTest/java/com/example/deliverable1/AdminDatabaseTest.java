package com.example.deliverable1;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;


import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.core.app.ApplicationProvider;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

@RunWith(AndroidJUnit4.class)
public class AdminDatabaseTest {

    private AdminDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = new AdminDatabase(context);
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void checkAdminTest() {
        SQLiteDatabase write = db.getWritableDatabase();

        ContentValues content = new ContentValues();

        write.insert("admins", null, content);

        boolean exists = db.checkAdmin("admin", "admin123");
        assertTrue(exists);
        boolean exists2 = db.checkAdmin("Mando", "Yoda");
        assertFalse(exists2);
    }

    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main("AdminDatabaseTest");
    }
}
