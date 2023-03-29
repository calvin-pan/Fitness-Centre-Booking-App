package com.example.deliverable1;

import static org.junit.Assert.*;
import org.junit.Test;

public class MemberHomeActivityTest {

    @Test
    public void testCheckTimes1() {
        // same times
        assertTrue(MemberHomeActivity.checkTimes(1100,50,1100,50));
    }

    @Test
    public void testCheckTimes2() {
        // first entirely before second
        assertFalse(MemberHomeActivity.checkTimes(1100,50,1300,50));
    }

    @Test
    public void testCheckTimes3() {
        // second entirely before first
        assertFalse(MemberHomeActivity.checkTimes(1100,50,800,200));
    }

    @Test
    public void testCheckTimes4() {
        // first start before second start, second start before first end
        assertTrue(MemberHomeActivity.checkTimes(1150,150,1250,100));
    }

    @Test
    public void testCheckTimes5() {
        // second start before first start, first start before second end
        assertTrue(MemberHomeActivity.checkTimes(1100,150,900,250));
    }

    @Test
    public void testCheckTimes6() {
        // first start before second start, first end after second end
        assertTrue(MemberHomeActivity.checkTimes(1100,300,1200,50));
    }

    @Test
    public void testCheckTimes7() {
        // second start before first start, first end before second end
        assertTrue(MemberHomeActivity.checkTimes(1100,50,1000,250));
    }

    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main("MemberHomeActivityTest");
    }
}
