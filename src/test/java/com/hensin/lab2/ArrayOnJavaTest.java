package com.hensin.lab2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayOnJavaTest {
    
    @Test
    public void testcountNum1() {
        ArrayOnJava ops = new ArrayOnJava(new int[]{3, 6, 2, 9, 12, 15});
        assertEquals(5, ops.countNum());
    }
    
    @Test
    public void testcountNum2() {
        ArrayOnJava ops = new ArrayOnJava(new int[]{1, 2, 4, 5, 7, 8, 10});
        assertEquals(0, ops.countNum());
    }
    
    @Test
    public void testcountNum3() {
        ArrayOnJava ops = new ArrayOnJava(new int[]{-3, -6, 9, -1, -2});
        assertEquals(3, ops.countNum());
    }
    
    @Test
    public void testcountNum4() {
        ArrayOnJava ops = new ArrayOnJava(new int[]{});
        assertEquals(0, ops.countNum());
    }

    @Test
    public void testSortArray1() {
        ArrayOnJava ops = new ArrayOnJava(new int[]{5, 2, 8, 1, 9});
        ops.sortArray();
        assertArrayEquals(new int[]{1, 2, 5, 8, 9}, ops.getArray());
    }
    
    @Test
    public void testSortArray2() {
        ArrayOnJava ops = new ArrayOnJava(new int[]{-3, 5, -1, 0, 2});
        ops.sortArray();
        assertArrayEquals(new int[]{-3, -1, 0, 2, 5}, ops.getArray());
    }
    
    @Test
    public void testSortArray3() {
        ArrayOnJava ops = new ArrayOnJava(new int[]{5});
        ops.sortArray();
        assertArrayEquals(new int[]{5}, ops.getArray());
    }
    
    @Test
    public void testSortArray4() {
        ArrayOnJava ops = new ArrayOnJava(new int[]{});
        ops.sortArray();
        assertArrayEquals(new int[]{}, ops.getArray());
    }
    
    @Test
    public void testConstructor() {
        int[] arr = {1, 2, 3, 4, 5};
        ArrayOnJava ops = new ArrayOnJava(arr);
        assertEquals(5, ops.getLength());
    }
    
    @Test
    public void testGetArray() {
        int[] arr = {1, 2, 3};
        ArrayOnJava ops = new ArrayOnJava(arr);
        assertArrayEquals(new int[]{1, 2, 3}, ops.getArray());
    }
}