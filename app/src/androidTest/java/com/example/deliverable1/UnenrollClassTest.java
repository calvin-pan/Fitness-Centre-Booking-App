package com.example.deliverable1;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class UnenrollClassTest {

    private ClassDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = new ClassDatabase(context);
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void unenrollClassTest(){
        //Test writing to enrollment table in classDatabase and conflict detection (checkEnrollment() function).
        //context = ApplicationProvider.getApplicationContext();
        SQLiteDatabase sq = db.getWritableDatabase();

        ContentValues content = new ContentValues();

        content.put("username", "Nelson");
        content.put("instructorName", "Stephen");
        content.put("classType", "Marathon Training");
        content.put("classDays", "Wednesday");
        content.put("classHours", "1 hour");
        content.put("classDiff", "Beginner");
        content.put("classCap", "30");
        content.put("startTime", "11:30");

        sq.insert("enrollment", null, content);

        boolean enrolled = db.enrollExists(content);

        assertTrue(enrolled);

        sq.delete("enrollment", "username = ? AND classType = ? AND instructorName = ? AND classDays = ?",
                new String[] {"Nelson", "Marathon Training", "Stephen", "Wednesday"});

        enrolled = db.enrollExists(content);

        assertFalse(enrolled);
    }
}
