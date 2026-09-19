package com.hensin.lab5;
import com.hensin.LabException;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StringOpsTest extends MainStringOps {

    private final StringOps ops = new StringOps();

    @Test
    public void testExample() throws LabException {
        assertEquals("Tesftsf", ops.insertAfter("Tests", "s", "f"));
    }

    @Test
    public void testNoMatch() throws LabException {
        assertEquals("abc", ops.insertAfter("abc", "z", "q"));
    }

    @Test
    public void testMultipleOccurrences() throws LabException {
        assertEquals("aXaXaX", ops.insertAfter("aaa", "a", "X"));
    }

    @Test
    public void testInsertAtEnd() throws LabException {
        assertEquals("hello!", ops.insertAfter("hello", "o", "!"));
    }

    @Test
    public void testEmptyString() throws LabException {
        assertEquals("", ops.insertAfter("", "a", "b"));
    }

    @Test
    public void testEmptyY() throws LabException {
        assertEquals("abc", ops.insertAfter("abc", "", "X"));
    }
}