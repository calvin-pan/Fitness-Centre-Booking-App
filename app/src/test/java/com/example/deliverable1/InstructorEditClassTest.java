package com.example.deliverable1;

import static org.junit.Assert.*;
import org.junit.Test;

public class InstructorEditClassTest {
    @Test
    public void testCheckConflict1() {
        // same times
        assertEquals(1, InstructorEditClass.checkConflict(1100,50,1100,50));
    }

    @Test
    public void testCheckConflict2() {
        // first entirely before second
        assertEquals(0, InstructorEditClass.checkConflict(1100,50,1300,50));
    }

    @Test
    public void testCheckConflict3() {
        // second entirely before first
        assertEquals(0, InstructorEditClass.checkConflict(1100,50,800,200));
    }

    @Test
    public void testCheckConflict4() {
        // first start before second start, second start before first end
        assertEquals(1, InstructorEditClass.checkConflict(1150,150,1250,100));
    }

    @Test
    public void testCheckConflict5() {
        // second start before first start, first start before second end
        assertEquals(1, InstructorEditClass.checkConflict(1100,150,900,250));
    }

    @Test
    public void testCheckConflict6() {
        // first start before second start, first end after second end
        assertEquals(1, InstructorEditClass.checkConflict(1100,300,1200,50));
    }

    @Test
    public void testCheckConflict7() {
        // second start before first start, first end before second end
        assertEquals(1, InstructorEditClass.checkConflict(1100,50,1000,250));
    }

    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main("InstructorEditClassTest");
    }
}
