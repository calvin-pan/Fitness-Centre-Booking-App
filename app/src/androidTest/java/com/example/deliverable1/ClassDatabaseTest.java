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
public class ClassDatabaseTest {

    private ClassDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = new ClassDatabase(context);
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void writeClassandRead() {
        // tests writing to the database and conflict detection (itemExists() method)
        SQLiteDatabase write = db.getWritableDatabase();

        ContentValues content = new ContentValues();

        content.put("instructorName", "George");
        content.put("classType", "Yoga");
        content.put("classDays", "Wednesday");
        content.put("classHours", "2 hours");
        content.put("classDiff", "Advanced");
        content.put("classCap", "54");
        content.put("startTime", "11:00");

        write.insert("instructorClasses", null, content);

        String user = db.itemExists("Yoga", "Wednesday");
        assertEquals("George", user);

        write.delete("instructorClasses", "instructorName = ? AND classType = ? AND classDays = ? AND startTime = ?",
                new String[] {content.getAsString("instructorName"), content.getAsString("classType"), content.getAsString("classDays"), content.getAsString("startTime")});
    }

    @Test
    public void testCheckFullClass1() {

        SQLiteDatabase sq = db.getWritableDatabase();

        ContentValues content1 = new ContentValues();

        content1.put("username", "John");
        content1.put("instructorName", "Jacob");
        content1.put("classType", "Driving");
        content1.put("classDays", "Wednesday");
        content1.put("classHours", "2 hours");
        content1.put("classDiff", "Advanced");
        content1.put("classCap", "1");
        content1.put("startTime", "11:00");

        sq.insert("enrollment", null, content1);

        assertTrue(db.checkFullClass("Driving", "Wednesday"));

        sq.delete("enrollment", "username = ? AND instructorName = ?", new String[] {"John", "Jacob"});
    }
    @Test
    public void testCheckFullClass2() {

        SQLiteDatabase sq = db.getWritableDatabase();

        ContentValues content2 = new ContentValues();

        content2.put("username", "Jackson");
        content2.put("instructorName", "Regis");
        content2.put("classType", "Bike");
        content2.put("classDays", "Monday");
        content2.put("classHours", "2 hours");
        content2.put("classDiff", "Advanced");
        content2.put("classCap", "0");
        content2.put("startTime", "11:00");

        sq.insert("enrollment", null, content2);

        assertTrue(db.checkFullClass("Bike", "Monday"));

        sq.delete("enrollment", "username = ? AND instructorName = ?", new String[] {"Jackson", "Regis"});
    }

    @Test
    public void testCheckFullClass3() {

        SQLiteDatabase sq = db.getWritableDatabase();

        ContentValues content3 = new ContentValues();

        content3.put("username", "Jordan");
        content3.put("instructorName", "Michaels");
        content3.put("classType", "Bike");
        content3.put("classDays", "Sunday");
        content3.put("classHours", "2 hours");
        content3.put("classDiff", "Advanced");
        content3.put("classCap", "2");
        content3.put("startTime", "11:00");

        sq.insert("enrollment", null, content3);

        assertFalse(db.checkFullClass("Bike", "Sunday"));

        sq.delete("enrollment", "username = ? AND instructorName = ?", new String[] {"Jordan", "Michaels"});
    }


    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main("ClassDatabaseTest");
    }
}
