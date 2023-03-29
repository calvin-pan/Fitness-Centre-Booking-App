package com.example.deliverable1;

import junit.framework.TestCase;

import static org.junit.Assert.*;
import org.junit.Test;

public class MainActivityTest {

    @Test
    public void testgetdecDay() {
        assertArrayEquals(new String[] {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"}, MainActivity.getdecDay());
    }

    @Test
    public void testgetdDuration() {
        assertArrayEquals(new String[] {"0.5 hours", "1 hour", "1.5 hours", "2 hours", "2.5 hours", "3 hours"}, MainActivity.getdDuration());
    }

    @Test
    public void testgetdDifficulty() {
        assertArrayEquals(new String[] {"Beginner", "Intermediate", "Advanced"}, MainActivity.getdDifficulty());
    }

    @Test
    public void testgetdTime() {
        assertArrayEquals(new String[] {"07:00", "07:30", "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00",
                "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00"}, MainActivity.getdTime());
    }

    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main("MainActivityTest");
    }
}
