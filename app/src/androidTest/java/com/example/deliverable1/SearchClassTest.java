package com.example.deliverable1;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

import junit.framework.TestCase;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;


import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchClassTest {

    @Test
    public void testSearchClass() {

        //Add class into database
        List<HashMap<String, String>> listClasses = new ArrayList<>();
        ContentValues values = new ContentValues();
        values.put("className", "Math");
        values.put("classDesc", "mathematics");
        ClassDatabase classDatabase = new ClassDatabase(getApplicationContext());
        SQLiteDatabase db = classDatabase.getWritableDatabase();
        long num = db.insert("classes", null, values); // returns -1 if conflict
        HashMap<String, String> list = new HashMap<>();
        if (num != -1) {
            list.put("Math", "mathematics");

            HashMap<String, String> resultsMap = new HashMap<>();
            resultsMap.put("Class", "Math");
            resultsMap.put("Description", "mathematics");
            listClasses.add(resultsMap);
        }

        //Assigning Instructor and preferences to the class
        String username = "RandomInstructor";
        String classType = "Math";
        String day = "Monday";
        String hours = "3";
        String difficulty = "Beginner";
        String cap = "25";
        String time = "07:00";
        ContentValues content = new ContentValues();
        content.put("instructorName", username);
        content.put("classType", classType);
        content.put("classDays", day);
        content.put("classHours", hours);
        content.put("classDiff", difficulty);
        content.put("classCap", cap);
        content.put("startTime", time);
        num = db.insert("instructorClasses", null, content);
        ArrayList<HashMap<String, String>> arrayHashes = new ArrayList<HashMap<String, String>>();
        if (num != -1) {
            HashMap<String, String> resultsMap = new HashMap<String, String>();
            resultsMap.put("classType", classType);
            resultsMap.put("instructorName", username);
            resultsMap.put("classDays", day);
            resultsMap.put("classHours", hours);
            resultsMap.put("classDiff", difficulty);
            resultsMap.put("classCap", cap);
            resultsMap.put("startTime", time);
            arrayHashes.add(resultsMap);
        }

        //Searching for the class in the database
        String cType = "Math";
        String iName = "RandomInstructor";
        String dayy = "Monday";
        ArrayList<String> items = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from instructorClasses WHERE instructorName = ? AND classType = ? AND classDays = ?", new String[] {iName, cType, dayy});
        cursor.moveToFirst(); // if this fails, we have an issue where our click isn't corresponding correctly to the arrayHashes array
        for (int i = 1; i < 8; i++) {
            items.add(cursor.getString(i));
        }
        cursor.close();

        //Checking if the input names match the ones from the database
        assertEquals("RandomInstructor",items.get(0));
        assertEquals("Math",items.get(1));
        assertEquals("Monday",items.get(2));
        assertEquals("3",items.get(3));
        assertEquals("Beginner",items.get(4));
        assertEquals("25",items.get(5));
        assertEquals("07:00",items.get(6));

        int num1 = db.delete("classes", "className = ? AND classDesc = ?", new String[] {"Math", "Mathematics"});
        int num2 = db.delete("instructorClasses", "instructorName = ? AND classType = ? AND classDays = ?", new String[] {username, classType, day});
        System.out.println(num2);
    }

    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main("SearchClassTest");
    }
}