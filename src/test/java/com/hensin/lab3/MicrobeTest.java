package com.hensin.lab3;
import com.hensin.LabException;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MicrobeTest {
    
    @Test
    public void testCountBacteria0() throws LabException {
        Microbe ops = new Microbe(0);
        assertEquals(1, ops.countBacteria());
    }
    
    @Test
    public void testCountBacteria1() throws LabException {
        Microbe ops = new Microbe(1);
        assertEquals(2, ops.countBacteria());
    }
    
    @Test
    public void testCountBacteria2() throws LabException {
        Microbe ops = new Microbe(2);
        assertEquals(4, ops.countBacteria());
    }
    
    @Test
    public void testCountBacteria3() throws LabException {
        Microbe ops = new Microbe(3);
        assertEquals(8, ops.countBacteria());
    }
    
    @Test
    public void testCountBacteriaSeven() throws LabException {
        Microbe ops = new Microbe(7);
        assertEquals(128, ops.countBacteria());
    }
    
    @Test
    public void testCountBacteriaFifteen() throws LabException {
        Microbe ops = new Microbe(15);
        assertEquals(32768, ops.countBacteria());
    }
    
    @Test
    public void testCountBacteriaTen() throws LabException {
        Microbe ops = new Microbe(30);
        assertEquals(1073741824, ops.countBacteria());
    }
    
    @Test
    public void testGetTime() throws LabException {
        Microbe ops = new Microbe(12);
        assertEquals(12, ops.getTime());
    }

    @Test
    public void testGetTime1() throws LabException {
        Microbe ops = new Microbe(30);
        assertEquals(30, ops.getTime());
    }
}