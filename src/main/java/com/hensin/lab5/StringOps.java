package com.hensin.lab5;

import com.hensin.LabException;

public class StringOps {

    public String insertAfter(String x, String y, String z) throws LabException {
        if (x == null) throw new LabException("Строка x = null");
        if (y == null) throw new LabException("Символ y = null");
        if (z == null) throw new LabException("Символ z = null");
        if (y.isEmpty()) throw new LabException("Символ y не может быть пустым");
        if (z.isEmpty()) throw new LabException("Символ z не может быть пустым");
        if (y.length() > 1) throw new LabException("y должен быть одним символом");
        if (z.length() > 1) throw new LabException("z должен быть одним символом");

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