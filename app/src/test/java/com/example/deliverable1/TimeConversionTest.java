package com.example.deliverable1;

import org.junit.Test;
import static org.junit.Assert.*;

public class TimeConversionTest {
    @Test
    public void testTimeConversionOne() {
        assertArrayEquals(new Integer[] {750, 50}, MainActivity.timeConversion("07:30", "0.5 hours"));
    }

    @Test
    public void testTimeConversionTwo() {
        assertArrayEquals(new Integer[] {900, 100}, MainActivity.timeConversion("09:00", "1 hour"));
    }

    @Test
    public void testTimeConversionThree() {
        assertArrayEquals(new Integer[] {1200, 150}, MainActivity.timeConversion("12:00", "1.5 hours"));
    }
    @Test
    public void testTimeConversionFour() {
        assertArrayEquals(new Integer[] {1450, 200}, MainActivity.timeConversion("14:30", "2 hours"));
    }
    @Test
    public void testTimeConversionFive() {
        assertArrayEquals(new Integer[] {1650, 300}, MainActivity.timeConversion("16:30", "3 hours"));
    }

    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main("TimeConversionTest");
    }

}
