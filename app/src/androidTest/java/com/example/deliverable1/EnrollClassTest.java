package com.example.deliverable1;


import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Test;
import static org.junit.Assert.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import java.io.IOException;

@RunWith(AndroidJUnit4.class)
public class EnrollClassTest {

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
    public void enrollClassTest(){
        //Test writing to enrollment table in classDatabase and conflict detection (checkEnrollment() function).
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

        sq.delete("enrollment", "username = ? AND classType = ? AND instructorName = ? AND classDays = ?"
                , new String[] {"Nelson", "Marathon Training", "Stephen", "Wednesday"});
    }

}
