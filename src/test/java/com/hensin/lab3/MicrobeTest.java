package com.hensin.lab3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MicrobeTest {
    
    @Test
    public void testCountBacteria0() {
        Microbe ops = new Microbe(0);
        assertEquals(1, ops.countBacteria());
    }
    
    @Test
    public void testCountBacteria1() {
        Microbe ops = new Microbe(1);
        assertEquals(2, ops.countBacteria());
    }
    
    @Test
    public void testCountBacteria2() {
        Microbe ops = new Microbe(2);
        assertEquals(4, ops.countBacteria());
    }
    
    @Test
    public void testCountBacteria3() {
        Microbe ops = new Microbe(3);
        assertEquals(8, ops.countBacteria());
    }
    
    @Test
    public void testCountBacteriaSeven() {
        Microbe ops = new Microbe(7);
        assertEquals(128, ops.countBacteria());
    }
    
    @Test
    public void testCountBacteriaFifteen() {
        Microbe ops = new Microbe(15);
        assertEquals(32768, ops.countBacteria());
    }
    
    @Test
    public void testCountBacteriaTen() {
        Microbe ops = new Microbe(30);
        assertEquals(1073741824, ops.countBacteria());
    }
    
    @Test
    public void testGetTime() {
        Microbe ops = new Microbe(12);
        assertEquals(12, ops.getTime());
    }

    @Test
    public void testGetTime1() {
        Microbe ops = new Microbe(30);
        assertEquals(30, ops.getTime());
    }
}