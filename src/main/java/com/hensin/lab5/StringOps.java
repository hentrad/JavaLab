package com.hensin.lab5;

import com.hensin.LabException;
import com.hensin.LabException.Code;

public class StringOps {

    public String insertAfter(String x, String y, String z) throws LabException {
        if (x == null) throw new LabException(Code.STRING_NULL, "x");
        if (y == null) throw new LabException(Code.SYMBOL_NULL, "y");
        if (z == null) throw new LabException(Code.SYMBOL_NULL, "z");
        if (y.isEmpty()) throw new LabException(Code.SYMBOL_EMPTY, "y");
        if (z.isEmpty()) throw new LabException(Code.SYMBOL_EMPTY, "z");
        if (y.length() > 1) throw new LabException(Code.SYMBOL_MULTIPLE_CHARS, "y");
        if (z.length() > 1) throw new LabException(Code.SYMBOL_MULTIPLE_CHARS, "z");


        char needle = y.charAt(0);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < x.length(); i++) {
            char c = x.charAt(i);
            sb.append(c);
            if (c == needle) {
                sb.append(z);
            }
        }
        return sb.toString();
    }
}