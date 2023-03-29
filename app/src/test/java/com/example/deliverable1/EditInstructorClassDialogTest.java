package com.example.deliverable1;

import junit.framework.TestCase;

import static org.junit.Assert.*;
import org.junit.Test;

public class EditInstructorClassDialogTest {

    EditInstructorClassDialog edit1;
    EditInstructorClassDialog edit2;
    EditInstructorClassDialog edit3;
    EditInstructorClassDialog edit4;

    public void setUp() {
        edit1 = new EditInstructorClassDialog("Jackson", "Yoga", "Wednesday", "2.5 hours", "Intermediate", "32" ,"16:00");
        edit2 = new EditInstructorClassDialog("Jorge", "Cycling", "Saturday", "1 hour", "Beginner", "25" ,"12:00");
        edit3 = new EditInstructorClassDialog("Red", "Weightlifting", "Monday", "0.5 hours", "Advanced", "150" ,"07:00");
        edit4 = new EditInstructorClassDialog("Bryson", "Tilling", "Sunday", "3 hours", "Advanced", "150" ,"18:00");
    }

    @Test
    public void testdecodenDay() {
        setUp();

        //tests decode() for nDay
        edit1.decode();
        edit2.decode();
        edit3.decode();
        edit4.decode();

        assertEquals(2, edit1.getnDay());
        assertEquals(5, edit2.getnDay());
        assertEquals(0, edit3.getnDay());
        assertEquals(6, edit4.getnDay());
    }

    @Test
    public void testdecodenDuration() {
        setUp();

        //tests decode() for nDuration
        edit1.decode();
        edit2.decode();
        edit3.decode();
        edit4.decode();

        assertEquals(4, edit1.getnDuration());
        assertEquals(1, edit2.getnDuration());
        assertEquals(0, edit3.getnDuration());
        assertEquals(5, edit4.getnDuration());
    }

    @Test
    public void testdecodenDifficulty() {
        setUp();

        //tests decode() for nDifficulty
        edit1.decode();
        edit2.decode();
        edit3.decode();
        edit4.decode();

        assertEquals(1, edit1.getnDifficulty());
        assertEquals(0, edit2.getnDifficulty());
        assertEquals(2, edit3.getnDifficulty());
        assertEquals(2, edit4.getnDifficulty());
    }

    @Test
    public void testdecodenTime() {
        setUp();

        //tests decode() for nTime
        edit1.decode();
        edit2.decode();
        edit3.decode();
        edit4.decode();

        assertEquals(18, edit1.getnTime());
        assertEquals(10, edit2.getnTime());
        assertEquals(0, edit3.getnTime());
        assertEquals(22, edit4.getnTime());
    }

    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main("EditInstructorClassDialogTest");
    }
}
