package com.hensin.lab1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class BoxTest {

    @Test
    public void testPr1() {
        Box p = new Box(10);
        assertEquals(20, p.pr1(5, 2));
        assertEquals(10, p.pr1(0, 3));
        assertEquals(25, p.pr1(5, 3));
    }

    @Test
    public void testPr2() {
        Box p = new Box(11);
        assertEquals(13, p.pr2(5, 2));
        assertEquals(11, p.pr2(0, 3));
        assertEquals(12, p.pr2(3, 2));
    }

    @Test
    public void testPr3() {
        Box p = new Box(10);
        assertEquals(12, p.pr3(8));
        assertEquals(10, p.pr3(2));
        assertEquals(11, p.pr3(4));
    }

    @Test
    public void testPr4() {
        Box p = new Box(13);
        assertTrue(p.pr4(5, true));
        assertFalse(p.pr4(15, false));
        assertTrue(p.pr4(15, true));
    }

    @Test
    public void testPr5() {
        Box p = new Box(14);
        assertTrue(p.pr5(5, true));
        assertFalse(p.pr5(5, false));
        assertFalse(p.pr5(15, true));
    }

    @Test
    public void testAs1() {
        Box p = new Box(10);
        assertEquals(18, p.as1(5, 3));
        assertEquals(15, p.as1(2, 3));
        assertEquals(10, p.as1(0, 0));
    }

    @Test
    public void testAs2() {
        Box p = new Box(5);
        assertEquals(2, p.as2(20, 2));
        assertEquals(0, p.as2(10, 3));
        assertEquals(2, p.as2(40, 4));
    }

    @Test
    public void testAs3() {
        Box p = new Box(10);
        assertEquals(0, p.as3(32, 1));
        assertEquals(0, p.as3(20, 2));
        assertEquals(0, p.as3(40, 3));
    }

    @Test
    public void testAs4() {
        Box p = new Box(10);
        assertEquals(15, p.as4(5, 3));
        assertEquals(11, p.as4(1, 2));
        assertEquals(11, p.as4(3, 0));
    }

    @Test
    public void testAs5() {
        Box p = new Box(10);
        assertTrue(p.as5(true, true, true));
        assertFalse(p.as5(true, true, false));

        assertFalse(p.as5(false, true, true));
    }
    
}