package com.example.deliverable1;

import junit.framework.TestCase;

import static org.junit.Assert.*;
import org.junit.Test;

public class ViewClassDialogTest {

    @Test
    public void testcreateClassTime() {
        //charHold[2] == 'h'
        assertEquals("10:00 - 12:00", ViewClassDialog.createClassTime("10:00", "2 hours"));
        //charHold[2] != 'h'
        assertEquals("11:30 - 14:00", ViewClassDialog.createClassTime("11:30", "2.5 hours"));
        //endConvert.length == 3
        assertEquals("08:00 - 09:00", ViewClassDialog.createClassTime("08:00", "1 hours"));
        //endChar[2] == '6'
        assertEquals("14:30 - 16:00", ViewClassDialog.createClassTime("14:30", "1.5 hours"));
        //endChar[2] == '6' then endConvert.length == 3
        assertEquals("07:30 - 09:00", ViewClassDialog.createClassTime("07:30", "1.5 hours"));
    }

    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main("ViewClassDialogTest");
    }
}
